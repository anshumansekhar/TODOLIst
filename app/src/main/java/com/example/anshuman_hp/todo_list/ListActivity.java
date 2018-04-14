package com.example.anshuman_hp.todo_list;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.RectF;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    RecyclerView list_recycler;
    FloatingActionButton addtolist;
    Paint p=new Paint();
    DatabaseReference reference;
    FirebaseRecyclerAdapter<listItem,TodoViewHolder> listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_list);
        reference= FirebaseDatabase.getInstance().getReference();
        list_recycler=(RecyclerView)findViewById(R.id.list_recycler);
        addtolist=(FloatingActionButton)findViewById(R.id.addtolist);
        fillTheList();
        list_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ItemTouchHelper.SimpleCallback callback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int postion=viewHolder.getAdapterPosition();
                Bundle b=new Bundle();
                b.putInt("position",postion);
                if(direction==ItemTouchHelper.LEFT) {
                    DeleteDialougeFragment deleteDialougeFragment=new DeleteDialougeFragment();
                    deleteDialougeFragment.setArguments(b);
                    deleteDialougeFragment.show(getSupportFragmentManager(), "DeleteDialouge");
//                    new DeleteDialougeFragment().show();
                }
                else if(direction==ItemTouchHelper.RIGHT)
                {
                     AddDialogFragment dialogFragment=new AddDialogFragment();
                    dialogFragment.show(getSupportFragmentManager(),"EditDialouge");
                }

            }
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;
                    if (dX > 0) {
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.edit);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.delete);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(list_recycler);
        list_recycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.HORIZONTAL));
        addtolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialogFragment addDialogFragment=new AddDialogFragment();
                addDialogFragment.show(getSupportFragmentManager(),"Dialog");
                listAdapter.notifyDataSetChanged();
            }
        });
    }
    private void fillTheList() {
        listAdapter=new FirebaseRecyclerAdapter<listItem, TodoViewHolder>(listItem.class,
                R.layout.list_item,TodoViewHolder.class,reference.child("Todo")) {
            @Override
            protected void populateViewHolder(TodoViewHolder viewHolder, listItem model, int position) {
                viewHolder.description.setText(model.getDescprition());
                viewHolder.item_layout.setBackgroundColor(5665);
            }
        };
        list_recycler.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }
}
