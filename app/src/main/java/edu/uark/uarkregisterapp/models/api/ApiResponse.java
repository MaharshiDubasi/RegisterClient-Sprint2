package edu.uark.uarkregisterapp.models.api;

import org.apache.commons.lang3.StringUtils;

public class ApiResponse<T> {
    private boolean isValidResponse;
    public boolean isValidResponse() {
        return this.isValidResponse;
    }
    public ApiResponse<T> setValidResponse(boolean validResponse) {
        this.isValidResponse = validResponse;
        return this;
    }

    private String message;
    public String getMessage() {
        return this.message;
    }
    public ApiResponse<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    private String rawResponse;
    public String getRawResponse() {
        return this.rawResponse;
    }
    public ApiResponse<T> setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
        return this;
    }

    private T data;
    public T getData() {
        return this.data;
    }
    public ApiResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    public ApiResponse() {
        this.data = null;
        this.isValidResponse = true;
        this.message = StringUtils.EMPTY;
        this.rawResponse = StringUtils.EMPTY;
    }
}
