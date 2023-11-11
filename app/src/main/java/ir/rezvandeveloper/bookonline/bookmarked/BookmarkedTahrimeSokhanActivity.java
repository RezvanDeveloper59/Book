package ir.rezvandeveloper.bookonline.bookmarked;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ir.rezvandeveloper.bookonline.Api;
import ir.rezvandeveloper.bookonline.R;
import ir.rezvandeveloper.bookonline.TahrimeSokhanActivity;
import ir.rezvandeveloper.bookonline.adapter.AdapterBookmarkTahrimeSokhanRV;
import ir.rezvandeveloper.bookonline.model.ModelBookRv;

public class BookmarkedTahrimeSokhanActivity extends AppCompatActivity {

    AppCompatTextView tvNoBookmarkTahrimeSokhan;
    RecyclerView rvBookmarkedTahrimeSokhan;
    List<ModelBookRv> modelBookRvs = new ArrayList<>();
    AdapterBookmarkTahrimeSokhanRV adapterBookmarkTahrimeSokhanRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_tahrime_sokhan);

        cast();
        init();

        getDataOfBookmarked();
    }

    private void getDataOfBookmarked(){
        modelBookRvs.clear();
        rvBookmarkedTahrimeSokhan.setAdapter(adapterBookmarkTahrimeSokhanRV);

        int length = TahrimeSokhanActivity.listAllNamesTahrimeSokhan.size();
        for (int i = 0 ; i < length ; i++) {
            if (Api.sharedPreferences.contains("TS" + TahrimeSokhanActivity.listAllNamesTahrimeSokhan.get(i).getName())) {
                modelBookRvs.add(new ModelBookRv(TahrimeSokhanActivity.listAllNamesTahrimeSokhan.get(i).getId(),TahrimeSokhanActivity.listAllNamesTahrimeSokhan.get(i).getName()));
            }
        }
        adapterBookmarkTahrimeSokhanRV.notifyDataSetChanged();

        if (modelBookRvs.size() == 0) {
            tvNoBookmarkTahrimeSokhan.setVisibility(View.VISIBLE);
        }else {
            tvNoBookmarkTahrimeSokhan.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        modelBookRvs.clear();
        rvBookmarkedTahrimeSokhan.setAdapter(adapterBookmarkTahrimeSokhanRV);
        getDataOfBookmarked();
        super.onResume();
    }

    private void init(){
        adapterBookmarkTahrimeSokhanRV = new AdapterBookmarkTahrimeSokhanRV(modelBookRvs, this);
        rvBookmarkedTahrimeSokhan.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager gLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        rvBookmarkedTahrimeSokhan.setLayoutManager(manager);
        rvBookmarkedTahrimeSokhan.setHasFixedSize(true);

        RecyclerView.ItemDecoration iDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvBookmarkedTahrimeSokhan.addItemDecoration(iDecoration);
        rvBookmarkedTahrimeSokhan.setHasFixedSize(true);
        rvBookmarkedTahrimeSokhan.setAdapter(adapterBookmarkTahrimeSokhanRV);
    }

    private void cast(){
        tvNoBookmarkTahrimeSokhan = findViewById(R.id.tvNoBookmarkTahrimeSokhan);
        rvBookmarkedTahrimeSokhan = findViewById(R.id.rvBookmarkedTahrimeSokhan);
    }
}