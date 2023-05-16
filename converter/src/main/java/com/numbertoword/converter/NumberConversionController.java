package com.numbertoword.converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.xml.soap.SOAPException;

import javax.xml.transform.TransformerException;

@RestController
@RequestMapping("/api")
public class NumberConversionController {

    private final NumberConversionService numberConversionService;

    @Autowired
    public NumberConversionController(NumberConversionService numberConversionService) {
        this.numberConversionService = numberConversionService;
    }

    @GetMapping("/number-to-words/{number}")
    public String convertNumberToWords(@PathVariable int number) throws TransformerException, SOAPException {
        return numberConversionService.convertNumberToWords(number);
    }
}

