//准备随机验证码用于调用
function codeConfirm() {
    var generConfirmCode = Math.round(Math.random() * 8999 + 1000);
    $("#randomCode").val(generConfirmCode);
}

// 页面加载有两种方式表示1. $(document).ready();2. $(); 这种比较常用图片加载用load()函数dblclick() 表示双击
// 注: 空白键和回车键也可以造成click事件，但是只有双击鼠标才能造成dblclick事件
//html文档加载完成后再执行
$(codeConfirm());

// 客户上传头像缩略图实时展现
$(function () {
    $("#userPhoto").on("change", function () {
        //this表示触发事件的组件，可以在调用函数的时候，作为参数传进去
        var file = this.files[0];
        if (this.files && file) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $("#usersPhoto").attr("src", e.target.result);
            };
            reader.readAsDataURL(file);
        }
    });
});

$(function () {
    $("#accountName").on("change", function () {
        var name = $(this).val();
        $.ajax({
            url: "Registerable",
            type: "get",
            async: false,
            cache: false,
            data: {"name": name},
            success: function (data) {
                //服务器返回了响应,不管是否重复账号名,都到此
                // document.getElementById("checkResult").innerHTML = data;
                $("#checkResult").html(data);
                // $("#checkResult").innerHTML =data ;错误用法
            },
            error: function (error) {
                //只有服务器发生故障才能到此
                // document.getElementById("checkResult").innerHTML = "<b style='color:red'>请稍后再试!</b>";
                $("#checkResult").html("<b style='color:red'>请稍后再试!</b>");
            }
        });
    });
});

//密码验证https://www.cnblogs.com/GaiaBing/p/9341857.html
//todo:很多时候我们提交表单并不是用的form形式，而是ajax，这时候就不能用submit来触发了。
function loginValidForm() {
    return $("#registerAndLogin").validate({
        rules: {
            accountName: "required",
            password: "required",
            confirmCode: {
                required: true,
                equalTo: "#randomCode"
                // equalTo:"#randomCode"错误，注意使用var()还是html()，equalTo比较的是2个输入框的值
            }
        },
        messages: {
            accountName: "请输入用户名",
            password: "请输入密码",
            confirmCode: "请输入验证码"
        }
    });
}


//不是onclick
$(function () {
    $("#login").click(function () {
        if (loginValidForm().form()) {
            var name = $("#accountName").val();
            var pwd = $("#password").val();
            $.ajax({
                url: "Login",
                type: "post",
                async: false,//同步：意思是当有返回值以后才会进行后面的js程序。
                cache: false,
                data: {"name": name, "password": pwd},
                success: function (data) {
                    alert(data);
                    $("#logResult").html(data);
                    if (data === "登陆成功，2秒后跳转") {//根据返回值进行跳转
                        //todo：睡2秒钟,且跳转不成功？？？
                        window.location.href = 'hello.html';
                    } else {
                        window.location.href = 'login.html';
                    }
                },
                error: function (err) {
                    $("#logResult").html(err);
                    window.location.href = 'login.html';
                }
            });
        } else {
            alert("登录失败");
            codeConfirm()
        }
    });
});


function registerValidForm() {
    return $("#registerAndLogin").validate({
        rules: {
            //此处关键字用html界面的name的值
            accountName: "required",
            password: "required",
            confirmCode: {
                required: true,
                equalTo: "#randomCode"
            },
            password_again: {
                required: true,
                equalTo: "#password"
            },
            filepath: "required"
        },
        messages: {
            accountName: "请设置用户名",
            password: "请设置密码",
            password_again: "请再次输入上面的密码",
            confirmCode: "请输入验证码",
            filepath: "请上传头像"
        }
    });
}

//todo:bug:先点击登录假装要登录，然后输入昵称密码和验证码再点击注册可以注册成功，二次密码和头像没有输入照样通过

//不是onclick
$(function () {
    $("#register").click(function () {
        if (registerValidForm().form()) {
            var name = $("#accountName").val();
            var pwd = $("#password").val();

            // var formData = new FormData($("#registerAndLogin").get(0));
            var formData = new FormData(document.getElementById("registerAndLogin"));
            formData.append("accountName", name);
            formData.append("password", pwd);
            $.ajax({
                url: "Register",
                type: "post",
                data: formData,

                //需将异步关闭，也即设置为同步，也即前面的执行有了结果才能执行后面的，将async设置为true也即异步的话，
                // 前面的还没看到结果后面的就执行了，导致看不到注册是否成功字样
                async: false,
                processData: false,
                contentType: false,
                cache: false,
                success: function (data) {
                    $("#registerResult").html(data);
                    if (data === "注册成功") {
                        //todo:跳转不成功，原因未知
                        window.location.href = 'hello.html';
                    } else {
                        window.location.href = 'login.html';
                    }
                },
                error: function () {
                    $("#registerResult").html("请稍后再试!");
                }
            });
        } else {
            alert("注册失败");
            codeConfirm()
        }
    });
});

$(function () {
    $("#hideAndShow").click(function () {
        // $("#hideAndShow").hover(function () {
        // $("#registerAndLogin").toggleClass("show");
        $(".hitme").toggle();
//         注： 一般不要使用[class=className] 而应该使用.className
// 因为使用$("[class='className']") .toggleClass("anotherClassName")
// 会导致class变成className anotherClassName,再次 使用 [class=className] 就无法选中了
// 而.className没有这个问题。
        //[class='hitme ppp']只能匹配到'hitme ppp'(因为是绝对等于),而.hitme可以匹配hitme以及hitme ppp(只要包含hitme的class都能匹配到)
        // $("button[class='hitme']").toggleClass("ppp");
        // $("button.hitme").val("ooooooo");
        // $("button[class='hitme ppp']").val("ooooooo");
        // $("button.ppp").val("popopopo");
        // 在监听函数中使用 $(this)，即表示触发该事件的组件。
        // $("#registerAndLogin").toggleClass("visible-xs-block");
        // if ($("#registerAndLogin").css("display")=="none") {
        //     $("#registerAndLogin").show();
        // } else {
        //     $("#registerAndLogin").hide();
        // }
    });
});
