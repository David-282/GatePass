package exceptions;

public class ResidentDisabledException extends RuntimeException {
    public ResidentDisabledException(String message) {
        super(message);
    }
}
