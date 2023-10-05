package ir.rezvandeveloper.bookonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

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

public class WithoutResponseActivity extends AppCompatActivity {

    List<ModelWithoutResponseRv> modelWithoutResponseRvs;
    AdapterWithoutResponseRV adapterWithoutResponseRV;
    RecyclerView rvWithoutResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_without_response);

        cast();
        init();
        get();
    }

    private void get() {
        ProgressDialog progressDialog = new ProgressDialog(WithoutResponseActivity.this);
        progressDialog.setMessage("در حال دریافت اطلاعات ...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_WITHOUT_RESPONSE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    modelWithoutResponseRvs.clear();
                    boolean exist = true;
                    int lengthQuestion = jsonObject.getJSONArray("question").length();
                    int lengthResponse = jsonObject.getJSONArray("response").length();
                    int id,id_question;
                    String name,question,subject,date;

                    for (int i = 0 ; i < lengthQuestion ; i++) {
                        id = jsonObject.getJSONArray("question").getJSONObject(i).getInt("id");
                        name = jsonObject.getJSONArray("question").getJSONObject(i).getString("name");
                        subject = jsonObject.getJSONArray("question").getJSONObject(i).getString("subject");
                        question = jsonObject.getJSONArray("question").getJSONObject(i).getString("question");
                        date = jsonObject.getJSONArray("question").getJSONObject(i).getString("date");

                        for (int j = 0 ; j < lengthResponse ; j++){
                            id_question = Integer.valueOf(jsonObject.getJSONArray("response").getJSONObject(j).getInt("id_question"));
                            if (id == id_question) {
                                exist = true;
                                break;
                            }else {
                                exist = false;
                            }
                        }

                        if (!exist) {
                            modelWithoutResponseRvs.add(new ModelWithoutResponseRv(id, name, subject, question,date));
                            exist = true;
                        }
                    }
                    adapterWithoutResponseRV.notifyDataSetChanged();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(WithoutResponseActivity.this);
        queue.add(stringRequest);
    }

    private void init(){
        modelWithoutResponseRvs = new ArrayList<>();
        adapterWithoutResponseRV = new AdapterWithoutResponseRV(modelWithoutResponseRvs, this);
        rvWithoutResponse.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager gLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        rvWithoutResponse.setLayoutManager(manager);
        rvWithoutResponse.setHasFixedSize(true);

        RecyclerView.ItemDecoration iDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvWithoutResponse.addItemDecoration(iDecoration);
        rvWithoutResponse.setHasFixedSize(true);
        rvWithoutResponse.setAdapter(adapterWithoutResponseRV);
    }

    private void cast(){
        rvWithoutResponse = findViewById(R.id.rvWithoutResponse);
    }
}