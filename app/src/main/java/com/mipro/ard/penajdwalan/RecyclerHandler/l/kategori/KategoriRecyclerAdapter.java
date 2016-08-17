package com.mipro.ard.penajdwalan.RecyclerHandler.l.kategori;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.daftar.daftar_kategori;
import com.mipro.ard.penajdwalan.daftar.daftar_personil;
import com.mipro.ard.penajdwalan.edit.edit_kategori;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ard on 7/26/2016.
 */
public class KategoriRecyclerAdapter extends RecyclerView.Adapter<ListRowViewHolderKategori>{

    private List<ListItemKategori> listItemListKategori, filterlist;
    private Context context;
    private int focusedItem;
    TextView id_kat_tv, nama_kat_tv;
    Bundle dataKat = new Bundle();

    public KategoriRecyclerAdapter(Context context, List<ListItemKategori> listItemListKategori) {
        this.listItemListKategori = listItemListKategori;
        this.context = context;
    }

    @Override
    public ListRowViewHolderKategori onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_kategori, parent, false);
        final ListRowViewHolderKategori holder = new ListRowViewHolderKategori(v);

        id_kat_tv= new ListRowViewHolderKategori(v).id_tv;
        nama_kat_tv = new ListRowViewHolderKategori(v).nama_kat_tv;





        holder.rLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parser.AKSES_SHARED_PREF.equalsIgnoreCase("admin")){
                    showDialog();
                }

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

    public void showDialog(){
        new MaterialDialog.Builder(context)
                .title(nama_kat_tv.getText().toString())
                .items(R.array.dialog_menu1)
                .itemsIds(R.array.dialog_menu1_id)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (which == 0){
                            dialogHapus();
                        }else {
                            String id_kat_str = id_kat_tv.getText().toString();
                            String nama_kat_str =  nama_kat_tv.getText().toString();

                            dataKat.putString("id_kat", id_kat_str);
                            dataKat.putString("nama_kat", nama_kat_str);

                            Intent editIntent = new Intent(context, edit_kategori.class);
                            editIntent.putExtras(dataKat);
                            context.startActivity(editIntent);

                        }

                    }
                })
                .show();
    }

    public void hapus(){
        String urlHapus = "http://"+parser.IP_PUBLIC+"/ditlantas/json/kategori/hapus.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, urlHapus,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jPesan = new JSONObject(response);
                            boolean pesan = jPesan.names().get(0).equals("success");
                            if (pesan){
                                hapusBerhasil();
                            }else if(!pesan){
                                Toast.makeText(context,
                                        "Terjadi Kesalahan",
                                        Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                String id_kat_str = id_kat_tv.getText().toString();
                params.put("id", id_kat_str);
                return params;
            }
        };

        MyApplication.getInstance().addToReqQueue(postRequest);
    }

    private void hapusBerhasil() {
        new MaterialDialog.Builder(context)
                .content("Hapus Berhasil")
                .positiveText("Kembali Ke Daftar")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent backDaftar = new Intent(context, daftar_kategori.class);
                        context.startActivity(backDaftar);
                    }
                })
                .show();
    }

    public void dialogHapus(){
        new MaterialDialog.Builder(context)
                .content("Anda Yakin?")
                .positiveText("YA")
                .negativeText("BATAL")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        hapus();
                        Intent intentYa = new Intent(context, daftar_kategori.class);
                        context.startActivity(intentYa);
                    }
                })
                .show();
    }


}
