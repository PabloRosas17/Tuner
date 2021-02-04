package interview.android.tuner.model

import com.google.gson.JsonArray
import com.google.gson.JsonObject

/* @desc models an object that will attach read api call from Apple iTunes Rss feed
*  @param various fields with their respective data types
*  @return model with bounded fields */
data class AppleApiModel(
    var title: String
    , var id: String
    , var author: JsonObject
    , var links: JsonArray
    , var copyright: String
    , var country: String
    , var icon: String
    , var updated: String
    , var results: JsonArray
)

/* @desc models an object that will attach read api call from Apple iTunes Rss feed
*  @param various fields with their respective data types
*  @return model with bounded fields
*  @note this model will be used to filer AppleApiModel.results JsonArray */
data class AppleApiModelResults(
    var artistName: String
    , var id: String
    , var releaseDate: String
    , var name: String
    , var kind: String
    , var copyright: String
    , var artistId: String
    , var artistUrl: String
    , var artworkUrl100: String
    , var genres: JsonArray
    , var url: String
)

/* @desc models an object that will attach read api call from Apple iTunes Rss feed
*  @param various fields with their respective data types
*  @return model with bounded fields */
data class AppleApiModelHotTrackResults(
    var id: String
    , var name: String
    , var kind: String
    , var artworkUrl100: String
    , var genres: JsonArray
    , var url: String
)

/* @desc models an object that will attach read api call from Apple iTunes Rss feed
*  @param various fields with their respective data types
*  @return model with bounded fields
*  @note this model will be used to filer AppleApiModelHotTrackResults.genres JsonArray */
data class AppleApiModeResultsGenres(
    var genreId: String
    , var name: String
    , var url: String
)