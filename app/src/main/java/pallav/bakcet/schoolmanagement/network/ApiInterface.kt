package pallav.bakcet.schoolmanagement.network

import io.reactivex.Single
import pallav.bakcet.schoolmanagement.pojo.login.LoginResponse
import pallav.bakcet.schoolmanagement.pojo.mark_attendance.MarkAttendanceResponse
import pallav.bakcet.schoolmanagement.pojo.register.RegisterResponse
import retrofit2.http.*

interface ApiInterface {
    /**
     * POST
     */
    @FormUrlEncoded
    @POST("login/")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Single<LoginResponse>

    @FormUrlEncoded
    @POST("register/")
    fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("year") year: Int,
        @Field("password") password: String
    ): Single<RegisterResponse>


    @FormUrlEncoded
    @POST("mark-attendance/")
    fun mark_attendance(
        @Field("qr_code") qr_code: String
    ): Single<MarkAttendanceResponse>


    /**
     * GET
     */
    @GET("userhome/{latitude}/{longitude}/{page_no}")
    fun getHomepageDetails(
        @Path("latitude") latitude: String,
        @Path("longitude") longitude: String,
        @Path("page_no") page_no: String
    ): Single<Any>
}