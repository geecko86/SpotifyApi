package com.drivemode.spotify.app;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.drivemode.spotify.Response;
import com.drivemode.spotify.SpotifyApi;
import com.drivemode.spotify.SpotifyLoader;
import com.drivemode.spotify.SpotifyService;
import com.drivemode.spotify.models.Pager;
import com.drivemode.spotify.models.Playlist;
import com.drivemode.spotify.models.User;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author KeishinYokomaku
 */
public class MainActivity extends Activity implements SpotifyApi.AuthenticationListener, LoaderManager.LoaderCallbacks<Response<User>> {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (SpotifyApi.getInstance().isAuthrorized()) {
            getLoaderManager().initLoader(0, null, this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        SpotifyApi.getInstance().onCallback(intent.getData(), this);
    }

    public void onAuthorizeClick(View view) {
        SpotifyApi.getInstance().authorize(this, new String[] {"playlist-read-private", "streaming", "user-library-read"}, false);
    }

    @Override
    public void onReady() {
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onError() {}

    @Override
    public Loader<Response<User>> onCreateLoader(int id, Bundle args) {
        return new SelfLoader(this, SpotifyApi.getInstance());
    }

    @Override
    public void onLoadFinished(Loader<Response<User>> loader, Response<User> data) {
        Toast.makeText(getApplicationContext(), "Logged in as " + data.getResult().id, Toast.LENGTH_SHORT).show();
        SpotifyApi.getInstance().getApiService().getPlaylists(data.getResult().id).enqueue(
                new Callback<Pager<Playlist>>() {
                    @Override
                    public void onResponse(Call<Pager<Playlist>> call, retrofit2.Response<Pager<Playlist>> response) {
                        Toast.makeText(getApplicationContext(), response.body().items.get(0).name, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Pager<Playlist>> call, Throwable t) {
                        t.printStackTrace();
                    }
                }
        );
    }

    @Override
    public void onLoaderReset(Loader<Response<User>> loader) {}

    static class SelfLoader extends SpotifyLoader<User> {
        public SelfLoader(Context context, SpotifyApi api) {
            super(context, api);
        }

        @Override
        public User call(SpotifyService service) throws Exception {
            return service.getMe().execute().body();
        }
    }
}
