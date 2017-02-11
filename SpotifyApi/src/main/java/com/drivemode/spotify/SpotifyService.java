package com.drivemode.spotify;

import com.drivemode.spotify.annotations.DELETEWITHBODY;
import com.drivemode.spotify.models.Album;
import com.drivemode.spotify.models.Albums;
import com.drivemode.spotify.models.AlbumsPager;
import com.drivemode.spotify.models.Artist;
import com.drivemode.spotify.models.Artists;
import com.drivemode.spotify.models.ArtistsPager;
import com.drivemode.spotify.models.FeaturedPlaylists;
import com.drivemode.spotify.models.NewReleases;
import com.drivemode.spotify.models.Pager;
import com.drivemode.spotify.models.Playlist;
import com.drivemode.spotify.models.PlaylistTrack;
import com.drivemode.spotify.models.PlaylistsPager;
import com.drivemode.spotify.models.SavedTrack;
import com.drivemode.spotify.models.SnapshotId;
import com.drivemode.spotify.models.Track;
import com.drivemode.spotify.models.Tracks;
import com.drivemode.spotify.models.TracksPager;
import com.drivemode.spotify.models.TracksToRemove;
import com.drivemode.spotify.models.TracksToRemoveWithPosition;
import com.drivemode.spotify.models.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface SpotifyService {

    /**
     * Profiles *
     */

    @GET("me")
    public Call<User> getMe();

    @GET("user/{id}")
    public Call<User> getUser(@Path("id") String userId);

    /**
     * Playlists *
     */

    @GET("users/{id}/playlists")
    public Call<Pager<Playlist>> getPlaylists(@Path("id") String userId, @Query("offset") int offset, @Query("limit") int limit);

    @GET("users/{id}/playlists")
    public Call<Pager<Playlist>> getPlaylists(@Path("id") String userId);

    @GET("users/{user_id}/playlists/{playlist_id}")
    public Call<Playlist> getPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId);

    @GET("users/{user_id}/playlists/{playlist_id}/tracks")
    public Call<Pager<PlaylistTrack>> getPlaylistTracks(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("offset") int offset, @Query("limit") int limit);

    @GET("users/{user_id}/playlists/{playlist_id}/tracks")
    public Call<Pager<PlaylistTrack>> getPlaylistTracks(@Path("user_id") String userId, @Path("playlist_id") String playlistId);

    @POST("users/{user_id}/playlists")
    public Call<Playlist> createPlaylist(@Path("user_id") String userId, @Query("name") String name);

    @POST("users/{user_id}/playlists")
    public Call<Playlist> createPlaylist(@Path("user_id") String userId, @Query("name") String name, @Query("public") boolean is_public);

    @POST("users/{user_id}/playlists/{playlist_id}/tracks")
    public Call<SnapshotId> addTracksToPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("uris") String trackUris);

    @POST("users/{user_id}/playlists/{playlist_id}/tracks")
    public Call<SnapshotId> addTracksToPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("uris") String trackUris, @Query("position") int position);

    @DELETEWITHBODY("users/{user_id}/playlists/{playlist_id}/tracks")
    public Call<SnapshotId> removeTracksFromPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Body TracksToRemove tracksToRemove);

    @DELETEWITHBODY("users/{user_id}/playlists/{playlist_id}/tracks")
    public Call<SnapshotId> removeTracksFromPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Body TracksToRemoveWithPosition tracksToRemoveWithPosition);

    // todo: process status code and return boolean
    @PUT("users/{user_id}/playlists/{playlist_id}/tracks")
    public Call<Boolean> replaceTracksInPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("uris") String trackUris);

    // todo: process status code and return boolean
    @PUT("users/{user_id}/playlists/{playlist_id}")
    public Call<Boolean> changePlaylistDetails(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("name") String name);

    // todo: process status code and return boolean
    @PUT("users/{user_id}/playlists/{playlist_id}")
    public Call<Boolean> changePlaylistDetails(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @Query("public") boolean is_public);

    /**
     * Albums *
     */

    @GET("albums/{id}")
    public Call<Album> getAlbum(@Path("id") String albumId);

    @GET("albums")
    public Call<Albums> getAlbums(@Query("ids") String albumIds);

    @GET("albums/{id}/tracks")
    public Call<Pager<Track>> getAlbumTracks(@Path("id") String albumId);

    @GET("albums/{id}/tracks")
    public Call<Pager<Track>> getAlbumTracks(@Path("id") String albumId, @Query("offset") int offset, @Query("limit") int limit);

    /**
     * Artists *
     */

    @GET("artists/{id}/albums")
    public Call<Pager<Album>> getArtistAlbums(@Path("id") String artistId, @Query("offset") int offset, @Query("limit") int limit);

    @GET("artists/{id}/albums")
    public Call<Pager<Album>> getArtistAlbums(@Path("id") String artistId);

    @GET("artists/{id}/top-tracks")
    public Call<Pager<Track>> getArtistTopTrack(@Path("id") String artistId, @Query("offset") int offset, @Query("limit") int limit);

    @GET("artists/{id}/top-tracks")
    public Call<Pager<Track>> getArtistTopTrack(@Path("id") String artistId);

    @GET("artists/{id}/related-artists")
    public Call<Pager<Artist>> getRelatedArtists(@Path("id") String artistId, @Query("offset") int offset, @Query("limit") int limit);

    @GET("artists/{id}/related-artists")
    public Call<Pager<Artist>> getRelatedArtists(@Path("id") String artistId);

    @GET("artists/{id}")
    public Call<Artist> getArtist(@Path("id") String artistId);

    @GET("artists")
    public Call<Artists> getArtists(@Query("ids") String artistIds);

    /**
     * Tracks *
     */

    @GET("tracks/{id}")
    public Call<Track> getTrack(@Path("id") String trackId);

    @GET("tracks")
    public Call<Tracks> getTracks(@Query("ids") String trackIds);

    /**
     * Browse *
     */

    @GET("browse/featured-playlists")
    public Call<FeaturedPlaylists> getFeaturedPlaylists(@QueryMap Map<String, String> options);

    @GET("browse/featured-playlists")
    public Call<FeaturedPlaylists> getFeaturedPlaylists(@QueryMap Map<String, String> options, @Query("offset") int offset, @Query("limit") int limit);

    @GET("browse/new-releases")
    public Call<NewReleases> getNewReleases();

    @GET("browse/new-releases")
    public Call<NewReleases> getNewReleases(@Query("country") String country);

    @GET("browse/new-releases")
    public Call<NewReleases> getNewReleases(@Query("country") String country, @Query("offset") int offset, @Query("limit") int limit);


    /**
     * Library / Your Music *
     */

    @GET("me/tracks")
    public Call<Pager<SavedTrack>> getMySavedTracks();

    @GET("me/tracks")
    public Call<Pager<SavedTrack>> getMySavedTracks(@Query("offset") int offset, @Query("limit") int limit);

    @GET("me/tracks/contains")
    public Call<Boolean[]> containsMySavedTracks(@Query("ids") String ids);

    // todo: process status code and return boolean
    @PUT("me/tracks")
    public Call<Boolean> addToMySavedTracks(@Query("ids") String ids);

    // todo: process status code and return boolean
    @DELETE("me/tracks")
    public Call<Boolean> removeFromMySavedTracks(@Query("ids") String ids);

    /**
     * Search *
     */

    @GET("search?type=track")
    public Call<TracksPager> searchTracks(@Query("q") String q);

    @GET("search?type=track")
    public Call<TracksPager> searchTracks(@Query("q") String q, @Query("market") String market);

    @GET("search?type=track")
    public Call<TracksPager> searchTracks(@Query("q") String q, @Query("offset") int offset, @Query("limit") int limit);

    @GET("search?type=track")
    public Call<TracksPager> searchTracks(@Query("q") String q, @Query("market") String market, @Query("offset") int offset, @Query("limit") int limit);

    @GET("search?type=artist")
    public Call<ArtistsPager> searchArtists(@Query("q") String q);

    @GET("search?type=artist")
    public Call<ArtistsPager> searchArtists(@Query("q") String q, @Query("market") String market);

    @GET("search?type=artist")
    public Call<ArtistsPager> searchArtists(@Query("q") String q, @Query("offset") int offset, @Query("limit") int limit);

    @GET("search?type=artist")
    public Call<ArtistsPager> searchArtists(@Query("q") String q, @Query("market") String market, @Query("offset") int offset, @Query("limit") int limit);

    @GET("search?type=album")
    public Call<AlbumsPager> searchAlbums(@Query("q") String q);

    @GET("search?type=album")
    public Call<AlbumsPager> searchAlbums(@Query("q") String q, @Query("market") String market);

    @GET("search?type=album")
    public Call<AlbumsPager> searchAlbums(@Query("q") String q, @Query("offset") int offset, @Query("limit") int limit);

    @GET("search?type=album")
    public Call<AlbumsPager> searchAlbums(@Query("q") String q, @Query("market") String market, @Query("offset") int offset, @Query("limit") int limit);

    @GET("search?type=playlist")
    public Call<PlaylistsPager> searchPlaylists(@Query("q") String q);

    @GET("search?type=playlist")
    public Call<PlaylistsPager> searchPlaylists(@Query("q") String q, @Query("market") String market);

    @GET("search?type=playlist")
    public Call<PlaylistsPager> searchPlaylists(@Query("q") String q, @Query("offset") int offset, @Query("limit") int limit);

    @GET("search?type=playlist")
    public Call<PlaylistsPager> searchPlaylists(@Query("q") String q, @Query("market") String market, @Query("offset") int offset, @Query("limit") int limit);
}
