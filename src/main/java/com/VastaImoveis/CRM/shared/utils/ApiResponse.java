package com.VastaImoveis.CRM.shared.utils;

public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String text;
    public ApiResponse(boolean success,T data, String text) {
        this.success = success;
        this.data = data;
        this.text = text;
    }

    public T getData() {
        return data;
    }

    public String getText() {
        return text;
    }

    public boolean isSuccess() {
        return success;
    }
}
