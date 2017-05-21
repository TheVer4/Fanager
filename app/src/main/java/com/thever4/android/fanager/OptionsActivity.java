package com.thever4.android.fanager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //Block screen orientation if VERTICAL position.

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //OnCreateOptionsMenu to create ActionBar Menu
        getMenuInflater().inflate(R.menu.optionsview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.ovit1:
                startActivity(new Intent(getApplicationContext(), FileViewActivity.class));
                overridePendingTransition(R.anim.slide, R.anim.alpha);
                finish();
                break;
            case R.id.ovit2:

                break;
        }
        return super.onOptionsItemSelected(item);
    }




    public void goInternal(View view) {
        Intent intent = new Intent(getApplicationContext(), FileViewActivity.class);
        intent.putExtra("CURRENT_DIR", "/sdcard");
        startActivity(intent);
        /*overridePendingTransition(R.anim.slide, R.anim.alpha);*/
        finish();
    }

    public void  goExternal(View view) {
        Intent intent = new Intent(getApplicationContext(), FileViewActivity.class);
        intent.putExtra("CURRENT_DIR", Environment.getExternalStorageDirectory().toString());
        startActivity(intent);
        /*overridePendingTransition(R.anim.slide, R.anim.alpha);*/
        finish();
    }

    public void goFSRoot(View view) {
        Intent intent = new Intent(getApplicationContext(), FileViewActivity.class);
        /*intent.putExtra("CURRENT_DIR", null);*/
        startActivity(intent);
        /*overridePendingTransition(R.anim.slide, R.anim.alpha);*/
        finish();
    }

}
