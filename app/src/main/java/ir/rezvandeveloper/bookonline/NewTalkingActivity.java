package ir.rezvandeveloper.bookonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewTalkingActivity extends AppCompatActivity {

    AppCompatEditText etSubjectNewTalking,etQuestionNewTalking;
    AppCompatButton btnSentNewTalking;
    String mobile,name,subject,newTalking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_talking);

        cast();

        if (Api.sharedPreferences.contains("mobile")) {
            mobile = Api.sharedPreferences.getString("mobile", null);
            name = Api.sharedPreferences.getString("nameUser", null);
        }else {
            Toast.makeText(this, "ابتدا وارد شوید .", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(NewTalkingActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        btnSentNewTalking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subject = etSubjectNewTalking.getText().toString().trim();
                newTalking = etQuestionNewTalking.getText().toString().trim();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_SEND_NEW_TALKING, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String s = jsonObject.getString("message");
                            if (s.equals("ok")) {
                                Intent intent = new Intent(NewTalkingActivity.this,TalkActivity.class);
                                new AlertDialog.Builder(NewTalkingActivity.this)
                                    .setTitle("ارسال شد")
                                    .setMessage("پیام شما با موفقیت ارسال شد .")
                                    .setCancelable(false)
                                    .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(intent);
                                            finish();
                                        }
                                    })
                                    .create()
                                    .show();
                            } else {
//                                Toast.makeText(NewTalkingActivity.this, "خطا 1 در برقراری ارتباط با سرور . مجدد تلاش کنید .", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(NewTalkingActivity.this, "خطا 2 در برقراری ارتباط با سرور . مجدد تلاش کنید .", Toast.LENGTH_LONG).show();
                    }
                }){
                    protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                        HashMap<String,String> params = new HashMap<>();
                        params.put("mobile", mobile);
                        params.put("name", name);
                        params.put("subject", subject);
                        params.put("question", newTalking);

                        DateFormat df = new SimpleDateFormat("HH:mm - yyyy/MM/d");
                        String date = df.format(Calendar.getInstance().getTime());
                        params.put("date",date);
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(NewTalkingActivity.this);
                queue.add(stringRequest);
            }
        });
    }

    private void cast() {
        etSubjectNewTalking = findViewById(R.id.etSubjectNewTalking);
        etQuestionNewTalking = findViewById(R.id.etQuestionNewTalking);
        btnSentNewTalking = findViewById(R.id.btnSentNewTalking);
    }
}