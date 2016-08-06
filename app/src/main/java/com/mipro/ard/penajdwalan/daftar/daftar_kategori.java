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
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;
import com.mipro.ard.penajdwalan.tambah.tambah_kategori;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class daftar_kategori extends AppCompatActivity {
    public static final String TAG = "personillist";

    private List<ListItemKategori> listItemListKategori = new ArrayList<ListItemKategori>();
    private RecyclerView recyclerView;
    private KategoriRecyclerAdapter adapter;
    private ProgressDialog PD;


    TextView title_bar, search_et;
    ImageButton m_back_btn, m_search_btn, m_add_btn;
    LinearLayout search_wrap;
    boolean status_cari = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kategori);

        title_bar = (TextView) findViewById(R.id.bar_title);
        m_back_btn = (ImageButton) findViewById(R.id.kembali_btn);
        m_search_btn= (ImageButton) findViewById(R.id.search_btn);
        m_add_btn = (ImageButton) findViewById(R.id.add_btn);
        search_et = (EditText) findViewById(R.id.search_box);
        search_wrap = (LinearLayout) findViewById(R.id.wrap_search);

        search_wrap.setVisibility(View.GONE);


        title_bar.setText("DAFTAR KATEGORI");

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
                Intent addInt = new Intent(daftar_kategori.this, tambah_kategori.class);
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

        recyclerView = (RecyclerView) findViewById(R.id.rec_kategori);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);

        updateList();
        addTextListener();

        if (parser.AKSES_SHARED_PREF.equalsIgnoreCase("user")){
            m_add_btn.setVisibility(View.GONE);
        }
    }

    public void addTextListener() {
        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence query, int start, int before, int count) {
                query  = query.toString().toLowerCase();

                final List<ListItemKategori> filterList = new ArrayList<>();
                for (int i = 0; i < listItemListKategori.size(); i++){
                    final String namaKat    = listItemListKategori.get(i).getNama().toLowerCase();
                    final String idKat      = listItemListKategori.get(i).getId().toLowerCase();

                    if (namaKat.contains(query) || idKat.contains(query)){
                        ListItemKategori katFilter = new ListItemKategori();
                        String namaCap = WordUtils.capitalize(namaKat);
                        katFilter.setId(idKat.toUpperCase());
                        katFilter.setNama(namaCap);
                        filterList.add(katFilter);
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(daftar_kategori.this));
                adapter = new KategoriRecyclerAdapter(daftar_kategori.this, filterList);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }



            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void updateList() {

        PD.show();

        adapter = new KategoriRecyclerAdapter(this, listItemListKategori);
        recyclerView.setAdapter(adapter);
        adapter.clearAdapter();


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                parser.DATA_KATEGORI, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                PD.dismiss();
                Log.d(TAG, response.toString());
                try {
                    JSONObject jPesan = response.getJSONObject("data");
                    JSONArray jsonArray = jPesan.getJSONArray("kategori");

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject listKat = jsonArray.getJSONObject(i);
                        ListItemKategori item = new ListItemKategori();

                        item.setId(listKat.getString("idKategori"));
                        item.setNama(listKat.getString("namaKategori"));

                        listItemListKategori.add(item);
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
