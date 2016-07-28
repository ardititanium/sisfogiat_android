package com.mipro.ard.penajdwalan.jadwal;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.personil.ListItemPersonil;
import com.mipro.ard.penajdwalan.jadwal.RecyclerJadwal.pilihPersonil.ListItemPilihPersonil;
import com.mipro.ard.penajdwalan.jadwal.RecyclerJadwal.pilihPersonil.ListRowViewHolderPilihPersonil;
import com.mipro.ard.penajdwalan.jadwal.RecyclerJadwal.pilihPersonil.pilihPersonilRecyclerAdapter;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class pilih_personil extends AppCompatActivity {
    final String TAG = "Pilih Personil";
    private List<ListItemPilihPersonil> listItemPilihPersonils = new ArrayList<ListItemPilihPersonil>();
    RecyclerView recyclerView;
    private pilihPersonilRecyclerAdapter adapter;
    private ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_personil);

        recyclerView = (RecyclerView) findViewById(R.id.rec_pilih_personil);

        final LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);

        UpdateList();
    }

    private void UpdateList(){
        adapter = new pilihPersonilRecyclerAdapter(this, listItemPilihPersonils);
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
                        ListItemPilihPersonil item = new ListItemPilihPersonil();

                        item.setNrp(listPers.getString("nrp"));
                        item.setNama(listPers.getString("namaLengkap").toUpperCase());
                        item.setPangkat(listPers.getString("pangkat").toUpperCase());

                        listItemPilihPersonils.add(item);
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
