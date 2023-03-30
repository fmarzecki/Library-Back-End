package SpringSecurity.SpringSecurityJWT.demo;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@CrossOrigin("*")
public class DemoController {
    
    @GetMapping()
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("This endpoint is secured, how did you get gere!?");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> sayHello2() {
        return ResponseEntity.ok("it is for admin");
    }
}
