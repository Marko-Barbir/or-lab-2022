package hr.fer.zari.or.backend.model.dto;

public class ResponseWrapper<T> {

    private String status;
    private String message;
    private T response;

    public ResponseWrapper() {
    }

    public ResponseWrapper(String status, String message, T response) {
        this.status = status;
        this.message = message;
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
