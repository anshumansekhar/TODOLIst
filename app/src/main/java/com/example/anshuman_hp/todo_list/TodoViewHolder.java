package com.example.anshuman_hp.todo_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
* Created by Anshuman-HP on 07-04-2017.
*/
class TodoViewHolder extends RecyclerView.ViewHolder{
   TextView description;
   ImageButton delete;
   LinearLayout item_layout;
   public TodoViewHolder(View itemView) {
       super(itemView);
       description=(TextView)itemView.findViewById(R.id.item_description);
       item_layout=(LinearLayout)itemView.findViewById(R.id.list_item);
   }
}
