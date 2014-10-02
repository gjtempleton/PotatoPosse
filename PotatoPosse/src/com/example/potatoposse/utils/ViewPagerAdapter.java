package com.example.potatoposse.utils;

import com.example.potatoposse.R;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
 
/**
 * @original http://www.androidbegin.com/tutorial/android-viewpager-gallery-images-and-texts-tutorial/
 */
public class ViewPagerAdapter extends PagerAdapter 
{
    Context context;
    String dir;
    String[] paths;
    LayoutInflater inflater;
    
    public ViewPagerAdapter(Context context, String dir, String[] paths) 
    {
        this.context = context;
        this.dir = dir;
        this.paths = paths;
    }
 
    @Override
    public int getCount() 
    {
        return paths.length;
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
        image.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        image.setImageBitmap(BitmapFactory.decodeFile(dir+"/"+paths[position]));
        ((ViewPager)container).addView(itemView);
 
        return itemView;
    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) 
    {
        ((ViewPager)container).removeView((TableLayout)object);
    }
}