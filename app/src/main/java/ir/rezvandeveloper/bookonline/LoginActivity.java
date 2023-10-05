package ir.rezvandeveloper.bookonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

public class LoginActivity extends AppCompatActivity {

    TextInputLayout etMobileLogin,etPasswordLogin;
    AppCompatButton btnLogin;
    AppCompatTextView tvRegisterLogin,tvForgetPasswordLogin;
    String mobile,name,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cast();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile = etMobileLogin.getEditText().getText().toString();
                password = etPasswordLogin.getEditText().getText().toString();

                if (mobile.equals("")) {
                    Toast.makeText(LoginActivity.this, "شماره موبایل را وارد کنید .", Toast.LENGTH_LONG).show();
                    return;
                }

                if (mobile.length() != 11) {
                    Toast.makeText(LoginActivity.this, "شماره موبایل را صحیح وارد کنید .", Toast.LENGTH_LONG).show();
                    return;
                }

                if (password.equals("")) {
                    Toast.makeText(LoginActivity.this, "رمز را وارد کنید .", Toast.LENGTH_LONG).show();
                    return;
                }

                checkUser();
            }
        });

        tvForgetPasswordLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = etMobileLogin.getEditText().getText().toString();

                if (mobile.equals("")) {
                    Toast.makeText(LoginActivity.this, "شماره موبایل را وارد کنید .", Toast.LENGTH_LONG).show();
                    return;
                }

                if (mobile.length() != 11) {
                    Toast.makeText(LoginActivity.this, "شماره موبایل را صحیح وارد کنید .", Toast.LENGTH_LONG).show();
                    return;
                }

                ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("در حال ارسال ...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_FORGET_PASSWORD, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            tvForgetPasswordLogin.setVisibility(View.INVISIBLE);
                            Api.ShowAlertDialog(LoginActivity.this, "اوکی", "رمز برای شما ارسال شد .");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                }) {
                    protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("mobile", mobile);
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(stringRequest);
            }
        });

        tvRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkUser() {
        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
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
                        loginUser();
                    }else {
                        //oneTimePassword();
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
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(stringRequest);
    }

    private void loginUser() {
        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("در حال ارسال اطلاعات ...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_LOGIN_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                JSONObject jsonObject;
//                Log.i("TAG", "onResponse: " + response);

                try {
                    jsonObject = new JSONObject(response);
//                    Log.i("TAG", "onResponse: " + jsonObject.getString("message"));

                    if (jsonObject.getString("message").equals("login beshe")){
                        name = jsonObject.getString("name");
                        //login
                        SharedPreferences.Editor editor = Api.sharedPreferences.edit();
                        editor.putString("mobile", mobile);
                        editor.putString("nameUser", name);
                        editor.putString("password", password);
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this,TalkActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Api.ShowAlertDialog(LoginActivity.this,"توجه","رمز وارد شده صحیح نمی باشد .");
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
                params.put("password", password);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(stringRequest);
    }

    private void cast() {
        etMobileLogin = findViewById(R.id.etMobileLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegisterLogin = findViewById(R.id.tvRegisterLogin);
        tvForgetPasswordLogin = findViewById(R.id.tvForgetPasswordLogin);
    }
}