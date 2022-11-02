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

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    ArrayList todoId, todoHead, todoCate, todoStart;
    Context context;

    // Constructor for initialization
    public TodoAdapter(Context context, ArrayList todoId, ArrayList todoHead,ArrayList todoCate, ArrayList todoStart ) {
        this.context = context;
        this.todoId = todoId;
        this.todoHead = todoHead;
        this.todoCate = todoCate;
        this.todoStart = todoStart;
    }

    @NonNull
    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list, parent, false);

        TodoAdapter.ViewHolder viewHolder = new TodoAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.ViewHolder holder, int position) {
        holder.head.setText((String) todoHead.get(position));
        holder.start.setText((String) todoStart.get(position));
        holder.category.setText((String) todoCate.get(position));
    }

    @Override
    public int getItemCount() {
        return todoHead.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView head, start, category;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //images = (ImageView) itemView.findViewById(R.id.imgCat);
            head = (TextView) itemView.findViewById(R.id.tvItemHead);
            start = (TextView) itemView.findViewById(R.id.tvItemStart);
            category = (TextView) itemView.findViewById(R.id.tvItemCategory);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (itemClickListener != null) {
//                        itemClickListener.itemListener(head.getText().toString());
//                    }
                }
            });
        }
    }
}
