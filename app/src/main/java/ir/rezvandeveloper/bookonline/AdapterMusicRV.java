package ir.rezvandeveloper.bookonline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterMusicRV extends RecyclerView.Adapter<AdapterMusicRV.MyViewHolder> {

    List<ModelBookRv> list;
    Context context;

    public AdapterMusicRV(List<ModelBookRv> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_book_rv, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ModelBookRv model_bookRv = list.get(position);

        holder.tv_item_rv.setText(model_bookRv.getName());
        holder.ll_parent_rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayMusicActivity.class);
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

        LinearLayoutCompat ll_parent_rv;
        AppCompatTextView tv_item_rv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_parent_rv = itemView.findViewById(R.id.llItemTalkQuestionRv);
            tv_item_rv = itemView.findViewById(R.id.tv_item_rv);


        }
    }
}
