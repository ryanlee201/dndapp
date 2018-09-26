package com.example.dndapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class SpellCardGalleryAdapter extends RecyclerView.Adapter<SpellCardGalleryAdapter.ViewHolder> {
    private String[] galleryList;
    private Context context;
    private HashMap<Integer, String> chosenSpells = new HashMap<>();
    private BitmapFactory.Options options;
    private Bitmap reusedBitmap;

    public SpellCardGalleryAdapter(Context context, String[] galleryList) {
        this.galleryList = galleryList;
        this.context = context;
    }

    @Override
    public SpellCardGalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SpellCardGalleryAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //viewHolder.img.setImageResource((context.getResources().getIdentifier(galleryList[position],"drawable",context.getPackageName())));
        options.inMutable = true;
        options.inSampleSize = 1;
        options.inBitmap = reusedBitmap;
        reusedBitmap = BitmapFactory.decodeResource(context.getResources(),context.getResources().getIdentifier(galleryList[position],"drawable",context.getPackageName()),options);

        Log.i("Image size",Integer.toString(reusedBitmap.getByteCount()));
        viewHolder.img.setImageBitmap(reusedBitmap);
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewSpellCard.class);
                intent.putExtra("Spell", new String[]{galleryList[position]});
                v.getContext().startActivity(intent);
            }
        });

        viewHolder.img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                grayOut(viewHolder,position);
                return true;
                }
            }
        );
    }

    @Override
    public int getItemCount() {
        return galleryList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        public ViewHolder(View view) {
            super(view);

            img = (ImageView) view.findViewById(R.id.img);
            options = new BitmapFactory.Options();
        }
    }

    public void grayOut(final SpellCardGalleryAdapter.ViewHolder viewHolder, int position) {
        // if not grayed
        if(viewHolder.img.getTag() != "grayed") {
            viewHolder.img.setColorFilter(Color.argb(180,200,200,200));
            viewHolder.img.setTag("grayed");
            chosenSpells.put(position,galleryList[position]);
        } else {
            viewHolder.img.setColorFilter(null);
            viewHolder.img.setTag("");
            chosenSpells.remove(position);
        }
    }

    public String[] getSpells(){
        return chosenSpells.values().toArray(new String[chosenSpells.size()]);
    }

}
