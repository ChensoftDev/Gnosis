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
import java.util.Calendar;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    ArrayList myCategory;
    ArrayList<String> myKeys;
    List<todo_list_model> myTodoList;

    // Constructor for initialization
    public TodoAdapter(ArrayList<String> myKeys, List<todo_list_model> myTodoList, ArrayList myCategory) {
        this.myKeys = myKeys;
        this.myTodoList = myTodoList;
        this.myCategory = myCategory;
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
        Calendar today = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        String[] startDateFull = myTodoList.get(position).getStartDate().split("-");
        String[] StartTimeFull = myTodoList.get(position).getStartTime().split(" ");
        String[] StartTime = StartTimeFull[0].split(":");
        int addMidday = 0;
        if(StartTimeFull[1].equals("PM")) {addMidday = 12;}
        startDate.set(Integer.parseInt(startDateFull[2]),
                Integer.parseInt(startDateFull[1])-1,
                Integer.parseInt(startDateFull[0]),
                Integer.parseInt(StartTime[0]) + addMidday ,
                Integer.parseInt(StartTime[1]));


        holder.key.setText(myKeys.get(position));
        holder.head.setText(myTodoList.get(position).getName());
        holder.start.setText(myTodoList.get(position).getStartDate() +" "+ myTodoList.get(position).getStartTime());
        holder.category.setText((String) myCategory.get(position));

    }

    @Override
    public int getItemCount() {
        return myTodoList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView key, head, start, category;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //images = (ImageView) itemView.findViewById(R.id.imgCat);
            key = itemView.findViewById(R.id.tvItemKey);
            head = (TextView) itemView.findViewById(R.id.tvItemHead);
            start = (TextView) itemView.findViewById(R.id.tvItemStart);
            category = (TextView) itemView.findViewById(R.id.tvItemCategory);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (todoClickListener != null) {
                        todoClickListener.itemClickListener(key.getText().toString(), category.getText().toString());
                    }
                }
            });
        }
    }

    public  interface TodoClickListener {
        void itemClickListener(String itemId, String category);
    }

    TodoClickListener todoClickListener = null;

    public void setTodoClickListener(TodoClickListener todoClickListener) {
        this.todoClickListener = todoClickListener;
    }
}
