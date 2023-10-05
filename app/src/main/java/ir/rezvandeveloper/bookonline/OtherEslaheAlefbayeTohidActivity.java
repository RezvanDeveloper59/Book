package ir.rezvandeveloper.bookonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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
import java.util.Random;

public class OtherEslaheAlefbayeTohidActivity extends AppCompatActivity {
    ImageButton ibBookmarkedEAT;
    AppCompatButton btnPlayLastEAT, btnPlayNextEAT, btnChanceEAT;
    AppCompatTextView tvLastLessonEAT, tv_refresh_EAT;
    RecyclerView rvOtherEslaheAlefbayeTohid;
    public static List<ModelBookRv> modelBookRvs = new ArrayList<>();
    AdapterOtherEslaheAlefbayeTohidRV adapterOtherEslaheAlefbayeTohidRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_eslahe_alefbaye_tohid);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        Init();
        GetListNames();

        btnPlayLastEAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name= "درسی گوش نداده اید .";
                if (Api.sharedPreferences.contains("nameOtherEslaheAlefbayeTohid")) {
                    name = Api.sharedPreferences.getString("nameOtherEslaheAlefbayeTohid", null);
                }else {
                    Toast.makeText(OtherEslaheAlefbayeTohidActivity.this, name, Toast.LENGTH_SHORT).show();
                    return;
                }

                String finalName = name;
                Intent intent = new Intent(OtherEslaheAlefbayeTohidActivity.this, PlayOtherEslaheAlefbayeTohidActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", finalName);
                intent.putExtras(bundle);
                startActivity(intent, bundle);
            }
        });

        btnPlayNextEAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name= "درسی گوش نداده اید .";
                if (Api.sharedPreferences.contains("nameOtherEslaheAlefbayeTohid")) {
                    name = Api.sharedPreferences.getString("nameOtherEslaheAlefbayeTohid", null);
                }else {
                    Toast.makeText(OtherEslaheAlefbayeTohidActivity.this, name, Toast.LENGTH_SHORT).show();
                    return;
                }

                String nextName = next(name);
                Intent intent = new Intent(OtherEslaheAlefbayeTohidActivity.this, PlayOtherEslaheAlefbayeTohidActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", nextName);
                intent.putExtras(bundle);
                startActivity(intent, bundle);
            }
        });

        ibBookmarkedEAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherEslaheAlefbayeTohidActivity.this, BookmarkedOtherEslaheAlefbayeTohidActivity.class);
                startActivity(intent);
            }
        });

        btnChanceEAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = modelBookRvs.size();
                Random random = new Random();
                length = random.nextInt(length);
                Intent intent = new Intent(OtherEslaheAlefbayeTohidActivity.this,PlayOtherEslaheAlefbayeTohidActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",modelBookRvs.get(length).getName());
                intent.putExtras(bundle);
                startActivity(intent,bundle);
            }
        });

        tv_refresh_EAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetListNames();
            }
        });
    }

    @Override
    protected void onResume() {
        String name= "درسی گوش نداده اید .";
        if (Api.sharedPreferences.contains("nameOtherEslaheAlefbayeTohid")) {
            name = Api.sharedPreferences.getString("nameOtherEslaheAlefbayeTohid", null);
            tvLastLessonEAT.setVisibility(View.VISIBLE);
            tvLastLessonEAT.setText("آخرین صوتی که گوش کردید :" + "\n" + name);
        }else tvLastLessonEAT.setVisibility(View.GONE);

        super.onResume();
    }

    private void GetListNames() {
        tv_refresh_EAT.setVisibility(View.GONE);
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(OtherEslaheAlefbayeTohidActivity.this);
        progressDialog.setMessage("در حال بارگیری لیست");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.URL_GET_OTHER_ESLAHE_ALEFBAYE_TOHID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
//Log.i("TAG", "onResponse GetListNames: " + response);
                Gson gson = new Gson();
                ModelBookRv[] model_bookRvs = gson.fromJson(response, ModelBookRv[].class);
                modelBookRvs.addAll(Arrays.asList(model_bookRvs));
                adapterOtherEslaheAlefbayeTohidRV.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv_refresh_EAT.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(OtherEslaheAlefbayeTohidActivity.this, "اتصال به سرور برقرار نشد .", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(OtherEslaheAlefbayeTohidActivity.this);
        queue.add(stringRequest);
    }

    private String next(String currentName){
        int lengthName = OtherEslaheAlefbayeTohidActivity.modelBookRvs.size();

        String nameEnd = OtherEslaheAlefbayeTohidActivity.modelBookRvs.get(lengthName - 1).getName();
        if (currentName.equals(nameEnd)) {
            Toast.makeText(this, "تکرار شد .", Toast.LENGTH_LONG).show();
            return nameEnd;
        }

        for (int i = 0 ; i < lengthName ; i++) {
            if (OtherEslaheAlefbayeTohidActivity.modelBookRvs.get(i).getName().equals(currentName)){
                return OtherEslaheAlefbayeTohidActivity.modelBookRvs.get(i + 1).getName();
            }
        }
        return "";
    }

    private void Init() {

        ibBookmarkedEAT = findViewById(R.id.ibBookmarkedOther);
        btnChanceEAT = findViewById(R.id.btnChanceOther);
        btnPlayLastEAT = findViewById(R.id.btnPlayLastOther);
        btnPlayNextEAT = findViewById(R.id.btnPlayNextOther);
        rvOtherEslaheAlefbayeTohid = findViewById(R.id.rvOther);
        tvLastLessonEAT = findViewById(R.id.tvLastLessonOther);
        tv_refresh_EAT = findViewById(R.id.tv_refresh_Other);

        adapterOtherEslaheAlefbayeTohidRV = new AdapterOtherEslaheAlefbayeTohidRV(modelBookRvs, this);
        rvOtherEslaheAlefbayeTohid.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager gLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        rvOtherEslaheAlefbayeTohid.setLayoutManager(manager);
        rvOtherEslaheAlefbayeTohid.setHasFixedSize(true);

        RecyclerView.ItemDecoration iDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvOtherEslaheAlefbayeTohid.addItemDecoration(iDecoration);
        rvOtherEslaheAlefbayeTohid.setHasFixedSize(true);
        rvOtherEslaheAlefbayeTohid.setAdapter(adapterOtherEslaheAlefbayeTohidRV);

    }
}