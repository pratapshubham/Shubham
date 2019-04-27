package com.braintech.carpooling.common.push;

import android.util.Log;

import com.braintech.carpooling.common.helpers.FcmSession;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Braintech on 8/8/2016.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {

        String token = "cmjTyLMB3_A:APA91bHv_relALYIiCvoXYjnBE0HBJu8HZOws-o0suqAy0TibJVer6YSWsgSYBuj1y_DNCuDhwyqCLJKVUqdQ6XqS8vnPpY25Z4hcnWfyRzBzRJ9qIvWUmqhrGqjEKZTKONROuV4EmrXkEYiH6B_KETxAh9XHV8NfA";
        //Getting registration tokerefreshedTokenn
        String refreshedToken  = FirebaseInstanceId.getInstance().getToken();

        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        FcmSession fcmSession = new FcmSession(this);
        fcmSession.saveFcmToken(token);
    }
}
