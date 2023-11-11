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
import ir.rezvandeveloper.bookonline.HalghehayeMafghoodeActivity;
import ir.rezvandeveloper.bookonline.MotefaregheActivity;
import ir.rezvandeveloper.bookonline.R;
import ir.rezvandeveloper.bookonline.adapter.AdapterBookmarkHalghehayeMafghoodeRV;
import ir.rezvandeveloper.bookonline.adapter.AdapterBookmarkMotefaregheRV;
import ir.rezvandeveloper.bookonline.model.ModelBookRv;

public class BookmarkedHalghehayeMafghoodeActivity extends AppCompatActivity {

    AppCompatTextView tvNoBookmarkHalghehayeMafghoode;
    RecyclerView rvBookmarkedHalghehayeMafghoode;
    List<ModelBookRv> modelBookRvs = new ArrayList<>();
    AdapterBookmarkHalghehayeMafghoodeRV bookmarkHalghehayeMafghoodeRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_halghehaye_mafghoode);

        cast();
        init();

        getDataOfBookmarked();
    }

    private void getDataOfBookmarked(){
        modelBookRvs.clear();
        rvBookmarkedHalghehayeMafghoode.setAdapter(bookmarkHalghehayeMafghoodeRV);

        int length = HalghehayeMafghoodeActivity.listAllNamesHalghehayeMafghoode.size();
        for (int i = 0 ; i < length ; i++) {
            if (Api.sharedPreferences.contains("HalghehayeMafghoode" + HalghehayeMafghoodeActivity.listAllNamesHalghehayeMafghoode.get(i).getName())) {
                modelBookRvs.add(new ModelBookRv(HalghehayeMafghoodeActivity.listAllNamesHalghehayeMafghoode.get(i).getId(),HalghehayeMafghoodeActivity.listAllNamesHalghehayeMafghoode.get(i).getName()));
            }
        }
        bookmarkHalghehayeMafghoodeRV.notifyDataSetChanged();

        if (modelBookRvs.size() == 0) {
            tvNoBookmarkHalghehayeMafghoode.setVisibility(View.VISIBLE);
        }else {
            tvNoBookmarkHalghehayeMafghoode.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        modelBookRvs.clear();
        rvBookmarkedHalghehayeMafghoode.setAdapter(bookmarkHalghehayeMafghoodeRV);
        getDataOfBookmarked();
        super.onResume();
    }

    private void init(){
        bookmarkHalghehayeMafghoodeRV = new AdapterBookmarkHalghehayeMafghoodeRV(modelBookRvs, this);
        rvBookmarkedHalghehayeMafghoode.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager gLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        rvBookmarkedHalghehayeMafghoode.setLayoutManager(manager);
        rvBookmarkedHalghehayeMafghoode.setHasFixedSize(true);

        RecyclerView.ItemDecoration iDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvBookmarkedHalghehayeMafghoode.addItemDecoration(iDecoration);
        rvBookmarkedHalghehayeMafghoode.setHasFixedSize(true);
        rvBookmarkedHalghehayeMafghoode.setAdapter(bookmarkHalghehayeMafghoodeRV);
    }

    private void cast(){
        tvNoBookmarkHalghehayeMafghoode = findViewById(R.id.tvNoBookmarkHalghehayeMafghoode);
        rvBookmarkedHalghehayeMafghoode = findViewById(R.id.rvBookmarkedHalghehayeMafghoode);
    }
}