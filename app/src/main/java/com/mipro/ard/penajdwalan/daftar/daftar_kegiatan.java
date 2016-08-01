package com.mipro.ard.penajdwalan.daftar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mipro.ard.penajdwalan.MainActivity;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.kegiatan.KegiatanRecyclerAdapter;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.kegiatan.ListItemKegiatan;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.satlantas.ListItemSatlantas;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.satlantas.SatlantasRecyclerAdapter;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

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

    TextView title_bar;
    ImageButton m_back_btn, m_search_btn, m_add_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kegiatan);

        title_bar = (TextView) findViewById(R.id.bar_title);
        m_back_btn = (ImageButton) findViewById(R.id.kembali_btn);
        m_search_btn= (ImageButton) findViewById(R.id.search_btn);
        m_add_btn = (ImageButton) findViewById(R.id.add_btn);


        title_bar.setText("DAFTAR KEGIATAN");

        m_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(back_home);
                finish();
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
}
