// lần đầu vào web
$(document).ready(function () {
    getCompanyInfo();
});
// khi thay đổi company
$(document).ready(function () {
    $('#company').change(function () {
        getCompanyInfo();
    });
});

getCompanyInfo = () => {
    var companyId = $("#company").val();
    $.ajax({
        type: 'GET',
        data: {
            company: companyId
        },
        url: "/info_company",
        success: function (data) {
            infoConpany(data);
        }
    });
}

infoConpany = (data) => {
    $('.trInfo td').remove();
    let companyShow = '';
    companyShow += '<tr class="trInfo">';
    companyShow += '<td>' + "Tên công ty" + '</td>';
    companyShow += '<td>' + data.name + '</td>';
    companyShow += '</tr>';
    companyShow += '<tr class="trInfo">';
    companyShow += '<td>' + "Địa chỉ" + '</td>';
    companyShow += '<td>' + data.address + '</td>';
    companyShow += '</tr>';
    companyShow += '<tr class="trInfo">';
    companyShow += '<td>' + "Email" + '</td>';
    companyShow += '<td>' + data.email + '</td>';
    companyShow += '</tr>';
    companyShow += '<tr class="trInfo">';
    companyShow += '<td>' + "Điện thoại" + '</td>';
    companyShow += '<td>' + data.telephone + '</td>';
    companyShow += '</tr>';
    $('#infoCompany').append(companyShow);
}

function formatDate(input) {
    let datePart = input.split("-");
    let year = datePart[0]; // get only two digits
    let month = datePart[1];
    let day = datePart[2];
    return day + '-' + month + '-' + year;
}