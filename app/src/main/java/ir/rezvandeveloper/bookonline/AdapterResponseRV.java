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

public class AdapterResponseRV extends RecyclerView.Adapter<AdapterResponseRV.MyViewHolder> {

    List<ModelResponseRv> list;
    Context context;

    public AdapterResponseRV(List<ModelResponseRv> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_response_rv, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ModelResponseRv modelResponseRv = list.get(position);

        holder.tvItemNameRv.setText(modelResponseRv.getName());
        holder.tvItemResponseRv.setText(modelResponseRv.getResponse());
        holder.tvDateResponseRv.setText(modelResponseRv.getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvItemNameRv,tvItemResponseRv,tvDateResponseRv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemNameRv = itemView.findViewById(R.id.tvItemNameRv);
            tvItemResponseRv = itemView.findViewById(R.id.tvItemResponseRv);
            tvDateResponseRv = itemView.findViewById(R.id.tvDateResponseRv);


        }
    }
}
