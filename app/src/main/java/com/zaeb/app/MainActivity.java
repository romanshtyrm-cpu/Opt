package com.zaeb.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends Activity {

    private static final int REQUEST_AUDIO = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Проверка разрешения RECORD_AUDIO
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(
                            MainActivity.this,
                            new String[]{Manifest.permission.RECORD_AUDIO},
                            REQUEST_AUDIO
                    );

                } else {
                    startMicrophoneService();
                }
            }
        });

        Button btnStop = findViewById(R.id.btn_stop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent svc = new Intent(MainActivity.this, AudioService.class);
                stopService(svc);
                Toast.makeText(MainActivity.this, "AudioService stopped", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Запуск сервиса — вынесено в отдельный метод
    private void startMicrophoneService() {
        Intent svc = new Intent(MainActivity.this, AudioService.class);
        startForegroundService(svc);
        Toast.makeText(MainActivity.this, "AudioService started", Toast.LENGTH_SHORT).show();
    }

    // Обработка результата запроса разрешения
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_AUDIO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startMicrophoneService();
            } else {
                Toast.makeText(this, "Разрешение RECORD_AUDIO нужно для работы сервиса", Toast.LENGTH_LONG).show();
            }
        }
    }
}