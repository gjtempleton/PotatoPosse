package com.example.potatoposse.utils;

import com.example.potatoposse.R;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
 
/**
 * @basedon http://www.androidbegin.com/tutorial/android-viewpager-gallery-images-and-texts-tutorial/
 *
 */
public class ViewPagerAdapter extends PagerAdapter 
{
    Context context;
    int[] images;
    LayoutInflater inflater;
    Typeface font;
 
    public ViewPagerAdapter(Context context, int[] images, Typeface font) 
    {
        this.context = context;
        this.images = images;
        this.font = font;
    }
 
    @Override
    public int getCount() 
    {
        return images.length;
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) 
    {
        return view == ((TableLayout)object);
    }
 
    @Override
    public Object instantiateItem(ViewGroup container, int position) 
    {     	
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager, container, false);
 
        ImageView image = (ImageView)itemView.findViewById(R.id.image);
        image.setPadding(20, 20, 20, 20);
        image.setImageResource(images[position]);
        
        TextView counter = (TextView)itemView.findViewById(R.id.counter);
        counter.setTypeface(font);
        counter.setTextSize(20f);
        counter.setGravity(Gravity.CENTER);
        counter.setPadding(20, 20, 20, 20);
        counter.setText(position+1 + "/" + getCount());
 
        ((ViewPager)container).addView(itemView);
 
        return itemView;
    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) 
    {
        ((ViewPager)container).removeView((TableLayout)object);
    }
}