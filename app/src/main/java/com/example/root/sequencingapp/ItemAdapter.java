package com.example.root.sequencingapp;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> implements View.OnDragListener {
    private List<Item> items;
    private ImageView dropTarget, dropped, dropped_temp;
    private Drawable target_draw, dragged_draw;
    private Integer tagDropTarget, tagDroppedImage;
    private View draggedImageView;
    private JsonHandler jsonHandler;
    private Context context;
    private boolean matchFlag = false;
    private ImageButton imageButtonBack;
    private MediaPlayer rightVoice, wrongVoice, mp;
    private ArrayList<Integer> sounds;

    private int i = 0, nowclicked = -1;


    public ItemAdapter(List<Item> items, Context context) {
        this.items = items;
        this.context = context;
        jsonHandler = new JsonHandler(context);


    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View statusContainer = inflater.inflate(R.layout.user_item, parent, false);
        rightVoice = MediaPlayer.create(context, R.raw.correct);
        wrongVoice = MediaPlayer.create(context, R.raw.wrong);
        sounds = new ArrayList<>();
        sounds.add(R.raw.correct1);
        sounds.add(R.raw.correct2);
        sounds.add(R.raw.correct3);
        sounds.add(R.raw.correct4);
        sounds.add(R.raw.correct5);
        sounds.add(R.raw.correct6);


        return new ItemViewHolder(statusContainer);


    }


    private void nextScene(int value) {

        if (value == 3) {
            items.clear();
            SceneTracker.setLevel(SceneTracker.getLevel() + 1);
            items = jsonHandler.getSceneData(SceneTracker.getLevel() - 1);
            ItemAdapter.this.notifyDataSetChanged();
            ItemAdapter.this.dropTarget.setBackgroundColor(Color.WHITE);


            SceneTracker.setCount(0);

        }

    }




    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        Item status = items.get(position);
        holder.bind(status);
        holder.imageButton.setVisibility(View.VISIBLE);
        holder.imageButton.setBackgroundColor(0x00000000);

        if (position != SceneTracker.getNotDrag()) {
            holder.imageButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    ClipData clipData = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(clipData, shadowBuilder, view, 0);

                    nowclicked = holder.getAdapterPosition();
                    return true;
                }
            });
        }


        holder.imageButton.setOnDragListener(this);


        //  Log.d("tagAdapter",String .valueOf(nowclicked));
    }


    @Override
    public boolean onDrag(View receivingLayoutView, DragEvent dragEvent) {
        draggedImageView = (View) dragEvent.getLocalState();

        // ImageView draggedImageView = (ImageView) dragEvent.getLocalState();

        switch (dragEvent.getAction()) {

            case DragEvent.ACTION_DRAG_STARTED:

                return true;

            case DragEvent.ACTION_DRAG_ENTERED:

                return true;

            case DragEvent.ACTION_DRAG_LOCATION:

                return true;

            case DragEvent.ACTION_DRAG_EXITED:

                return true;

            case DragEvent.ACTION_DROP:


                dropped_temp = (ImageView) draggedImageView;

                tagDropTarget = (Integer) receivingLayoutView.getTag();
                tagDroppedImage = (Integer) draggedImageView.getTag();

                dropTarget = (ImageView) receivingLayoutView;
                target_draw = dropTarget.getDrawable();// stores target image
                dropped = (ImageView) draggedImageView;


                Log.d("tagAdapter", String.valueOf(nowclicked));
                // dragged_draw = dropped.getDrawable();

                isDragMatching();

                return true;


            case DragEvent.ACTION_DRAG_ENDED:

                if (!dragEvent.getResult()) {

                    draggedImageView.setVisibility(View.VISIBLE);

                }
                return true;

            default:

                break;
        }
        return false;
    }


    private boolean isDragMatching() {

        if ((nowclicked == tagDropTarget)&&(tagDropTarget!=tagDroppedImage)) {


            dropTarget.setImageDrawable(dropped.getDrawable());
            dropped.setImageDrawable(target_draw);

            dropped.setBackgroundColor(Color.GREEN);
            dropTarget.setBackgroundColor(Color.GREEN);
            dropped.setTag(9);
            dropTarget.setTag(9);

            SceneTracker.setCount(SceneTracker.getCount() + 1);
            nextScene(SceneTracker.getCount());


            //Log.d("tagid", Integer.toString(dropped.getId()));

        } else if (nowclicked == tagDroppedImage) {

            dropped.setBackgroundColor(Color.GREEN);
            matchFlag = false;

        } else {

            matchFlag = false;
           /*dropTarget.setImageDrawable(dropped.getDrawable());
            dropped.setImageDrawable(target_draw);*/
        }


        return true;
    }


    public void prevScene() {
        items.clear();
        SceneTracker.setLevel(SceneTracker.getLevel() - 1);
        items = jsonHandler.getSceneData(SceneTracker.getLevel() - 1);
        ItemAdapter.this.notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


}