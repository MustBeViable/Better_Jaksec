package com.api.login;

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
    @PostMapping("/login")
    public UserDto postLogin(@Valid @RequestBody LoginRequest request){
        return this.service.login(request);
    }

    @GetMapping("/me/{token}")
    public UserDto getMe(@PathVariable String token){
        return this.service.me(JwtUtils.parse(token));
    }
}
