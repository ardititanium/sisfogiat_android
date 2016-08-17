package com.mipro.ard.penajdwalan.jadwal.RecylerPersTerlibat;


import android.content.Context;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ScaleGestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.str.StrList;
import com.mipro.ard.penajdwalan.json_handler.parser;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by ard on 8/13/2016.
 */
public class StrFull_fragment extends DialogFragment {
    ArrayList<StrList> strLists;
    ViewPager viewPager;
    strViewPagerAdapter strPagerAdapter;
    TextView jumlah_tv, nama_tv;
    int PosisiItem = 0;
    Matrix matrix = new Matrix();
    Float scale = 1f;
    ScaleGestureDetector SGD;
    ImageView imageView;

    public static StrFull_fragment newInstance(){
        StrFull_fragment f = new StrFull_fragment();
        return f;
    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale = scale * detector.getScaleFactor();
            scale = Math.max(0.1f, Math.min(scale, 5f));
            matrix.setScale(scale, scale);
            imageView.setImageMatrix(matrix);
            return true;
        }
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_str_slider, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager_str);
        jumlah_tv = (TextView) v.findViewById(R.id.jumlah_page_str);
        nama_tv = (TextView) v.findViewById(R.id.nama_str);

        SGD = new ScaleGestureDetector(getActivity(), new ScaleListener());
        strLists = (ArrayList<StrList> ) getArguments().getSerializable("strLists");
        PosisiItem = getArguments().getInt("position");
        strPagerAdapter = new strViewPagerAdapter();
        viewPager.setAdapter(strPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SGD.onTouchEvent(event);
                return true;
            }
        });

        setCurrentItem(PosisiItem);
        return v;
    }



    private void setCurrentItem(int position){
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(PosisiItem);
    }



    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    private void displayMetaInfo(int position) {
        jumlah_tv.setText((position + 1) +" dari "+ strLists.size());

        StrList list = strLists.get(position);
        nama_tv.setText(list.getNamaStr());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
    }

    public class strViewPagerAdapter extends PagerAdapter{
        private LayoutInflater layoutInflater;

        public strViewPagerAdapter(){

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.preview_str, container, false);

            imageView = (ImageView) view.findViewById(R.id.str_preview);

            StrList strList = strLists.get(position);

            String url = strList.getUrlStr();
            Log.d("URLgambar", url);

               Glide.with(getActivity()).load(strList.getUrlStr())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);

            container.addView(view);


            return view;
        }

        @Override
        public int getCount() {
            return strLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
