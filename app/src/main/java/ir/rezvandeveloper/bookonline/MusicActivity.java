package ir.rezvandeveloper.bookonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ir.rezvandeveloper.bookonline.adapter.AdapterMusicRV;
import ir.rezvandeveloper.bookonline.model.ModelBookRv;

public class MusicActivity extends AppCompatActivity {

    RecyclerView rvMusic;
    AppCompatTextView tvRefreshMusic;
    public static List<ModelBookRv> modelMusicRvs = new ArrayList<>();
    AdapterMusicRV adapterMusicRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        init();
        GetListNames();
    }

    private void GetListNames() {
        tvRefreshMusic.setVisibility(View.GONE);
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(MusicActivity.this);
        progressDialog.setMessage("در حال بارگیری لیست");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.URL_GET_ALL_LIST_MUSIC, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                modelMusicRvs.clear();
//Log.i("TAG", "onResponse GetListNames: " + response);
                Gson gson = new Gson();
                ModelBookRv[] model_bookRvs = gson.fromJson(response, ModelBookRv[].class);
                modelMusicRvs.addAll(Arrays.asList(model_bookRvs));
                adapterMusicRV.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvRefreshMusic.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(MusicActivity.this, "اتصال به سرور برقرار نشد .", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(MusicActivity.this);
        queue.add(stringRequest);
    }

    private void init() {
        rvMusic = findViewById(R.id.rvMusic);
        tvRefreshMusic = findViewById(R.id.tvRefreshMusic);

        adapterMusicRV = new AdapterMusicRV(modelMusicRvs, this);
        rvMusic.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager gLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        rvMusic.setLayoutManager(manager);
        rvMusic.setHasFixedSize(true);

        RecyclerView.ItemDecoration iDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvMusic.addItemDecoration(iDecoration);
        rvMusic.setHasFixedSize(true);
        rvMusic.setAdapter(adapterMusicRV);

    }
}