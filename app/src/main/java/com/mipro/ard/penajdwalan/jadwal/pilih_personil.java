package com.mipro.ard.penajdwalan.jadwal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.personil.ListItemPersonil;
import com.mipro.ard.penajdwalan.daftar.daftar_kategori;
import com.mipro.ard.penajdwalan.daftar.daftar_kegiatan;
import com.mipro.ard.penajdwalan.jadwal.RecyclerJadwal.pilihPersonil.ListItemPilihPersonil;
import com.mipro.ard.penajdwalan.jadwal.RecyclerJadwal.pilihPersonil.ListRowViewHolderPilihPersonil;
import com.mipro.ard.penajdwalan.jadwal.RecyclerJadwal.pilihPersonil.pilihPersonilRecyclerAdapter;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pilih_personil extends AppCompatActivity implements View.OnLongClickListener{
    public boolean isActionMode = false;
    TextView counter_tv, hideIdJadwal_tv;
    int counter = 0;
    Toolbar toolbar;
    final String TAG = "Pilih Personil";
    private List<ListItemPilihPersonil> listItemPilihPersonils = new ArrayList<ListItemPilihPersonil>();
    RecyclerView recyclerView;
    pilihPersonilRecyclerAdapter adapter;
    private ProgressDialog PD;
    public ImageButton done_btn;
    StringBuffer stringBuffer = null;
    String[] arrPers = null;
    String url = "http://"+ parser.IP_PUBLIC +"/ditlantas/json/persjadwal/insert.php";
    int jumlah;

    Context context;
    String idJadwal, idjadwaltosave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_personil);
        toolbar = (Toolbar) findViewById(R.id.selected_bar);
        setSupportActionBar(toolbar);

        Intent getJadwal = getIntent();
        idJadwal = getJadwal.getStringExtra("idJadwal");

        recyclerView = (RecyclerView) findViewById(R.id.rec_pilih_personil);

        final LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);

        adapter = new pilihPersonilRecyclerAdapter(this, listItemPilihPersonils);
        recyclerView.setAdapter(adapter);
        adapter.clearAdapter();
        counter_tv = (TextView) findViewById(R.id.counterTextPersonil);

        UpdateList();

    }

    private void UpdateList(){


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                parser.DATA_PERSONIL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                PD.dismiss();
                Log.d(TAG, response.toString());
                try {
                    JSONObject jPesan = response.getJSONObject("data");
                    JSONArray jsonArray = jPesan.getJSONArray("personil");
                    jumlah = jsonArray.length();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_selected, menu);
        return true;
    }

    @Override
    public boolean onLongClick(View v) {
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_mode_selected);
        isActionMode = true;
        adapter.notifyDataSetChanged();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return false;
    }

    public void preparedSelection(View view, int position){
            if(((CheckBox)view).isChecked()){
                adapter.personilTerpilih.add(listItemPilihPersonils.get(position));
                counter = counter+1;
                Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
                updateCounter(counter);

            }else {
                adapter.personilTerpilih.remove(listItemPilihPersonils.get(position));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.done_selected_mode){
            stringBuffer = new StringBuffer();

            for (ListItemPilihPersonil daftar : adapter.personilTerpilih){
                stringBuffer.append(daftar.getNrp());
                stringBuffer.append(" ");
                String selPers = stringBuffer.toString();
                arrPers = selPers.split("\\s+");
            }

            if (adapter.personilTerpilih.size()>0){
                save();
                new MaterialDialog.Builder(pilih_personil.this)
                        .content("Jadwal Telah diatur")
                        .positiveText("Lihat Daftar")
                        .backgroundColorRes(R.color.success)
                        .positiveColorRes(R.color.mdtp_white)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Intent backList = new Intent(pilih_personil.this, daftar_kegiatan.class);
                                startActivity(backList);
                            }
                        })
                        .show();




            }else {
                Toast.makeText(getApplicationContext(), "Tidak ada yg terpilih", Toast.LENGTH_LONG).show();
            }

        }
        return super.onOptionsItemSelected(item);
    }


    public void save(){
        PD.show();
        int i;
        for(i = 0; i < arrPers.length; i++){
            final int finalI = i;
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

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
                        params.put("nrp", arrPers[finalI]);
                        params.put("id-jadwal", idJadwal);
                    return params;
                }
            };

            MyApplication.getInstance().addToReqQueue(postRequest);
        }
        PD.dismiss();

    }




}
