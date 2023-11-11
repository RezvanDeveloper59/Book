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

import ir.rezvandeveloper.bookonline.adapter.AdapterHalghehayeMafghoodeRV;
import ir.rezvandeveloper.bookonline.adapter.AdapterMotefaregheRV;
import ir.rezvandeveloper.bookonline.bookmarked.BookmarkedHalghehayeMafghoodeActivity;
import ir.rezvandeveloper.bookonline.model.ModelBookRv;
import ir.rezvandeveloper.bookonline.player.PlayHalghehayeMafghoodeActivity;

public class HalghehayeMafghoodeActivity extends AppCompatActivity {

    ImageButton ibBookmarkedHalghehayeMafghoode;
    AppCompatButton btnPlayLastHalghehayeMafghoode, btnPlayNextHalghehayeMafghoode, btnChanceHalghehayeMafghoode;
    RecyclerView rvNamesHalghehayeMafghoode;
    AppCompatTextView tvLastLessonHalghehayeMafghoode, tvRefreshMainHalghehayeMafghoode;
    RequestQueue requestQueue;
    public static List<ModelBookRv> listAllNamesHalghehayeMafghoode = new ArrayList<>();
    AdapterHalghehayeMafghoodeRV halghehayeMafghoodeRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halghehaye_mafghoode);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        Init();
        GetListNames();

        btnPlayLastHalghehayeMafghoode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name= "صوتی گوش نداده اید .";
                if (Api.sharedPreferences.contains("nameHalghehayeMafghoode")) {
                    name = Api.sharedPreferences.getString("nameHalghehayeMafghoode", null);
                }else {
                    Toast.makeText(HalghehayeMafghoodeActivity.this, name, Toast.LENGTH_LONG).show();
                    return;
                }
                String finalName = name;

                Intent intent = new Intent(HalghehayeMafghoodeActivity.this, PlayHalghehayeMafghoodeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", finalName);
                intent.putExtras(bundle);
                startActivity(intent, bundle);
            }
        });

        btnPlayNextHalghehayeMafghoode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= "صوتی گوش نداده اید .";
                if (Api.sharedPreferences.contains("nameHalghehayeMafghoode")) {
                    name = Api.sharedPreferences.getString("nameHalghehayeMafghoode", null);
                }else {
                    Toast.makeText(HalghehayeMafghoodeActivity.this, name, Toast.LENGTH_LONG).show();
                    return;
                }

                String nextName = next(name);
                Intent intent = new Intent(HalghehayeMafghoodeActivity.this, PlayHalghehayeMafghoodeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", nextName);
                intent.putExtras(bundle);
                startActivity(intent, bundle);
            }
        });

        ibBookmarkedHalghehayeMafghoode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HalghehayeMafghoodeActivity.this, BookmarkedHalghehayeMafghoodeActivity.class);
                startActivity(intent);
            }
        });

        btnChanceHalghehayeMafghoode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = listAllNamesHalghehayeMafghoode.size();
                Random random = new Random();
                length = random.nextInt(length);
                Intent intent = new Intent(HalghehayeMafghoodeActivity.this, PlayHalghehayeMafghoodeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", listAllNamesHalghehayeMafghoode.get(length).getName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        tvRefreshMainHalghehayeMafghoode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetListNames();
            }
        });
    }

    @Override
    protected void onResume() {
        String name= "درسی گوش نداده اید .";
        if (Api.sharedPreferences.contains("nameHalghehayeMafghoode")) {
            name = Api.sharedPreferences.getString("nameHalghehayeMafghoode", null);
            tvLastLessonHalghehayeMafghoode.setVisibility(View.VISIBLE);
            tvLastLessonHalghehayeMafghoode.setText("آخرین صوتی که گوش کردید :" + "\n" + name);
        }else tvLastLessonHalghehayeMafghoode.setVisibility(View.GONE);

        super.onResume();
    }

    private void GetListNames() {
        listAllNamesHalghehayeMafghoode.clear();
        tvRefreshMainHalghehayeMafghoode.setVisibility(View.GONE);
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(HalghehayeMafghoodeActivity.this);
        progressDialog.setMessage("در حال بارگیری لیست");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.URL_GET_ALL_LIST__HALGHEHAYE_MAFGHOODE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
//Log.i("TAG", "onResponse: " + response);
                Gson gson = new Gson();
                ModelBookRv[] model_bookRvs = gson.fromJson(response, ModelBookRv[].class);
                listAllNamesHalghehayeMafghoode.addAll(Arrays.asList(model_bookRvs));
                halghehayeMafghoodeRV.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvRefreshMainHalghehayeMafghoode.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(HalghehayeMafghoodeActivity.this, "اتصال به سرور برقرار نشد .", Toast.LENGTH_LONG).show();
            }
    });
        requestQueue.add(stringRequest);
    }

    private String next(String currentName){
        int lengthName = HalghehayeMafghoodeActivity.listAllNamesHalghehayeMafghoode.size();
        String nameEnd = HalghehayeMafghoodeActivity.listAllNamesHalghehayeMafghoode.get(lengthName - 1).getName();

        if (currentName.equals(nameEnd)) {
            Toast.makeText(this, "تکرار شد .", Toast.LENGTH_LONG).show();
            return nameEnd;
        }

        for (int i = 0 ; i < lengthName ; i++) {
            if (HalghehayeMafghoodeActivity.listAllNamesHalghehayeMafghoode.get(i).getName().equals(currentName)){
                return HalghehayeMafghoodeActivity.listAllNamesHalghehayeMafghoode.get(i + 1).getName();
            }
        }
        return "";
    }

    private void Init() {
        ibBookmarkedHalghehayeMafghoode = findViewById(R.id.ibBookmarkedHalghehayeMafghoode);
        btnChanceHalghehayeMafghoode = findViewById(R.id.btnChanceHalghehayeMafghoode);
        btnPlayLastHalghehayeMafghoode = findViewById(R.id.btnPlayLastHalghehayeMafghoode);
        btnPlayNextHalghehayeMafghoode = findViewById(R.id.btnPlayNextHalghehayeMafghoode);
        rvNamesHalghehayeMafghoode = findViewById(R.id.rvNamesHalghehayeMafghoode);
        tvLastLessonHalghehayeMafghoode = findViewById(R.id.tvLastLessonHalghehayeMafghoode);
        tvRefreshMainHalghehayeMafghoode = findViewById(R.id.tvRefreshMainHalghehayeMafghoode);
        requestQueue = Volley.newRequestQueue(this);

        halghehayeMafghoodeRV = new AdapterHalghehayeMafghoodeRV(listAllNamesHalghehayeMafghoode, this);
        rvNamesHalghehayeMafghoode.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager gLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        rvNamesHalghehayeMafghoode.setLayoutManager(manager);
        rvNamesHalghehayeMafghoode.setHasFixedSize(true);

        RecyclerView.ItemDecoration iDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvNamesHalghehayeMafghoode.addItemDecoration(iDecoration);
        rvNamesHalghehayeMafghoode.setHasFixedSize(true);
        rvNamesHalghehayeMafghoode.setAdapter(halghehayeMafghoodeRV);

    }


}