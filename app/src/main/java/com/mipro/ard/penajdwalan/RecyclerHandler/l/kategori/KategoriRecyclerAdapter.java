package com.mipro.ard.penajdwalan.RecyclerHandler.l.kategori;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mipro.ard.penajdwalan.R;

import java.util.List;

/**
 * Created by ard on 7/26/2016.
 */
public class KategoriRecyclerAdapter extends RecyclerView.Adapter<ListRowViewHolderKategori>{
    private List<ListItemKategori> listItemListKategori;
    private Context context;
    private int focusedItem;

    public KategoriRecyclerAdapter(Context context, List<ListItemKategori> listItemListKategori) {
        this.listItemListKategori = listItemListKategori;
        this.context = context;
    }

    @Override
    public ListRowViewHolderKategori onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_kategori, parent, false);
        final ListRowViewHolderKategori holder = new ListRowViewHolderKategori(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ListRowViewHolderKategori holder, int position) {
        ListItemKategori listItemKategori = listItemListKategori.get(position);
        holder.itemView.setSelected(focusedItem == position);
        holder.getLayoutPosition();

        holder.id_tv.setText(listItemKategori.getId());
        holder.nama_kat_tv.setText(listItemKategori.getNama());
    }

    public void clearAdapter(){
        listItemListKategori.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != listItemListKategori ? listItemListKategori.size() : 0);
    }
}
