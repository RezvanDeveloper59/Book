package ir.rezvandeveloper.bookonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class BookmarkedMotefaregheActivity extends AppCompatActivity {

    AppCompatTextView tvNoBookmarkMotefareghe;
    RecyclerView rvBookmarkedMotefareghe;
    List<ModelBookRv> modelBookRvs = new ArrayList<>();
    AdapterBookmarkMotefaregheRV adapterBookmarkMotefaregheRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_motefareghe);

        cast();
        init();

        getDataOfBookmarked();
    }

    private void getDataOfBookmarked(){
        modelBookRvs.clear();
        rvBookmarkedMotefareghe.setAdapter(adapterBookmarkMotefaregheRV);

        int length = MotefaregheActivity.listAllNamesMotefareghe.size();
        for (int i = 0 ; i < length ; i++) {
            if (Api.sharedPreferences.contains("Mo" + MotefaregheActivity.listAllNamesMotefareghe.get(i).getName())) {
                modelBookRvs.add(new ModelBookRv(MotefaregheActivity.listAllNamesMotefareghe.get(i).getId(),MotefaregheActivity.listAllNamesMotefareghe.get(i).getName()));
            }
        }
        adapterBookmarkMotefaregheRV.notifyDataSetChanged();

        if (modelBookRvs.size() == 0) {
            tvNoBookmarkMotefareghe.setVisibility(View.VISIBLE);
        }else {
            tvNoBookmarkMotefareghe.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        modelBookRvs.clear();
        rvBookmarkedMotefareghe.setAdapter(adapterBookmarkMotefaregheRV);
        getDataOfBookmarked();
        super.onResume();
    }

    private void init(){
        adapterBookmarkMotefaregheRV = new AdapterBookmarkMotefaregheRV(modelBookRvs, this);
        rvBookmarkedMotefareghe.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager gLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        rvBookmarkedMotefareghe.setLayoutManager(manager);
        rvBookmarkedMotefareghe.setHasFixedSize(true);

        RecyclerView.ItemDecoration iDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvBookmarkedMotefareghe.addItemDecoration(iDecoration);
        rvBookmarkedMotefareghe.setHasFixedSize(true);
        rvBookmarkedMotefareghe.setAdapter(adapterBookmarkMotefaregheRV);
    }

    private void cast(){
        tvNoBookmarkMotefareghe = findViewById(R.id.tvNoBookmarkMotefareghe);
        rvBookmarkedMotefareghe = findViewById(R.id.rvBookmarkedMotefareghe);
    }
}