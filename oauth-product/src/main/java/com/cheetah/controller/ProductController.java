package com.cheetah.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/findAll")
    @Secured("ROLE_PRODUCT")
    public String findAll(){
        return "产品列表查询成功";
    }

}
