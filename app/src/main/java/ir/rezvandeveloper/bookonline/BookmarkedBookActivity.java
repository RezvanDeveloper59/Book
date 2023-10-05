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

public class BookmarkedBookActivity extends AppCompatActivity {

    AppCompatTextView tvNoBookmarkBook;
    RecyclerView rvBookmarkedBook;
    List<ModelBookRv> modelBookRvs = new ArrayList<>();
    AdapterBookmarkBookRV adapterBookmarkBookRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_book);

        cast();
        init();

        getDataOfBookmarked();
    }

    private void getDataOfBookmarked(){
        modelBookRvs.clear();
        rvBookmarkedBook.setAdapter(adapterBookmarkBookRV);

        int length = BookActivity.listAllNames.size();
        for (int i = 0 ; i < length ; i++) {
            if (Api.sharedPreferences.contains("AB" + BookActivity.listAllNames.get(i).getName())) {
                modelBookRvs.add(new ModelBookRv(BookActivity.listAllNames.get(i).getId(),BookActivity.listAllNames.get(i).getName()));
            }
        }
        adapterBookmarkBookRV.notifyDataSetChanged();

        if (modelBookRvs.size() == 0) {
            tvNoBookmarkBook.setVisibility(View.VISIBLE);
        }else {
            tvNoBookmarkBook.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        modelBookRvs.clear();
        rvBookmarkedBook.setAdapter(adapterBookmarkBookRV);
        getDataOfBookmarked();
        super.onResume();
    }

    private void init(){
        adapterBookmarkBookRV = new AdapterBookmarkBookRV(modelBookRvs, this);
        rvBookmarkedBook.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager gLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        rvBookmarkedBook.setLayoutManager(manager);
        rvBookmarkedBook.setHasFixedSize(true);

        RecyclerView.ItemDecoration iDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvBookmarkedBook.addItemDecoration(iDecoration);
        rvBookmarkedBook.setHasFixedSize(true);
        rvBookmarkedBook.setAdapter(adapterBookmarkBookRV);
    }

    private void cast(){
        tvNoBookmarkBook = findViewById(R.id.tvNoBookmarkBook);
        rvBookmarkedBook = findViewById(R.id.rvBookmarked);
    }
}