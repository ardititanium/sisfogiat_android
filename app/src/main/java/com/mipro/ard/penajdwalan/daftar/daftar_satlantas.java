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
import com.mipro.ard.penajdwalan.RecyclerHandler.l.personil.ListItemPersonil;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.personil.PersonilRecyclerAdapter;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.satlantas.ListItemSatlantas;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.satlantas.SatlantasRecyclerAdapter;
import com.mipro.ard.penajdwalan.json_handler.parser;
import com.mipro.ard.penajdwalan.tambah.tambah_satlantas;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class daftar_satlantas extends AppCompatActivity {
    public static final String TAG = "satlntasList";

    private List<ListItemSatlantas> listItemListSatlantas = new ArrayList<ListItemSatlantas>();

    private RecyclerView recyclerView;
    private SatlantasRecyclerAdapter adapter;

    private ProgressDialog PD;

    TextView title_bar, search_et;
    ImageButton m_back_btn, m_search_btn, m_add_btn;

    LinearLayout search_wrap;
    boolean status_cari = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_satlantas);

        title_bar = (TextView) findViewById(R.id.bar_title);
        m_back_btn = (ImageButton) findViewById(R.id.kembali_btn);
        m_search_btn= (ImageButton) findViewById(R.id.search_btn);
        m_add_btn = (ImageButton) findViewById(R.id.add_btn);

        search_et = (EditText) findViewById(R.id.search_box);
        search_wrap = (LinearLayout) findViewById(R.id.wrap_search);

        search_wrap.setVisibility(View.GONE);


        if(parser.AKSES_SHARED_PREF.equals("user")){
            m_add_btn.setVisibility(View.GONE);
        }

        title_bar.setText("DAFTAR SATLANTAS");

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
                Intent intAdd = new Intent(daftar_satlantas.this, tambah_satlantas.class);
                startActivity(intAdd);
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



        recyclerView = (RecyclerView) findViewById(R.id.rec_satlantas);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());

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

        adapter = new SatlantasRecyclerAdapter(this, listItemListSatlantas);
        recyclerView.setAdapter(adapter);
        adapter.clearAdapter();


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                parser.DATA_SATLANATAS, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                PD.dismiss();
                Log.d(TAG, response.toString());
                try {
                    JSONObject jPesan = response.getJSONObject("data");
                    JSONArray jsonArray = jPesan.getJSONArray("satlantas");

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject listSat = jsonArray.getJSONObject(i);
                        ListItemSatlantas item = new ListItemSatlantas();

                        item.setIc_sat(listSat.getString("idSatuan"));
                        item.setNama_sat(listSat.getString("namaSatuan"));
                        item.setAlamat_sat(listSat.getString("alamat").toUpperCase());
                        listItemListSatlantas.add(item);
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

                final List<ListItemSatlantas> filterList = new ArrayList<>();
                for (int i = 0; i < listItemListSatlantas.size(); i++){

                    final String id_q       = listItemListSatlantas.get(i).getIc_sat().toLowerCase();
                    final String nama_q     = listItemListSatlantas.get(i).getNama_sat().toLowerCase();


                    final String id         = listItemListSatlantas.get(i).getIc_sat();
                    final String nama       = listItemListSatlantas.get(i).getNama_sat();
                    final String alamat     = listItemListSatlantas.get(i).getAlamat_sat();

                    if (id_q.contains(query) || nama_q.contains(query)){
                        ListItemSatlantas satFilter = new ListItemSatlantas();
                        satFilter.setIc_sat(id);
                        satFilter.setNama_sat(nama);
                        satFilter.setAlamat_sat(alamat);

                        filterList.add(satFilter);
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(daftar_satlantas.this));
                adapter = new SatlantasRecyclerAdapter(daftar_satlantas.this, filterList);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }



            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
