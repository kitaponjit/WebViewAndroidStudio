package com.example.kitaponjit.iteamproject;

import android.content.Intent;
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
