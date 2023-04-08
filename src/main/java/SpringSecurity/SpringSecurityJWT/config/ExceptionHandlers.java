package SpringSecurity.SpringSecurityJWT.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import SpringSecurity.SpringSecurityJWT.auth.BasicResponse;
import SpringSecurity.SpringSecurityJWT.exception.EmailAlreadyTakenException;

@RestControllerAdvice
public class ExceptionHandlers {
    // Dodaje ExceptionHandler dla StudentNotFoundException ktory zwroci ResponseEntity<StudentErrorMessage>
    @ExceptionHandler
    public ResponseEntity<BasicResponse> handleException(EmailAlreadyTakenException exc) {
        return new ResponseEntity<>(new BasicResponse(exc.getMessage()), HttpStatus.FORBIDDEN);
    }

}
