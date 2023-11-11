package ir.rezvandeveloper.bookonline.bookmarked;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.rezvandeveloper.bookonline.Api;
import ir.rezvandeveloper.bookonline.R;
import ir.rezvandeveloper.bookonline.TarkeMoadelateTekrariActivity;
import ir.rezvandeveloper.bookonline.adapter.AdapterBookmarkMotefaregheRV;
import ir.rezvandeveloper.bookonline.adapter.AdapterBookmarkTarkeMoadelatetekrariRV;
import ir.rezvandeveloper.bookonline.model.ModelBookRv;

public class BookmarkedTarkeMoadelateTekrariActivity extends AppCompatActivity {

    AppCompatTextView tvNoBookmarkTarkeMoadelateTekrari;
    RecyclerView rvBookmarkedTarkeMoadelateTekrari;
    List<ModelBookRv> modelBookRvs = new ArrayList<>();
    AdapterBookmarkTarkeMoadelatetekrariRV adapterBookmarkTarkeMoadelatetekrariRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_tarke_moadelate_tekrari);

        cast();
        init();

        getDataOfBookmarked();
    }

    private void getDataOfBookmarked(){
        modelBookRvs.clear();
        rvBookmarkedTarkeMoadelateTekrari.setAdapter(adapterBookmarkTarkeMoadelatetekrariRV);

        int length = TarkeMoadelateTekrariActivity.listAllNamesTarkeMoadelateTekrari.size();
        for (int i = 0 ; i < length ; i++) {
            if (Api.sharedPreferences.contains("TarkeMoadelateTekrari" + TarkeMoadelateTekrariActivity.listAllNamesTarkeMoadelateTekrari.get(i).getName())) {
                modelBookRvs.add(new ModelBookRv(TarkeMoadelateTekrariActivity.listAllNamesTarkeMoadelateTekrari.get(i).getId(),TarkeMoadelateTekrariActivity.listAllNamesTarkeMoadelateTekrari.get(i).getName()));
            }
        }
        adapterBookmarkTarkeMoadelatetekrariRV.notifyDataSetChanged();

        if (modelBookRvs.size() == 0) {
            tvNoBookmarkTarkeMoadelateTekrari.setVisibility(View.VISIBLE);
        }else {
            tvNoBookmarkTarkeMoadelateTekrari.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        modelBookRvs.clear();
        rvBookmarkedTarkeMoadelateTekrari.setAdapter(adapterBookmarkTarkeMoadelatetekrariRV);
        getDataOfBookmarked();
        super.onResume();
    }

    private void init(){
        adapterBookmarkTarkeMoadelatetekrariRV = new AdapterBookmarkTarkeMoadelatetekrariRV(modelBookRvs, this);
        rvBookmarkedTarkeMoadelateTekrari.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager gLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        rvBookmarkedTarkeMoadelateTekrari.setLayoutManager(manager);
        rvBookmarkedTarkeMoadelateTekrari.setHasFixedSize(true);

        RecyclerView.ItemDecoration iDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvBookmarkedTarkeMoadelateTekrari.addItemDecoration(iDecoration);
        rvBookmarkedTarkeMoadelateTekrari.setHasFixedSize(true);
        rvBookmarkedTarkeMoadelateTekrari.setAdapter(adapterBookmarkTarkeMoadelatetekrariRV);
    }

    private void cast(){
        tvNoBookmarkTarkeMoadelateTekrari = findViewById(R.id.tvNoBookmarkTarkeMoadelateTekrari);
        rvBookmarkedTarkeMoadelateTekrari = findViewById(R.id.rvBookmarkedTarkeMoadelateTekrari);
    }
}