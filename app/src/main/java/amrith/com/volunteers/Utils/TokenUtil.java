package amrith.com.volunteers.Utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

/**
 * Created by amrith on 6/14/17.
 */

public class TokenUtil {

    public static void getFirebaseToken(final Listener listener ){

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

        if(mUser!=null) {

            mUser.getToken(false).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                @Override
                public void onComplete(@NonNull Task<GetTokenResult> task) {
                    if (task.isSuccessful()) {
                        listener.tokenObtained(task.getResult().getToken());
                    } else {
                        Log.d("Token","ERROR!!!");
                    }
                }
            });

        }

        else{
            listener.tokenObtained("");
        }

    }
    public static interface Listener{
        public void tokenObtained(String token);
    }

}
