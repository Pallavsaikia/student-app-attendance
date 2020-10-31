package pallav.bakcet.schoolmanagement.pojo.login


import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("username")
    val username: List<String>?,
    @SerializedName("password")
    val password: List<String>?
)