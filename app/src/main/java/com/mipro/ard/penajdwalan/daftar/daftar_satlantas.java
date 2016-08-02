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

    TextView title_bar;
    ImageButton m_back_btn, m_search_btn, m_add_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_satlantas);

        title_bar = (TextView) findViewById(R.id.bar_title);
        m_back_btn = (ImageButton) findViewById(R.id.kembali_btn);
        m_search_btn= (ImageButton) findViewById(R.id.search_btn);
        m_add_btn = (ImageButton) findViewById(R.id.add_btn);


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



        recyclerView = (RecyclerView) findViewById(R.id.rec_satlantas);
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
}
