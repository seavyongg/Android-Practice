
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitApi {
    @GET("api/v1/category")
    fun getCategories(): Call<Categories>


}
