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

import ir.rezvandeveloper.bookonline.bookmarked.BookmarkedMotefaregheActivity;
import ir.rezvandeveloper.bookonline.model.ModelBookRv;
import ir.rezvandeveloper.bookonline.player.PlayMotefaregheActivity;
import ir.rezvandeveloper.bookonline.adapter.AdapterMotefaregheRV;

public class MotefaregheActivity extends AppCompatActivity {

    ImageButton ibBookmarkedMotefareghe;
    AppCompatButton btnPlayLastMotefareghe,btnPlayNextMotefareghe,btnChanceMotefareghe;
    RecyclerView rvNamesMotefareghe;
    AppCompatTextView tvLastLessonMotefareghe,tv_refresh_main_motefareghe;
    RequestQueue requestQueue;
    public static List<ModelBookRv> listAllNamesMotefareghe = new ArrayList<>();
    AdapterMotefaregheRV adapterMotefaregheRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motefareghe);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        Init();
        GetListNames();

        btnPlayLastMotefareghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name= "صوتی گوش نداده اید .";
                if (Api.sharedPreferences.contains("nameMotefareghe")) {
                    name = Api.sharedPreferences.getString("nameMotefareghe", null);
                }else {
                    Toast.makeText(MotefaregheActivity.this, name, Toast.LENGTH_LONG).show();
                    return;
                }
                String finalName = name;

                Intent intent = new Intent(MotefaregheActivity.this, PlayMotefaregheActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", finalName);
                intent.putExtras(bundle);
                startActivity(intent, bundle);
            }
        });

        btnPlayNextMotefareghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= "صوتی گوش نداده اید .";
                if (Api.sharedPreferences.contains("nameMotefareghe")) {
                    name = Api.sharedPreferences.getString("nameMotefareghe", null);
                }else {
                    Toast.makeText(MotefaregheActivity.this, name, Toast.LENGTH_LONG).show();
                    return;
                }

                String nextName = next(name);
                Intent intent = new Intent(MotefaregheActivity.this, PlayMotefaregheActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", nextName);
                intent.putExtras(bundle);
                startActivity(intent, bundle);
            }
        });

        ibBookmarkedMotefareghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MotefaregheActivity.this, BookmarkedMotefaregheActivity.class);
                startActivity(intent);
            }
        });

        btnChanceMotefareghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = listAllNamesMotefareghe.size();
                Random random = new Random();
                length = random.nextInt(length);
                Intent intent = new Intent(MotefaregheActivity.this,PlayMotefaregheActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", listAllNamesMotefareghe.get(length).getName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        tv_refresh_main_motefareghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetListNames();
            }
        });
    }

    @Override
    protected void onResume() {
        String name= "درسی گوش نداده اید .";
        if (Api.sharedPreferences.contains("nameMotefareghe")) {
            name = Api.sharedPreferences.getString("nameMotefareghe", null);
            tvLastLessonMotefareghe.setVisibility(View.VISIBLE);
            tvLastLessonMotefareghe.setText("آخرین صوتی که گوش کردید :" + "\n" + name);
        }else tvLastLessonMotefareghe.setVisibility(View.GONE);

        super.onResume();
    }

    private void GetListNames() {
        listAllNamesMotefareghe.clear();
        tv_refresh_main_motefareghe.setVisibility(View.GONE);
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(MotefaregheActivity.this);
        progressDialog.setMessage("در حال بارگیری لیست");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.URL_GET_ALL_LIST_MOTEFAREGHE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
//Log.i("TAG", "onResponse: " + response);
                Gson gson = new Gson();
                ModelBookRv[] model_bookRvs = gson.fromJson(response, ModelBookRv[].class);
                listAllNamesMotefareghe.addAll(Arrays.asList(model_bookRvs));
                adapterMotefaregheRV.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv_refresh_main_motefareghe.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(MotefaregheActivity.this, "اتصال به سرور برقرار نشد .", Toast.LENGTH_LONG).show();
            }
    });
        requestQueue.add(stringRequest);
    }

    private String next(String currentName){
        int lengthName = MotefaregheActivity.listAllNamesMotefareghe.size();
        String nameEnd = MotefaregheActivity.listAllNamesMotefareghe.get(lengthName - 1).getName();

        if (currentName.equals(nameEnd)) {
            Toast.makeText(this, "تکرار شد .", Toast.LENGTH_LONG).show();
            return nameEnd;
        }

        for (int i = 0 ; i < lengthName ; i++) {
            if (MotefaregheActivity.listAllNamesMotefareghe.get(i).getName().equals(currentName)){
                return MotefaregheActivity.listAllNamesMotefareghe.get(i + 1).getName();
            }
        }
        return "";
    }

    private void Init() {
        ibBookmarkedMotefareghe = findViewById(R.id.ibBookmarkedMotefareghe);
        btnChanceMotefareghe = findViewById(R.id.btnChanceMotefareghe);
        btnPlayLastMotefareghe = findViewById(R.id.btnPlayLastMotefareghe);
        btnPlayNextMotefareghe = findViewById(R.id.btnPlayNextMotefareghe);
        rvNamesMotefareghe = findViewById(R.id.rvNamesMotefareghe);
        tvLastLessonMotefareghe = findViewById(R.id.tvLastLessonMotefareghe);
        tv_refresh_main_motefareghe = findViewById(R.id.tvRefreshMainMotefareghe);
        requestQueue = Volley.newRequestQueue(this);

        adapterMotefaregheRV = new AdapterMotefaregheRV(listAllNamesMotefareghe, this);
        rvNamesMotefareghe.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager gLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        rvNamesMotefareghe.setLayoutManager(manager);
        rvNamesMotefareghe.setHasFixedSize(true);

        RecyclerView.ItemDecoration iDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvNamesMotefareghe.addItemDecoration(iDecoration);
        rvNamesMotefareghe.setHasFixedSize(true);
        rvNamesMotefareghe.setAdapter(adapterMotefaregheRV);

    }


}