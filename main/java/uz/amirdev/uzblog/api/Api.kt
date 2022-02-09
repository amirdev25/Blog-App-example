package uz.amirdev.uzblog.api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import uz.amirdev.uzblog.models.posts.PostModel
import uz.amirdev.uzblog.models.user.UserModel

interface Api {

    @Headers("app-id: 61f2cdf17df80468faa05aaa")
    @GET("user?limit=20")
    fun getUsers(): Call<UserModel>


    @Headers("app-id: 61f2cdf17df80468faa05aaa")
    @GET("post?limit=20")
    fun getPosts(): Call<PostModel>

    @Headers("app-id: 61f2cdf17df80468faa05aaa")
    @GET("user/{user_id}/post?limit=20")
    fun getPostsByUser(@Path("user_id") id: String): Call<PostModel>

}