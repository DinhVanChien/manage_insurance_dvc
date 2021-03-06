package com.insurance.application.dto;

public class Response<T> {
    /**
     * Function có thành công hay không
     */
    private boolean status;
    /**
     * Message thông báo từ function
     */
    private String message;
    /**
     * check xem có tạo company ko
     */
    private boolean newCompany;
    /**
     * Dữ liệu trả về của function
     */
    private T data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getNewCompany() {
        return newCompany;
    }

    public void setNewCompany(boolean newCompany) {
        this.newCompany = newCompany;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    public Response(boolean status) {
        this.status = status;
    }
    public Response(boolean status, boolean newCompany) {
        this.status = status;
        this.newCompany = newCompany;
    }
}
