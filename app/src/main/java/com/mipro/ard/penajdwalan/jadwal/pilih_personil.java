package com.mipro.ard.penajdwalan.jadwal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.mipro.ard.penajdwalan.daftar.daftar_kegiatan;
import com.mipro.ard.penajdwalan.jadwal.RecyclerJadwal.pilihPersonil.ListItemPilihPersonil;
import com.mipro.ard.penajdwalan.jadwal.RecyclerJadwal.pilihPersonil.pilihPersonilRecyclerAdapter;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.MyCommand;
import com.mipro.ard.penajdwalan.json_handler.parser;
import com.mipro.ard.penajdwalan.tambah.tambah_agenda;
import com.mipro.ard.penajdwalan.tambah.tambah_str;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pilih_personil extends AppCompatActivity implements View.OnLongClickListener{
    public boolean isActionMode = false;
    TextView counter_tv;
    int counter = 0;
    Toolbar toolbar;
    final String TAG = "Pilih Personil";
    private List<ListItemPilihPersonil> listItemPilihPersonils = new ArrayList<ListItemPilihPersonil>();
    private List<ListItemPilihPersonil> listTerpilih = new ArrayList<ListItemPilihPersonil>();
    RecyclerView recyclerView;
    pilihPersonilRecyclerAdapter adapter;
    private ProgressDialog PD;

    String reqTglMulai, reqJamMulai, reqTglSelesai, reqJamSelesai, nrpTerpilih;
    String urlReq;
    String url      = "http://"+ parser.IP_PUBLIC +"/ditlantas/json/persjadwal/insert.php";

    int jumlah;

    String idJadwal;
    TextView search_et;
    LinearLayout search_wrap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_personil);
        toolbar = (Toolbar) findViewById(R.id.selected_bar);
        setSupportActionBar(toolbar);




        search_et           = (EditText) findViewById(R.id.search_box);
        search_wrap         = (LinearLayout) findViewById(R.id.wrap_search);

        Intent getJadwal    = getIntent();
        idJadwal            = getJadwal.getStringExtra("idJadwal");
        reqTglMulai         = getJadwal.getStringExtra("tglMulai");
        reqJamMulai         = getJadwal.getStringExtra("jamMulai");
        reqTglSelesai       = getJadwal.getStringExtra("tglSelesai");
        reqJamSelesai       = getJadwal.getStringExtra("jamSelesai");



        urlReq   = "http://"+ parser.IP_PUBLIC +"/ditlantas/json/jadwal/pers_tersedia.php?tglMulai="+ reqTglMulai +"&jamMulai="+reqJamMulai+"&jamSelesai="+reqJamSelesai;
        Log.d("Isigetjadwal", urlReq);
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
        addTextListener();

    }

    private void UpdateList(){
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlReq, null, new Response.Listener<JSONObject>() {

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
            for (int i = 0; i < adapter.personilTerpilih.size(); i++){
                ListItemPilihPersonil listSelected = new ListItemPilihPersonil();
                ListItemPilihPersonil listItemPilihPersonil = listItemPilihPersonils.get(i);
                if (listItemPilihPersonil.isSelected() ){
                    listSelected.setNrp(listItemPilihPersonil.getNrp());
                    listTerpilih.add(listSelected);
                }
            }

            if (adapter.personilTerpilih.size()>0){
                save();
            }else {
                Toast.makeText(getApplicationContext(), "Tidak ada yg terpilih", Toast.LENGTH_LONG).show();
            }

        }
        return super.onOptionsItemSelected(item);
    }


    public void save(){
        PD.show();
        
        MyCommand myCommand = new MyCommand();

        for(final ListItemPilihPersonil daftar : adapter.personilTerpilih){
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
        savedSucced();

    }

    public void addTextListener() {
        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence query, int start, int before, int count) {
                query  = query.toString().toLowerCase();

                final List<ListItemPilihPersonil> filterList = new ArrayList<>();
                for (int i = 0; i < listItemPilihPersonils.size(); i++){

                    final String nama_q     = listItemPilihPersonils.get(i).getNama().toLowerCase();
                    final String nrp_q      = listItemPilihPersonils.get(i).getNrp().toLowerCase();
                    final String pangkat_q  = listItemPilihPersonils.get(i).getPangkat().toLowerCase();

                    final String nama     = listItemPilihPersonils.get(i).getNama();
                    final String nrp      = listItemPilihPersonils.get(i).getNrp();
                    final String pangkat  = listItemPilihPersonils.get(i).getPangkat();

                    if (nama_q.contains(query) || nrp_q.contains(query) || pangkat_q.contains(query)){
                        ListItemPilihPersonil persFilter = new ListItemPilihPersonil();
                        persFilter.setNama(nama);
                        persFilter.setNrp(nrp);
                        persFilter.setPangkat(pangkat);
                        filterList.add(persFilter);
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(pilih_personil.this));
                adapter = new pilihPersonilRecyclerAdapter(pilih_personil.this, filterList);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }



            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void savedSucced(){
        new MaterialDialog.Builder(pilih_personil.this)
                .title("Personil Terpilih Telah disimpan")
                .positiveText("Lanjut")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(getApplicationContext(), tambah_agenda.class);
                        intent.putExtra("idJadwal", idJadwal);
                        intent.putExtra("tglMulai", reqTglMulai);
                        intent.putExtra("jamMulai", reqJamMulai);
                        intent.putExtra("tglSelesai", reqTglSelesai);
                        intent.putExtra("jamSelesai", reqJamSelesai);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }




}
