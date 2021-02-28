package com.insurance.application.dto;

import com.insurance.application.form.InsuranceForm;

import java.util.List;

public class PagesDto {
    private int totalPage;
    private int pageSize = 5;
    // là list danh sach page cần hiển thị vd: 1 2 3
    private int listPageToShow;
    private int startPage;
    private int endPage;
    private int currentPage;
    private List<InsuranceForm> insuranceForms;

    public PagesDto() {
    }

    public void startPageEndPage(int totalPage, int currentPage, int listPageToShow) {
        this.totalPage = totalPage;
        this.listPageToShow = listPageToShow;
        this.currentPage = currentPage;
        int halfListPageToShow = listPageToShow / 2;
        // case 1: totalPage <= listPageToShow
        if (totalPage <= listPageToShow) {
            setStartPage(1);
            setEndPage(totalPage);
        } else if (currentPage <= halfListPageToShow) {
            setStartPage(1);
            setEndPage(getListPageToShow());
        } else if (currentPage + halfListPageToShow == totalPage) {
            setStartPage(currentPage - halfListPageToShow);
            setEndPage(totalPage);
        } else if (currentPage + halfListPageToShow > totalPage) {
            setStartPage(totalPage - getListPageToShow() + 1);
            setEndPage(totalPage);
        } else {
            setStartPage(currentPage - halfListPageToShow);
            setEndPage(currentPage + halfListPageToShow);
        }
    }

    public int getTotalPage(int totalUser) {
        if (totalUser % getPageSize() == 0) {
            return totalUser / getPageSize();
        }
        return totalUser / getPageSize() + 1;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getListPageToShow() {
        return listPageToShow;
    }

    public void setListPageToShow(int listPageToShow) {
        this.listPageToShow = listPageToShow;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public List<InsuranceForm> getInsuranceForms() {
        return insuranceForms;
    }

    public void setInsuranceForms(List<InsuranceForm> insuranceForms) {
        this.insuranceForms = insuranceForms;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
