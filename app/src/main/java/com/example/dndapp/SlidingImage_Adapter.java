package com.example.dndapp;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dndapp.R;

import java.util.Arrays;
import java.util.List;

public class SlidingImage_Adapter extends PagerAdapter{
    Context context;
    int images[];
    LayoutInflater layoutInflater;


    public SlidingImage_Adapter(Context context, String[] imageNames) {
        this.context = context;
        this.images = imageNamesToIds(imageNames);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.spellcard_view, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.SpellCard);
        imageView.setImageResource(images[position]);

        container.addView(itemView);

        //listening to image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    public int[] imageNamesToIds(String[] imageNames)
    {
        int[] imageIds= new int[imageNames.length];
        for(int i = 0; i < imageIds.length; i++)
        {
            int resId = context.getResources().getIdentifier(imageNames[i],"drawable",context.getPackageName());
            imageIds[i] = resId;

        }
        //Log.d("this is my array", "String: " + Arrays.toString(imageNames));
        //Log.d("this is my array", "Integer: " + Arrays.toString(imageIds));
        return imageIds;
    }
}