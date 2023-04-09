package SpringSecurity.SpringSecurityJWT.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    final private UserService userService;
 
    @GetMapping
    public ResponseEntity<UserRequest> register()
    {
        return ResponseEntity.ok(userService.getUserDetails());
    }
}
