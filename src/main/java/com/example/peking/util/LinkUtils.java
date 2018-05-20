package com.example.peking.util;

import com.example.peking.domain.Link;

import java.lang.reflect.Field;

public class LinkUtils {

    private static final String HTTP_LOCALHOST_8083 = "http://localhost:8083/";

    public static Link generateLink(Field field) {
        try {
            String name = field.getName();
            String link = HTTP_LOCALHOST_8083 + field.get(null);
            return new Link(name, link);
        } catch (Exception ex) {
            return null;
        }
    }
}
