package com.baobao.ware_x.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {

    @GetMapping(path = "/")
    public HashMap index() {
        // get a successful user login
        OAuth2User user = ((OAuth2User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return new HashMap(){{
            put("hello", user.getAttribute("name"));
            put("your email is", user.getAttribute("email"));
            put("your role is", user.getAttribute("role"));
        }};
    }

    @GetMapping(path = "/unauthenticated")
    public Map<String, String> unauthenticatedRequests() {
        Map<String, String> response = new HashMap<>();
        response.put("this is", "unauthenticated endpoint");
        return response;
    }
}

