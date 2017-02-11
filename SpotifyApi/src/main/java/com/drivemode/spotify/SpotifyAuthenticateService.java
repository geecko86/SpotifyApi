package com.drivemode.spotify;

import com.drivemode.spotify.auth.AccessToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author KeishinYokomaku
 */
public interface SpotifyAuthenticateService {
    public static final String TAG = SpotifyAuthenticateService.class.getSimpleName();

    @FormUrlEncoded
    @POST("/api/token")
    public Call<AccessToken> getAccessToken(@Field("grant_type") String grantType, @Field("code") String code, @Field("redirect_uri") String redirectUri, @Field("client_id") String clientId, @Field("client_secret") String clientSecret);

    @FormUrlEncoded
    @POST("/api/token")
    public Call<AccessToken> refreshAccessToken(@Field("grant_type") String grantType, @Field("refresh_token") String refreshToken, @Field("client_id") String clientId, @Field("client_secret") String clientSecret);
}
