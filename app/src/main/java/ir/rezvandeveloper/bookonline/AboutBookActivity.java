package ir.rezvandeveloper.bookonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutBookActivity extends AppCompatActivity {

    TextView tvAboutBook;
    ImageView ivPlay, ivPause;
    MediaPlayer mplayer;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_book);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tvAboutBook = findViewById(R.id.tv_about_book);
        ivPlay = findViewById(R.id.iv_play);
        ivPause = findViewById(R.id.iv_pause);

        // player
        mplayer = MediaPlayer.create(this, R.raw.noskhe2852);
        // audio manager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        tvAboutBook.setText("در دائرةالمعارفت انوار است");
        tvAboutBook.append("\n" + "چون قبله تو ظهور پر امیال است");

        tvAboutBook.append("\n\n" + "تدریس چو کرد خارج دانش حق");
        tvAboutBook.append("\n" + "شد معرفت اله و غیرش به زَهق");

        tvAboutBook.append("\n\n" + "منبر به اوین آمد و کرسی به قفس");
        tvAboutBook.append("\n" + "آن والی وسطی که به زندان هوس");

        tvAboutBook.append("\n\n" + "در کرب و بلا چون پدرش قطع نفَس");
        tvAboutBook.append("\n" + "در هر دو زمان حسین و غوغا به جَرس");

        tvAboutBook.append("\n\n" + "آموزش و پرورش دهد هر دو به کس");
        tvAboutBook.append("\n" + "او بر لب گودال و یکی در یَد خَس");

        tvAboutBook.append("\n\n" + "اینک به خدای خود بگو: نازِله بس!");
        tvAboutBook.append("\n" + "در باب اجابتت مقیمم که تو رَس");

        tvAboutBook.append("\n\n" + "هیهات اگر بداء دهی با من و پس!");

        tvAboutBook.append("\n\n" + "شماره فایل صوتی");
        tvAboutBook.append("\n" + "1395/9/23 - 2852" + "\n\n");

        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mplayer.isPlaying()) {
                    mplayer.start();
                }
            }
        });

        ivPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mplayer.isPlaying()) {
                    mplayer.pause();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        mplayer.stop();
        super.onBackPressed();
    }
}