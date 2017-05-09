package com.thever4.android.fanager;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by thever4 on 02.04.17.
 */

public class FileControl extends AppCompatActivity {




    /*public static String getMime(String path) { //This is method, that uses external libraries and used to get MIME type of selected file
        MagicMatch match = null;
        try {
            match = Magic.getMagicMatch(new File(path), true);

            String ret =  match.getMimeType();
            Log.d("FANAGER_MIME_TESTER", "File " + path + " with detected MIME: " + ret);
            return ret;
        } catch (Exception e){
            Log.e("FANAGER_MIME", "Fatal error on file MIME type detecting!!!");
            //tostik("File is unavailable", Toast.LENGTH_LONG);
        }
        return null;
    }*/

/***
 *
 *
 .exe +
 .msi +
 .accdb
 .flac, .ape, .ac3, .wma, .aac
 .avi, .wmw, .mkv, .3gp, .flv, .mpeg, .mp4, .mov, .vob
 .swf, .flv
 .rar,  .7z, .tar, .jar
 .dll
 .ini
 .iso, .mds .mdf, .vdf, .img, .daa, .vcd, .nrg
 *
 */


    @NonNull
    public static String getMime(String path) {
       String extension = path.substring(path.lastIndexOf('.') + 1, path.length());
        if(extension.equals("html") || extension.equals("htm")) return "text/html";
        else if (extension.equals("txt")) return "text/plain";
        else if (extension.equals("php")) return "text/php";
        else if (extension.equals("js")) return "application/javascript";
        else if (extension.equals("json")) return "application/json";
        else if (extension.equals("zip")) return "appication/zip";
        else if (extension.equals("gz") || extension.equals("gzip")) return "application/gzip";
        else if (extension.equals("pdf")) return "application/pdf";
        else if (extension.equals("djvu")) return "image/x.djvu";
        else if (extension.equals("mp3") || extension.equals("wav") || extension.equals("ogg") || extension.equals("m4a") || extension.equals("wma")) return "audio/*";
        else if (extension.equals("bmp") || extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif") || extension.equals("ico") || extension.equals("tiff") || extension.equals("raw")) return "image/*";
        else if (extension.equals("doc") || extension.equals("docx") || extension.equals("odt") || extension.equals("rtf")) return "application/msword";
        else if (extension.equals("ppt") || extension.equals("pptx") || extension.equals("odp")) return "application/vnd.ms-powerpoint";
        else if (extension.equals("xls") || extension.equals("xlsx") || extension.equals("ods")) return "application/vnd.ms-excel";
        else if (extension.equals("cmd") || extension.equals("sh") || extension.equals("com") || extension.equals("bat")) return "text/cmd";
        else if (extension.equals("css")) return "text/css";
        else if (extension.equals("csv")) return "text/csv";
        else if (extension.equals("xml")) return "text/xml";
        /*** must be continued at newest versions... ****/
        else return "*/*";
    }

}
