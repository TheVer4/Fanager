package com.thever4.android.fanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class FileViewActivity extends AppCompatActivity {

    boolean writeable;
    File CURRENT_DIR;
    String ENTERED_FILENAME;
    final String LOG_TAG = "FANAGER"; //LOG_TAG
    File file; //Main File manager
    HashMap<String, Object> hmfiles; //Array of string of file names that available in `file`
    ArrayList<HashMap<String, Object>> alfiles;
    SimpleAdapter arrayAdapter; //Adapter to ListView on layout
    long back_pressed; //long type variable to check exit
    String[] fromKey;
    int[] toId;
    private boolean HIDE_HIDDEN;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //Traditional onCreate
        setContentView(R.layout.activity_file_view); //Setting View
        alfiles = new ArrayList< HashMap<String, Object> >();
        fromKey = new String[] { "type", "item"};
        toId = new int[] {R.id.fstypeIV, R.id.filesTV};
        HIDE_HIDDEN = true;
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //Block screen orientation if VERTICAL position.

        Intent intent = getIntent();
        if(intent.getStringExtra("CURRENT_DIR") == null) loadArr("/"); //Using method `loadArr(String path)` with default value
        else {
            loadArr(intent.getStringExtra("CURRENT_DIR")); //Using method `loadArr(String path)` with intent value
        }

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        arrayAdapter = new SimpleAdapter(getApplicationContext(), alfiles, R.layout.adapterview, fromKey, toId); //Declaring of Adapter `arrayAdapter`
        ListView lv = (ListView) findViewById(R.id.fileListView); //Declaring of ListView on Layout with varName `lv`
        lv.setAdapter(arrayAdapter); //Installing adapter to `lv`
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() { //onItemClickListeners setting
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goDirDown((String) alfiles.get(i).get("item")); //Using method `goDirDown(String item)`
                arrayAdapter.notifyDataSetChanged(); //Reloading ListView values
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final File file;
                final String item = (String) alfiles.get(i).get("item");
                if(CURRENT_DIR.toString().equals("/")) file = new File("/" + item);
                else file = new File(CURRENT_DIR.toString() + "/" + item);
                AlertDialog.Builder adb = new AlertDialog.Builder(FileViewActivity.this);
                adb.setIcon(R.mipmap.ic_launcher);
                adb.setTitle("Deleting file");
                adb.setMessage("Are you sure want to delete \"" + item + "\"");
                adb.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(file.delete()) Toast.makeText(getApplicationContext(), "Item \"" + item + "\" deleted successful" , Toast.LENGTH_SHORT).show();
                        else Toast.makeText(getApplicationContext(), "Error while deleting item. If it directory, it must be empty!", Toast.LENGTH_SHORT).show();
                        loadArr(CURRENT_DIR.toString());
                        dialog.dismiss();
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                AlertDialog ad = adb.create();
                ad.show();

                return true;
            }
        });
    } //onCreate end

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //OnCreateOptionsMenu to create ActionBar Menu
        if(writeable) getMenuInflater().inflate(R.menu.fileviewpermissionsavailable, menu);
        else getMenuInflater().inflate(R.menu.fileviewpermissionsnotavailable, menu);
        this.menu = menu;
        return true;
    }

    private void changeMenu(File f) {

        if(f.isDirectory() && f.canWrite()) {
            writeable = true;
            super.invalidateOptionsMenu();
        }
        else {
            writeable = false;
            super.invalidateOptionsMenu();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            /*case R.id.fvit1:
                startActivity(new Intent(getApplicationContext(), OptionsActivity.class));
                overridePendingTransition(R.anim.slide, R.anim.alpha);
                finish();
                break;
            case R.id.fvit2:

                break;*/

            /*fvpai - decrypts as File View Permissions Available Id N*/

            /*case R.id.fvpai1: //SELECT

                break;*/
            case R.id.fvpaii1:
                Intent intentOptions = new Intent(getApplicationContext(), OptionsActivity.class);
                startActivity(intentOptions);
                finish();
                break;
            case R.id.fvpai2: //NEW FILE
                try {
                    if (CURRENT_DIR.isDirectory()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("New file");
                        builder.setMessage(null);
                        builder.setIcon(R.mipmap.ic_launcher);
                        builder.setCancelable(false);

                        final EditText input = new EditText(FileViewActivity.this);
                        /*LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);*/
                        input.setWidth(200);
                        input.setHint("Filename.txt");

                        builder.setView(input);
                        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() { // Кнопка ОК
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ENTERED_FILENAME = input.getText().toString();
                                try {
                                    if (ENTERED_FILENAME != null && !ENTERED_FILENAME.trim().equals("")) {
                                        new File(CURRENT_DIR.toString() + "/" + ENTERED_FILENAME.trim()).createNewFile();
                                        loadArr(CURRENT_DIR.toString());
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Empty filename", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Unable to create file", Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss(); // Отпускает диалоговое окно
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    else Log.d(LOG_TAG, "ELSE ");
                }
                catch (Exception e) {
                    Log.d(LOG_TAG, "EXCEPTION " + e);
                }
                finally {
                    ENTERED_FILENAME = null;
                }
                break;
            case R.id.fvpai3: //CREATE FOLDER ================================================
                try {
                    if (CURRENT_DIR.isDirectory()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("New folder");
                        builder.setMessage(null);
                        builder.setIcon(R.mipmap.ic_launcher);
                        builder.setCancelable(false);

                        final EditText input = new EditText(FileViewActivity.this);
                        /*LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);*/
                        input.setWidth(200);
                        input.setHint("Folder name");

                        builder.setView(input);
                        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() { // Кнопка ОК
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ENTERED_FILENAME = input.getText().toString();
                                try {
                                    if (ENTERED_FILENAME != null && !ENTERED_FILENAME.trim().equals("")) {
                                        new File(CURRENT_DIR.toString() + "/" + ENTERED_FILENAME.trim()).mkdir();
                                        loadArr(CURRENT_DIR.toString());
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Empty directory name", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Unable to create folder", Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss(); // Отпускает диалоговое окно
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    else Log.d(LOG_TAG, "ELSE ");
                }
                catch (Exception e) {
                    Log.d(LOG_TAG, "EXCEPTION " + e);
                }
                finally {
                    ENTERED_FILENAME = null;
                }
                break;
            case R.id.fvpai4: //HIDE HIDDEN FILES
                MenuItem menuItem = menu.findItem(R.id.fvpai4);
                    if (HIDE_HIDDEN)  {
                        HIDE_HIDDEN = false;
                        menuItem.setTitle("Hide hidden files");
                    }
                    else {
                        HIDE_HIDDEN = true;
                        menuItem.setTitle("Show hidden files");
                    }
                    changeMenu(CURRENT_DIR);
                    loadArr(CURRENT_DIR.toString());
                    arrayAdapter.notifyDataSetChanged();
                break;
            case R.id.fvpai5: //ABOUT FANAGER
                Intent intent = new Intent(getApplicationContext(), AboutAppActivity.class);
                intent.putExtra("dir", CURRENT_DIR.toString());
                startActivity(intent);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void goDirUp() { //goDirUp method to Listen the higher directory in file system
        String fs = file.toString(); //String variable with name `fs` that encrypts as "file string" to translate `file` to String value
        Log.d(LOG_TAG, "VAR fs = " + fs); //inference to LogCat value of `fs` variable
        StringBuffer sb = new StringBuffer(fs); //Making exemplar of StringBuffer class to fraction of fs variable
        Log.d(LOG_TAG, "VAR sb = " + sb); //inference to LogCat value of... What for?!
        int lenin = sb.length() - 1; //Integer variable with name `lenin` (:D) that encrypts as "length in array" to use the loops
        Log.d(LOG_TAG, "VAR lenin = " + lenin); //OMG!!! What I did?!
        int i; //"Global" counter-variable as Integer
        for (i = lenin; sb.charAt(i) != '/'; i--) { //Crutches :3 Favorite crutches <3
            sb.deleteCharAt(i);
        }
        if (i > 0 && sb.charAt(i) == '/') sb.deleteCharAt(i); //and to final - deleting '/' char from buffer
        else if (i == 0 && sb.charAt(i) == '/') exit(); //Safety...
        Log.d(LOG_TAG, "VAR i = " + i); //What for?
        fs = sb.toString(); //And returning main StringBuffer value to `fs` variable
        Log.d(LOG_TAG, "VAR fs = " + fs); //...
        loadArr(fs); //Using method `loadArr(String path)`
        arrayAdapter.notifyDataSetChanged(); //Reloading ListView values
    }

    private void exit() { //exit() method to go out the application. It used that "BACK" key is reassigned
        if (back_pressed + 2000 > System.currentTimeMillis() && CURRENT_DIR.toString().equals("/")) /*super.onBackPressed();*/ finish(); //Genius!!!!!!
        else Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis(); //no more! Its so fun :D
    }

    @Override
    public void onBackPressed() { //And... Reassigning of "BACK" key to use the `goDirUp()` method
        goDirUp();
    }

    private void goDirDown(String item) { //goDirDown(String path) method is using to listen the lower user-selected directory in file system
        String addr = file.getAbsolutePath().toString(); //String variable with name `addr` that encrypts as "address" that used to fraction and rebuilding main path variable
        String oldaddr = addr; //not used variable... And what were the prospects
        Log.d("FANAGER", "ITEM: " + item); //Again! LogCat... But why?!
        if(addr.equals("/")) addr = addr + item; //Safety... Such safety...
        else addr = addr + "/" + item; //Yep! really cool building the `addr` value
        if (new File(addr).isDirectory()) { //if `addr` is Directory use `loadArr(String path)` method
            Log.d("FANAGER", addr);
            loadArr(addr);
            return; //AND GO BACK!!! Please <3
        }
        else {
            //Again :D Safety :D
            openFile(addr);
        }
    }

    private void loadArr(String path) { //`loadArr(String path)` method is used to reload the `files` array
        file = new File(path); //reload `file` variable
        Log.d(LOG_TAG, "Hide: " + HIDE_HIDDEN);
        if (file.isDirectory()) { //WE NEED MORE SAFETY!!!
            if (!alfiles.isEmpty()) alfiles.clear(); //Yes! It looks oddly, but inoffensively
            if (file.list() != null) { //FOREACH, BABY!!!
                for (String item : file.list()) {
                    hmfiles = new HashMap<String, Object>();
                    if (file.toString().equals("/")) {
                        if (!(HIDE_HIDDEN && new File(new File(file.toString()) + item).isHidden())) {
                            hmfiles.put("item", item);
                            hmfiles.put("type" ,new File(file.toString() + item).isDirectory()?R.drawable.directory:R.drawable.file);
                            alfiles.add(hmfiles);
                        }
                    }
                    else {
                        if (!(HIDE_HIDDEN && new File(new File(file.toString()) + item).isHidden())) {
                            hmfiles.put("item", item);
                            hmfiles.put("type", new File(file.toString() + "/" + item).isDirectory() ? R.drawable.directory : R.drawable.file);
                            alfiles.add(hmfiles);
                        }
                    }
                   // alfiles.add(hmfiles);  //Now turn it off
                }
                changeMenu(file);
                CURRENT_DIR = file;
            }
            try {
                getSupportActionBar().setSubtitle(path);
            }
            catch (NullPointerException ex) {
                Log.e(LOG_TAG, "" + ex);
            }
        }
        else Toast.makeText(getApplicationContext(), "This is file. Pls contact with developer! (It's an error)" + path, Toast.LENGTH_LONG).show();
        return; //Sorry! You must to go back of algorithm hierarchy
    }

    private void openFile(String sfile) {
        File file = new File(sfile);
        String title = "Open this file via..."; //An title to chooser of action of file opening
        Intent music = new Intent(); //Strange name to intent. By secret I can tell you that when i was started, it just opening music files
        music.setAction(Intent.ACTION_VIEW);
        music.setDataAndType(Uri.fromFile(file), FileControl.getMime(sfile)); //"audio/*"
        Intent chooser = Intent.createChooser(music, title);
       /* if (music.resolveActivity(getPackageManager()) != null) {*/
            startActivity(chooser);
       /* }
        else {
            Toast.makeText(getApplicationContext(), "Cannot open file", Toast.LENGTH_SHORT).show();
        }*/
    }


}
