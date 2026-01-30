package com.api.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController notates method to be REST API controller (like in express)
 * For routing it seems to be enough to have @RequestMapping notation to be like this
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping
    public String adminMain() {
        return "Nyt oot adminissa";
    }
}
