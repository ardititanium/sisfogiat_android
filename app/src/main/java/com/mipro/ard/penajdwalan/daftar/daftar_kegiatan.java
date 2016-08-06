package com.mipro.ard.penajdwalan.daftar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mipro.ard.penajdwalan.MainActivity;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.kategori.KategoriRecyclerAdapter;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.kategori.ListItemKategori;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.kegiatan.KegiatanRecyclerAdapter;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.kegiatan.ListItemKegiatan;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.satlantas.ListItemSatlantas;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.satlantas.SatlantasRecyclerAdapter;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;
import com.mipro.ard.penajdwalan.tambah.tambah_kegiatan;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class daftar_kegiatan extends AppCompatActivity {
    public static final String TAG = "satlntasList";

    private List<ListItemKegiatan> listItemListKegiatan = new ArrayList<ListItemKegiatan>();

    private RecyclerView recyclerView;
    private KegiatanRecyclerAdapter adapter;

    private ProgressDialog PD;

    LinearLayout search_wrap;

    TextView title_bar, search_et;
    ImageButton m_back_btn, m_search_btn, m_add_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kegiatan);

        title_bar = (TextView) findViewById(R.id.bar_title);
        m_back_btn = (ImageButton) findViewById(R.id.kembali_btn);
        m_search_btn= (ImageButton) findViewById(R.id.search_btn);
        m_add_btn = (ImageButton) findViewById(R.id.add_btn);
        search_et = (EditText) findViewById(R.id.search_box);
        search_wrap = (LinearLayout) findViewById(R.id.wrap_search);

        search_wrap.setVisibility(View.GONE);


        title_bar.setText("DAFTAR KEGIATAN");

        m_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(back_home);
                finish();
            }
        });

        m_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addInt = new Intent(daftar_kegiatan.this, tambah_kegiatan.class);
                startActivity(addInt);
            }
        });


        final int[] i = {0};
        m_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i[0] == 0){
                    search_wrap.setVisibility(View.VISIBLE);
                    i[0] = 1;
                }else if (i[0] == 1){
                    search_wrap.setVisibility(View.GONE);
                    i[0] = 0;
                }
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.rec_kegiatan);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);

        updateList();
        addTextListener();

        if(parser.AKSES_SHARED_PREF.equals("user")){
            m_add_btn.setVisibility(View.GONE);
        }


    }

    private void updateList() {

        PD.show();

        adapter = new KegiatanRecyclerAdapter(this, listItemListKegiatan);
        recyclerView.setAdapter(adapter);
        adapter.clearAdapter();


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                parser.DATA_KEGIATAN, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                PD.dismiss();
                Log.d(TAG, response.toString());
                try {
                    JSONObject jPesan = response.getJSONObject("data");
                    JSONArray jsonArray = jPesan.getJSONArray("kegiatan");

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject listGiat = jsonArray.getJSONObject(i);

                        ListItemKegiatan item = new ListItemKegiatan();

                        item.setNamaKegiatan(listGiat.getString("namaKegiatan"));
                        item.setNamaKategori(listGiat.getString("namaKategori"));
                        item.setNamaLokasi(listGiat.getString("namaLokasi"));
                        item.setStatus(listGiat.getString("status"));
                        item.setIdKegiatan(listGiat.getString("idKegiatan"));
                        item.setDetailLokasi(listGiat.getString("detailLokasi"));
                        item.setAlamatLokasi(listGiat.getString("alamatLokasi"));
                        item.setKota(listGiat.getString("kota"));
                        item.setBagian(listGiat.getString("bagian"));
                        item.setDeskripsi(listGiat.getString("deskripsi"));
                        item.setNamaSatuan(listGiat.getString("namaSatuan"));
                        listItemListKegiatan.add(item);
                        Log.d("Isi Deskripsi", listGiat.getString("deskripsi"));
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
                Toast.makeText(getApplicationContext(),
                        "Terjadi Kesalahan Jarigan", Toast.LENGTH_SHORT).show();


            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToReqQueue(jsonObjReq);

    }

    public void addTextListener() {
        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence query, int start, int before, int count) {
                query  = query.toString().toLowerCase();

                final List<ListItemKegiatan> filterList = new ArrayList<>();
                for (int i = 0; i < listItemListKegiatan.size(); i++){

                    final String namaGiat_q   = listItemListKegiatan.get(i).getNamaKegiatan().toLowerCase();
                    final String namaKat_q    = listItemListKegiatan.get(i).getNamaKategori().toLowerCase();
                    final String lokasi_q     = listItemListKegiatan.get(i).getNamaLokasi().toLowerCase();

                    final String namaGiat   = listItemListKegiatan.get(i).getNamaKegiatan();
                    final String namaKat    = listItemListKegiatan.get(i).getNamaKategori();
                    final String lokasi     = listItemListKegiatan.get(i).getNamaLokasi();
                    final String status     = listItemListKegiatan.get(i).getStatus();


                    if (namaGiat_q.contains(query) || namaKat_q.contains(query) || lokasi_q.contains(query)){
                        ListItemKegiatan giatFilter = new ListItemKegiatan();
                        giatFilter.setNamaKegiatan(namaGiat);
                        giatFilter.setNamaKategori(namaKat);
                        giatFilter.setNamaLokasi(lokasi);
                        giatFilter.setStatus(status);
                        filterList.add(giatFilter);
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(daftar_kegiatan.this));
                adapter = new KegiatanRecyclerAdapter(daftar_kegiatan.this, filterList);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }



            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
