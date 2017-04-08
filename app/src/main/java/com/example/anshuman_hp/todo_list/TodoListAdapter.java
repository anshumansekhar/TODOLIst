package com.example.anshuman_hp.todo_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Anshuman-HP on 07-04-2017.
 */

public class TodoListAdapter extends RecyclerView.Adapter<TodoViewHolder> {
    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TodoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        holder.item_layout.setBackgroundColor(234);
        holder.description.setText("TEST");

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
