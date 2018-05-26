package com.example.peking.util;

import java.net.URI;

public class URIUtils {

    private static final String SLICE = "/";

    public static URI getNewResourcesLocation(String resource, String id) {

        try {
            return new URI(resource + SLICE + id);
        } catch (Exception ex) {
            return null;
        }
    }
}
