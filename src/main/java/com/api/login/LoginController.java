package com.api.login;

import com.api.common.util.JwtUtils;
import com.api.login.dto.LoginRequest;
import com.api.login.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService service;

    public LoginController(LoginService service) {
        this.service = service;
    }
    @GetMapping("/email/{email}")
    public String getEmailAvailable(@PathVariable String email){
        return "{\"isAvailable\":"+service.isEmailAvailable(email)+"}";
    }

    @PostMapping("/login")
    public UserDto postLogin(@Valid @RequestBody LoginRequest request){
        return this.service.login(request);
    }

    @GetMapping("/me")
    public UserDto getMe(@RequestHeader("Authorization") String token) {
        return this.service.me(JwtUtils.toAuth(token));
    }
    @GetMapping("/admin/init")
    public String getInitAdmin(){
        return "{\"isAvailable\":"+service.createAdmin()+"}";
    }
}
