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
import ir.rezvandeveloper.bookonline.adapter.AdapterTarkeMoadelateTekrariRV;
import ir.rezvandeveloper.bookonline.bookmarked.BookmarkedTarkeMoadelateTekrariActivity;
import ir.rezvandeveloper.bookonline.model.ModelBookRv;
import ir.rezvandeveloper.bookonline.player.PlayTarkeMoadelateTekrariActivity;

public class TarkeMoadelateTekrariActivity extends AppCompatActivity {

    ImageButton ibBookmarkedTarkeMoadelateTekrari;
    AppCompatButton btnPlayLastTarkeMoadelateTekrari, btnPlayNextTarkeMoadelateTekrari, btnChanceTarkeMoadelateTekrari;
    RecyclerView rvNamesTarkeMoadelateTekrari;
    AppCompatTextView tvLastLessonTarkeMoadelateTekrari, tvRefreshMainTarkeMoadelateTekrari;
    RequestQueue requestQueue;
    public static List<ModelBookRv> listAllNamesTarkeMoadelateTekrari = new ArrayList<>();
    AdapterTarkeMoadelateTekrariRV tarkeMoadelateTekrariRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarke_moadelate_tekrari);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        Init();
        GetListNames();

        btnPlayLastTarkeMoadelateTekrari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name= "صوتی گوش نداده اید .";
                if (Api.sharedPreferences.contains("nameTarkeMoadelateTekrari")) {
                    name = Api.sharedPreferences.getString("nameTarkeMoadelateTekrari", null);
                }else {
                    Toast.makeText(TarkeMoadelateTekrariActivity.this, name, Toast.LENGTH_LONG).show();
                    return;
                }
                String finalName = name;

                Intent intent = new Intent(TarkeMoadelateTekrariActivity.this, PlayTarkeMoadelateTekrariActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", finalName);
                intent.putExtras(bundle);
                startActivity(intent, bundle);
            }
        });

        btnPlayNextTarkeMoadelateTekrari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= "صوتی گوش نداده اید .";
                if (Api.sharedPreferences.contains("nameTarkeMoadelateTekrari")) {
                    name = Api.sharedPreferences.getString("nameTarkeMoadelateTekrari", null);
                }else {
                    Toast.makeText(TarkeMoadelateTekrariActivity.this, name, Toast.LENGTH_LONG).show();
                    return;
                }

                String nextName = next(name);
                Intent intent = new Intent(TarkeMoadelateTekrariActivity.this, PlayTarkeMoadelateTekrariActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", nextName);
                intent.putExtras(bundle);
                startActivity(intent, bundle);
            }
        });

        ibBookmarkedTarkeMoadelateTekrari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TarkeMoadelateTekrariActivity.this, BookmarkedTarkeMoadelateTekrariActivity.class);
                startActivity(intent);
            }
        });

        btnChanceTarkeMoadelateTekrari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = listAllNamesTarkeMoadelateTekrari.size();
                Random random = new Random();
                length = random.nextInt(length);
                Intent intent = new Intent(TarkeMoadelateTekrariActivity.this, PlayTarkeMoadelateTekrariActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", listAllNamesTarkeMoadelateTekrari.get(length).getName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        tvRefreshMainTarkeMoadelateTekrari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetListNames();
            }
        });
    }

    @Override
    protected void onResume() {
        String name= "درسی گوش نداده اید .";
        if (Api.sharedPreferences.contains("nameTarkeMoadelateTekrari")) {
            name = Api.sharedPreferences.getString("nameTarkeMoadelateTekrari", null);
            tvLastLessonTarkeMoadelateTekrari.setVisibility(View.VISIBLE);
            tvLastLessonTarkeMoadelateTekrari.setText("آخرین صوتی که گوش کردید :" + "\n" + name);
        }else tvLastLessonTarkeMoadelateTekrari.setVisibility(View.GONE);

        super.onResume();
    }

    private void GetListNames() {
        listAllNamesTarkeMoadelateTekrari.clear();
        tvRefreshMainTarkeMoadelateTekrari.setVisibility(View.GONE);
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(TarkeMoadelateTekrariActivity.this);
        progressDialog.setMessage("در حال بارگیری لیست");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.URL_GET_ALL_LIST__TARKE_MOADELATE_TEKRARI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
//Log.i("TAG", "onResponse: " + response);
                Gson gson = new Gson();
                ModelBookRv[] model_bookRvs = gson.fromJson(response, ModelBookRv[].class);
                listAllNamesTarkeMoadelateTekrari.addAll(Arrays.asList(model_bookRvs));
                tarkeMoadelateTekrariRV.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvRefreshMainTarkeMoadelateTekrari.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(TarkeMoadelateTekrariActivity.this, "اتصال به سرور برقرار نشد .", Toast.LENGTH_LONG).show();
            }
    });
        requestQueue.add(stringRequest);
    }

    private String next(String currentName){
        int lengthName = TarkeMoadelateTekrariActivity.listAllNamesTarkeMoadelateTekrari.size();
        String nameEnd = TarkeMoadelateTekrariActivity.listAllNamesTarkeMoadelateTekrari.get(lengthName - 1).getName();

        if (currentName.equals(nameEnd)) {
            Toast.makeText(this, "تکرار شد .", Toast.LENGTH_LONG).show();
            return nameEnd;
        }

        for (int i = 0 ; i < lengthName ; i++) {
            if (TarkeMoadelateTekrariActivity.listAllNamesTarkeMoadelateTekrari.get(i).getName().equals(currentName)){
                return TarkeMoadelateTekrariActivity.listAllNamesTarkeMoadelateTekrari.get(i + 1).getName();
            }
        }
        return "";
    }

    private void Init() {
        ibBookmarkedTarkeMoadelateTekrari = findViewById(R.id.ibBookmarkedTarkeMoadelateTekrari);
        btnChanceTarkeMoadelateTekrari = findViewById(R.id.btnChanceTarkeMoadelateTekrari);
        btnPlayLastTarkeMoadelateTekrari = findViewById(R.id.btnPlayLastTarkeMoadelateTekrari);
        btnPlayNextTarkeMoadelateTekrari = findViewById(R.id.btnPlayNextTarkeMoadelateTekrari);
        rvNamesTarkeMoadelateTekrari = findViewById(R.id.rvNamesTarkeMoadelateTekrari);
        tvLastLessonTarkeMoadelateTekrari = findViewById(R.id.tvLastLessonTarkeMoadelateTekrari);
        tvRefreshMainTarkeMoadelateTekrari = findViewById(R.id.tvRefreshMainTarkeMoadelateTekrari);
        requestQueue = Volley.newRequestQueue(this);

        tarkeMoadelateTekrariRV = new AdapterTarkeMoadelateTekrariRV(listAllNamesTarkeMoadelateTekrari, this);
        rvNamesTarkeMoadelateTekrari.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager gLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        rvNamesTarkeMoadelateTekrari.setLayoutManager(manager);
        rvNamesTarkeMoadelateTekrari.setHasFixedSize(true);

        RecyclerView.ItemDecoration iDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvNamesTarkeMoadelateTekrari.addItemDecoration(iDecoration);
        rvNamesTarkeMoadelateTekrari.setHasFixedSize(true);
        rvNamesTarkeMoadelateTekrari.setAdapter(tarkeMoadelateTekrariRV);

    }


}