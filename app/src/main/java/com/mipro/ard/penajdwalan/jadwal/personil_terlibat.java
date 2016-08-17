package com.mipro.ard.penajdwalan.jadwal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.jadwal.RecyclerJadwal.pilihPersonil.ListItemPilihPersonil;
import com.mipro.ard.penajdwalan.jadwal.RecylerPersTerlibat.ListItemPersonilTerlibat;
import com.mipro.ard.penajdwalan.jadwal.RecylerPersTerlibat.PersonilTerlibatRecyclerAdapter;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.MyCommand;
import com.mipro.ard.penajdwalan.json_handler.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class personil_terlibat extends AppCompatActivity implements View.OnLongClickListener {
    public boolean isActionMode = false;
    TextView counter_tv;
    int counter = 0;
    Toolbar toolbar;
    final String TAG = "Pilih Personil";

    private List<ListItemPersonilTerlibat> listItemPersonilTerlibats= new ArrayList<ListItemPersonilTerlibat>();
    private List<ListItemPersonilTerlibat> listTerpilih = new ArrayList<>(listItemPersonilTerlibats);
    RecyclerView recyclerView;
    PersonilTerlibatRecyclerAdapter adapter;
    String url;
    String idJadwal;

    ProgressDialog PD;

    int jumlah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personil_terlibat);
        toolbar = (Toolbar) findViewById(R.id.selected_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        counter_tv = (TextView) findViewById(R.id.counterTextPersonil);


        Intent getJadwal    = getIntent();
        idJadwal            = getJadwal.getStringExtra("idJadwal");



        recyclerView = (RecyclerView) findViewById(R.id.rec_pilih_personil);

        final LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);

        adapter = new PersonilTerlibatRecyclerAdapter(this, listItemPersonilTerlibats);
        recyclerView.setAdapter(adapter);
        adapter.clearAdapter();



        UpdateList();
    }

    private void UpdateList(){
        url = "http://"+ parser.IP_PUBLIC+ "/ditlantas/json/persterlibat/view.php?idj="+idJadwal;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                PD.dismiss();
                Log.d(TAG, response.toString());
                if (response.length() > 0){
                    try {
                        JSONObject jPesan = response.getJSONObject("data");

                        JSONArray jsonArray = jPesan.getJSONArray("personil");
                        int jumlahPers = jPesan.getInt("jumlah");
                        counter_tv.setText(String.valueOf(jumlahPers)+ " Personil Terlibat");

                        Log.d("Jumlah Pers", String.valueOf(jumlahPers));
                        jumlah = jsonArray.length();

                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject listPers = jsonArray.getJSONObject(i);
                            ListItemPersonilTerlibat item = new ListItemPersonilTerlibat();
                            item.setNrp(listPers.getString("nrp"));
                            item.setNama(listPers.getString("namaLengkap").toUpperCase());
                            item.setPangkat(listPers.getString("pangkat").toUpperCase());

                            listItemPersonilTerlibats.add(item);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getApplicationContext(),"Personil Terlibat Belum ditentukan",
                            Toast.LENGTH_LONG).show();
                }


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

    @Override
    public boolean onLongClick(View v) {
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_mode_selected);
        isActionMode = true;
        adapter.notifyDataSetChanged();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    public void preparedSelection(View view, int position){
        if(((CheckBox)view).isChecked()){
            adapter.personilTerpilih.add(listItemPersonilTerlibats.get(position));
            counter = counter+1;
            updateCounter(counter);
        }else {
            adapter.personilTerpilih.remove(listItemPersonilTerlibats.get(position));
            counter = counter-1;
            updateCounter(counter);
        }
    }

    public void updateCounter(int counter) {
        if (counter == 0) {
            counter_tv.setText("0 Personil Terpilih dari " + String.valueOf(jumlah));
        } else {
            counter_tv.setText(counter + " Personil Terpilih dari " +jumlah);
        }
    }

    @Override
    public void onBackPressed() {
        if (isActionMode){
            clearActionMode();
            adapter.notifyDataSetChanged();
        }else{
            super.onBackPressed();
        }
    }


    public void clearActionMode(){
        isActionMode = false;
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_main_selected);
        listTerpilih.clear();
        counter = 0;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.done_selected_mode){
            for (int i = 0; i < adapter.personilTerpilih.size(); i++){
                ListItemPersonilTerlibat listSelected = new ListItemPersonilTerlibat();
                ListItemPersonilTerlibat listItemPersonilTerlibat = listItemPersonilTerlibats.get(i);
                if (listItemPersonilTerlibat.isSelected() ){
                    listSelected.setNrp(listItemPersonilTerlibat.getNrp());
                    listTerpilih.add(listSelected);
                }
            }

            if (adapter.personilTerpilih.size()>0){
                hapus();
            }else {
                Toast.makeText(getApplicationContext(), "Tidak ada yg terpilih", Toast.LENGTH_LONG).show();
            }

        }else if (item.getItemId()== android.R.id.home && isActionMode){
            clearActionMode();
            adapter.notifyDataSetChanged();

        }else if (item.getItemId()== android.R.id.home && !isActionMode){
            finish();
        }

        return true;
    }

    public void hapus(){
        PD.show();

        MyCommand myCommand = new MyCommand();

        for(final ListItemPersonilTerlibat daftar : adapter.personilTerpilih){
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jPesan = new JSONObject(response);
                                boolean pesan = jPesan.names().get(0).equals("success");
                                if (pesan){
                                    PD.dismiss();
                                }else{
                                    Toast.makeText(getApplicationContext(),
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
                    PD.dismiss();
                    Toast.makeText(getApplicationContext(),
                            "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("nrp", daftar.getNrp());
                    params.put("id-jadwal", idJadwal);
                    return params;
                }
            };

            myCommand.add(postRequest);
        }

        myCommand.execute();
        PD.dismiss();

    }
}
