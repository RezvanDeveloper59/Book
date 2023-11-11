package ir.rezvandeveloper.bookonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.rezvandeveloper.bookonline.DB.Database;
import ir.rezvandeveloper.bookonline.DB.ModelDB;
import ir.rezvandeveloper.bookonline.adapter.AdapterEslaheAlefbayeTohidLV;
import ir.rezvandeveloper.bookonline.player.PlayEslaheAlefbayeTohidActivity;

public class EslaheAlefbayeTohidActivity extends AppCompatActivity {

    AppCompatTextView tvLastLessonEAT;
    AppCompatButton btnPlayNextEAT,btnPlayLastEAT;
    ListView lvTitr;
    ArrayList<ItemTitrLV> itemTitrLVS;
    List<ModelDB> modelDBS = new ArrayList<>();
    Database database;
    AdapterEslaheAlefbayeTohidLV adapterEslaheAlefbayeTohidLV;
    String CURRENT_NAME = null,CURRENT_LESSON= "درسی گوش نداده اید .",CURRENT_LINK = null,CURREBT_ID = null,titr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eslahe_alefbaye_tohid);

        cast();

        itemTitrLVS = new ArrayList<>();

        adapterEslaheAlefbayeTohidLV = new AdapterEslaheAlefbayeTohidLV(this, itemTitrLVS);

        setItems();

        lvTitr.setAdapter(adapterEslaheAlefbayeTohidLV);

        btnPlayLastEAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = null,lesson= "درسی گوش نداده اید .",link = null,id = null,titr = null;
                if (Api.sharedPreferences.contains("currentLesson_eslahe_alefbaye_tohid")) {
                    name = Api.sharedPreferences.getString("name_eslahe_alefbaye_tohid", null);
                    lesson = Api.sharedPreferences.getString("currentLesson_eslahe_alefbaye_tohid", null);
                    link = Api.sharedPreferences.getString("currentLink_eslahe_alefbaye_tohid", null);
                    id = Api.sharedPreferences.getString("currentId_eslahe_alefbaye_tohid", null);
                    titr = Api.sharedPreferences.getString("titrs_eslahe_alefbaye_tohid", null);
                }else {
                    Toast.makeText(EslaheAlefbayeTohidActivity.this, "درسی گوش نداده اید .", Toast.LENGTH_LONG).show();
                    return;
                }

                String finalName = name;
                String finalId = id;
                String finalLink = link;
                String finalLesson = lesson;
                String finalTitr = titr;

                Intent intent = new Intent(EslaheAlefbayeTohidActivity.this, PlayEslaheAlefbayeTohidActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", finalName);
                bundle.putString("currentId", finalId);
                bundle.putString("currentLink", finalLink);
                bundle.putString("currentLesson", finalLesson);
                bundle.putString("titrs", finalTitr);
                intent.putExtras(bundle);
                startActivity(intent, bundle);
            }
        });

        btnPlayNextEAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String m = getNextLinkOfDB();
                if (m.equals("no")) {
                    Toast.makeText(EslaheAlefbayeTohidActivity.this, "درسی گوش نداده اید .", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(EslaheAlefbayeTohidActivity.this, PlayEslaheAlefbayeTohidActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", CURRENT_NAME);
                bundle.putString("currentId", CURREBT_ID);
                bundle.putString("currentLink", CURRENT_LINK);
                bundle.putString("currentLesson", CURRENT_LESSON);
                bundle.putString("titrs", titr);
                intent.putExtras(bundle);
                startActivity(intent, bundle);
            }
        });
    }

    @Override
    protected void onResume() {
        String name = null,lesson= "درسی گوش نداده اید .",link = null,id = null,titr = null;
        if (Api.sharedPreferences.contains("currentLesson_eslahe_alefbaye_tohid")) {
            name = Api.sharedPreferences.getString("name_eslahe_alefbaye_tohid", null);
            lesson = Api.sharedPreferences.getString("currentLesson_eslahe_alefbaye_tohid", null);
            tvLastLessonEAT.setVisibility(View.VISIBLE);
            tvLastLessonEAT.setText("آخرین درسی که گوش کردید :" + "\n" + lesson);
        }else tvLastLessonEAT.setVisibility(View.GONE);

        super.onResume();
    }

    public void setItems() {
        database = new Database(EslaheAlefbayeTohidActivity.this);
        modelDBS = database.getAllPrescription();

        int i,l = modelDBS.size();

        for(i=0 ; i<l ; i++) {
            itemTitrLVS.add(new ItemTitrLV(i,modelDBS.get(i).getTitr(), Api.ConvertNumberEnToFa(modelDBS.get(i).getLesson()), "نسخه " + Api.ConvertNumberEnToFa(modelDBS.get(i).getNumber()),modelDBS.get(i).getLink()));
        }
    }

    private String getNextLinkOfDB(){

        if (Api.sharedPreferences.contains("currentLesson_eslahe_alefbaye_tohid")) {
            CURRENT_NAME = Api.sharedPreferences.getString("name_eslahe_alefbaye_tohid", null);
            CURRENT_LESSON = Api.sharedPreferences.getString("currentLesson_eslahe_alefbaye_tohid", null);
            CURRENT_LINK = Api.sharedPreferences.getString("currentLink_eslahe_alefbaye_tohid", null);
            CURREBT_ID = Api.sharedPreferences.getString("currentId_eslahe_alefbaye_tohid", null);
            titr = Api.sharedPreferences.getString("titrs_eslahe_alefbaye_tohid", null);
        }else return "no";

        Database database = new Database(EslaheAlefbayeTohidActivity.this);
        modelDBS = database.getAllPrescription();
        if (CURRENT_LESSON.equals("درس ۴۰۸")) {
            Toast.makeText(this, "تکرار درس آخر", Toast.LENGTH_LONG).show();
            return "صوت 963_درس ۵۹_ص ۴۰۸";
        }

        String currentLink = CURRENT_LINK;
        titr = "";

        for (int i = 0; i < modelDBS.size() ; i++){
            if (modelDBS.get(i).getLink().equals(currentLink)) {
                for (int j = i ; j < modelDBS.size() ; j++) {
                    if (! modelDBS.get(j).getLink().equals(currentLink)) {
                        CURRENT_LINK = modelDBS.get(j).getLink();
                        CURRENT_LESSON = modelDBS.get(j).getLesson();

                        String lesson = modelDBS.get(j).getLesson();
                        int l = j;
                        for (int k = 0; k < modelDBS.size(); k++){
                            if (modelDBS.get(l - 1).getLesson().equals(lesson)){
                                titr = titr + "\n" + modelDBS.get(l).getTitr();
                                l++;
                            }
                        }
                        return CURRENT_LINK;
                    }
                }
            }
        }
        return "";
    }

    private void cast() {
        tvLastLessonEAT = findViewById(R.id.tvLastLessonEAT);
        btnPlayNextEAT = findViewById(R.id.btnPlayNextEAT);
        btnPlayLastEAT = findViewById(R.id.btnPlayLastEAT);
        lvTitr = findViewById(R.id.lvTitr);
    }
}