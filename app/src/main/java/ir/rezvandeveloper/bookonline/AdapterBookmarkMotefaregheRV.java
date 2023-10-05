package ir.rezvandeveloper.bookonline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterBookmarkMotefaregheRV extends RecyclerView.Adapter<AdapterBookmarkMotefaregheRV.MyViewHolder> {

    List<ModelBookRv> list;
    Context context;

    public AdapterBookmarkMotefaregheRV(List<ModelBookRv> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_bookmark_motefareghe_rv, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ModelBookRv model_bookRv = list.get(position);

        holder.tv_item_rv.setText(model_bookRv.getName());

        holder.ibMotefaregheOffItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ibMotefaregheOffItem.setVisibility(View.GONE);

                SharedPreferences.Editor editor = Api.sharedPreferences.edit();
                editor.remove("Mo" + model_bookRv.getName());
                editor.apply();

            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayMotefaregheActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", model_bookRv.getName());
                intent.putExtras(bundle);
                context.startActivity(intent, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayout;
        AppCompatTextView tv_item_rv;
        AppCompatImageButton ibMotefaregheOffItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.llItemTalkQuestionRvM);
            tv_item_rv = itemView.findViewById(R.id.tv_item_rv_m);
            ibMotefaregheOffItem = itemView.findViewById(R.id.ibMotefaregheOffItem);


        }
    }
}
