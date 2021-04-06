function validateLookup() {
let insuId = $("#insuranceId").val();
let fullName = $("#fullName").val();
let check = true;
if (insuId.length == 0) {
    $("#erNuInServer").text("");
    $("#erNumberInsurance").text("Nhập mã số bảo hiểm");
    $("#erNumberInsurance").css({'color': 'red'});
    $("#erNumberInsurance").show();
    check = false;
} else {
    var pattern = /\D/;
    if(pattern.test(insuId) || insuId.length != 10) {
        $("#erNumberInsurance").text("Mã số bảo hiểm phải là số và có độ dài bằng 10");
        $("#erNumberInsurance").css({'color': 'red'});
        $("#erNumberInsurance").show();
    } else {
        $("#erNumberInsurance").hide();
    }
}
if (fullName.length == 0) {
    $("#erFullNameServer").text("");
    $("#erFullName").text("Nhập họ và tên");
    $("#erFullName").css({'color': 'red'});
    $("#erFullName").show();
    check = false;
} else {
    $("#erFullName").hide();
}


if (!check) {
    return false;
}
return true;
}