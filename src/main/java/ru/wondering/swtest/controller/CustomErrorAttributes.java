package ru.wondering.swtest.controller;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * Include Exception message in error response.
 */
@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(
            WebRequest webRequest, ErrorAttributeOptions options) {
        return super.getErrorAttributes(webRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));
    }
}