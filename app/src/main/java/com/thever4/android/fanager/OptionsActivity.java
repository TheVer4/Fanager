package com.thever4.android.fanager;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class OptionsActivity extends AppCompatActivity {

    String internal, external;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        internal = this.getExternalFilesDirs(null)[0].toString();
        external = this.getExternalFilesDirs(null)[1].toString();

        internal = internal.substring(0, internal.length() - "/Android/data/com.thever4.android.fanager/files".length());
        external = external.substring(0, external.length() - "/Android/data/com.thever4.android.fanager/files".length());

        Log.d("DIRS", internal);
        Log.d("DIRS", external);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //Block screen orientation if VERTICAL position.

    }

    /*@Override
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
    */



    public void goInternal(View view) {
        Intent intent = new Intent(getApplicationContext(), FileViewActivity.class);
        intent.putExtra("CURRENT_DIR", internal);
        startActivity(intent);
        /*overridePendingTransition(R.anim.slide, R.anim.alpha);*/
        finish();
    }

    public void  goExternal(View view) {
        Intent intent = new Intent(getApplicationContext(), FileViewActivity.class);
        intent.putExtra("CURRENT_DIR", external);
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
