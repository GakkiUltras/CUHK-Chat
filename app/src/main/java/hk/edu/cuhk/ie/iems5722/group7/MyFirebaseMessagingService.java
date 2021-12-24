package hk.edu.cuhk.ie.iems5722.group7;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import hk.edu.cuhk.ie.iems5722.group7.chatview.ChatActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";;
    private static int count=0;

    // Other overridden methods
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message notification payload: " +
                    remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody(),
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getTag());
            this.count++;
        }
    }

    private void sendRegistrationToServer(String token) {
        // Implement your own logic to submit token to server
        HashMap<String, String> params = new HashMap<String, String>();
        final String STUDENT_ID = getString(R.string.STUDENT_ID);
        params.put("user_id",STUDENT_ID);
        params.put("token", token);

        String urlSubmitPushToken=getString(R.string.API_submit_push_token);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST,urlSubmitPushToken, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").equals("OK")) {
                        ;
                    } else {
                        Log.d(TAG, "status==Error");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        });
        VolleyController.getInstance(this).addToRequestQueue(jsObjRequest);
    }

    private void sendNotification(String messageBody, String messageTitle,String chatroom_id) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Set bundle to Open the specific chatroom
        Bundle bundle=new Bundle();
        bundle.putInt("id", Integer.parseInt(chatroom_id));
        bundle.putString("name",messageTitle);
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, count, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Create the notification builder which is used to build a customized notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

//        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(this.count /* ID of notification */, builder.build());
    }


}