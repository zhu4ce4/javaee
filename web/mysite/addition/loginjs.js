//准备随机验证码用于调用
function codeConfirm() {
    var generConfirmCode = Math.round(Math.random() * 8999 + 1000);
    // var generConfirmCode = "ok"
    $("#randomCode").val(generConfirmCode);
    // $("#randomCode").html(generConfirmCode);
}

//页面准备完成后调用上述函数,注意此处可以不用括号
$(document).ready(codeConfirm);

// 客户上传头像缩略图实时展现
$(function () {
    $("#userPhoto").on("change", function () {
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
    $("#accountName").on("keyup", function () {
        var name = $("#accountName").val();
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

// $(registerValidForm());

//todo:bug:先点击登录假装要登录，然后输入昵称密码和验证码再点击注册可以注册成功，二次密码和头像没有输入照样通过

// $(function () {
//不是onclick
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
// });

