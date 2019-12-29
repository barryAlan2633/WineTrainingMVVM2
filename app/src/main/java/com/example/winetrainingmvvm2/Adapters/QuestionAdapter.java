package com.example.winetrainingmvvm2.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.winetrainingmvvm2.Models.Question;
import com.example.winetrainingmvvm2.R;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private static final String TAG = "WineAdapter";

    private List<Question> mQuestionList;
    private OnItemClickListener mOnQuestionClickListener;

    public QuestionAdapter(OnItemClickListener onQuestionClickListener) {
        this.mOnQuestionClickListener = onQuestionClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_question, parent, false);
        return new ViewHolder(view, mOnQuestionClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.tvQuestion.setText(mQuestionList.get(position).getQuestion());
            holder.tvType.setText(mQuestionList.get(position).getType());

        } catch (NullPointerException e) {
            Log.e(TAG, "onBindViewHolder: NullPointerException " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        if (mQuestionList != null) {
            return mQuestionList.size();
        }
        return 0;
    }

    public void setQuestionList(List<Question> questions) {
        mQuestionList = questions;
        notifyDataSetChanged();
    }

    public Question getQuestionAt(int adapterPosition) {
        return mQuestionList.get(adapterPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvQuestion, tvType;
        OnItemClickListener onQuestionClickListener;

        public ViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tv_item_question);
            tvType = itemView.findViewById(R.id.tv_type);


            this.onQuestionClickListener = onItemClickListener;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onQuestionClickListener.onItemClick(getAdapterPosition());
        }
    }
}
