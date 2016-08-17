package com.mipro.ard.penajdwalan.RecyclerHandler.l.str;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.jadwal.lihat_str;

import java.util.List;

/**
 * Created by ard on 8/12/2016.
 */
public class StrAdapter extends RecyclerView.Adapter<StrViewHolder> {
    private List<StrList> strList;
    private Context context;
    String hapus;

    public StrAdapter(List<StrList> strList, Context context) {
        this.strList = strList;
        this.context = context;
    }

    @Override
    public StrViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.str_thumbnail, parent, false);
        return new StrViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StrViewHolder holder, int position) {
        final StrList list = new StrList();
        StrList str = strList.get(position);
        Glide.with(context).load(str.getUrlStr())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.strThumb);
        holder.strThumb.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new MaterialDialog.Builder(context)
                        .title("Pilih...")
                        .items(R.array.dialog_menu1)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                switch (which){
                                    case 0:
                                        hapus = new lihat_str().hapusStr();


                                }
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return strList.size();
    }


    public interface ClickListener{
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }


    public static class RecylerTouchListener implements RecyclerView.OnItemTouchListener{
        private GestureDetector gestureDetector;
        private StrAdapter.ClickListener clickListener;

        public RecylerTouchListener (Context context, final RecyclerView recyclerView, final StrAdapter.ClickListener clickListener){
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null){
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)){
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
