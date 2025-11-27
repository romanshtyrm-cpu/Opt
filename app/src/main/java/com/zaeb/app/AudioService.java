package com.zaeb.app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class AudioService extends Service {

    private static final String CHANNEL_ID = "audio_channel";
    private MediaRecorder recorder;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        startForeground(1, createNotification());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null; // сервис не позволяет биндинг
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        startRecording();

        return START_STICKY;
    }

    private void startRecording() {
        try {
            if (recorder != null) {
                recorder.release();
            }

            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC); // микрофон
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            // Файл записи (временный)
            recorder.setOutputFile(getExternalCacheDir() + "/rec.3gp");

            recorder.prepare();
            recorder.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Notification createNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Audio service running")
                .setContentText("Микрофон активен")
                .setSmallIcon(android.R.drawable.ic_btn_speak_now)
                .setOngoing(true)
                .build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            "Audio Microphone Service",
                            NotificationManager.IMPORTANCE_LOW
                    );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (recorder != null) {
                recorder.stop();
                recorder.release();
            }
        } catch (Exception ignored) {}
    }
}