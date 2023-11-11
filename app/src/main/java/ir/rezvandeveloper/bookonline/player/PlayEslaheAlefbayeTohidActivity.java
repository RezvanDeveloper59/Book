package ir.rezvandeveloper.bookonline.player;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ir.rezvandeveloper.bookonline.Api;
import ir.rezvandeveloper.bookonline.DB.Database;
import ir.rezvandeveloper.bookonline.DB.ModelDB;
import ir.rezvandeveloper.bookonline.R;

public class PlayEslaheAlefbayeTohidActivity extends AppCompatActivity {

    AppCompatTextView tvTitrPlay408, tv_name, tv_current_time, tv_total_time;

    AppCompatSeekBar sb_main;
    ImageButton ib_download_play, ib_play, ib_pause,ib_left_jump, ib_right_jump,ib_next_jump408,ib_previous_jump408,ib_rotate_jump408;
    MediaPlayer mediaPlayer = new MediaPlayer();
    boolean doubleBackToExitPressedOnce = false,boolRotate = false;
    int dlSize = 0, cdl = 0,CURRENT_ID = 0;
    private final int EXTERNAL_MEMORY_REQUEST_CODE = 100;
    //String a = ".",b = ",";
    Timer timer;
    String titr, URL,CURRENT_NAME,CURRENT_LESSON,CURRENT_LINK;
    List<ModelDB> modelDBS = new ArrayList<>();
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_eslahe_alefbaye_tohid);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        Cast();

        bundle = getIntent().getExtras();

        CURRENT_NAME = bundle.getString("name");
        CURRENT_LESSON = bundle.getString("currentLesson");
        CURRENT_LINK = bundle.getString("currentLink");
        CURRENT_ID = Integer.valueOf(bundle.getString("currentId"));

        tv_name.setText(CURRENT_LINK);

        // ست کردن تیترها روی تکس ویو برای بار اول
        titr = bundle.getString("titrs");
        String t[] = titr.split("/");
        titr = "";
        tvTitrPlay408.setText(Api.ConvertNumberEnToFa(CURRENT_LESSON) + "\n");
        for (int i = 0 ; i < t.length ; i++){
            titr = titr + t[i] + "\n";
        }
        tvTitrPlay408.append(titr);

        saveLast(CURRENT_NAME,CURRENT_LESSON,CURRENT_LINK,CURRENT_ID,titr);

        URL = Api.LINK_1To408_URL + CURRENT_LINK + ".mp3";

        play(URL);

        ib_rotate_jump408.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolRotate = true;

            }
        });

        /*if (endCurrentPlay) {
            Log.i("TAG", "onCreate1: " + mediaPlayer.getCurrentPosition());

            Log.i("TAG", "onCreate1: " + mediaPlayer.getDuration());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    play(URL);
                }
            },2500);

        }*/

        ib_download_play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        if (ContextCompat.checkSelfPermission(PlayEslaheAlefbayeTohidActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            requestPermission();
                        } else {
                            URL = Api.LINK_1To408_URL + CURRENT_LINK + ".mp3";
                            download(URL);
                        }
                    }
                }).start();
            }
        });

        ib_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                ib_play.setVisibility(View.GONE);
                ib_pause.setVisibility(View.VISIBLE);
            }
        });

        ib_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                ib_play.setVisibility(View.VISIBLE);
                ib_pause.setVisibility(View.GONE);
            }
        });

        ib_left_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo((mediaPlayer.getCurrentPosition() - 30000));
            }
        });

        ib_right_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(30000 + mediaPlayer.getCurrentPosition());
                //Toast.makeText(PlayActivity.this, mediaPlayer.getCurrentPosition()/1000+"", Toast.LENGTH_SHORT).show();
            }
        });

        ib_next_jump408.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CURRENT_LESSON = getNextLesson(CURRENT_LESSON);
