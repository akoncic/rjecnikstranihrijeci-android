package com.dekoraktiv.android.rsr.converters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public final class CustomConverterFactory extends Converter.Factory {

    private CustomConverterFactory() {
    }

    public static CustomConverterFactory create() {
        return new CustomConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        return CustomResponseBodyConverter.INSTANCE;
    }
}
