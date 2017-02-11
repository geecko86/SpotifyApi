package com.drivemode.spotify.rest;

import com.drivemode.spotify.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .registerTypeAdapter(Call.class, new InterfaceAdapter<Call<User>>())
                        .create()))
                .build();
    }

    public Retrofit provideAuthenticateApiAdapter() {
        if (mOkClient == null)
            mOkClient = new OkHttpClient();

        return new Retrofit.Builder()
                .client(mOkClient)
                .baseUrl(SPOTIFY_AUTHENTICATE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(User.class, new InterfaceAdapter<User>())
                        .create()))
                .build();
    }

    private static final class InterfaceAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
        public JsonElement serialize(T object, Type interfaceType, JsonSerializationContext context) {
            final JsonObject wrapper = new JsonObject();
            wrapper.addProperty("type", object.getClass().getName());
            wrapper.add("data", context.serialize(object));
            return wrapper;
        }

        public T deserialize(JsonElement elem, Type interfaceType, JsonDeserializationContext context) throws JsonParseException {
            final JsonObject wrapper = (JsonObject) elem;
            final JsonElement typeName = get(wrapper, "type");
            final Type actualType = typeForName(typeName);
            return new Gson().fromJson(elem, actualType);
        }

        private Type typeForName(final JsonElement typeElem) {
            try {
                String className = typeElem.getAsString();
                return Class.forName("com.drivemode.spotify.models."+className.substring(0,1).toUpperCase() + className.substring(1));
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e);
            }
        }

        private JsonElement get(final JsonObject wrapper, String memberName) {
            final JsonElement elem = wrapper.get(memberName);
            if (elem == null) throw new JsonParseException("no '" + memberName + "' member found in what was expected to be an interface wrapper");
            return elem;
        }
    }
}
