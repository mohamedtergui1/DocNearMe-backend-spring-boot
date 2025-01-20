package ma.tr.docnearme.exception;

public class ProcessNotCompletedException extends RuntimeException {
    public ProcessNotCompletedException(String message) {
        super(message);
    }
}
