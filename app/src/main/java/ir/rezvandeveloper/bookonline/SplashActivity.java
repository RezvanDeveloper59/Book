package ir.rezvandeveloper.bookonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {

    AppCompatTextView tv_refresh, tv_kashef;
    public static AppCompatButton btnDownload;
    AppCompatImageView ib_download_myket_splash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // جلوگیری از چرخش صفحه
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        Cast();

        if(Api.HasInternetConnection(SplashActivity.this)){
            ReadVersion(SplashActivity.this);
//            tv_refresh.setVisibility(View.GONE);
//            tv_kashef.setVisibility(View.VISIBLE);
//            tv_kashef.setText("کتاب دائره المعارف ظهور");
//            tv_kashef.append("\n\n" + "کاشف توحید بدون مرز");
//            tv_kashef.append("\n\n" + "درس ۱ تا ۶۰۰۰");
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }, Integer.valueOf(3590));
        }else{
            Toast.makeText(this, "اینترنت گوشی خاموش است .", Toast.LENGTH_LONG).show();
        }

        tv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Api.HasInternetConnection(SplashActivity.this)){
                    ReadVersion(SplashActivity.this);
//                    tv_refresh.setVisibility(View.GONE);
//                    tv_kashef.setVisibility(View.VISIBLE);
//                    tv_kashef.setText("کتاب دائره المعارف ظهور");
//                    tv_kashef.append("\n\n" + "کاشف توحید بدون مرز");
//                    tv_kashef.append("\n\n" + "درس ۱ تا ۶۰۰۰");
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    }, Integer.valueOf(3590));
                }
                else{
                    Toast.makeText(SplashActivity.this, "اینترنت گوشی خاموش است .", Toast.LENGTH_LONG).show();
                }
            }
        });

        ib_download_myket_splash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://myket.ir/app/ir.rezvandeveloper.bookonline");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }

    private void ReadVersion(Context context) {
        tv_refresh.setVisibility(View.INVISIBLE);
        tv_kashef.setVisibility(View.INVISIBLE);
        tv_kashef.setText("");

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("لطفاً صبر کنید .");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // خواندن مقادیر آی دی 2 از دیتابیس کانفیگ
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_GET_SETTING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                tv_refresh.setVisibility(View.INVISIBLE);
                tv_kashef.setVisibility(View.VISIBLE);
                String delay = "3000";
                try {
                    JSONObject jsonObject = new JSONObject(response);
//Log.i(TAG, "onResponse ReadVersion : " + response);
//Log.i(TAG, "onResponse ReadVersion : " + jsonObject.getJSONArray("config").getJSONObject(1).getString("data"));

                    // پیام خوش آمد گویی
                    tv_kashef.setText(jsonObject.getJSONArray("setting").getJSONObject(4).getString("b"));
//                    tv_kashef.setBackgroundColor(0XFFFFFFFF);

                    // خواندن زمان تاخیر اسپلش برای پیغام خوش آمدگویی
                    delay = jsonObject.getJSONArray("setting").getJSONObject(2).getString("b");

                    // خواندن ورژن برنامه
                    String version = "-";
                    try {
                        PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
//Log.i(TAG, "onResponse version : " + version.substring(0, 2));
                        version = pInfo.versionName.substring(0, 1 );
//Log.i("TAG", "onResponse version: " + version);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }

                    if(Integer.valueOf(jsonObject.getJSONArray("setting").getJSONObject(3).getString("b")) > Integer.valueOf(version)) {
//                        btnDownload.setVisibility(View.VISIBLE);
//                        ib_download_bazaar_splash.setVisibility(View.VISIBLE);
                        ib_download_myket_splash.setVisibility(View.VISIBLE);
                        String message = jsonObject.getJSONArray("setting").getJSONObject(3).getString("c");
                        delay = jsonObject.getJSONArray("setting").getJSONObject(2).getString("c");
                        tv_kashef.setText(message);
//                        tv_kashef.setBackgroundColor(0xFFEEEEEE);
                        tv_kashef.setTextColor(0xFF0000FF);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Integer.valueOf(delay));

                        // اگر نسخه نصب شده با نسخه جدید برابر نبود
                    }else {

                        btnDownload.setVisibility(View.GONE);
//                        ib_download_bazaar_splash.setVisibility(View.GONE);
                        ib_download_myket_splash.setVisibility(View.GONE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, Integer.valueOf(delay));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
//Log.d(TAG, "onResponse ReadVersion: " + e);
                    Api.ShowAlertDialog(SplashActivity.this, "خطا", "خطایی در برقراری ارتباط با سرور رخ داد .");
                    tv_kashef.setText(e.toString());
//                    tv_kashef.setBackgroundColor(0xFFFF5500);
                    tv_kashef.setTextColor(0xFF0000FF);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                tv_refresh.setVisibility(View.VISIBLE);
//Log.d(TAG, "onErrorResponse onErrorResponse ReadVersion: " + error.toString());
//                Api.ShowSnackBar(SplashActivity.this, llMain, "خطایی در برقراری ارتباط با سرور رخ داد .");
                tv_kashef.setText("خطایی در برقراری ارتباط با سرور رخ داد .");
                tv_kashef.setVisibility(View.VISIBLE);
//                tv_kashef.setBackgroundColor(0xFFFF5500);
                tv_kashef.setTextColor(0xFF0000FF);
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    private void Cast() {
        tv_kashef = findViewById(R.id.tv_kashef_splash);
        tv_refresh = findViewById(R.id.tv_refresh_splash);

        btnDownload = findViewById(R.id.btn_download_splash);
        ib_download_myket_splash = findViewById(R.id.ib_download_myket_splash);
    }
}