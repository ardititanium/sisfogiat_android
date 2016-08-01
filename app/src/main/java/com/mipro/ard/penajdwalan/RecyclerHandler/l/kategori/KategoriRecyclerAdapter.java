package com.mipro.ard.penajdwalan.RecyclerHandler.l.kategori;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.edit.edit_kategori;

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

        final TextView id_kat_tv= new ListRowViewHolderKategori(v).id_tv;
        final TextView nama_kat_tv = new ListRowViewHolderKategori(v).nama_kat_tv;

        holder.rLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bundle dataKat = new Bundle();
                new MaterialDialog.Builder(context)
                        .title(nama_kat_tv.getText().toString())
                        .items(R.array.dialog_menu1)
                        .itemsIds(R.array.dialog_menu1_id)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                if (which == 0){
                                    new MaterialDialog.Builder(context)
                                            .title(nama_kat_tv.getText().toString())
                                            .content("Yakin akan menghapus " + nama_kat_tv.getText().toString())
                                            .positiveText("Hapus")
                                            .negativeText("Batal")
                                            .show();
                                }else {
                                    String id_kat_str = id_kat_tv.getText().toString();
                                    String nama_kat_str =  nama_kat_tv.getText().toString();

                                    dataKat.putString("id_kat", id_kat_str);
                                    dataKat.putString("nama_kat", nama_kat_str);

                                    Intent editIntent = new Intent(context, edit_kategori.class);
                                    editIntent.putExtras(dataKat);
                                    context.startActivity(editIntent);

                                }

//
//                                if (view.getId() == 0) {
//                                    Toast.makeText(context, which + ": " + text + ", ID = " + view.getId(), Toast.LENGTH_SHORT).show();


//                                }else if (){

//                                }
                            }
                        })
                        .show();
            }
        });

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
