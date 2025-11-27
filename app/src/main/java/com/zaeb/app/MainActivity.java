package com.zaeb.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent svc = new Intent(MainActivity.this, AudioService.class);
                startForegroundService(svc);
                Toast.makeText(MainActivity.this, "AudioService started", Toast.LENGTH_SHORT).show();
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
}