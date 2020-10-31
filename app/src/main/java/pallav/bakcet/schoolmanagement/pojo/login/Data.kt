package pallav.bakcet.schoolmanagement.pojo.login


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("token")
    val token: String
)