package eu.wisercat.filter.controller.exception;

public class ApiException {

    private String error;

    public ApiException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public ApiException setError(String error) {
        this.error = error;
        return this;
    }
}
