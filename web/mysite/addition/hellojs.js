//准备随机验证码用于调用
function codeConfirm() {
    var generConfirmCode = Math.round(Math.random() * 8999 + 1000);
    $("#randomCode").val(generConfirmCode);
    // $("#randomCode").html(generConfirmCode);
}

//页面准备完成后调用上述函数,注意此处可以不用括号
$(document).ready(codeConfirm);
// $(document).ready(function codeConfirm() {
//     var generConfirmCode = Math.round(Math.random() * 8999 + 1000);
//     $("#randomCode").val(generConfirmCode);
//     // $("#randomCode").html(generConfirmCode);
// });


function getResult() {
    var first = parseFloat(document.getElementById('first').value); //获取到的是string需要转换!
    var second = parseFloat(document.getElementById('second').value);

    var selected = document.getElementById('mym');
    var index = selected.selectedIndex;
    var option = selected.options[index].value;

    if (isNaN(first) || isNaN(second)) {
        alert("请输入数据后再计算!");
        return;
    }
    if (option === "add") {
        document.getElementById('result').value = first + second;   //此处必须是一个动作!
    } else if (option === "minus") {
        document.getElementById('result').value = first - second;
    } else if (option === "multiply") {
        document.getElementById('result').value = first * second;
    } else if (option === "divided") {
        if (second === 0) {
            alert("除数不能为0，请重新输入");
            return;
        }
        document.getElementById('result').value = first / second;
    }
}

function resetInput() {
    document.getElementById('first').value = '';
    document.getElementById('second').value = '';
    document.getElementById('result').value = '';
}

// var box = document.querySelector('.box');
// var content = document.querySelector('.content');
// var text = document.querySelector('.text');
//
// var textWidth = text.offsetWidth;
// var boxWidth=box.offsetWidth;
//
// window.onload = function checkScrollLeft() {
//     content.innerHTML += content.innerHTML;
//     document.querySelector('.text').classList.add('padding');
//     // 更新
//     textWidth = document.querySelector('.text').offsetWidth;
//     toScrollLeft()
// };
//
// function toScrollLeft() {
//     // for (let i = 0; i <3; i++) {
//     box.scrollLeft++;
//     setTimeout('toScrollLeft()', 40);
//     // }
// }


function compute() {
    var qishimoney = parseFloat(document.getElementById('origin').value);
    var rate = parseFloat(document.getElementById('rate').value);
    var years = parseInt(document.getElementById('years').value);
    var appendmoney = parseFloat(document.getElementById('benjin').value);

    var benjinhe = qishimoney + appendmoney * years;
    document.getElementById('amountOfBenjin').value = benjinhe;

    var benxihe = qishimoney;
    var lixi = 0;
    var lixihe = 0;

    while (years > 0) {
        lixi = (benxihe + appendmoney) * (rate / 100);
        lixihe += lixi;
        benxihe += appendmoney + lixi;
        years--;
    }
    document.getElementById('lixihe').value = lixihe.toFixed(2);
    document.getElementById('benxihe').value = benxihe.toFixed(2);
}

function GetPage() {
    //将$.ajax做成方法并命名为doAjax以便调用，调用时提供参数demand
    this.doAjax = function (demand) {
        $.ajax({
        url: "readyForRen",
        type: "get",
        async: false,
            data: {"demand": demand},
            // success: function (data) {
            //     var datas = $.parseJSON(data);
            //     for (var i = 0; i < datas.length; i++) {
            //         var lp = datas[i];
            //         $("#Person" + i).html(lp.name);
            //         $("#Describe" + i).html(lp.messages);
            //         $("#Hao" + i).html(lp.haoma);
            //     }
            // },
        success: function (data) {
            var datas = $.parseJSON(data);
            //length()会报错！！
            // var length = datas.length();
            var length = datas.length;
            for (var i = 0; i < length; i++) {
                var lp = datas[i];
                $("#Person" + i).html(lp.name);
                $("#Describe" + i).html(lp.messages);
                $("#Hao" + i).html(lp.haoma);
            }
            if (length < 5) {
                for (var j = 0; j < (5 - length); j++) {
                    var newj = j + length;
                    $("#Person" + newj).html("<span>&nbsp</span>");
                    $("#Describe" + newj).html("<span>&nbsp</span>");
                    $("#Hao" + newj).html("<span>&nbsp</span>");
                }
            }
        },
        error: function (err) {
            for (var i = 0; i < 5; i++) {
                $("#Person" + i).html("内容获取失败");
                $("#Describe" + i).html("内容获取失败，请刷新或稍后再试！");
                $("#Hao" + i).html("内容获取失败");
            }
        }
    })
    }
}


//先生成一个page变量备用
var page = new GetPage();
//页面加载完成随机调用取最新5条数据方法
$(document).ready(page.doAjax("newestFive"));

$("#firstPage").on("click", function () {
    page.doAjax("newestFive");
});

$("#lastPage").on("click", function () {
    page.doAjax("lastFive");
});
$("#nextPage").on("click", function () {
    page.doAjax("nextFive");
});
// 通过new Object创建对象有个问题，就是每创建一个对象，都得重新定义属性和函数。这样代码的重用性不好
// 那么，采用另一种方式，通过function设计一种对象。 然后实例化它 。
// 这种思路很像Java里的设计一种类，但是 javascript没有类，只有对象，所以我们叫设计一种对象

