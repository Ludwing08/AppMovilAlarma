package dev.android.appalarma.clases;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import dev.android.appalarma.BotonActivity;
import dev.android.appalarma.MainActivity;
import dev.android.appalarma.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Map<String, String> data = remoteMessage.getData();

        Log.d(TAG,"FROM: "+ remoteMessage.getFrom());

        if (remoteMessage.getData().size()>0){
            Log.d(TAG, "Message: " + remoteMessage.getData());

            if (true){

            }else{
                handleNow();
            }

        }

        if(remoteMessage.getNotification() != null){
            Log.d(TAG, "Mssg: " + remoteMessage.getNotification().getBody());
            mostrar(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }

    private  void sendRegistrationToServer(String token){

    }

    @Override
    public void onNewToken(@NonNull @org.jetbrains.annotations.NotNull String token) {
        Log.d(TAG,"token "+ token);
        sendRegistrationToServer(token);
        super.onNewToken(token);
    }

    private void handleNow(){
        Log.d(TAG,"OK");
    }

    private void mostrar(String title, String body) {
       Intent intent = new Intent(this, BotonActivity.class);
       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_ONE_SHOT);

       Uri defaultUir = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
       NotificationCompat.Builder notificactionBuilder = new NotificationCompat.Builder(this)
               .setSmallIcon(R.drawable.ic_boton_panico)
               .setContentTitle(title)
               .setContentText(body)
               .setAutoCancel(true)
               .setSound(defaultUir)
               .setContentIntent(pendingIntent);

       NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
       notificationManager.notify(0,notificactionBuilder.build());



    }
}


