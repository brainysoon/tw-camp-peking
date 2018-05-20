package com.example.peking.controller;

import com.example.peking.constant.UriConstants;
import com.example.peking.domain.Response;
import com.example.peking.enums.ResponseInfoEnum;
import com.example.peking.util.ResponseWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HubController {

    @GetMapping(UriConstants.INDEX)
    public Response<String> index() {

        return ResponseWrapper.wrapResponse(ResponseInfoEnum.REQUEST_SUCCESSFULLY);
    }
}
