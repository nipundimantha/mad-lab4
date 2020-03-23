package com.madlab4.notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The Main Activity program implements an application that
 * simply displays the notification and clicking notification it transfer to the registration
 *
 * @author  Dimantha H.V.N
 * @version 1.0
 * @since   2020-03-23
 *
 */

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = String.valueOf(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //notification channel support library initializing

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    //moving to the signUp page

    public void signUp(View view) {

        EditText text = (EditText) findViewById(R.id.editTextname);
        String message = text.getText().toString();

        // checking the input field empty or not

        if (message.equals("")){

            Context context = getApplicationContext(); //The context to use. Usually your Application or Activity object
            CharSequence errorMessage = "Please enter the Name, Then click the button";
            //Display string
            int duration = Toast.LENGTH_SHORT; //How long the toast message will lasts
            Toast toast = Toast.makeText(context, errorMessage, duration);
            toast.show();

        }else {

            Intent intent = new Intent(this, Registration.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification_icon)
                    .setContentTitle("Notification")
                    .setContentText("Hello " + message + " Welcome to MAD Team")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);


            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(0, builder.build());

        }
    }
}

