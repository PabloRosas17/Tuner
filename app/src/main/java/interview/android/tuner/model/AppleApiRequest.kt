package interview.android.tuner.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

/* @desc definition for api request
* @param mFeed of JsonObject type, serialized as "feed"
* @return model */
data class AppleApiRequest(
    @SerializedName("feed")
    var mFeed: JsonObject
)