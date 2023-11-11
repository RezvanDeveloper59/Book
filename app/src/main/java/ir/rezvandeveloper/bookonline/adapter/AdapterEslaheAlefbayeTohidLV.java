package ir.rezvandeveloper.bookonline.adapter;

import android.content.Context;
//import android.support.v4.content.res.ResourcesCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.List;

import ir.rezvandeveloper.bookonline.DB.Database;
import ir.rezvandeveloper.bookonline.ItemTitrLV;
import ir.rezvandeveloper.bookonline.DB.ModelDB;
import ir.rezvandeveloper.bookonline.player.PlayEslaheAlefbayeTohidActivity;
import ir.rezvandeveloper.bookonline.R;

public class AdapterEslaheAlefbayeTohidLV extends BaseAdapter {

    private Context context;
    private ArrayList<ItemTitrLV> itemTitrLVS;
    List<ModelDB> modelDBS = new ArrayList<>();
    List<ModelDB> modelDBSForLink = new ArrayList<>();

    public AdapterEslaheAlefbayeTohidLV(Context context, ArrayList<ItemTitrLV> itemTitrLVS) {
        this.context = context;
        this.itemTitrLVS = itemTitrLVS;
    }

    @Override
    public int getCount() {
        return itemTitrLVS.size();
    }

    @Override
    public Object getItem(int i) {
        return itemTitrLVS.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {

        LinearLayout llTitr;
        AppCompatTextView tvTitr, tvLesson,tvNumber;

        public ViewHolder(View view) {
            llTitr = view.findViewById(R.id.llTitr);
            tvTitr = view.findViewById(R.id.tvTitr);
            tvLesson = view.findViewById(R.id.tvLesson);
            tvNumber = view.findViewById(R.id.tvNumber);
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_titr, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        // i = position
        ItemTitrLV currentItemTitrLV = (ItemTitrLV) getItem(i);

        viewHolder.tvTitr.setText(currentItemTitrLV.getName());
        viewHolder.tvLesson.setText(currentItemTitrLV.getLesson());
        viewHolder.tvNumber.setText(currentItemTitrLV.getNumber());

        viewHolder.llTitr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayEslaheAlefbayeTohidActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", currentItemTitrLV.getName());
                bundle.putString("currentId", String.valueOf(currentItemTitrLV.getId()));
                bundle.putString("currentLink", currentItemTitrLV.getLink());
                bundle.putString("currentLesson", currentItemTitrLV.getLesson());
                String titrs = getTitrs(currentItemTitrLV.getLesson());
                bundle.putString("titrs", titrs);
                intent.putExtras(bundle);
                context.startActivity(intent, bundle);
            }
        });
        return view;
    }

    private String getTitrs(String lesson){
        int x = 0 , placeA = 0 ,countLesson = 0 , l = Integer.valueOf(lesson.substring(4));
        //Log.i("TAG", "getTitrs: " + l);

        Database database = new Database(context);
        modelDBS = database.getAllPrescription();
        String titr = "";
        for (int j = 0 ; j < modelDBS.size() ; j++) {
            if (l == Integer.valueOf(modelDBS.get(j).getLesson().substring(4))) {
                countLesson++;
                if(countLesson != 0)
                    x++;
            }
            if (x != 0){
                placeA = j - countLesson + 1;
                x = 0;
            }
        }
        for (int i = 0 ; i < countLesson ; i++) {
            titr = titr + "/" + modelDBS.get(i + placeA).getTitr();
        }
        //Log.i("TAG", "getTitrs: " + titr);
        return titr;
    }

    private String getNextLink(String currentLink, int id){
//        Log.i("TAG", "getNextLinkID: " + id);
        Database database = new Database(context);
        modelDBSForLink = database.getAllPrescription();

        int i = 0,l = modelDBSForLink.size();
        String nextLink;
        for (i = id ; i < l ; i++) {
            nextLink = modelDBSForLink.get(i).getLink();
            if (! currentLink.equals(nextLink)){
                //Log.i("TAG", "getNextLink: " + nextLink);
                return nextLink;
            }
        }
        return "صوت 963_درس ۵۹_ص ۴۰۸";
    }
}
