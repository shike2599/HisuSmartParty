package com.jaydenxiao.common.baserx;

import com.google.gson.Gson;
import com.jaydenxiao.common.basebean.BaseResponse;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    /**
     * 针对数据返回成功、错误不同类型字段处理
     */
    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        try {
            BaseResponse baseResponse = gson.fromJson(response, BaseResponse.class);
            if(!baseResponse.success()){
                throw new ResultException(baseResponse.getResultDesc(), baseResponse.getResultCode());
            }else{
                return gson.fromJson(response, type);
            }
        }finally {
            value.close();
        }
    }
}
