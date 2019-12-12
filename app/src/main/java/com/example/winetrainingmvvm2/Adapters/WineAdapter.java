package com.example.winetrainingmvvm2.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.winetrainingmvvm2.Models.Wine;
import com.example.winetrainingmvvm2.R;

import java.util.List;

public class WineAdapter extends RecyclerView.Adapter<WineAdapter.ViewHolder> {

    private static final String TAG = "WineAdapter";

    private List<Wine> mWineList;
    private OnWineClickListener mOnWineClickListener;

    public WineAdapter(OnWineClickListener onWineClickListener) {
        this.mOnWineClickListener = onWineClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_wine, parent, false);
        return new ViewHolder(view, mOnWineClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.tvName.setText(mWineList.get(position).getName());
            holder.tvCategory.setText(mWineList.get(position).getCategory());
            holder.tvGlassPrice.setText(Float.toString(mWineList.get(position).getGlassPrice()));
            holder.tvBottlePrice.setText(Float.toString(mWineList.get(position).getBottlePrice()));

        } catch (NullPointerException e) {
            Log.e(TAG, "onBindViewHolder: NullPointerException " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        if (mWineList != null) {
            return mWineList.size();
        }
        return 0;
    }

    public void setWineList(List<Wine> wines) {
        mWineList = wines;
        notifyDataSetChanged();
    }

    public Wine getWineAt(int adapterPosition) {
        return mWineList.get(adapterPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName, tvCategory, tvGlassPrice, tvBottlePrice;
        OnWineClickListener onWineClickListener;

        public ViewHolder(View itemView, OnWineClickListener onNoteListener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvCategory = itemView.findViewById(R.id.tv_category);
            tvGlassPrice = itemView.findViewById(R.id.tv_glassPrice);
            tvBottlePrice = itemView.findViewById(R.id.tv_bottlePrice);

            this.onWineClickListener = onNoteListener;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onWineClickListener.onWineClick(getAdapterPosition());
        }
    }
}
