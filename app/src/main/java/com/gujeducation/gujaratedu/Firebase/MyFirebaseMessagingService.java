package com.gujeducation.gujaratedu.Firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.gujeducation.R;
import com.gujeducation.gujaratedu.Activity.HomeScreen;
import com.gujeducation.gujaratedu.Activity.LanguageScreen;
import com.gujeducation.gujaratedu.Activity.MyNotification;
import com.gujeducation.gujaratedu.Activity.SignInScreen;
import com.gujeducation.gujaratedu.Activity.SplashScreen;
import com.gujeducation.gujaratedu.Helper.Functions;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    Functions mFunction;

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        Log.e(TAG, "Refreshed token: " + token);
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
        }


        if (remoteMessage.getData() != null && remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Server payload: " + remoteMessage.getData());
            String title = "" + remoteMessage.getData().get("title");
            String message = "" + remoteMessage.getData().get("subtitle");

            Log.e(TAG, " title-" + title + " message-" + message);
            sendNotification(title, message);
        }
        Log.e(TAG, "Received: " + remoteMessage.toString());
    }


    private void sendNotification(String strTitle, String strMsg) {
        mFunction = new Functions(this);

        Log.e(TAG, "Preparing to send notification...: " + strTitle + " " + strMsg + "\nchklogin-"+mFunction.getPrefLogin());

        Intent intent;

        if (mFunction.getPrefLogin()) {
            intent = new Intent(this, MyNotification.class);
        } else {
            intent = new Intent(this, LanguageScreen.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        int requestID = (int) System.currentTimeMillis();
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                requestID, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("ID", "Name", importance);
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(getApplicationContext(), notificationChannel.getId());
        } else {
            builder = new NotificationCompat.Builder(getApplicationContext());
        }

        builder = builder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(contentIntent)
                .setContentTitle(strTitle)
                .setContentText(strMsg)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setVibrate(new long[]{100, 100, 0, 0});
        notificationManager.notify(1, builder.build());
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*mBuilder.setAutoCancel(true);
        if (!strMsg.contains("Your OTP is"))
            mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID++, mBuilder.build());*/
        Log.e(TAG, "Notification sent successfully.");
    }

}