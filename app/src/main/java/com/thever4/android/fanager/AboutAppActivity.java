package com.thever4.android.fanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AboutAppActivity extends AppCompatActivity {

    Intent intent;
    static String dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        intent = getIntent();
        dir = intent.getStringExtra("dir");
    }



    @Override
    public void onBackPressed() {
        Intent intent1 = new Intent(this, FileViewActivity.class);
        intent1.putExtra("CURRENT_DIR", dir);
        startActivity(intent1);
        finish();
    }
}
