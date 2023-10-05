package ir.rezvandeveloper.bookonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout etNameRegister,etMobileRegister,etPasswordRegister,etRepeatPasswordRegister;
    AppCompatButton btnRegister,btnOneTimePassword;
    AppCompatEditText etOneTimePassword;
    AppCompatTextView tvCallbackRegister;
    String mobile,name,password,repeatPassword,oneTimePassword,oneTimePasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        cast();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = etNameRegister.getEditText().getText().toString();
                mobile = etMobileRegister.getEditText().getText().toString();
                password = etPasswordRegister.getEditText().getText().toString();
                repeatPassword = etRepeatPasswordRegister.getEditText().getText().toString();

                if (name.equals("")){
                    Toast.makeText(RegisterActivity.this, "نام وارد نشده است .", Toast.LENGTH_LONG).show();
                    return;
                }

                if (mobile.equals("")){
                    Toast.makeText(RegisterActivity.this, "شماره موبایل وارد نشده است .", Toast.LENGTH_LONG).show();
                    return;
                }

                if (mobile.length() != 11){
                    Toast.makeText(RegisterActivity.this, "شماره موبایل درست وارد نشده است .", Toast.LENGTH_LONG).show();
                    return;
                }

                if (password.equals("")){
                    Toast.makeText(RegisterActivity.this, "رمز ، تعیین نشده است .", Toast.LENGTH_LONG).show();
                    return;
                }

                if (repeatPassword.equals("")){
                    Toast.makeText(RegisterActivity.this, "تکرار رمز ، تعیین نشده است .", Toast.LENGTH_LONG).show();
                    return;
                }

                if (! password.equals(repeatPassword)){
                    Toast.makeText(RegisterActivity.this, "رمز تعیین شده ، با تکرار آن برابر نیست .", Toast.LENGTH_LONG).show();
                    return;
                }

                if (Api.HasInternetConnection(RegisterActivity.this)){
                    checkUser();
                }else {
                    Toast.makeText(RegisterActivity.this, "اینترنت گوشی قطع است .", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnOneTimePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // مخفی کردن صفحه کلید
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                // Check if no view has focus & close keyboard
                View view = RegisterActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                oneTimePasswordInput = etOneTimePassword.getText().toString().trim();

                if(oneTimePasswordInput.isEmpty()) {
                    etOneTimePassword.setError("رمز وارد نشده است .");
                    etOneTimePassword.requestFocus();
                    return;
                }

                if(oneTimePasswordInput.equals(oneTimePassword)) {
                    if (Api.HasInternetConnection(RegisterActivity.this)) {
                        registeringUser();
                    } else {
                        Api.ShowAlertDialog(RegisterActivity.this,"توجه", "اینترنت قطع است .");
                    }
                }else{
                    Api.ShowAlertDialog(RegisterActivity.this,"توجه", "رمز درست نیست .");
                }
            }
        });
    }

    private void checkUser() {

        ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("در حال ارسال اطلاعات ...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_CHECK_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                JSONObject jsonObject;
//                Log.i("TAG", "onResponse: " + response);

                try {
                    jsonObject = new JSONObject(response);
//                    Log.i("TAG", "onResponse: " + jsonObject.getString("message"));

                    if (jsonObject.getString("message").equals("exist")){
                        Toast.makeText(RegisterActivity.this, "کاربری با این شماره قبلاً ثبت نام کرده است .", Toast.LENGTH_SHORT).show();
                    }else {
                        oneTimePassword();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("mobile", mobile);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(stringRequest);
    }

    private void oneTimePassword(){

        GenerateOneTimePassword();

        // ارسال اس ام اس یکبار مصرف
        ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("لطفاً صبر کنید .");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_ONE_TIME_PASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
//Log.i(TAG, "OneTimePassword() : " + response);
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response);

                    if(jsonObject.getJSONObject("result").getString("message").equals("success")){

                        etNameRegister.setVisibility(View.GONE);
                        etMobileRegister.setVisibility(View.GONE);
                        etPasswordRegister.setVisibility(View.GONE);
                        etRepeatPasswordRegister.setVisibility(View.GONE);
                        btnRegister.setVisibility(View.GONE);

                        etOneTimePassword.setVisibility(View.VISIBLE);
                        btnOneTimePassword.setVisibility(View.VISIBLE);

                        tvCallbackRegister.setText( "رمز یکبار مصرف برای شما ، بصورت پیامک ارسال شد .");
                        tvCallbackRegister.setVisibility(View.VISIBLE);
                        tvCallbackRegister.setBackgroundColor(getResources().getColor(R.color.GREEN));

                    }else{
                        Api.ShowAlertDialog(RegisterActivity.this, "مجدد تلاش نمایید", "خطایی در ارسال اس ام اس رخ داد .");
                        Intent intent = new Intent(RegisterActivity.this,TalkActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Intent intent = new Intent(PrimaryActivity.this, MainActivity.class);
                    Api.ShowAlertDialog(RegisterActivity.this, "متاسفانه ثبت ناموفق بود . بعداً تلاش کنید .1" , e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//Log.i(TAG, "onErrorResponse OneTimePassword(): " + error);
                progressDialog.dismiss();
                Api.ShowAlertDialog(RegisterActivity.this, "متاسفانه ثبت ناموفق بود . بعداً تلاش کنید .2", error.toString());
//                Intent intent = new Intent(PrimaryActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        }) {
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("mobile", mobile);
                params.put("one_time_password", oneTimePassword);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(stringRequest);
    }

    private void GenerateOneTimePassword() {
        int random = new Random().nextInt(99999);
        oneTimePassword = String.valueOf(random);
        Log.i("TAG", "GenerateOneTimePassword : " + oneTimePassword);
    }

    private void registeringUser(){
        ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("لطفاً صبر کنید .");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                JSONObject jsonObject;

                try {
                    jsonObject = new JSONObject(response);

                    if(jsonObject.getString("error").equals("false")){
//                        String s = "ثبت اولیه اطلاعات ، با موفقیت انجام شد";
                        SharedPreferences.Editor editor = Api.sharedPreferences.edit();
                        editor.putString("mobile", mobile);
                        editor.putString("nameUser", name);
                        editor.apply();

                        Toast.makeText(RegisterActivity.this, "ثبت نام شما با موفقیت انجام شد .", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(RegisterActivity.this,TalkActivity.class);
                        startActivity(intent);
                        finish();

                    }else {
                        String s = "خطای ناشناخته رخ داد ." ;
                        etOneTimePassword.setVisibility(View.VISIBLE);
                        btnOneTimePassword.setVisibility(View.VISIBLE);
                        tvCallbackRegister.setVisibility(View.VISIBLE);
                        tvCallbackRegister.setText(s);
                        tvCallbackRegister.setBackgroundColor(getResources().getColor(R.color.GREEN));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Api.ShowAlertDialog(RegisterActivity.this, "خطای JSONException UserRegistering :", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//Log.i(TAG, "onErrorResponse 333: " + error);
                progressDialog.dismiss();
                Api.ShowAlertDialog(RegisterActivity.this, "خطا", "UserRegistering onErrorResponse : " + error.toString());
            }
        }) {
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("mobile", mobile);
                params.put("name", name);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(stringRequest);
    }

    private void cast() {
        etNameRegister = findViewById(R.id.etNameRegister);
        etMobileRegister = findViewById(R.id.etMobileRegister);
        etPasswordRegister = findViewById(R.id.etPasswordRegister);
        etRepeatPasswordRegister = findViewById(R.id.etRepeatPasswordRegister);
        btnRegister = findViewById(R.id.btnRegister);
        etOneTimePassword = findViewById(R.id.etOneTimePassword);
        btnOneTimePassword = findViewById(R.id.btnOneTimePassword);
        tvCallbackRegister = findViewById(R.id.tvCallbackRegister);
    }
}