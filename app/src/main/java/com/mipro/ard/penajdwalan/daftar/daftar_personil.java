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
import com.mipro.ard.penajdwalan.RecyclerHandler.l.kegiatan.KegiatanRecyclerAdapter;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.kegiatan.ListItemKegiatan;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.personil.ListItemPersonil;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.personil.PersonilRecyclerAdapter;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.satlantas.ListItemSatlantas;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.satlantas.SatlantasRecyclerAdapter;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;
import com.mipro.ard.penajdwalan.tambah.tambah_personil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class daftar_personil extends AppCompatActivity {
    public static final String TAG = "personillist";

    private List<ListItemPersonil> listItemPersonils = new ArrayList<ListItemPersonil>();
    private RecyclerView recyclerView;
    private PersonilRecyclerAdapter adapter;
    private ProgressDialog PD;

    TextView title_bar, search_et;
    ImageButton m_back_btn, m_search_btn, m_add_btn;

    LinearLayout search_wrap;
    boolean status_cari = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_personil);

        title_bar = (TextView) findViewById(R.id.bar_title);
        m_back_btn = (ImageButton) findViewById(R.id.kembali_btn);
        m_search_btn= (ImageButton) findViewById(R.id.search_btn);
        m_add_btn = (ImageButton) findViewById(R.id.add_btn);
        search_et = (EditText) findViewById(R.id.search_box);
        search_wrap = (LinearLayout) findViewById(R.id.wrap_search);

        search_wrap.setVisibility(View.GONE);


        title_bar.setText("DAFTAR PERSONIL");

        m_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backHome = new Intent(daftar_personil.this, MainActivity.class);
                startActivity(backHome);
            }
        });

        m_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addInt = new Intent(daftar_personil.this, tambah_personil.class);
                startActivity(addInt);
            }
        });


        m_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!status_cari){
                    search_wrap.setVisibility(View.VISIBLE);
                    status_cari = true;
                }else{
                    search_wrap.setVisibility(View.GONE);
                    status_cari = false;
                }
            }
        });

        if (parser.AKSES_SHARED_PREF.equals("user")){
            m_add_btn.setVisibility(View.GONE);
        }


        recyclerView = (RecyclerView) findViewById(R.id.rec_personil);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);

        updateList();
        addTextListener();
    }


    private void updateList() {

        PD.show();

        adapter = new PersonilRecyclerAdapter(this, listItemPersonils);
        recyclerView.setAdapter(adapter);
        adapter.clearAdapter();


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                parser.DATA_PERSONIL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                PD.dismiss();
                Log.d(TAG, response.toString());
                try {
                    JSONObject jPesan = response.getJSONObject("data");
                    JSONArray jsonArray = jPesan.getJSONArray("personil");

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject listPers = jsonArray.getJSONObject(i);
                        ListItemPersonil item = new ListItemPersonil();

                        item.setNrp(listPers.getString("nrp"));
                        item.setNama(listPers.getString("namaLengkap").toUpperCase());
                        item.setPangkat(listPers.getString("pangkat").toUpperCase());
                        item.setSatuan(listPers.getString("namaSatuan").toUpperCase());
                        item.setKelamin(listPers.getString("kelamin").toUpperCase());
                        item.setHakAkses(listPers.getString("hakAkses"));
                        item.setPassword(listPers.getString("password"));
                        listItemPersonils.add(item);
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

                final List<ListItemPersonil> filterList = new ArrayList<>();
                for (int i = 0; i < listItemPersonils.size(); i++){

                    final String nama_q     = listItemPersonils.get(i).getNama().toLowerCase();
                    final String nrp_q      = listItemPersonils.get(i).getNrp().toLowerCase();
                    final String satuan_q   = listItemPersonils.get(i).getSatuan().toLowerCase();
                    final String pangkat_q  = listItemPersonils.get(i).getPangkat().toLowerCase();

                    final String nama     = listItemPersonils.get(i).getNama();
                    final String nrp      = listItemPersonils.get(i).getNrp();
                    final String satuan   = listItemPersonils.get(i).getSatuan();
                    final String pangkat  = listItemPersonils.get(i).getPangkat();

                    if (nama_q.contains(query) || nrp_q.contains(query) || satuan_q.contains(query) || pangkat_q.contains(query)){
                        ListItemPersonil persFilter = new ListItemPersonil();
                        persFilter.setNama(nama);
                        persFilter.setNrp(nrp);
                        persFilter.setPangkat(pangkat);
                        persFilter.setSatuan(satuan);
                        filterList.add(persFilter);
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(daftar_personil.this));
                adapter = new PersonilRecyclerAdapter(daftar_personil.this, filterList);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }



            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
