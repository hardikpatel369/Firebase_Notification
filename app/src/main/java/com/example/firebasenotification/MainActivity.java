package com.example.firebasenotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

import static com.example.firebasenotification.NetworkStateChangeReceiver.IS_NETWORK_AVAILABLE;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkFilter();
        topicSubscribe();
    }

    private void topicSubscribe() {
        FirebaseMessaging.getInstance().subscribeToTopic("8401293964")
                .addOnCompleteListener(task -> {
                    String msg = "Topic Subscribed Successfully";
                    if (!task.isSuccessful()) {
                        msg = "Fail To Subscribe!";
                    }
                    Log.d(TAG, msg);
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                });
    }

    private void networkFilter() {
        IntentFilter intentFilter = new IntentFilter(NetworkStateChangeReceiver.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);

                String networkStatus = isNetworkAvailable ? "connected" : "disconnected";
                Snackbar.make(findViewById(R.id.activity_main), "Network " + networkStatus, Snackbar.LENGTH_LONG).show();
            }
        }, intentFilter);
    }
}