package ir.rezvandeveloper.bookonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import ir.rezvandeveloper.bookonline.about.AboutAppActivity;
import ir.rezvandeveloper.bookonline.about.AboutBookActivity;
import ir.rezvandeveloper.bookonline.about.SourceActivity;

public class MainActivity extends AppCompatActivity {

    AppCompatButton btnTitrEslaheAlefbayeTohid,btnOtherEslaheAlefbayeTohid,btnBook,btnTahrimeSokhan,btnMotefareghe,btnMatneFarmayeshat,btnMusic,btnTalk,btnPodcast,btnHalghehayeMafghoode,btnTarkeMoadelateTekrari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cast();

        visibilityBtnTalkBtnTahrimeSokhan();

        btnTitrEslaheAlefbayeTohid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EslaheAlefbayeTohidActivity.class);
                startActivity(intent);
            }
        });

        btnOtherEslaheAlefbayeTohid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OtherEslaheAlefbayeTohidActivity.class);
                startActivity(intent);
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BookActivity.class);
                startActivity(intent);
            }
        });

        btnTahrimeSokhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TahrimeSokhanActivity.class);
                startActivity(intent);
            }
        });

        btnMotefareghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MotefaregheActivity.class);
                startActivity(intent);
            }
        });

        btnPodcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PodcastActivity.class);
                startActivity(intent);
            }
        });


        btnHalghehayeMafghoode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HalghehayeMafghoodeActivity.class);
                startActivity(intent);
            }
        });


        btnTarkeMoadelateTekrari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TarkeMoadelateTekrariActivity.class);
                startActivity(intent);
            }
        });

        btnMatneFarmayeshat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MatneFarmayshatActivity.class);
                startActivity(intent);
            }
        });

        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MusicActivity.class);
                startActivity(intent);
            }
        });

        btnTalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TalkActivity.class);
                startActivity(intent);
            }
        });
    }

    private void visibilityBtnTalkBtnTahrimeSokhan(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.URL_GET_SETTING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String sBtnTalk = jsonObject.getJSONArray("setting").getJSONObject(8).getString("b");
                    String sBtnTahrimeSokhan = jsonObject.getJSONArray("setting").getJSONObject(9).getString("b");

                    if (sBtnTalk.equals("visible")) {
                        btnTalk.setVisibility(View.VISIBLE);
                    }else {
                        btnTalk.setVisibility(View.INVISIBLE);
                    }

                    if (sBtnTahrimeSokhan.equals("visible")) {
                        btnTahrimeSokhan.setVisibility(View.VISIBLE);
                    }else {
                        btnTahrimeSokhan.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("درباره کتاب").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, AboutBookActivity.class);
                startActivity(intent);
                return false;
            }
        });
        menu.add("منابع").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, SourceActivity.class);
                startActivity(intent);
                return false;
            }
        });
        menu.add("درباره اپلیکیشن").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, AboutAppActivity.class);
                startActivity(intent);
                return false;
            }
        });
        menu.add("خروج").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                finish();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void cast() {
        btnTitrEslaheAlefbayeTohid = findViewById(R.id.btnTitrEslaheAlefbayeTohid);
        btnOtherEslaheAlefbayeTohid = findViewById(R.id.btnOtherEslaheAlefbayeTohid);
        btnBook = findViewById(R.id.btnBook);
        btnTahrimeSokhan = findViewById(R.id.btnTahrimeSokhan);
        btnMotefareghe = findViewById(R.id.btnMotefareghe);
        btnMatneFarmayeshat = findViewById(R.id.btnMatneFarmayeshat);
        btnMusic = findViewById(R.id.btnMusic);
        btnTalk = findViewById(R.id.btnTalk);
        btnPodcast = findViewById(R.id.btnPodcast);
        btnHalghehayeMafghoode = findViewById(R.id.btnHalghehayeMafghoode);
        btnTarkeMoadelateTekrari = findViewById(R.id.btnTarkeMoadelateTekrari);

        Api.sharedPreferences = getSharedPreferences("PREFERS", Context.MODE_PRIVATE);

    }
}