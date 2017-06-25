package amrith.com.volunteers.Utils;

import android.text.BoringLayout;

import amrith.com.volunteers.models.Admin;
import amrith.com.volunteers.models.Event;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by amrith on 6/14/17.
 */

public interface RestApiInterface {

    @FormUrlEncoded
    @POST("volunteer-admin/auth/login")
    Call<Admin> login(@Field("idToken") String idToken);

    @FormUrlEncoded
    @POST("volunteer-admin/auth/register")
    Call<String> register(@Field("idToken") String idToken, @Field("phone") String phone, @Field("college")String college, @Field("registered")Boolean registered);

    @FormUrlEncoded
    @POST("volunteer-admin/event/newEvent")
    Call<Event> createEvent(@Field("idToken")String idToken,@Field("name")String name,@Field("regFee") String fee,@Field("desc")String desc,@Field("date")String date,@Field("time")String time);
}
