package pallav.bakcet.schoolmanagement.network

import io.reactivex.Single
import pallav.bakcet.schoolmanagement.pojo.LoginResponse
import retrofit2.http.*

interface ApiInterface {
    /**
     * POST
     */
    @FormUrlEncoded
    @POST("login/")
    fun login(
        @Field("username") name: String,
        @Field("password") email: String
    ): Single<LoginResponse>



    /**
     * GET
     */
    @GET("userhome/{latitude}/{longitude}/{page_no}")
    fun getHomepageDetails(
        @Path("latitude") latitude:String,
        @Path("longitude") longitude:String,
        @Path("page_no") page_no:String
    ):Single<Any>
}