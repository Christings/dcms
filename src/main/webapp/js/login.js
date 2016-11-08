$(document).ready(function () {
    document.onkeydown = function (event) {
        e = event ? event : (window.event ? window.event : null);
        if (e.keyCode == 13) {
            login();
        }
    }

    $("#loginBtn").click(function () {
        var inputCode = document.getElementById("input1").value;
        if (null == inputCode || inputCode.length <= 0) {
            showErrorMsg("请输入验证码！");
        } else {
            login();
        }
    });
});

/**
 * 刷新验证码
 */
function createCode() {
    var date = new Date();
    var img = document.getElementById("checkCode");
    img.src = 'code?t=' + date.getTime();
}

/**
 * 显示错误提示
 */
function showErrorMsg(msg) {
    var errorMsg = $("#errorMsg");
    errorMsg.html(msg);
    errorMsg.show();
}

/**
 * 登录
 */
function login() {
    var username = $("#username").val();
    var password = $("#password").val();
    var inputCode = $("#input1").val();

    var userInformation = {username: '', password: '', code: ''};

    if (null == inputCode || inputCode.length <= 0) {
        showErrorMsg("请输入验证码！");
        return;
    }
    if (username == "") {
        showErrorMsg("请输入登录用户名!");
        $("#username").focus();
        return false;
    }
    if (password == "") {
        showErrorMsg("请输入登录密码!");
        $("#password").focus();
        return false;
    }


    userInformation['username'] = username;
    userInformation['password'] = password;
    userInformation['code'] = inputCode;
    $("#errorMsg").html("");
    $("#errorMsg").hide();
    DCMSUtils.Modal.showLoading("拼命登录中....");
    DCMSUtils.Ajax.doPost("main/login", userInformation)
        .then(function (data) {
            console.log(data);
            DCMSUtils.Modal.hideLoading();
            if (data.status == "1") {
                console.log("login");
                DCMSBusi.USER.set(data.data);
                DCMSUtils.Modal.showLoading();
                return DCMSUtils.Ajax.doPost("menu/tree");
            } else {
                console.log("wrong");
                showErrorMsg(data.msg);
                return;
            }
        }, function (error) {
            DCMSUtils.Modal.hideLoading();
            showErrorMsg('登陆失败');
            return;
        }).then(function (data) {
        console.log(data);
        if (data) {
            if (data.status == '1') {
                var user = DCMSBusi.USER.get();
                user.userMenus = data.data;
                DCMSBusi.USER.set(user);
                DCMSUtils.NAV.gotoPage("./index.html");
            } else {
                DCMSUtils.Modal.hideLoading();
                showErrorMsg('登陆失败');
            }
        }
    }, function (error) {
        DCMSUtils.Modal.hideLoading();
        showErrorMsg('登陆失败');
    });
}