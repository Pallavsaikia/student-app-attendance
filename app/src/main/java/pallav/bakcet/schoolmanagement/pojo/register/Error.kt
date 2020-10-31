package pallav.bakcet.schoolmanagement.pojo.register


import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("username")
    val username: List<String>?,
    @SerializedName("password")
    val password: List<String>?
)