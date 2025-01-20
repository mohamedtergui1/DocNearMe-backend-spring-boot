package ma.tr.docnearme.exception;

import java.util.Map;

public class Errors422Exception extends RuntimeException {
    private Map<String,String> errors;
    public Errors422Exception(Map<String,String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
