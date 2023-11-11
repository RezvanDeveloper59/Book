package ir.rezvandeveloper.bookonline.player;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

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
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import ir.rezvandeveloper.bookonline.Api;
import ir.rezvandeveloper.bookonline.R;
import ir.rezvandeveloper.bookonline.MotefaregheActivity;

public class PlayMotefaregheActivity extends AppCompatActivity {

    AppCompatTextView tv_name, tv_current_time, tv_total_time;

    AppCompatSeekBar sb_main;
    ImageButton ibBookmarkOffMotefareghe,ibBookmarkOnMotefareghe,ib_download_play, ib_play, ib_pause,ib_left_jump, ib_right_jump,ib_next_jump,ib_previous_jump;
    MediaPlayer mediaPlayer = new MediaPlayer();
    boolean doubleBackToExitPressedOnce = false;
    Timer timer;
    int dlsize = 0, cdl = 0;
    private final int EXTERNAL_MEMORY_REQUEST_CODE = 100;
    String CURRENT_NAME, URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_motefareghe);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        Cast();

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        CURRENT_NAME = bundle.getString("name");
        tv_name.setText(CURRENT_NAME);

        URL = Api.MOTEFAREGHE_URL + CURRENT_NAME;

        SaveLastNumber(CURRENT_NAME);

        play(URL);

        readBookmarked();

        ibBookmarkOffMotefareghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibBookmarkOffMotefareghe.setVisibility(View.GONE);
                ibBookmarkOnMotefareghe.setVisibility(View.VISIBLE);

                SharedPreferences.Editor editor = Api.sharedPreferences.edit();
                // AB == All Book
                editor.putString("Mo" + CURRENT_NAME,CURRENT_NAME);
                editor.apply();
            }
        });

        ibBookmarkOnMotefareghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibBookmarkOffMotefareghe.setVisibility(View.VISIBLE);
                ibBookmarkOnMotefareghe.setVisibility(View.GONE);

                SharedPreferences.Editor editor = Api.sharedPreferences.edit();
                editor.remove("Mo" + CURRENT_NAME);
                editor.apply();
            }
        });

        ib_download_play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        if (ContextCompat.checkSelfPermission(PlayMotefaregheActivity.this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            requestPermission();
                        } else {
                            URL = Api.MOTEFAREGHE_URL + CURRENT_NAME;
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

        ib_next_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CURRENT_NAME = next(CURRENT_NAME);
                URL = Api.MOTEFAREGHE_URL + CURRENT_NAME;
                mediaPlayer.stop();
                mediaPlayer.reset();
                readBookmarked();
                play(URL);
            }
        });

        ib_previous_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CURRENT_NAME = previous(CURRENT_NAME);
                URL = Api.MOTEFAREGHE_URL + CURRENT_NAME;
                mediaPlayer.stop();
                mediaPlayer.reset();
                readBookmarked();
                play(URL);
            }
        });
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

    private void readBookmarked(){
        // AB == All Book
        if(Api.sharedPreferences.contains("Mo" + CURRENT_NAME)) {
            ibBookmarkOnMotefareghe.setVisibility(View.VISIBLE);
            ibBookmarkOffMotefareghe.setVisibility(View.GONE);
        }else {
            ibBookmarkOnMotefareghe.setVisibility(View.GONE);
            ibBookmarkOffMotefareghe.setVisibility(View.VISIBLE);
        }
    }

    // ذخیره آخرین بازدید در SharedPreference
    private void SaveLastNumber(String name) {
        SharedPreferences.Editor editor = Api.sharedPreferences.edit();
        editor.putString("nameMotefareghe", name);
        editor.apply();
    }

    private String next(String currentName){
        int lengthName = MotefaregheActivity.listAllNamesMotefareghe.size();
        String nameEnd = MotefaregheActivity.listAllNamesMotefareghe.get(lengthName - 1).getName();

        if (currentName.equals(nameEnd)) {
            Toast.makeText(this, "تکرار شد .", Toast.LENGTH_LONG).show();
            return nameEnd;
        }

        for (int i = 0 ; i < lengthName ; i++) {
            if (MotefaregheActivity.listAllNamesMotefareghe.get(i).getName().equals(currentName)){
                return MotefaregheActivity.listAllNamesMotefareghe.get(i + 1).getName();
            }
        }
        return "";
    }

    private String previous(String currentName){
        int lengthName = MotefaregheActivity.listAllNamesMotefareghe.size();

        String nameEnd = MotefaregheActivity.listAllNamesMotefareghe.get(0).getName();

        if (currentName.equals(nameEnd)) {
            Toast.makeText(this, "تکرار صوت اول .", Toast.LENGTH_LONG).show();
            return nameEnd;
        }

        for (int i = lengthName ; i > 0 ; i--) {
            if (MotefaregheActivity.listAllNamesMotefareghe.get(i - 1).getName().equals(currentName)){
                return MotefaregheActivity.listAllNamesMotefareghe.get(i - 2).getName();
            }
        }
        return "";
    }

    private void play(String url){
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(PlayMotefaregheActivity.this);
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
                    mediaPlayer.prepare(); // might take long! (for buffering, etc)
                    mediaPlayer.start();
                    tv_name.setText(CURRENT_NAME);

                    ib_play.setVisibility(View.GONE);
                    ib_pause.setVisibility(View.VISIBLE);

                    SaveLastNumber(CURRENT_NAME);
                    increment();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                progressDialog.dismiss();

                // ست کردن مدا زمان تایم درس
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
        RequestQueue queue = Volley.newRequestQueue(PlayMotefaregheActivity.this);
        queue.add(stringRequest);
    }

    private void download(String link){
        DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(link);

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(CURRENT_NAME);
        request.setDescription("");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,CURRENT_NAME);
        downloadmanager.enqueue(request);
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale
                (PlayMotefaregheActivity.this, WRITE_EXTERNAL_STORAGE)) {
            reqPermission();
        } else {
            reqPermission();
        }
    }

    private void reqPermission() {
        ActivityCompat.requestPermissions(PlayMotefaregheActivity.this, new String[] {WRITE_EXTERNAL_STORAGE}, EXTERNAL_MEMORY_REQUEST_CODE);
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
        tv_name = findViewById(R.id.tvTitrM);
        tv_current_time = findViewById(R.id.tv_current_time_m);
        tv_total_time = findViewById(R.id.tv_total_time_m);

        sb_main = findViewById(R.id.sb_main_m);

        ibBookmarkOffMotefareghe = findViewById(R.id.ibBookmarkOffMotefareghe);
        ibBookmarkOnMotefareghe = findViewById(R.id.ibBookmarkOnMotefareghe);
        ib_download_play = findViewById(R.id.ib_download_play_m);
        ib_play = findViewById(R.id.ib_play_m);
        ib_pause = findViewById(R.id.ib_pause_m);
        ib_left_jump = findViewById(R.id.ib_left_jump_m);
        ib_right_jump = findViewById(R.id.ib_right_jump_m);
        ib_next_jump = findViewById(R.id.ib_next_jump_m);
        ib_previous_jump = findViewById(R.id.ib_previous_jump_m);

        timer = new Timer();
    }
}