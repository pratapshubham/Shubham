package com.braintech.carpooling.common.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.braintech.carpooling.R;
import com.braintech.carpooling.common.helpers.FcmSession;
import com.braintech.carpooling.common.requestresponse.ConstIntent;
import com.braintech.carpooling.mvp.offer_a_ride.activity.RideConfirmationActivity;
import com.braintech.carpooling.mvp.your_rides.activity.YourRidesActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

/**
 * Created by Braintech on 8/8/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    int notificationId = 0;

    FcmSession fcmSession;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        try {

            //Displaying data in log
            //It is optional
            Log.d(TAG, "From: " + remoteMessage.getFrom());
            Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification());
            Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

            fcmSession = new FcmSession(this);

            //Calling method to generate notification
            sendNotification(remoteMessage.getNotification().getBody());

            //sendBroadcast();

        } catch (NullPointerException ee) {
            ee.printStackTrace();

            Log.d(TAG, "Notification Receive From : " + remoteMessage.getFrom());
        }
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String messageBody) {

        notificationId = fcmSession.getFcmCount();
        notificationId++;
        fcmSession.saveFcmCount(notificationId);

        Log.e("notification", "" + notificationId);
        Intent intent = new Intent(this, RideConfirmationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notificationId, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        intent.putExtra(ConstIntent.KEY_NOTIFICATION_MESSAGE_BODY,messageBody);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,"M_CH_ID")
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.app_icon))
                .setSmallIcon(R.mipmap.app_icon)
                .setContentTitle("Car Pooling")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, notificationBuilder.build());

        //refreshActivity();
    }


   /* private void refreshActivity() {

        Intent intent = App.getInstance().getIntent();
        App.getInstance().overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        App.getInstance().finish();
        overridePendingTransition(0, 0);
        startActivity(intent);

        try {

            // Using ACTIVITY_SERVICE with getSystemService(String)
            // to retrieve a ActivityManager for interacting with the global system state.

            ActivityManager am = (ActivityManager) this
                    .getSystemService(Context.ACTIVITY_SERVICE);

            // Return a list of the tasks that are currently running,
            // with the most recent being first and older ones after in order.
            // Taken 1 inside getRunningTasks method means want to take only
            // top activity from stack and forgot the olders.

            List<ActivityManager.RunningTaskInfo> alltasks = am
                    .getRunningTasks(1);


            //
            for (ActivityManager.RunningTaskInfo aTask : alltasks) {


                ComponentName componentName = aTask.topActivity;

                String className = componentName.getClassName();
                String shortClassName = componentName.getShortClassName();

                String actionName = null;

                if (shortClassName.contains("DashboardActivity")) {
                    actionName = BroadCastReceiverActionName.DASHBOARD_ACTIVITY;
                } else if (shortClassName.contains("AllListingActivity")) {
                    actionName = BroadCastReceiverActionName.ALL_LISTING_ACTIVITY;
                } else if (shortClassName.contains("CategoriesActivity")) {
                    actionName = BroadCastReceiverActionName.CATEGORY_ACTIVITY;
                } else if (shortClassName.contains("MyListingActivity")) {
                    actionName = BroadCastReceiverActionName.MY_LISTING_ACTIVITY;
                } else if (shortClassName.contains("EditProfileActivity")) {
                    actionName = BroadCastReceiverActionName.EDIT_PROFILE_ACTIVITY;
                } else if (shortClassName.contains("MyListingDetailActivity")) {
                    actionName = BroadCastReceiverActionName.MY_LIST_DETAIL_ACTIVITY;
                } else if (shortClassName.contains("MessageListActivity")) {
                    actionName = BroadCastReceiverActionName.MESSAGE_LIST_ACTIVITY;
                } else if (shortClassName.contains("NotificationActivity")) {
                    actionName = BroadCastReceiverActionName.NOTIFICATION_ACTIVITY;
                } else if (shortClassName.contains("ReplyMessageActivity")) {
                    actionName = BroadCastReceiverActionName.REPLY_MESSAGE_ACTIVITY;
                } else if (shortClassName.contains("MessageDetailActivity")) {
                    actionName = BroadCastReceiverActionName.MESSAGE_DETAIL_ACTIVITY;
                } else if (shortClassName.contains("NewListingActivity")) {
                    actionName = BroadCastReceiverActionName.NEW_LISTING_ACTIVITY;
                }

                if (actionName != null) {
                    Intent intent = new Intent(actionName);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                }
            }

        } catch (Throwable t) {
            Log.i(TAG, "Throwable caught: "
                    + t.getMessage(), t);
        }


    }
*/

   /* public void sendBroadcast() {

        Intent intent = new Intent(ConstIntent.KEY_SEND_MESSAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }*/
}
