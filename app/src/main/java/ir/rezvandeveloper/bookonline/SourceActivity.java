package ir.rezvandeveloper.bookonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;

public class SourceActivity extends AppCompatActivity {

    AppCompatTextView tv_source;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source);

        tv_source = findViewById(R.id.tv_source);

        tv_source.setText("منابع هر صوت ( درس ) :");
        tv_source.append("\n\n");
        tv_source.append("منبع هر درس معمولاً در ابتدای آن صوت یا بعد از آوردن عنوان درس آورده شده است .");
        tv_source.append("\n\n");
        tv_source.append("این حدیث و روایات تماماً از کتب معتبر شیعه و سنی آورده شده است که یا از قرآن است یا به نقل قول از راویان و در نهایت به پیامبر اکرم حضرت محمّد مصطفی و حضرات معصومین علیه السلام ختم می شوند . ");
        tv_source.append("\n\n");
        tv_source.append("این مباحث ، توسط کاشف توحید بدون مرز ( سیّد حسین کاظمینی بروجردی ) تدریس می شوند که کمتر کسی به این آیات و روایات توجه نموده است و از اینها به عنوان زیر خاکی ها می توان نام برد .");
        tv_source.append("\n\n");
        tv_source.append("استفاده از دروس ایشان برای عموم آزاد است .");
        tv_source.append("\n\n");
        tv_source.append("شماره تماس مستقیم با کاشف توحید بدون مرز :");
        tv_source.append("\n\n");
        tv_source.append("09388629681");
        tv_source.append("\n\n");
        tv_source.append("منابع فایل های صوتی و متون :");
        tv_source.append("\n\n");
        tv_source.append("https://t.me/doostanone");
        tv_source.append("\n\n");
        tv_source.append("https://t.me/NegaheMotazeranBeValiMiyani");
        tv_source.append("\n\n");
        tv_source.append("https://t.me/Matnefarmayeshat");
        tv_source.append("\n\n");
        tv_source.append("https://t.me/Matnefarmayeshat_2");
        tv_source.append("\n\n");

    }
}