package com.drivemode.spotify.rest;

import com.drivemode.spotify.auth.AccessToken;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author KeishinYokomaku
 */
public class RestAdapterFactory {
    public static final String TAG = RestAdapterFactory.class.getSimpleName();
    public static final String SPOTIFY_WEB_API_ENDPOINT = "https://api.spotify.com/v1/";
    public static final String SPOTIFY_AUTHENTICATE_ENDPOINT = "https://accounts.spotify.com/";
    private OkHttpClient mOkClient;

    public Retrofit provideWebApiAdapter(Interceptor interceptor) {
        if (mOkClient == null || mOkClient.interceptors().size() == 0) {
            mOkClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor).build();
        }
        return new Retrofit.Builder()
                .client(mOkClient)
                .baseUrl(SPOTIFY_WEB_API_ENDPOINT)
                .build();
    }

    public Retrofit provideAuthenticateApiAdapter() {
        if (mOkClient == null)
            mOkClient = new OkHttpClient();

        return new Retrofit.Builder()
                .client(mOkClient)
                .baseUrl(SPOTIFY_AUTHENTICATE_ENDPOINT)
                .addConverterFactory(new Converter.Factory() {
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return new Converter<ResponseBody, AccessToken>() {
                            @Override
                            public AccessToken convert(ResponseBody value) throws IOException {
                                AccessToken token = new AccessToken();
                                JsonObject responseOBject = (JsonObject) new JsonParser().parse(value.string());
                                token.accessToken = responseOBject.get("access_token").getAsString();
                                token.expiresIn = responseOBject.get("expires_in").getAsLong();
                                token.refreshToken = responseOBject.get("refresh_token").getAsString();
                                token.tokenType = responseOBject.get("token_type").getAsString();
                                return token;
                            }
                        };
                    }
                })
                .build();
    }
}
