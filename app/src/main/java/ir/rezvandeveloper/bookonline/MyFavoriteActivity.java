package ir.rezvandeveloper.bookonline;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class MyFavoriteActivity extends AppCompatActivity {

    RecyclerView rvMyFavorite;
    List<ModelTalkQuestionRv> modelTalkQuestionRvs = new ArrayList<>();
    AdapterTalkQuestionRV adapterTalkQuestionRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite);

        cast();
        init();

        for (int i = 0 ; i < 100000 ; i++) {
            if (Api.sharedPreferences.contains(String.valueOf(i))) {
                Log.i("TAG", "onCreate i: " + i);
                int finalI = i;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_GET_MY_FAVORITE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            int id = Integer.valueOf(jsonObject.getString("id"));
                            String name = jsonObject.getString("name");
                            String subject = jsonObject.getString("subject");
                            String question = jsonObject.getString("question");
                            String date = jsonObject.getString("date");
                            modelTalkQuestionRvs.add(new ModelTalkQuestionRv(id,name,subject,question,date));
                            adapterTalkQuestionRV.notifyDataSetChanged();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put("id",String.valueOf(finalI));
                        return hashMap;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(MyFavoriteActivity.this);
                queue.add(stringRequest);
            }
        }

    }

    private void init(){
        // init rv response
        adapterTalkQuestionRV = new AdapterTalkQuestionRV(modelTalkQuestionRvs, this);
        rvMyFavorite.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager managerR = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager gLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        rvMyFavorite.setLayoutManager(managerR);
        rvMyFavorite.setHasFixedSize(true);

        RecyclerView.ItemDecoration iDecorationR = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvMyFavorite.addItemDecoration(iDecorationR);
        rvMyFavorite.setHasFixedSize(true);
        rvMyFavorite.setAdapter(adapterTalkQuestionRV);
    }

    private void cast() {
        rvMyFavorite = findViewById(R.id.rvMyFavorite);
    }
}