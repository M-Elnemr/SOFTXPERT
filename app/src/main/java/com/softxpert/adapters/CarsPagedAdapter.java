package com.softxpert.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.softxpert.R;
import com.softxpert.responses.CarResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarsPagedAdapter extends PagedListAdapter<CarResponse.Data, CarsPagedAdapter.CarHolder> {

    private static DiffUtil.ItemCallback<CarResponse.Data> DIFF_CALLBACK = new DiffUtil.ItemCallback<CarResponse.Data>() {
        @Override
        public boolean areItemsTheSame(@NonNull CarResponse.Data oldItem, @NonNull CarResponse.Data newItem) {
            return (oldItem.getId().equals(newItem.getId()));
        }

        @Override
        public boolean areContentsTheSame(@NonNull CarResponse.Data oldItem, @NonNull CarResponse.Data newItem) {
            return true;
        }
    };

    private Context context;

    public CarsPagedAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }


    public static class CarHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.brand)
        TextView brand;
        @BindView(R.id.year)
        TextView year;
        @BindView(R.id.car_image)
        ImageView carImage;
        @BindView(R.id.used_or_new)
        TextView usedOrNew;

        public CarHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new CarHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CarHolder holder, int position) {

        CarResponse.Data data = getItem(position);

        holder.brand.setText(data.getBrand());
        if (data.getIsUsed()) {
            holder.usedOrNew.setText("Used");
        } else {
            holder.usedOrNew.setText("New");
        }

        if (data.getConstractionYear() != null) {
            if (data.getConstractionYear().length() > 0) {
                holder.year.setVisibility(View.VISIBLE);
                holder.year.setText(data.getConstractionYear());
            } else {
                holder.year.setVisibility(View.INVISIBLE);
            }
        } else {
            holder.year.setVisibility(View.INVISIBLE);

        }

        if (data.getImageUrl() != null) {
            if (data.getImageUrl().length() > 0) {
                holder.carImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(data.getImageUrl()).into(holder.carImage);
            } else {
                holder.carImage.setVisibility(View.GONE);
            }
        } else {
            holder.carImage.setVisibility(View.GONE);

        }
    }
}
