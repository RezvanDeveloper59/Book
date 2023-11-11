package ir.rezvandeveloper.bookonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ir.rezvandeveloper.bookonline.about.SourceActivity;
import ir.rezvandeveloper.bookonline.adapter.AdapterBookRV;
import ir.rezvandeveloper.bookonline.bookmarked.BookmarkedBookActivity;
import ir.rezvandeveloper.bookonline.model.ModelBookRv;
import ir.rezvandeveloper.bookonline.player.PlayBookActivity;

public class BookActivity extends AppCompatActivity {

    AppCompatTextView tvLastLessonBook;
    AppCompatButton btn_source,btnPlayLastBook,btnPlayNextBook,btnTavasol,btnChanceBook;
    ImageButton ibBookmarkedBook;
    SearchView sv_main;
    RecyclerView recyclerView;
    AppCompatTextView tv_refresh_main;
    RequestQueue requestQueue;
    public static List<ModelBookRv> listAllNames = new ArrayList<>();
    AdapterBookRV adapterBookRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        Init();
        GetListNames();

        btn_source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookActivity.this, SourceActivity.class);
                startActivity(intent);
            }
        });

        btnPlayLastBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name= "درسی گوش نداده اید .";
                if (Api.sharedPreferences.contains("name_book")) {
                    name = Api.sharedPreferences.getString("name_book", null);
                }else {
                    Toast.makeText(BookActivity.this, name, Toast.LENGTH_SHORT).show();
                    return;
                }
                String finalName = name;

                Intent intent = new Intent(BookActivity.this, PlayBookActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", finalName);
                intent.putExtras(bundle);
                startActivity(intent, bundle);
            }
        });

        btnPlayNextBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name= "درسی گوش نداده اید .";
                if (Api.sharedPreferences.contains("name_book")) {
                    name = Api.sharedPreferences.getString("name_book", null);
                }else {
                    Toast.makeText(BookActivity.this, name, Toast.LENGTH_SHORT).show();
                    return;
                }

                String nextName = next(name);
                Intent intent = new Intent(BookActivity.this, PlayBookActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", nextName);
                intent.putExtras(bundle);
                startActivity(intent, bundle);
            }
        });

        ibBookmarkedBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookActivity.this, BookmarkedBookActivity.class);
                startActivity(intent);
            }
        });

        btnTavasol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAllNames.clear();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_QUERY_TADRIS_VA_TAVASOL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            SetDataRV(jsonObject);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue queue = Volley.newRequestQueue(BookActivity.this);
                queue.add(stringRequest);
            }
        });

        btnChanceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = listAllNames.size();
                Random random = new Random();
                length = random.nextInt(length);
                Intent intent = new Intent(BookActivity.this,PlayBookActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",listAllNames.get(length).getName());
                intent.putExtras(bundle);
                startActivity(intent,bundle);
            }
        });

        sv_main.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listAllNames.clear();
                Search(BookActivity.this, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        tv_refresh_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetListNames();
            }
        });
    }

    @Override
    protected void onResume() {
        String name= "درسی گوش نداده اید .";
        if (Api.sharedPreferences.contains("name_book")) {
            name = Api.sharedPreferences.getString("name_book", null);
            tvLastLessonBook.setVisibility(View.VISIBLE);
            tvLastLessonBook.setText("آخرین درسی که گوش کردید :" + "\n" + name);
        }else tvLastLessonBook.setVisibility(View.GONE);

        super.onResume();
    }

    private void GetListNames() {
        listAllNames.clear();
        tv_refresh_main.setVisibility(View.GONE);
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(BookActivity.this);
        progressDialog.setMessage("در حال بارگیری لیست دروس");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.URL_GET_ALL_LIST_BOOK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
//Log.i("TAG", "onResponse: " + response);
                Gson gson = new Gson();
                ModelBookRv[] model_bookRvs = gson.fromJson(response, ModelBookRv[].class);
                listAllNames.addAll(Arrays.asList(model_bookRvs));
                adapterBookRV.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv_refresh_main.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(BookActivity.this, "اتصال به سرور برقرار نشد .", Toast.LENGTH_LONG).show();
            }
    });
        requestQueue.add(stringRequest);
    }

    public void Search(Context context, String query){
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("لطفاً صبر کنید .");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_QUERY,  response -> {
//            Log.i(TAG, "onResponse Search : " + response);
            progressDialog.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(response);
//Log.i(TAG, "Search jsonObject: " + jsonObject);
                SetDataRV(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
//Log.i(TAG, "onResponse e: " + e);
                Toast.makeText(context, "خطایی رخ داد .", Toast.LENGTH_LONG).show();
            }
        }, error -> {
//Log.i(TAG, "onErrorResponse: " + error);
            progressDialog.dismiss();
            Toast.makeText(context, "خطایی رخ داد .", Toast.LENGTH_SHORT).show();
        }) {
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("query", query);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    private void SetDataRV(JSONObject jsonObject) {

        int length = 0;
        try {
            length = jsonObject.getJSONArray("result").length();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
//Log.i(TAG, "SetDataRV length: " + length);
        for(int i = 0; i < length; i++){
            int id = 0;
            try {
                id = Integer.parseInt(jsonObject.getJSONArray("result").getJSONObject(i).getString("id"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            //fullName = jsonObject.getJSONArray("result").getJSONObject(i).getString("full_name");
            String name = null;
            try {
                name = jsonObject.getJSONArray("result").getJSONObject(i).getString("name");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
//Log.i(TAG, "SetDataRV result: " + jsonObject.getJSONArray("result").getJSONObject(i));

            listAllNames.add(new ModelBookRv(id, name));
        }
        recyclerView.setAdapter(adapterBookRV);
        adapterBookRV.notifyDataSetChanged();
//Log.i(TAG, "SetDataRV users: " + users);
    }

    private String next(String currentName){
        int lengthName = BookActivity.listAllNames.size();

        String nameEnd = BookActivity.listAllNames.get(lengthName - 1).getName();
        if (currentName.equals(nameEnd)) {
            Toast.makeText(this, "تکرار شد .", Toast.LENGTH_LONG).show();
            return nameEnd;
        }

        for (int i = 0 ; i < lengthName ; i++) {
            if (BookActivity.listAllNames.get(i).getName().equals(currentName)){
                return BookActivity.listAllNames.get(i + 1).getName();
            }
        }
        return "";
    }

    private void Init() {
        tvLastLessonBook = findViewById(R.id.tvLastLessonBook);
        btn_source = findViewById(R.id.btn_source);
        btnPlayLastBook = findViewById(R.id.btnPlayLastBook);
        btnPlayNextBook = findViewById(R.id.btnPlayNextBook);
        btnTavasol = findViewById(R.id.btnTavasol);
        btnChanceBook = findViewById(R.id.btnChanceBook);
        ibBookmarkedBook = findViewById(R.id.ibBookmarkedBook);
        sv_main = findViewById(R.id.sv_main);
        recyclerView = findViewById(R.id.rv_names);
        tv_refresh_main = findViewById(R.id.tv_refresh_main);
        requestQueue = Volley.newRequestQueue(this);

        adapterBookRV = new AdapterBookRV(listAllNames, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager gLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration iDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(iDecoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterBookRV);

    }


}