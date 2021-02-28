function validateAddNew() {
    let insuId = $("#insuranceId").val();
    let fullName = $("#fullName").val();
    let birthDate = $("#birthDate").val();
    let placeRegister = $("#placeRegister").val();
    let username = $("#username").val();
    let startDate = $("#startDate").val();
    let endDate = $("#endDate").val();
    let newCompany = $("#newCompany").val();
    let newAddress = $("#newAddress").val();
    let newEmail = $("#newEmail").val();
    let newPhone = $("#newPhone").val();
    let checkCompany = $("input:checked").val();
    let check = true;
    if (insuId.length == 0) {
        $("#erNumberInsurance").text("Nhập mã số bảo hiểm");
        $("#erNumberInsurance").css({'color': 'red'});
        $("#erNumberInsurance").show();
        check = false;
    } else {
        $("#erNumberInsurance").hide();
    }
    if (fullName.length == 0) {
        $("#erFullName").text("Nhập họ và tên");
        $("#erFullName").css({'color': 'red'});
        $("#erFullName").show();
        check = false;
    } else {
        $("#erFullName").hide();
    }
    if (username.length == 0) {
        $("#erUsername").text("Nhập Username");
        $("#erUsername").css({'color': 'red'});
        $("#erUsername").show();
        check = false;
    } else {
        $("#erUsername").hide();
    }
    if (birthDate.length == 0) {
        $("#erBirthDate").text("Nhập birthDate");
        $("#erBirthDate").css({'color': 'red'});
        $("#erBirthDate").show();
        check = false;
    } else {
        $("#erBirthDate").hide();
    }
    if (placeRegister.length == 0) {
        $("#erPlaceRegister").text("Nhập nơi đăng kí KCB");
        $("#erPlaceRegister").css({'color': 'red'});
        $("#erPlaceRegister").show();
        check = false;
    } else {
        $("#erPlaceRegister").hide();
    }
    if (startDate.length == 0) {
        $("#erStartDate").text("Nhập ngày bắt đầu BH");
        $("#erStartDate").css({'color': 'red'});
        $("#erStartDate").show();
        check = false;
    } else {
        $("#erStartDate").hide();
    }
    if (endDate.length == 0) {
        $("#erEndDate").text("Nhập ngày hết hạn BH");
        $("#erEndDate").css({'color': 'red'});
        $("#erEndDate").show();
        check = false;
    } else {
        $("#erEndDate").hide();
    }
    if (checkCompany == "no") {

        if (newCompany.length == 0) {
            $("#erNewCompany").text("Nhập tên Company mới");
            $("#erNewCompany").css({'color': 'red'});
            $("#erNewCompany").show();
            check = false;
        } else {
            $("#erNewCompany").hide();
        }
        if (newAddress.length == 0) {
            $("#erNewAddress").text("Nhập tên place");
            $("#erNewAddress").css({'color': 'red'});
            $("#erNewAddress").show();
            check = false;
        } else {
            $("#erNewAddress").hide();
        }
        if (newEmail.length == 0) {
            $("#erNewEmail").text("Nhập tên email");
            $("#erNewEmail").css({'color': 'red'});
            $("#erNewEmail").show();
            check = false;
        } else {
            $("#erNewEmail").hide();
        }
        if (newPhone.length == 0) {
            $("#erNewPhone").text("Nhập tên phone");
            $("#erNewPhone").css({'color': 'red'});
            $("#erNewPhone").show();
            check = false;
        } else {
            $("#erNewPhone").hide();
        }
    } else {
        $("#erNewCompany").text("");
        $("#erNewAddress").text("");
        $("#erNewEmail").text("");
        $("#erNewPhone").text("");
    }
    if (!check) {
        return false;
    }
    return true;
}

function showNewCompany() {
    let value = $("input:checked").val();
    if (value == "yes") {
        $("#existCompany").show();
        $("#isNewCompany").val("");
        $("#divNew").hide();
    } else {
        $("#existCompany").hide();
        $("#isNewCompany").val("no");
        $("#divNew").show();
    }
}
