package cmc.sole.android.Signup.Retrofit

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface SignupRetrofitInterface {

    @POST("/api/members/{provider}")
    fun socialCheck(
        @Path("provider") provider: String?,
        @Body signupCheckRequest: SignupCheckRequest
    ): Call<SignupCheckResponse>

    @POST("/api/members/nickname")
    fun checkNickname(
        @Body nicknameRequest: SignupNicknameRequest
    ): Call<Boolean>

    @Multipart
    @POST("/api/members/{provider}/signup")
    fun socialSignup(
        @Path("provider") provider: String?,
        @Part memberRequestDto: MultipartBody.Part,
        @Part multipartFile: MultipartBody.Part?
    ): Call<SignupSocialResponse>

    @PATCH("/api/members/logout")
    fun logout(): Call<Void>
}