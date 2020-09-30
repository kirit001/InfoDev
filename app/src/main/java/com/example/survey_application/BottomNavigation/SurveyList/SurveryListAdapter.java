package com.example.survey_application.BottomNavigation.SurveyList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.survey_application.Database.Info;
import com.example.survey_application.R;

import java.util.ArrayList;
import java.util.List;

public class SurveryListAdapter extends RecyclerView.Adapter<SurveryListAdapter.SurveylistViewholder> {

    private Context context;
    private List<Info> data;
    OnDataClickListener onDataClickListener;
    public SurveryListAdapter(Context context, List<Info> data) {
        this.context = context;
        this.data = data;
        this.onDataClickListener = (OnDataClickListener) context;
    }

    @NonNull
    @Override
    public SurveylistViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_surverylist_item, parent,false);
        return new SurveylistViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SurveylistViewholder holder, int position) {
        holder.nameView.setText(data.get(position).getFirstname());
        holder.idView.setText(data.get(position).getId().toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SurveylistViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameView;
        TextView idView;
        public SurveylistViewholder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.nameview);
            idView = itemView.findViewById(R.id.idview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onDataClickListener.onClick(data.get(getAdapterPosition()));
        }
    }

    interface OnDataClickListener {
        void onClick(Info info);
    }
}
