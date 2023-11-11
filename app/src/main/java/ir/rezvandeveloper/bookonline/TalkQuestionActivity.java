package ir.rezvandeveloper.bookonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ir.rezvandeveloper.bookonline.adapter.AdapterTalkQuestionRV;
import ir.rezvandeveloper.bookonline.model.ModelTalkQuestionRv;

public class TalkQuestionActivity extends AppCompatActivity {

    List<ModelTalkQuestionRv> modelTalkQuestionRvs = new ArrayList<>();
    AdapterTalkQuestionRV adapterTalkQuestionRV;
    RecyclerView rvTalkQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_question);

        cast();
        init();
        getQuestion();
    }

    private void getQuestion(){
        ProgressDialog progressDialog = new ProgressDialog(TalkQuestionActivity.this);
        progressDialog.setMessage("در حال بار گذاری ...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_GET_ALL_TALK_QUESTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int length =jsonObject.getJSONArray("questions").length(),id;
                    String name,subject,question,date;

                    for (int i = 0 ; i < length ; i++) {
                        id = jsonObject.getJSONArray("questions").getJSONObject(i).getInt("id");
                        name = jsonObject.getJSONArray("questions").getJSONObject(i).getString("name");
                        subject = jsonObject.getJSONArray("questions").getJSONObject(i).getString("subject");
                        question = jsonObject.getJSONArray("questions").getJSONObject(i).getString("question");
                        date = jsonObject.getJSONArray("questions").getJSONObject(i).getString("date");

                        modelTalkQuestionRvs.add(new ModelTalkQuestionRv(id,name,subject,question,date));
                    }
                    adapterTalkQuestionRV.notifyDataSetChanged();
                } catch (JSONException e) {
                    Toast.makeText(TalkQuestionActivity.this,"مجدد تلاش کنید .",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(TalkQuestionActivity.this,TalkActivity.class);
                    startActivity(intent);
                    finish();
                    throw new RuntimeException(e);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(TalkQuestionActivity.this,"مجدد تلاش کنید .",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(TalkQuestionActivity.this,TalkActivity.class);
                startActivity(intent);
                finish();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(TalkQuestionActivity.this);
        queue.add(stringRequest);

    }

    private void init(){
        // init rv question
        adapterTalkQuestionRV = new AdapterTalkQuestionRV(modelTalkQuestionRvs, this);
        rvTalkQuestion.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager gLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        rvTalkQuestion.setLayoutManager(manager);
        rvTalkQuestion.setHasFixedSize(true);

        RecyclerView.ItemDecoration iDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvTalkQuestion.addItemDecoration(iDecoration);
        rvTalkQuestion.setHasFixedSize(true);
        rvTalkQuestion.setAdapter(adapterTalkQuestionRV);
    }

    private void cast(){
        rvTalkQuestion = findViewById(R.id.rvTalkQuestion);
    }
}