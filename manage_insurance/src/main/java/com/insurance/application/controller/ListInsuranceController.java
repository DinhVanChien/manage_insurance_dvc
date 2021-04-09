package com.insurance.application.controller;

import com.insurance.application.dto.CompanyDto;
import com.insurance.application.dto.PagesDto;
import com.insurance.application.exception.NotFoundException;
import com.insurance.application.form.InsuranceForm;
import com.insurance.application.form.SearchForm;
import com.insurance.application.service.CompanyService;
import com.insurance.application.utils.Common;
import com.insurance.application.utils.Constant;
import com.insurance.application.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * ChienDV
 */
@Controller
@RequestMapping(Constant.INSURANCE_ANNOTATION)
public class ListInsuranceController {

    private UserService userService;
    private CompanyService companyService;

    @Autowired
    public ListInsuranceController(UserService userService, CompanyService companyService) {
        this.userService = userService;
        this.companyService = companyService;
    }

    @ModelAttribute(value = "listCompany")
    private List<CompanyDto> companyList() {
        return Common.compareName(companyService.findAll());
    }

    @GetMapping
    private String homePage() {
        return Constant.REDIRECT_INSURANCES_PAGE.concat("?type=first");
    }

    @PostMapping
    private String search(HttpServletRequest request,
                          @ModelAttribute(Constant.SEARCH_FORM) SearchForm searchForm) {
        request.getSession().setAttribute(Constant.SEARCH_FORM, searchForm);
        return Constant.REDIRECT_INSURANCES_PAGE.concat("?type=search");
    }

    @GetMapping("/home")
    public String listInsurance(HttpServletRequest request,
                                @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                @RequestParam(value = Constant.TYPE, required = false) String type,
                                @RequestParam(value = "sort-name", required = false) String sortName,
                                @RequestParam(value = "sort-innum", required = false) String sortInNum,
                                @RequestParam(value = "sort-createdate", required = false) String sortCreateDate,
                                Model model) throws Exception {
        try {
            PagesDto pagesDto = new PagesDto();
            List<InsuranceForm> insurances = new ArrayList<>();
            SearchForm searchForm = new SearchForm();
            if(sortCreateDate == null) {
                sortCreateDate = "DESC";
            }

            // lần đầu vào trang home sẽ lấy bảo hiểm của sort cty đầu tiên
            if ("first".equals(type)) {
                searchForm.setCompanyId(companyList().get(0).getId());
            }
            // Trường hợp ở page detail back lại thì vẫn giữ nguyên trang hiện tại
            else if ("back".equals(type)) {
                searchForm = (SearchForm) request.getSession().getAttribute(Constant.SEARCH_FORM);
                page = searchForm.getPage();
            } else {
                searchForm = (SearchForm) request.getSession().getAttribute(Constant.SEARCH_FORM);
            }
            searchForm.setSortCreateDate(sortCreateDate);
            searchForm.setSortName(sortName);
            searchForm.setSortInNumber(sortInNum);
            insurances = listInsurance(searchForm, page);
            pagesDto.setCurrentPage(page);
            pagesDto.setInsuranceForms(insurances);
            pagesDto.setTotalPage(pagesDto.getTotalPage(this.totalUser(searchForm)));
            pagesDto.startPageEndPage(pagesDto.getTotalPage(),
                    pagesDto.getCurrentPage(),
                    Constant.DEFAULT_LIST_SHOW_PAGE);
            searchForm.setPage(page);
            request.getSession().setAttribute(Constant.SEARCH_FORM, searchForm);
            String baseUrl = "/insurances/home/?page=";
            this.addModelAttribute(searchForm, model, pagesDto, baseUrl);

        } catch (NotFoundException fx) {
            throw new NotFoundException("Error không có record tồn tại");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return "listUserInsurance";
    }

    @GetMapping(value = "/export")
    @ResponseBody
    public void exportCSV(HttpServletResponse response, HttpServletRequest request) throws Exception {
        try {
            SearchForm searchForm = (SearchForm) request.getSession().getAttribute(Constant.SEARCH_FORM);
            List<InsuranceForm> listInsurances = listInsurance(searchForm, 0);
            // Create Workbook
            HSSFWorkbook workbook = new HSSFWorkbook();
            // Create sheet
            Sheet sheet = workbook.createSheet("insurance");
            int rowIndex = 0;
            // Write header
            CellStyle cellStyle = Common.createStyleForHeader(sheet);
            // Create row
            String[] header = {"Tên người sử dụng","Công ty", "Giới tính", "Ngày sinh", "Mã thẻ bảo hiểm", "Kì hạn", "Nơi đăng kí KCB", "Ngày đăng kí"};
            Common.createCellHeader(sheet, cellStyle, header);
            // Write data
            rowIndex++;
            for (InsuranceForm in : listInsurances) {
                // Create row
                Row row = sheet.createRow(rowIndex);
                String[] value = {
                        in.getFullName(),
                        in.getCompanyName(),
                        in.getSex().equals("1") ? "Nam" : "Nữ",
                        in.getBirthDate(),
                        in.getNumberInsurance(),
                        in.getStartDateInsurance() + " ~ "
                                + in.getEndDateInsurance(),
                        in.getPlaceRegisterOfInsurance(),
                        in.getCreateDate()
                };
                // Write data on row
                Common.writeInsurance(value, row);
                rowIndex++;
            }
            Common.writeXlsx(workbook, sheet, response, header.length, "insurance.xls");
        } catch (NotFoundException fx) {
            throw new NotFoundException("Error không có record tồn tại");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private void addModelAttribute(SearchForm searchForm, Model model, PagesDto pagesDto, String baseUrl) {
        model.addAttribute("listCompany", companyList());
        model.addAttribute("searchForm", searchForm);
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("pagesDto", pagesDto);
    }

    private List<InsuranceForm> listInsurance(SearchForm searchForm, int page) {
        List<InsuranceForm> listInsurance = userService.getListUser(
                searchForm.getCompanyId(),
                searchForm.getUserFullName(),
                searchForm.getInsuranceNumber(),
                searchForm.getPlaceRegister(),
                searchForm.getSortName(),
                searchForm.getSortInNumber(),
                searchForm.getSortCreateDate(),
                page);
        return listInsurance;
    }

    private int totalUser(SearchForm searchForm) {
        int totalUser = userService.getTotalUser(
                searchForm.getCompanyId(),
                searchForm.getUserFullName(),
                searchForm.getInsuranceNumber(),
                searchForm.getPlaceRegister());
        return totalUser;
    }
}
