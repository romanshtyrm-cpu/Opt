package com.zaeb.app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class AudioService extends Service {

    private static final String CHANNEL_ID = "zae_audio_channel";

    @Override
    public void onCreate() {
        super.onCreate();
        createChannel();

        Notification notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("Zae Service")
                .setContentText("Recording audio...")
                .setSmallIcon(android.R.drawable.ic_btn_speak_now)
                .build();

        startForeground(1, notification);

        // 👉 Здесь будет логика записи/стриминга
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Audio Service",
                    NotificationManager.IMPORTANCE_LOW
            );

            NotificationManager manager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 👉 Логика запуска записи
        return START_STICKY; // сервис продолжит работу даже после убийства процессом
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 👉 Останов твоей аудио логики
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null; // сервис не привязанный
    }
}