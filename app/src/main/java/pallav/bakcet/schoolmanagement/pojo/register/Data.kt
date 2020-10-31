package pallav.bakcet.schoolmanagement.pojo.register


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("token")
    val token: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("first_name")
    val first_name: String,
    @SerializedName("last_name")
    val last_name: String,
    @SerializedName("email")
    val email: String
)