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
import ir.rezvandeveloper.bookonline.PodcastActivity;
import ir.rezvandeveloper.bookonline.R;
import ir.rezvandeveloper.bookonline.adapter.AdapterBookmarkPodcastRV;
import ir.rezvandeveloper.bookonline.model.ModelBookRv;

public class BookmarkedPodcastActivity extends AppCompatActivity {

    AppCompatTextView tvNoBookmarkedPodcast;
    RecyclerView rvBookmarkedPodcast;
    List<ModelBookRv> modelBookRvs = new ArrayList<>();
    AdapterBookmarkPodcastRV adapterBookmarkPodcastRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_podcast);

        cast();
        init();

        getDataOfBookmarked();
    }

    private void getDataOfBookmarked(){
        modelBookRvs.clear();
        rvBookmarkedPodcast.setAdapter(adapterBookmarkPodcastRV);

        int length = PodcastActivity.listAllNamesPodcast.size();
        for (int i = 0 ; i < length ; i++) {
            if (Api.sharedPreferences.contains("Podcast" + PodcastActivity.listAllNamesPodcast.get(i).getName())) {
                modelBookRvs.add(new ModelBookRv(PodcastActivity.listAllNamesPodcast.get(i).getId(),PodcastActivity.listAllNamesPodcast.get(i).getName()));
            }
        }
        adapterBookmarkPodcastRV.notifyDataSetChanged();

        if (modelBookRvs.size() == 0) {
            tvNoBookmarkedPodcast.setVisibility(View.VISIBLE);
        }else {
            tvNoBookmarkedPodcast.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        modelBookRvs.clear();
        rvBookmarkedPodcast.setAdapter(adapterBookmarkPodcastRV);
        getDataOfBookmarked();
        super.onResume();
    }

    private void init(){
        adapterBookmarkPodcastRV = new AdapterBookmarkPodcastRV(modelBookRvs, this);
        rvBookmarkedPodcast.setLayoutManager(new LinearLayoutManager(this));
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //RecyclerView.LayoutManager gLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        rvBookmarkedPodcast.setLayoutManager(manager);
        rvBookmarkedPodcast.setHasFixedSize(true);

        RecyclerView.ItemDecoration iDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvBookmarkedPodcast.addItemDecoration(iDecoration);
        rvBookmarkedPodcast.setHasFixedSize(true);
        rvBookmarkedPodcast.setAdapter(adapterBookmarkPodcastRV);
    }

    private void cast(){
        tvNoBookmarkedPodcast = findViewById(R.id.tvNoBookmarkPodcast);
        rvBookmarkedPodcast = findViewById(R.id.rvBookmarkedPodcast);
    }
}