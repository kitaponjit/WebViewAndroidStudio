package com.example.kitaponjit.iteamproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ConfigActivity extends AppCompatActivity {

    EditText editText;
    Button editConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        editText = findViewById(R.id.editText);
        editConfig = findViewById(R.id.editConfig);

        editConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigActivity.this , MainActivity.class);
                intent.putExtra("host", editText.getText().toString().trim());

                Notification notification =
                        new NotificationCompat.Builder(ConfigActivity.this) // this is context
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("iTeam Management")
                                .setContentText("ยินดีต้อนรับสู่ iTeam Project Management")
                                .setAutoCancel(true)
                                .build();

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(1000, notification);

                startActivity(intent);
                finish();
            }
        });

        String host = PrefsUtils.getHost(this);
        if(host != null){
            Intent intent = new Intent(ConfigActivity.this , MainActivity.class);
            intent.putExtra("host", host);
            startActivity(intent);
            finish();
        }

    }
}
