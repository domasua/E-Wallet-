package smworks.lt.helloandroid;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("home")
    Call<ResponseBody> getHome();

    @FormUrlEncoded
    @POST("login")
    Call<User> login(@Field("email") String email,
                     @Field("password") String password);

    @FormUrlEncoded
    @POST ("registration")
    Call<ResponseBody> registration(@Field("email") String email,
                            @Field("password") String password,
                            @Field("confirmPassword") String confirmPassword);

    @FormUrlEncoded
    @POST ("desktop")
    Call<Wallet> desktop (@Field("id") Integer id,
                          @Field("cardbarcode") String cardbarcode);


}
