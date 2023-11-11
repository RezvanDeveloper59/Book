package ir.rezvandeveloper.bookonline;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Random;

import ir.rezvandeveloper.bookonline.adapter.AdapterMotefaregheRV;
import ir.rezvandeveloper.bookonline.adapter.AdapterPodcastRV;
import ir.rezvandeveloper.bookonline.bookmarked.BookmarkedPodcastActivity;
import ir.rezvandeveloper.bookonline.model.ModelBookRv;
import ir.rezvandeveloper.bookonline.player.PlayPodcastActivity;

public class PodcastActivity extends AppCompatActivity {

    ImageButton ibBookmarkedPodcast;
    AppCompatButton btnPlayLastPodcast, btnPlayNextPodcast, btnChancePodcast;
    RecyclerView rvNamesPodcast;
    AppCompatTextView tvLastLessonPodcast, tvRefreshMainPodcast;
    RequestQueue requestQueue;
    public static List<ModelBookRv> listAllNamesPodcast = new ArrayList<>();
    AdapterPodcastRV podcastRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        Init();
        GetListNames();

        btnPlayLastPodcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name= "صوتی گوش نداده اید .";
                if (Api.sharedPreferences.contains("namePodcast")) {
                    name = Api.sharedPreferences.getString("namePodcast", null);
                }else {
                    Toast.makeText(PodcastActivity.this, name, Toast.LENGTH_LONG).show();
                    return;
                }
                String finalName = name;

                Intent intent = new Intent(PodcastActivity.this, PlayPodcastActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", finalName);
                intent.putExtras(bundle);
                startActivity(intent, bundle);
            }
        });

        btnPlayNextPodcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= "صوتی گوش نداده اید .";
                if (Api.sharedPreferences.contains("namePodcast")) {
                    name = Api.sharedPreferences.getString("namePodcast", null);
                }else {
                    Toast.makeText(PodcastActivity.this, name, Toast.LENGTH_LONG).show();
                    return;
                }

                String nextName = next(name);
                Intent intent = new Intent(PodcastActivity.this, PlayPodcastActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", nextName);
                intent.putExtras(bundle);
                startActivity(intent, bundle);
            }
        });

        ibBookmarkedPodcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PodcastActivity.this, BookmarkedPodcastActivity.class);
                startActivity(intent);
            }
        });

        btnChancePodcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = listAllNamesPodcast.size();
                Random random = new Random();
                length = random.nextInt(length);
                Intent intent = new Intent(PodcastActivity.this,PlayPodcastActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", listAllNamesPodcast.get(length).getName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        tvRefreshMainPodcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetListNames();
            }
        });
    }

    @Override
    protected void onResume() {
        String name= "درسی گوش نداده اید .";
        if (Api.sharedPreferences.contains("namePodcast")) {
            name = Api.sharedPreferences.getString("namePodcast", null);
            tvLastLessonPodcast.setVisibility(View.VISIBLE);
            tvLastLessonPodcast.setText("آخرین صوتی که گوش کردید :" + "\n" + name);
        }else tvLastLessonPodcast.setVisibility(View.GONE);

        super.onResume();
    }

    private void GetListNames() {
        listAllNamesPodcast.clear();
        tvRefreshMainPodcast.setVisibility(View.GONE);
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(PodcastActivity.this);
        progressDialog.setMessage("در حال بارگیری لیست");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.URL_GET_ALL_LIST_PODCAST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
//Log.i("TAG", "onResponse: " + response);
                Gson gson = new Gson();
                ModelBookRv[] model_bookRvs = gson.fromJson(response, ModelBookRv[].class);
                listAllNamesPodcast.addAll(Arrays.asList(model_bookRvs));
                podcastRV.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvRefreshMainPodcast.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(PodcastActivity.this, "اتصال به سرور برقرار نشد .", Toast.LENGTH_LONG).show();
            }
    });
        requestQueue.add(stringRequest);
    }

    private String next(String currentName){
        int lengthName = PodcastActivity.listAllNamesPodcast.size();
        String nameEnd = PodcastActivity.listAllNamesPodcast.get(lengthName - 1).getName();

        if (currentName.equals(nameEnd)) {
            Toast.makeText(this, "تکرار شد .", Toast.LENGTH_LONG).show();
            return nameEnd;
        }

        for (int i = 0 ; i < lengthName ; i++) {
            if (PodcastActivity.listAllNamesPodcast.get(i).getName().equals(currentName)){
                return PodcastActivity.listAllNamesPodcast.get(i + 1).getName();
            }
        }
        return "";
    }

    private void Init() {
        ibBookmarkedPodcast = findViewById(R.id.ibBookmarkedPodcast);
        btnChancePodcast = findViewById(R.id.btnChancePodcast);
        btnPlayLastPodcast = findViewById(R.id.btnPlayLastPodcast);
        btnPlayNextPodcast = findViewById(R.id.btnPlayNextPodcast);
        rvNamesPodcast = findViewById(R.id.rvNamesPodcast);
        tvLastLessonPodcast = findViewById(R.id.tvLastLessonPodcast);
        tvRefreshMainPodcast = findViewById(R.id.tvRefreshMainPodcast);
        requestQueue = Volley.newRequestQueue(this);

        podcastRV = new AdapterPodcastRV(listAllNamesPodcast, this);
        rvNamesPodcast.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager gLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        rvNamesPodcast.setLayoutManager(manager);
        rvNamesPodcast.setHasFixedSize(true);

        RecyclerView.ItemDecoration iDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvNamesPodcast.addItemDecoration(iDecoration);
        rvNamesPodcast.setHasFixedSize(true);
        rvNamesPodcast.setAdapter(podcastRV);
    }
}