// function firstPage() {
//     var myDemand = "newestFive";
//     $.ajax({
//         url: "readyForRen",
//         type: "get",
//         async: false,
//         data: {"demand": myDemand},
//         success: function (data) {
//             var datas = $.parseJSON(data);
//             for (var i = 0; i < datas.length; i++) {
//                 var lp = datas[i];
//                 $("#Person" + i).html(lp.name);
//                 $("#Describe" + i).html(lp.messages);
//                 $("#Hao" + i).html(lp.haoma);
//             }
//         },
//         error: function (err) {
//             for (var i = 0; i < 5; i++) {
//                 $("#Person" + i).html("内容获取失败");
//                 $("#Describe" + i).html("内容获取失败，请刷新或稍后再试！");
//                 $("#Hao" + i).html("内容获取失败");
//             }
//         }
//     })
// }

// $(document).ready(firstPage);
// firstPage("newestFive")有括号，该函数会在页面加载完成后立即执行,不带括号则点击后才会执行
// $(function () {
// $("#firstPage").click(firstPage);
// });


// $(function () {
//     $("#lastPage").click(function () {
//         var myDemand = "lastFive";
//         $.ajax({
//             url: "readyForRen",
//             type: "get",
//             async: false,
//             data: {"demand": myDemand},
//             success: function (data) {
//                 var datas = $.parseJSON(data);
//                 //length()会报错！！
//                 // var length = datas.length();
//                 var length = datas.length;
//                 for (var i = 0; i < length; i++) {
//                     var lp = datas[i];
//                     $("#Person" + i).html(lp.name);
//                     $("#Describe" + i).html(lp.messages);
//                     $("#Hao" + i).html(lp.haoma);
//                 }
//                 if (length < 5) {
//                     for (var j = 0; j < (5 - length); j++) {
//                         var newj = j + length;
//                         $("#Person" + newj).html("<span>&nbsp</span>");
//                         $("#Describe" + newj).html("<span>&nbsp</span>");
//                         $("#Hao" + newj).html("<span>&nbsp</span>");
//                     }
//                 }
//             },
//             error: function (err) {
//                 for (var i = 0; i < 5; i++) {
//                     $("#Person" + i).html("内容获取失败");
//                     $("#Describe" + i).html("内容获取失败，请刷新或稍后再试！");
//                     $("#Hao" + i).html("内容获取失败");
//                 }
//             }
//         })
//     });
// });


// $(function () {
//     $("#nextPage").click(function () {
//         var myDemand = "nextFive";
//         $.ajax({
//             url: "readyForRen",
//             type: "get",
//             async: false,
//             data: {"demand": myDemand},
//             success: function (data) {
//                 var datas = $.parseJSON(data);
//                 var length = datas.length;
//                 for (var i = 0; i < length; i++) {
//                     var lp = datas[i];
//                     $("#Person" + i).html(lp.name);
//                     $("#Describe" + i).html(lp.messages);
//                     $("#Hao" + i).html(lp.haoma);
//                 }
//                 if (length < 5) {
//                     for (var j = 0; j < (5 - length); j++) {
//                         var newj = j + length;
//                         $("#Person" + newj).html("<span>&nbsp</span> ");
//                         $("#Describe" + newj).html("<span>&nbsp</span>");
//                         $("#Hao" + newj).html("<span>&nbsp</span>");
//                     }
//                 }
//             },
//             error: function (err) {
//                 for (var i = 0; i < 5; i++) {
//                     $("#Person" + i).html("内容获取失败");
//                     $("#Describe" + i).html("内容获取失败，请刷新或稍后再试！");
//                     $("#Hao" + i).html("内容获取失败");
//                 }
//             }
//         })
//     });
// });


function maValidForm() {
    return $("#jianyineirong").validate({
        rules: {
            bxzname: "required",
            haoma: "required",
            leavemessage: "required",
            confirmCode: {
                required: true,
                equalTo: "#randomCode"
            }
        },
        messages: {
            bxzname: "请输入被寻人名",
            haoma: "请输入联系方式",
            confirmCode: "请输入验证码",
            leavemessage: "请输入描述"
        }
    });
}

$("#MissMe").click(function () {
    if (maValidForm().form()) {
        var name = $("#bxzname").val();
        var haoma = $("#haoma").val();
        var content = $("#leavemessage").val();
        $.ajax({
            url: "MissMe",
            type: "post",
            async: false,//同步：意思是当有返回值以后才会进行后面的js程序。
            data: {"name": name, "hao": haoma, "content": content},
            success: function (data) {
                $("#missmeresult").html(data);
                alert("成功");
                //todo：睡2秒钟
                window.location.href = 'hello.html';
            },
            error: function (err) {
                alert("失败");
                $("#logResult").html(err);
                window.location.href = 'hello.html';
            }
        });
    } else {
        alert("提交失败");
        codeConfirm()
    }
});


function printDateTime() {
    var intYears, intMonths, intDays, intHours, intMinutes;
    var today;
    today = new Date(); //系统当前时间
    intYears = today.getFullYear(); //得到年份,getFullYear()比getYear()更普适
    intMonths = today.getMonth() + 1; //得到月份，要加1
    intDays = today.getDate(); //得到日期
    if (intDays < 10) {
        intDays = '0' + intDays;
    }
    intHours = today.getHours(); //得到小时
    if (intHours < 10) {
        intHours = '0' + intHours;
    }
    intMinutes = today.getMinutes(); //得到分钟
    if (intMinutes < 10) {
        intMinutes = '0' + intMinutes;
    }
    // var s = today.getSeconds();
    $('#datetimenow').html("当前：" + intYears + '年' + intMonths + '月' + intDays + '日' + intHours + ':' + intMinutes);
}

//todo:printdatetime后面加上括号只会执行一次，但不加括号，要等到1000*60后才执行一次
setTimeout(printDateTime, 0);
setInterval(printDateTime, 1000 * 60);


