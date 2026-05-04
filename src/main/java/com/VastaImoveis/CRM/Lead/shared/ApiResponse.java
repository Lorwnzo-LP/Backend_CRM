package com.VastaImoveis.CRM.Lead.shared;

public class ApiResponse<T> {
    private T data;
    private String text;

    public ApiResponse(T data, String text) {
        this.data = data;
        this.text = text;
    }

    public T getData() {
        return data;
    }

    public String getText() {
        return text;
    }
}
