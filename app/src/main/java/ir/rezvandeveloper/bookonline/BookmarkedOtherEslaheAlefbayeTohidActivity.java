package ir.rezvandeveloper.bookonline;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BookmarkedOtherEslaheAlefbayeTohidActivity extends AppCompatActivity {

    AppCompatTextView tvNoBookmarkOtherEAT;
    RecyclerView rvBookmarkedOtherEAT;
    List<ModelBookRv> modelBookRvs = new ArrayList<>();
    AdapterBookmarkOtherEATRV adapterBookmarkOtherEATRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_other_eslahe_alefbaye_tohid);

        cast();
        init();

        getDataOfBookmarked();
    }

    private void getDataOfBookmarked(){
        modelBookRvs.clear();
        rvBookmarkedOtherEAT.setAdapter(adapterBookmarkOtherEATRV);

        int length = OtherEslaheAlefbayeTohidActivity.modelBookRvs.size();
        for (int i = 0 ; i < length ; i++) {
            if (Api.sharedPreferences.contains("EAT" + OtherEslaheAlefbayeTohidActivity.modelBookRvs.get(i).getName())) {
                modelBookRvs.add(new ModelBookRv(OtherEslaheAlefbayeTohidActivity.modelBookRvs.get(i).getId(),OtherEslaheAlefbayeTohidActivity.modelBookRvs.get(i).getName()));
            }
        }
        adapterBookmarkOtherEATRV.notifyDataSetChanged();

        if (modelBookRvs.size() == 0) {
            tvNoBookmarkOtherEAT.setVisibility(View.VISIBLE);
        }else {
            tvNoBookmarkOtherEAT.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        modelBookRvs.clear();
        rvBookmarkedOtherEAT.setAdapter(adapterBookmarkOtherEATRV);
        getDataOfBookmarked();
        super.onResume();
    }

    private void init(){
        adapterBookmarkOtherEATRV = new AdapterBookmarkOtherEATRV(modelBookRvs, this);
        rvBookmarkedOtherEAT.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager gLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        rvBookmarkedOtherEAT.setLayoutManager(manager);
        rvBookmarkedOtherEAT.setHasFixedSize(true);

        RecyclerView.ItemDecoration iDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvBookmarkedOtherEAT.addItemDecoration(iDecoration);
        rvBookmarkedOtherEAT.setHasFixedSize(true);
        rvBookmarkedOtherEAT.setAdapter(adapterBookmarkOtherEATRV);
    }

    private void cast(){
        tvNoBookmarkOtherEAT = findViewById(R.id.tvNoBookmarkOtherEAT);
        rvBookmarkedOtherEAT = findViewById(R.id.rvBookmarkedOtherEAT);
    }
}