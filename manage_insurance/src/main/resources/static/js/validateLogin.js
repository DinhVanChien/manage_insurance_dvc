function validateLogin() {
    $("#isError").hide();
    let username = $("#username").val();
    let password = $("#password").val();
    let check = true;
    if (username.length == 0 && password.length == 0) {
        $("#error").text("Hãy nhập username và password");
        $("#error").show();
        check = false;
    } else if (username.length == 0) {
        $("#error").text("Hãy nhập username");
        $("#error").show();
        check = false;
    } else if (password.length <= 0) {
        $("#error").text("Hãy nhập password");
        $("#error").show();
        check = false;
    }
    if (!check) {
        $("#error").css({
            'color': 'red',
            'font-size': '20px',
            'font-family': 'auto'
        });
        $("#error").show();
        return false;
    }
    return true;
}