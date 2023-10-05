package ir.rezvandeveloper.bookonline;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class TahrimeSokhanActivity extends AppCompatActivity {

    ImageButton ibBookmarkedTahrimeSokhan;
    AppCompatButton btnPlayLastTS,btnPlayNextTS,btnChanceTahrimeSokhan;
    RecyclerView rvTahrimeSokhan;
    AppCompatTextView tvLastLessonTS,tv_refresh_tahrime_sokhan;
    public static List<ModelBookRv> listAllNamesTahrimeSokhan = new ArrayList<>();
    AdapterTahrimeSokhanRV adapterTahrimeSokhanRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tahrime_sokhan);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        Init();
        GetListNames();

        btnPlayLastTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name= "درسی گوش نداده اید .";
                if (Api.sharedPreferences.contains("nameTahrimeSokhan")) {
                    name = Api.sharedPreferences.getString("nameTahrimeSokhan", null);
                }else {
                    Toast.makeText(TahrimeSokhanActivity.this, name, Toast.LENGTH_SHORT).show();
                    return;
                }

                String finalName = name;
                Intent intent = new Intent(TahrimeSokhanActivity.this, PlayTahrimeSokhanActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", finalName);
                intent.putExtras(bundle);
                startActivity(intent, bundle);
            }
        });

        btnPlayNextTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name= "درسی گوش نداده اید .";
                if (Api.sharedPreferences.contains("nameTahrimeSokhan")) {
                    name = Api.sharedPreferences.getString("nameTahrimeSokhan", null);
                }else {
                    Toast.makeText(TahrimeSokhanActivity.this, name, Toast.LENGTH_SHORT).show();
                    return;
                }

                String nextName = next(name);
                Intent intent = new Intent(TahrimeSokhanActivity.this, PlayTahrimeSokhanActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", nextName);
                intent.putExtras(bundle);
                startActivity(intent, bundle);
            }
        });

        ibBookmarkedTahrimeSokhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TahrimeSokhanActivity.this, BookmarkedTahrimeSokhanActivity.class);
                startActivity(intent);
            }
        });

        btnChanceTahrimeSokhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = listAllNamesTahrimeSokhan.size();
                Random random = new Random();
                length = random.nextInt(length);
                Intent intent = new Intent(TahrimeSokhanActivity.this,PlayTahrimeSokhanActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",listAllNamesTahrimeSokhan.get(length).getName());
                intent.putExtras(bundle);
                startActivity(intent,bundle);
            }
        });

        tv_refresh_tahrime_sokhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetListNames();
            }
        });
    }

    @Override
    protected void onResume() {
        String name= "درسی گوش نداده اید .";
        if (Api.sharedPreferences.contains("nameTahrimeSokhan")) {
            name = Api.sharedPreferences.getString("nameTahrimeSokhan", null);
            tvLastLessonTS.setVisibility(View.VISIBLE);
            tvLastLessonTS.setText("آخرین صوتی که گوش کردید :" + "\n" + name);
        }else tvLastLessonTS.setVisibility(View.GONE);

        super.onResume();
    }

    private void GetListNames() {
        tv_refresh_tahrime_sokhan.setVisibility(View.GONE);
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(TahrimeSokhanActivity.this);
        progressDialog.setMessage("در حال بارگیری لیست");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.URL_GET_ALL_LIST_TAHRIME_SOKHAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
//Log.i("TAG", "onResponse GetListNames: " + response);
                Gson gson = new Gson();
                ModelBookRv[] model_bookRvs = gson.fromJson(response, ModelBookRv[].class);
                listAllNamesTahrimeSokhan.addAll(Arrays.asList(model_bookRvs));
                adapterTahrimeSokhanRV.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv_refresh_tahrime_sokhan.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(TahrimeSokhanActivity.this, "اتصال به سرور برقرار نشد .", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(TahrimeSokhanActivity.this);
        queue.add(stringRequest);
    }

    private String next(String currentName){
        int lengthName = TahrimeSokhanActivity.listAllNamesTahrimeSokhan.size();

        String nameEnd = TahrimeSokhanActivity.listAllNamesTahrimeSokhan.get(lengthName - 1).getName();
        if (currentName.equals(nameEnd)) {
            Toast.makeText(this, "تکرار شد .", Toast.LENGTH_LONG).show();
            return nameEnd;
        }

        for (int i = 0 ; i < lengthName ; i++) {
            if (TahrimeSokhanActivity.listAllNamesTahrimeSokhan.get(i).getName().equals(currentName)){
                return TahrimeSokhanActivity.listAllNamesTahrimeSokhan.get(i + 1).getName();
            }
        }
        return "";
    }

    private void Init() {
        ibBookmarkedTahrimeSokhan = findViewById(R.id.ibBookmarkedTahrimeSokhan);
        btnChanceTahrimeSokhan = findViewById(R.id.btnChanceTahrimeSokhan);
        btnPlayLastTS = findViewById(R.id.btnPlayLastTS);
        btnPlayNextTS = findViewById(R.id.btnPlayNextTS);
        rvTahrimeSokhan = findViewById(R.id.rvTahrimeSokhan);
        tvLastLessonTS = findViewById(R.id.tvLastLessonTS);
        tv_refresh_tahrime_sokhan = findViewById(R.id.tv_refresh_tahrime_sokhan);

        adapterTahrimeSokhanRV = new AdapterTahrimeSokhanRV(listAllNamesTahrimeSokhan, this);
        rvTahrimeSokhan.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager gLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        rvTahrimeSokhan.setLayoutManager(manager);
        rvTahrimeSokhan.setHasFixedSize(true);

        RecyclerView.ItemDecoration iDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvTahrimeSokhan.addItemDecoration(iDecoration);
        rvTahrimeSokhan.setHasFixedSize(true);
        rvTahrimeSokhan.setAdapter(adapterTahrimeSokhanRV);

    }
}