package com.api.login;

import com.api.common.error.exceptions.UnauthorizedException;
import com.api.common.util.JwtUtils;
import com.api.login.dto.LoginDto;
import com.api.login.dto.LoginRequest;
import com.api.login.dto.UserDto;
import com.api.login.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
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
}
