package pallav.bakcet.schoolmanagement.pojo


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data")
    val `data`: Data?,
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