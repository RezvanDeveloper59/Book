package ir.rezvandeveloper.bookonline.about;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;

import ir.rezvandeveloper.bookonline.R;

public class AboutAppActivity extends AppCompatActivity {
    AppCompatTextView tv_about_app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        tv_about_app = findViewById(R.id.tv_about_app);

        tv_about_app.setText("\n\n");
        tv_about_app.append("سایت برنامه");
        tv_about_app.append("\n\n");
        tv_about_app.append("https://Noskheha.RezvanDeveloper.ir");
        tv_about_app.append("\n\n\n");
        tv_about_app.append("برنامه نویس");
        tv_about_app.append("\n\n");
        tv_about_app.append("https://RezvanDeveloper.ir");
        tv_about_app.append("\n\n");
        tv_about_app.append("ایمیل");
        tv_about_app.append("\n\n");
        tv_about_app.append("RezvanDeveloper@Gmail.com");
        tv_about_app.append("\n\n");
    }
}