package com.thever4.android.fanager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
}
