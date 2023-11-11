package ir.rezvandeveloper.bookonline;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.rezvandeveloper.bookonline.adapter.AdapterResponseRV;
import ir.rezvandeveloper.bookonline.model.ModelResponseRv;

public class ResponseActivity extends AppCompatActivity {

    AppCompatTextView tvNameResponse,tvSubjectResponse,tvQuestionResponse,tvNumberResponse,tvDateResponse;
    AppCompatEditText etSendResponse;
    AppCompatButton btnSendResponse;
    ImageButton ibBookmarkOff,ibBookmarkOn;
    RecyclerView rvResponses;
    List<ModelResponseRv> modelResponseRvs = new ArrayList<>();
    AdapterResponseRV adapterResponseRV;
    String idQuestion, responseInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        cast();
        init();

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        idQuestion = bundle.getString("id");
        tvNumberResponse.setText(Api.ConvertNumberEnToFa(idQuestion));

        String name,subject,question,date;
        name = bundle.getString("name");
        subject = bundle.getString("subject");
        question = bundle.getString("question");
        date = bundle.getString("date");

        tvNameResponse.setText(name);
        tvSubjectResponse.setText(subject);
        tvQuestionResponse.setText(question);
        tvDateResponse.setText(date);

        getResponses();

        btnSendResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseInput = etSendResponse.getText().toString().trim();

                if (responseInput.equals("")) {
                    Toast.makeText(ResponseActivity.this,"متنی وارد نشده است .",Toast.LENGTH_LONG).show();
                    return;
                }

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_SEND_RESPONSE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String s = jsonObject.getString("message");

                            if (s.equals("ok")) {
                                Api.ShowAlertDialog(ResponseActivity.this,"ارسال","پاسخ شما با موفقیت ارسال شد .");
                                modelResponseRvs.add(new ModelResponseRv(Integer.valueOf(idQuestion),Integer.valueOf(idQuestion),name,responseInput,date));
                                adapterResponseRV.notifyDataSetChanged();
                                etSendResponse.setText("");
                            }else {
                                Toast.makeText(ResponseActivity.this, "بعداً تلاش کنید .", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ResponseActivity.this, "مجدد تلاش کنید .", Toast.LENGTH_LONG).show();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put("id_question", idQuestion);
                        hashMap.put("name", name);
                        hashMap.put("response", responseInput);

                        DateFormat df = new SimpleDateFormat("HH:mm - yyyy/MM/d");
                        String dateNew = df.format(Calendar.getInstance().getTime());
                        hashMap.put("date", dateNew);
                        return hashMap;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(ResponseActivity.this);
                queue.add(stringRequest);
            }
        });

        if(Api.sharedPreferences.contains(String.valueOf(idQuestion))){
            ibBookmarkOn.setVisibility(View.VISIBLE);
            ibBookmarkOff.setVisibility(View.INVISIBLE);
        }else{
            ibBookmarkOn.setVisibility(View.INVISIBLE);
            ibBookmarkOff.setVisibility(View.VISIBLE);
        }

        ibBookmarkOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibBookmarkOff.setVisibility(View.INVISIBLE);
                ibBookmarkOn.setVisibility(View.VISIBLE);

                SharedPreferences.Editor editor = Api.sharedPreferences.edit();
                editor.putString(String.valueOf(idQuestion),String.valueOf(idQuestion));
                editor.apply();
            }
        });

        ibBookmarkOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibBookmarkOff.setVisibility(View.VISIBLE);
                ibBookmarkOn.setVisibility(View.INVISIBLE);

                SharedPreferences.Editor editor = Api.sharedPreferences.edit();
                editor.remove(String.valueOf(idQuestion));
                editor.apply();
            }
        });
    }

    private void getResponses(){
        ProgressDialog progressDialog = new ProgressDialog(ResponseActivity.this);
        progressDialog.setMessage("در حال بارگیری ...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_GET_ALL_RESPONSE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int length = jsonObject.getJSONArray("responses").length(),id,id_question;
//                    Log.i("TAG", "onResponse length: " + length);
                    String name,responses,date;
                    for (int i = 0 ; i < length ; i++) {
                        id = jsonObject.getJSONArray("responses").getJSONObject(i).getInt("id");
                        id_question = jsonObject.getJSONArray("responses").getJSONObject(i).getInt("id_question");
                        name = jsonObject.getJSONArray("responses").getJSONObject(i).getString("name");
                        responses = jsonObject.getJSONArray("responses").getJSONObject(i).getString("response");
                        date = jsonObject.getJSONArray("responses").getJSONObject(i).getString("date");

                        modelResponseRvs.add(new ModelResponseRv(id,id_question,name,responses,date));
                    }
                    adapterResponseRV.notifyDataSetChanged();
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
                params.put("id_question", idQuestion);
                return params;
            }
        };
            RequestQueue queue = Volley.newRequestQueue(ResponseActivity.this);
            queue.add(stringRequest);
    }

    private void init(){
        // init rv response
        adapterResponseRV = new AdapterResponseRV(modelResponseRvs, this);
        rvResponses.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager managerR = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager gLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        rvResponses.setLayoutManager(managerR);
        rvResponses.setHasFixedSize(true);

        RecyclerView.ItemDecoration iDecorationR = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvResponses.addItemDecoration(iDecorationR);
        rvResponses.setHasFixedSize(true);
        rvResponses.setAdapter(adapterResponseRV);
    }

    private void cast() {
        tvNameResponse = findViewById(R.id.tvNameResponse);
        tvSubjectResponse = findViewById(R.id.tvSubjectResponse);
        tvQuestionResponse = findViewById(R.id.tvQuestionResponse);
        tvNumberResponse = findViewById(R.id.tvNumberResponse);
        tvDateResponse = findViewById(R.id.tvDateResponse);
        etSendResponse = findViewById(R.id.etSendResponse);
        btnSendResponse = findViewById(R.id.btnSendResponse);
        ibBookmarkOff = findViewById(R.id.ibBookmarkOff);
        ibBookmarkOn = findViewById(R.id.ibBookmarkOn);
        rvResponses = findViewById(R.id.rvResponses);
    }
}