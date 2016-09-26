function pageInit(){
    initMenuTree();
}

function loginOut(){
    DCMSUtils.SessionStorage.clear();
    window.location.href="./login.html";
}