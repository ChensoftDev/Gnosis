package com.example.gnosis.reclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gnosis.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList categoryImg, categoryName,catDesc;
    Context context;

    // Constructor for initialization
    public CategoryAdapter(Context context, ArrayList categoryImg, ArrayList categoryName,ArrayList catDesc) {
        this.context = context;
        this.categoryImg = categoryImg;
        this.categoryName = categoryName;
        this.catDesc = catDesc;
    }



    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Layout(Instantiates list_item.xml
        // layout file into View object)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);

        // Passing view to ViewHolder
        CategoryAdapter.ViewHolder viewHolder = new CategoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        // TypeCast Object to int type
        int res = (int) categoryImg.get(position);
        holder.images.setImageResource(res);
        holder.head.setText((String) categoryName.get(position));
        holder.Desc.setText((String) catDesc.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView images;
        TextView head;
        TextView Desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            images = (ImageView) itemView.findViewById(R.id.imgCat);
            head = (TextView) itemView.findViewById(R.id.tvHead);
            Desc = (TextView) itemView.findViewById(R.id.tvDesc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        itemClickListener.itemListener(head.getText().toString());
                    }
                }
            });
        }
    }

    // Chul Min: Pass click event to parent activity
    public interface ItemClickListener {
        void itemListener(String categoryName);
    }
    ItemClickListener itemClickListener;
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
