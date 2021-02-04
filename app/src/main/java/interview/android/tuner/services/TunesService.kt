package interview.android.tuner.services

import interview.android.tuner.model.AppleApiRequest
import retrofit2.Call
import retrofit2.http.GET

/* @desc request service which will make itself available at the core module through di
 * @param optional will insert into the endpoint as {...} format
 * @return callback to the specified class type Call<ClassType>*/
interface TunesService {

    /* @API_FORMAT "/{feedtype}/{genre}/{resultslimin}/{format}") */

    @GET("api/v1/us/apple-music/coming-soon/all/50/explicit.json")
    fun getComingSoon(
    ) : Call<AppleApiRequest>

    @GET("api/v1/us/apple-music/hot-tracks/all/50/explicit.json")
    fun getHotTracks(
    ) : Call<AppleApiRequest>

    @GET("api/v1/us/apple-music/new-releases/all/50/explicit.json")
    fun getNewReleases(
    ) : Call<AppleApiRequest>

    @GET("api/v1/us/apple-music/top-albums/all/50/explicit.json")
    fun getTopAlbums(
    ) : Call<AppleApiRequest>

    @GET("api/v1/us/apple-music/top-songs/all/50/explicit.json")
    fun getTopSongs(
    ) : Call<AppleApiRequest>
}