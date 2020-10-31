package pallav.bakcet.schoolmanagement.pojo.mark_attendance


import com.google.gson.annotations.SerializedName

data class MarkAttendanceResponse(
    @SerializedName("data")
    val `data`: Any,
    @SerializedName("error")
    val error: Error?,
    @SerializedName("error_code")
    val errorCode: Int,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("error_message")
    val error_message: String?,
    @SerializedName("success")
    val success: Boolean
)