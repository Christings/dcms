 var code; //在全局 定义验证码
  var code2; //在全局 定义验证码
function createCode() {
    code = "";
    var checkCode = document.getElementById("checkCode");
    function RndNum(n) {
        var rnd = "";
        for (var i = 0; i < n; i++)
            rnd += Math.floor(Math.random() * 10);
        return rnd;
    }
 
    var num = RndNum(1);
    var num2 = RndNum(2);
 
    code = num + " + " + num2 + " = ？";
    code2 = parseInt(num) + parseInt(num2)
 
    if (checkCode) {
      // checkCode.className = "code";
        checkCode.value = code;
    }
}
$(document).ready(function () {
    createCode();
    $("#loginBtn").click(function () {
        var inputCode = document.getElementById("input1").value;
        if (inputCode.length <= 0) {
            alert("请输入验证码！");
        }else if (inputCode != code2) {
            alert("验证码输入错误！");
            createCode(); //刷新验证码
        }else{
            login();
        }
    });
})