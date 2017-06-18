package amrith.com.volunteers.Utils;

import amrith.com.volunteers.models.Admin;
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

}
