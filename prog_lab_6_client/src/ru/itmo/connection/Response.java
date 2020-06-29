package ru.itmo.connection;

import java.io.Serializable;

public class Response implements Serializable {


    String errorMessage = null;
    String successMessage = null;


    public String getMessage() {
        if (errorMessage != null) {
            return errorMessage;
        } else if (successMessage != null) {
            return successMessage;
        } else return "";
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}
