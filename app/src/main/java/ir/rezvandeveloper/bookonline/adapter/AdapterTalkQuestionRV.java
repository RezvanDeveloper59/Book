package ir.rezvandeveloper.bookonline.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import ir.rezvandeveloper.bookonline.Api;
import ir.rezvandeveloper.bookonline.model.ModelTalkQuestionRv;
import ir.rezvandeveloper.bookonline.R;
import ir.rezvandeveloper.bookonline.ResponseActivity;

public class AdapterTalkQuestionRV extends RecyclerView.Adapter<AdapterTalkQuestionRV.MyViewHolder> {

    List<ModelTalkQuestionRv> list;
    Context context;

    public AdapterTalkQuestionRV(List<ModelTalkQuestionRv> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_talk_question_rv, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ModelTalkQuestionRv modelTalkQuestionRv = list.get(position);

        holder.tvItemNameRv.setText(modelTalkQuestionRv.getName());
        holder.tvItemSubjectRv.setText(modelTalkQuestionRv.getSubject());
        holder.tvItemQuestionRv.setText(modelTalkQuestionRv.getQuestion());
        holder.tvNumberTalk.setText(Api.ConvertNumberEnToFa(String.valueOf(modelTalkQuestionRv.getId())));

        DateFormat df = new SimpleDateFormat("HH:mm - yyyy/MM/d");
        String date = df.format(Calendar.getInstance().getTime());

        holder.rlItemTalkQuestionRv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ResponseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(modelTalkQuestionRv.getId()));
                bundle.putString("name", modelTalkQuestionRv.getName());
                bundle.putString("subject", modelTalkQuestionRv.getSubject());
                bundle.putString("question", modelTalkQuestionRv.getQuestion());
                bundle.putString("date", date);
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

        RelativeLayout rlItemTalkQuestionRv;
        AppCompatTextView tvItemNameRv,tvItemSubjectRv,tvItemQuestionRv,tvNumberTalk;
        ImageButton ibBookmarkOff,ibBookmarkOn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rlItemTalkQuestionRv = itemView.findViewById(R.id.rlItemTalkQuestionRv);
            tvItemNameRv = itemView.findViewById(R.id.tvItemNameRv);
            tvItemSubjectRv = itemView.findViewById(R.id.tvItemSubjectRv);
            tvItemQuestionRv = itemView.findViewById(R.id.tvItemQuestionRv);
            ibBookmarkOff = itemView.findViewById(R.id.ibBookmarkOff);
            ibBookmarkOn = itemView.findViewById(R.id.ibBookmarkOn);
            tvNumberTalk = itemView.findViewById(R.id.tvNumberTalk);
        }
    }
}