//                String link = getNextLink(CURRENT_LINK,CURRENT_ID);
                String link = getNextLinkOfDB(CURRENT_LINK);
                saveLast(CURRENT_NAME,CURRENT_LESSON,CURRENT_LINK,CURRENT_ID,titr);
                URL = Api.LINK_1To408_URL + link + ".mp3";
                mediaPlayer.stop();
                mediaPlayer.reset();
                play(URL);
//                CURRENT_ID = getNextId(CURRENT_LESSON);
            }
        });

        ib_previous_jump408.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = getPreviousLinkOfDB(CURRENT_LINK);
                URL = Api.LINK_1To408_URL + link + ".mp3";
                mediaPlayer.stop();
                mediaPlayer.reset();
                play(URL);
            }
        });
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    public void onBackPressed() {
        mediaPlayer.stop();
        super.onBackPressed();
    }

    // برگشت دو مرحله ای
    //    @Override
//    public void onBackPressed() {
//        mediaPlayer.stop();
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "برای خروج مجدد کلید عقب را لمس کنید .", Toast.LENGTH_LONG).show();
//
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//                mediaPlayer.stop();
//
//            }
//        }, 500);
//    }

    // ذخیره آخرین بازدید در SharedPreference
    private void saveLast(String name,String lesson,String link,int id,String titr) {
        SharedPreferences.Editor editor = Api.sharedPreferences.edit();
        editor.putString("name_eslahe_alefbaye_tohid", name);
        editor.putString("currentLesson_eslahe_alefbaye_tohid", lesson);
        editor.putString("currentLink_eslahe_alefbaye_tohid", link);
        editor.putString("currentId_eslahe_alefbaye_tohid", String.valueOf(id));
        editor.putString("titrs_eslahe_alefbaye_tohid", titr);
        editor.apply();
    }

    private void saveNextLesson(String lesson){
        SharedPreferences.Editor editor = Api.sharedPreferences.edit();
        String s = lesson.substring(4);
        s = Api.ConvertNumberEnToFa(String.valueOf(Integer.valueOf(s) + 1));
        editor.putString("nextLesson", "درس " + s);
        editor.apply();
    }

    private String getNextLink(String currentLink, int id){
//        Log.i("TAG", "getNextLinkID: " + id);
        Database database = new Database(PlayEslaheAlefbayeTohidActivity.this);
        modelDBS = database.getAllPrescription();

        int i = 0,l = modelDBS.size();
        String nextLink;
        for (i = id ; i < l ; i++) {
            nextLink = modelDBS.get(i).getLink();
            if (! currentLink.equals(nextLink)){
//                Log.i("TAG", "getNextLink: " + nextLink);
                CURRENT_LINK = nextLink;
                CURRENT_ID++;
                return nextLink;
            }
        }
        return "صوت 963_درس ۵۹_ص ۴۰۸";
    }

    private int getNextId(String lesson){
        int x = 0 , placeA = 0 ,countLesson = 0 , l = Integer.valueOf(lesson.substring(4));
        //Log.i("TAG", "getTitrs: " + l);

        Database database = new Database(PlayEslaheAlefbayeTohidActivity.this);
        modelDBS = database.getAllPrescription();
        String titr = "";
        for (int j = 0 ; j < modelDBS.size() ; j++) {
            if (l == Integer.valueOf(modelDBS.get(j).getLesson().substring(4))) {
                countLesson++;
                if(countLesson != 0)
                    x++;
            }
            if (x != 0){
                placeA = j - countLesson + 1;
                x = 0;
            }
        }
        for (int i = 0 ; i < countLesson ; i++) {
            titr = titr + "/" + modelDBS.get(i + placeA).getTitr();
        }

        String t[] = titr.split("/");
        int i = t.length + CURRENT_ID;
        return i;
    }

    private String getNextLesson(String lesson){
        String s = lesson.substring(4);
        s = Api.ConvertNumberEnToFa(String.valueOf(Integer.valueOf(s) + 1));
        s = "درس " + s;
        return s;
    }

    private String getNextLinkOfDB(String currentLink){
        Database database = new Database(PlayEslaheAlefbayeTohidActivity.this);
        modelDBS = database.getAllPrescription();

        /* if (CURRENT_LESSON.equals("درس ۴۰۸")) {
            Toast.makeText(this, "تکرار درس آخر", Toast.LENGTH_LONG).show();
            return "صوت 963_درس ۵۹_ص ۴۰۸";
        }

        if (CURRENT_LESSON.equals("درس ۴۰۷")) {
            return "صوت 963_درس ۵۹_ص ۴۰۸";
        }*/

        String s = CURRENT_LESSON.substring(4);
        int m = Integer.valueOf(s) + 1;
        tvTitrPlay408.setText("درس " + Api.ConvertNumberEnToFa(String.valueOf(m)) + "\n");
        titr = "";

        for (int i = 0; i < modelDBS.size() ; i++){
            if (modelDBS.get(i).getLink().equals(currentLink)) {
                for (int j = i ; j < modelDBS.size() ; j++) {
                    if (! modelDBS.get(j).getLink().equals(currentLink)) {
                        CURRENT_LINK = modelDBS.get(j).getLink();
                        CURRENT_LESSON = modelDBS.get(j).getLesson();

                        String lesson = modelDBS.get(j).getLesson();
                        int l = j;
                        for (int k = 0; k < modelDBS.size(); k++){
                            if (modelDBS.get(l - 1).getLesson().equals(lesson)){
                                tvTitrPlay408.append( "\n" + modelDBS.get(l).getTitr());
                                titr = titr + "\n" + modelDBS.get(l).getTitr();
                                l++;
                            }
                        }
                        return CURRENT_LINK;
                    }
                }
            }
        }
        return "صوت 963_درس ۵۹_ص ۴۰۸";
    }

    private String getPreviousLinkOfDB(String currentLink){
        Database database = new Database(PlayEslaheAlefbayeTohidActivity.this);
        modelDBS = database.getAllPrescription();

        if (CURRENT_LESSON.equals("درس ۱")) {
            Toast.makeText(this, "تکرار درس اول", Toast.LENGTH_LONG).show();
            return "صوتی 1- درس ۱- ص ۱- 21.11.90";
        }

        String s = CURRENT_LESSON.substring(4);
        int m = Integer.valueOf(s) - 1;
        tvTitrPlay408.setText("درس " + Api.ConvertNumberEnToFa(String.valueOf(m)) + "\n");
        titr = "";

        for (int i = 949 ; i > 0 ; i--) {
            if (modelDBS.get(i).getLink().equals(currentLink)) {
                for (int j = i ; j > 0 ; j--) {
                    if (! modelDBS.get(j).getLink().equals(currentLink)) {
                        CURRENT_LINK = modelDBS.get(j).getLink();
                        CURRENT_LESSON = modelDBS.get(j).getLesson();

                        String lesson = modelDBS.get(j).getLesson();

                        int lengthLesson = 0;
                        for (int k = 0; k < modelDBS.size(); k++){
                            if (modelDBS.get(k).getLesson().equals(lesson)){
                                lengthLesson++;
                            }
                        }
                        Log.i("TAG", "getPreviousLinkOfDB lengthLesson: " + lengthLesson);
                        int l = j - lengthLesson + 1;
                        Log.i("TAG", "getPreviousLinkOfDB l: " + l);
                        for (int k = 0; k < modelDBS.size(); k++){
                            if (modelDBS.get(l).getLesson().equals(lesson)){
                                tvTitrPlay408.append( "\n" + modelDBS.get(l).getTitr());
                                titr = titr + "\n" + modelDBS.get(l).getTitr();
                                l++;
                            }
                        }

                        return CURRENT_LINK;
                    }
                }
            }
        }
        return "";
    }

    private void play(String url){
        tv_name.setText(CURRENT_LINK);

//        URL = Api.LINK_1To408_URL + LINK + ".mp3";
//        Log.i("TAG", "play: " + URL);
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(PlayEslaheAlefbayeTohidActivity.this);
        progressDialog.setMessage("در حال بارگیری درس");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.setAudioAttributes(
                        new AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build()
                );
                try {
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    ib_play.setVisibility(View.GONE);
                    ib_pause.setVisibility(View.VISIBLE);

                    saveLast(CURRENT_NAME,CURRENT_LESSON,CURRENT_LINK,CURRENT_ID,titr);
                    increment();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                progressDialog.dismiss();

                // ست کردن مدت زمان تایم درس
                int totalTime = mediaPlayer.getDuration()/1000;
                sb_main.setMax(totalTime);
                String min = totalTime / 60 + "";
                String sec = totalTime % 60 + "";
                if(min.length() == 1){
                    min = 0 + min;
                }
                if(sec.length() == 1){
                    sec = 0 + sec;
                }
                tv_total_time.setText(min + ":" + sec);

                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        sb_main.setProgress(mediaPlayer.getCurrentPosition()/1000);
                        int currentTime = mediaPlayer.getCurrentPosition()/1000;
                        String minC = currentTime / 60 + "";
                        String secC = currentTime % 60 + "";
                        if(minC.length() == 1){
                            minC = 0 + minC;
                        }
                        if(secC.length() == 1){
                            secC = 0 + secC;
                        }
                        tv_current_time.setText(minC + ":" + secC);
                      }
                }, 1000, 1000);

                // وقتی به پایان درس رسید ، آیکن های پلی و پاوز جابجا شوند
                if(tv_total_time.getText().toString().equals(tv_current_time.getText().toString())){
                    ib_play.setVisibility(View.VISIBLE);
                    ib_pause.setVisibility(View.GONE);
                }
            }
        }, Integer.valueOf(200));
    }

    private void increment(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_INCREMENT,  response -> {
        }, error -> {
        });
        RequestQueue queue = Volley.newRequestQueue(PlayEslaheAlefbayeTohidActivity.this);
        queue.add(stringRequest);
    }

    private void download(String link){
        DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(link);

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(CURRENT_NAME);
        request.setDescription("");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,CURRENT_NAME + ".mp3");
        downloadmanager.enqueue(request);
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale
                (PlayEslaheAlefbayeTohidActivity.this, WRITE_EXTERNAL_STORAGE)) {
            reqPermission();
        } else {
            reqPermission();
        }
    }

    private void reqPermission() {
        ActivityCompat.requestPermissions(PlayEslaheAlefbayeTohidActivity.this, new String[] {WRITE_EXTERNAL_STORAGE}, EXTERNAL_MEMORY_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXTERNAL_MEMORY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "مجوز تایید شد .", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(this, "مجوز رد شد .", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void Cast() {
        tvTitrPlay408 = findViewById(R.id.tvTitrPlay408);
        tv_name = findViewById(R.id.tvTitr408);
        tv_current_time = findViewById(R.id.tv_current_time408);
        tv_total_time = findViewById(R.id.tv_total_time408);
        sb_main = findViewById(R.id.sb_main408);

        ib_download_play = findViewById(R.id.ib_download_play408);
        ib_play = findViewById(R.id.ib_play408);
        ib_pause = findViewById(R.id.ib_pause408);
        ib_left_jump = findViewById(R.id.ib_left_jump408);
        ib_right_jump = findViewById(R.id.ib_right_jump408);
        ib_next_jump408 = findViewById(R.id.ib_next_jump408);
        ib_previous_jump408 = findViewById(R.id.ib_previous_jump408);
        ib_rotate_jump408 = findViewById(R.id.ib_rotate_jump408);

        timer = new Timer();
    }
}