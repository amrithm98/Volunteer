package amrith.com.volunteers.Utils;

import android.text.BoringLayout;

import java.util.List;

import amrith.com.volunteers.models.Admin;
import amrith.com.volunteers.models.College;
import amrith.com.volunteers.models.Event;
import amrith.com.volunteers.models.Feed;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by amrith on 6/14/17.
 */

public interface RestApiInterface {

    @FormUrlEncoded
    @POST("volunteer-admin/auth/login")
    Call<Admin> login(@Field("idToken") String idToken);

    @FormUrlEncoded
    @POST("volunteer-admin/auth/register")
    Call<String> register(@Field("idToken") String idToken, @Field("phone") String phone,
                          @Field("collegeId")int college, @Field("registered")Boolean registered);

    @FormUrlEncoded
    @PUT("volunteer-admin/event/newEvent")
    Call<Event> createEvent(@Field("idToken")String idToken,@Field("name")String name,@Field("regFee") String fee,
                            @Field("desc")String desc, @Field("date")String date,
                            @Field("time")String time,@Field("adminUid")String uid);

    @FormUrlEncoded
    @POST("volunteer-admin/event/myEvents")
    Call<List<Event>> getEventList(@Field("idToken")String token,@Field("adminUid")String uid);

    @GET("public/college/")
    Call<List<College>> getCollegeList();

    @FormUrlEncoded
    @PUT("volunteer-admin/college/")
    Call<College> addCollege(@Field("idToken") String idToken,@Field("name") String collegeName);

    @FormUrlEncoded
    @POST("volunteer-admin/feed/eventFeeds")
    Call<List<Feed>> getAllFeed(@Field("idToken")String idToken,@Field("eventId") int eventId);

    @FormUrlEncoded
    @PUT("volunteer-admin/feed/new")
    Call<Feed> newFeed(@Field("idToken") String token,@Field("desc") String description,
                       @Field("ownerName")String name,@Field("ownerImage") String url,
                       @Field("adminUid")String uid,@Field("eventId") int eventId);

    @FormUrlEncoded
    @POST("volunteer-admin/college/people")
    Call<List<Admin>> getPeopleInCollege(@Field("idToken")String token,@Field("collegeId") int id);

}
