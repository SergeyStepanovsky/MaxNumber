package com.example.demo.controller;


import com.example.demo.service.NumberService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NumberContoller {

    @Autowired
    private NumberService numberService;

    @Operation(summary = "Получить N-ое максимальное число")
    @GetMapping("/nth-max")
    public Integer getNthMaxNumber(@RequestParam String filePath, @RequestParam int n)throws Exception{
        return numberService.findMaxNumber(filePath, n);
    }
}
