function pageInit(){
    initMenuTree();
}

function loginOut(){
    DCMSUtils.SessionStorage.clear();
    window.location.href="./";
}

function showUserInfo() {
    var user=DCMSBusi.USER.get();
    $("#infoName").html(user.username);
    $("#realName").html(user.realname);
    $("#infoPhone").html(user.phone);
    $("#infoEmail").html(user.email);
    $("#userInfoModal").modal();
}
var icon = "<i class='fa fa-times-circle'></i> ";
$("#pwdForm").validate({
    rules:{
        oldPwd:{
            required:true
        },
        newPwd:{
            required:true,
            minlength:2,
            maxlength:50
        },
        newPwdConfirm:{
            required:true,
            minlength:2,
            maxlength:50,
            equalTo:'#newPwd'
        }
    },
    messages:{
        oldPwd:icon + "请输入旧密码",
        newPwd:icon + "请输入2-50个字符的新密码",
        newPwdConfirm:{
            required:icon+"请输入2-50个字符的新密码",
            equalTo:icon+"两次密码输入不一致"
        }
    },
    submitHandler:function(form){
        var param={
            oldPassword:$("#oldPwd").val(),
            newPassword:$("#newPwd").val()
        };
        $("#pwdModal").modal('hide');
        DCMSUtils.Modal.showLoading();
        DCMSBusi.Api.invoke('user/password',param).then(function(data){
            DCMSUtils.Modal.hideLoading();
            document.getElementById('pwdForm').reset();
            if(data.status==1){
                DCMSUtils.Modal.toast('修改密码成功,请重新登陆');
                setTimeout(function(){
                    loginOut();
                },2000);
            }else{
                DCMSUtils.Modal.toast('修改密码错误'+data.msg,'forbidden');
            }
        },function(error){
            document.getElementById('pwdForm').reset();
            DCMSUtils.Modal.hideLoading();
            DCMSUtils.Modal.toast('修改密码异常','forbidden');
        });
    }
});
