package SpringSecurity.SpringSecurityJWT.exception;


public class EmailAlreadyTakenException extends RuntimeException {
    
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}
