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
import com.example.gnosis.model.todo_list_model;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    ArrayList myCategory;
    Context context;
    List<todo_list_model> myTodoList;

    // Constructor for initialization
    public TodoAdapter(Context context, List<todo_list_model> myTodoList, ArrayList myCategory) {
        this.context = context;
        this.myTodoList = myTodoList;
        this.myCategory = myCategory;
//        this.todoId = todoId;
//        this.todoHead = todoHead;
//        this.todoCate = todoCate;
//        this.todoStart = todoStart;
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
        holder.head.setText(myTodoList.get(position).getName());
        holder.start.setText(myTodoList.get(position).getStartDate() +" "+ myTodoList.get(position).getStartTime());
        holder.category.setText((String) myCategory.get(position));
//        holder.head.setText((String) todoHead.get(position));
//        holder.start.setText((String) todoStart.get(position));
//        holder.category.setText((String) todoCate.get(position));
    }

    @Override
    public int getItemCount() {
        return myTodoList.size();
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
