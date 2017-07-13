package amrith.com.volunteers.Utils;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by amrith on 6/14/17.
 */

public class Global {

    public final static int SUCCESS_CODE = 200;
    public static final String SHARED_PREF = "volunteers_shared_pref";

    public static String uid;
    public static String id;
    public static String user;
    public static String college;
    public static String picture;
    public static String name;
    public static String email;
    public static int eventId;
    public static boolean offline=false;
    public static String FRIENDLY_MSG_LENGTH="friendly_msg_length";
    public static ArrayList<String> teamList=new ArrayList<>(Arrays.asList("Accomodation","Food and Venue","Publicity",
                                                "Sessions","Sponsorship","Registration"));
    public static ArrayList<String> accessList=new ArrayList<>(Arrays.asList("Super Admin","Admin","Volunteer","View"));
}
