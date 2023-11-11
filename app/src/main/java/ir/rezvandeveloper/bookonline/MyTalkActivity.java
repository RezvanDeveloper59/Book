package ir.rezvandeveloper.bookonline;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.rezvandeveloper.bookonline.adapter.AdapterTalkQuestionRV;
import ir.rezvandeveloper.bookonline.model.ModelTalkQuestionRv;

public class MyTalkActivity extends AppCompatActivity {

    RecyclerView rvMyTalk;
    List<ModelTalkQuestionRv> modelTalkQuestionRvs = new ArrayList<>();
    AdapterTalkQuestionRV adapterTalkQuestionRV;

    String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_talk);

        cast();
        init();

        if (Api.sharedPreferences.contains("mobile")) {
            mobile = Api.sharedPreferences.getString("mobile",null);
        } else {
            Intent intent = new Intent(MyTalkActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        getMyQuestion();
    }

    private void getMyQuestion(){
        ProgressDialog progressDialog = new ProgressDialog(MyTalkActivity.this);
        progressDialog.setMessage("در حال دریافت اطلاعات ...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_GET_MY_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int length = jsonObject.getJSONArray("question").length(),id;
                    String name,subject,question,date;
                    Log.i("TAG", "onResponse len: " + length);
                    for (int i = 0 ; i < length ; i++) {
                        id = jsonObject.getJSONArray("question").getJSONObject(i).getInt("id");
                        name = jsonObject.getJSONArray("question").getJSONObject(i).getString("name");
                        subject = jsonObject.getJSONArray("question").getJSONObject(i).getString("subject");
                        question = jsonObject.getJSONArray("question").getJSONObject(i).getString("question");
                        date = jsonObject.getJSONArray("question").getJSONObject(i).getString("date");

                        modelTalkQuestionRvs.add(new ModelTalkQuestionRv(id,name,subject,question,date));
                    }
                    adapterTalkQuestionRV.notifyDataSetChanged();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("mobile",mobile);
                return hashMap;
            }
        };


        RequestQueue queue = Volley.newRequestQueue(MyTalkActivity.this);
        queue.add(stringRequest);
    }

    private void init(){
        // init rv response
        adapterTalkQuestionRV = new AdapterTalkQuestionRV(modelTalkQuestionRvs, this);
        rvMyTalk.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager managerR = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager gLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        rvMyTalk.setLayoutManager(managerR);
        rvMyTalk.setHasFixedSize(true);

        RecyclerView.ItemDecoration iDecorationR = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvMyTalk.addItemDecoration(iDecorationR);
        rvMyTalk.setHasFixedSize(true);
        rvMyTalk.setAdapter(adapterTalkQuestionRV);
    }

    private void cast() {
        rvMyTalk = findViewById(R.id.rvMyTalk);
    }
}