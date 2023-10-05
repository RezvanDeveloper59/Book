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

public class AdapterWithoutResponseRV extends RecyclerView.Adapter<AdapterWithoutResponseRV.MyViewHolder> {

    List<ModelWithoutResponseRv> list;
    Context context;

    public AdapterWithoutResponseRV(List<ModelWithoutResponseRv> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_without_response_rv, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ModelWithoutResponseRv modelWithoutResponseRv = list.get(position);

        holder.tvItemSubjectWithoutResponseRv.setText(modelWithoutResponseRv.getSubject());
        holder.tvItemWithoutResponseRv.setText(modelWithoutResponseRv.getQuestion());

        holder.llItemWithoutResponseRv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ResponseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(modelWithoutResponseRv.getId()));
                bundle.putString("name", modelWithoutResponseRv.getName());
                bundle.putString("subject", modelWithoutResponseRv.getSubject());
                bundle.putString("question", modelWithoutResponseRv.getQuestion());
                bundle.putString("date", modelWithoutResponseRv.getDate());
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

        LinearLayoutCompat llItemWithoutResponseRv;
        AppCompatTextView tvItemSubjectWithoutResponseRv,tvItemWithoutResponseRv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            llItemWithoutResponseRv = itemView.findViewById(R.id.llItemWithoutResponseRv);
            tvItemSubjectWithoutResponseRv = itemView.findViewById(R.id.tvItemSubjectWithoutResponseRv);
            tvItemWithoutResponseRv = itemView.findViewById(R.id.tvItemWithoutResponseRv);


        }
    }
}
