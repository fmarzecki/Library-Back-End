package SpringSecurity.SpringSecurityJWT.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import SpringSecurity.SpringSecurityJWT.exception.EmailAlreadyTakenException;

@ControllerAdvice
public class AuthenticationExceptionHandler {
    // Dodaje ExceptionHandler dla StudentNotFoundException ktory zwroci ResponseEntity<StudentErrorMessage>
    @ExceptionHandler
    public ResponseEntity<BasicResponse> handleException(EmailAlreadyTakenException exc) {

        BasicResponse error = new BasicResponse();
        error.setResponse(exc.getMessage());

        // pierwszy argument to cialo, a drugi status tej odpowiedzi
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
}
