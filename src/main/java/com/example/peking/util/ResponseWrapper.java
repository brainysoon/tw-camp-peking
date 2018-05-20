package com.example.peking.util;

import com.example.peking.domain.Response;
import com.example.peking.enums.ResponseInfoEnum;

import java.util.function.Predicate;

public class ResponseWrapper<T> {

    public static <T> Response<T> wrapResponse(ResponseInfoEnum responseInfoEnum, T data) {
        Response<T> response = new Response<>(responseInfoEnum);
        response.setData(data);
        return response;
    }

    public static <T> Response<T> wrapGetResponse(Predicate<T> emptyPredicate, T data) {
        ResponseInfoEnum responseInfoEnum = emptyPredicate.test(data) ?
                ResponseInfoEnum.RESOURCE_NOT_FOUND : ResponseInfoEnum.REQUEST_SUCCESSFULLY;

        Response<T> response = new Response<>(responseInfoEnum);
        response.setData(data);
        return response;
    }

    public static <T> Response<T> wrapResponse(ResponseInfoEnum responseInfoEnum) {
        return new Response<>(responseInfoEnum);
    }

    public static <T> Response<T> wrapPutResponse(Predicate<T> updateSuccessfullyPredicate, T influenceLine) {

        ResponseInfoEnum responseInfoEnum = updateSuccessfullyPredicate.test(influenceLine) ?
                ResponseInfoEnum.RESOURCE_UPDATED : ResponseInfoEnum.RESOURCE_NOT_FOUND;
        Response<T> response = new Response<>(responseInfoEnum);
        response.setData(influenceLine);
        return response;
    }
}