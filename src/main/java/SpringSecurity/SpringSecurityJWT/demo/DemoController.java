package SpringSecurity.SpringSecurityJWT.demo;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SpringSecurity.SpringSecurityJWT.auth.BasicResponse;

@RestController
@RequestMapping("/test")
public class DemoController {
    
    @GetMapping()
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("This endpoint is secured, how did you get gere!?");
    }

    @GetMapping("/admin")
    public BasicResponse sayHello2() {
        BasicResponse response = new BasicResponse("It it only for admin!");
        return response;
    }
}
