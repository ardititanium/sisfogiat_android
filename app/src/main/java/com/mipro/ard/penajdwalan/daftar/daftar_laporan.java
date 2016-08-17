package com.mipro.ard.penajdwalan.daftar;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.Laporan.LaporanRecyclerAdapter;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.Laporan.ListItemLaporan;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class daftar_laporan extends AppCompatActivity {

    RecyclerView recyclerView;
    LaporanRecyclerAdapter adapter;
    List<ListItemLaporan> listItemLaporan = new ArrayList<ListItemLaporan>();
    ProgressDialog PD;
    TextView priode;
    ImageButton back;

    int tahun_1, tahun_2, tahun_3;
    String thn_1, thn_2, thn_3, title_bar, param_tahun, param_bulan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_laporan);

        priode = (TextView) findViewById(R.id.priode_title);
        back = (ImageButton) findViewById(R.id.back_btn_d_jadwal);

        initView();
        pilihPriode();

        priode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihPriode();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView(){
        recyclerView = (RecyclerView) findViewById(R.id.daftar_laporan);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new LaporanRecyclerAdapter(getApplicationContext(), listItemLaporan);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
    }

    private void pilihPriode() {
        thn_1 = String.valueOf(getTahun(-2));
        thn_2 = String.valueOf(getTahun(-1));
        thn_3 = String.valueOf(getTahun(0));

        String[] listTahun = {thn_1, thn_2, thn_3};
        new MaterialDialog.Builder(daftar_laporan.this)
                .title("Pilih Bulan...")
                .items(listTahun)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        switch (which){
                            case 0:
                                param_tahun = text.toString();
                                showBulan(param_tahun);
                                break;
                            case 1:
                                param_tahun = text.toString();
                                showBulan(param_tahun);
                                break;
                            case 2:
                                param_tahun = text.toString();
                                showBulan(param_tahun);
                                break;
                        }
                    }
                })
                .show();
    }

    private static int getTahun(int i){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, i);
        return calendar.get(Calendar.YEAR);
    }

    private void loadLaporan(String tahun, String bulan){
        String url = "http://"+ parser.IP_PUBLIC +"/ditlantas/json/jadwal/view_report.php?thn="+tahun+"&bln="+bulan;
        Log.d("URLLaporan", url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject   = response.getJSONObject("data");
                            JSONArray status_obj    = jsonObject.getJSONArray("status");
                            JSONObject status_arr   = status_obj.getJSONObject(0);
                            String status           = status_arr.getString("pesan");
                            if (status.equals("true")){
                                JSONArray jsonArray     = jsonObject.getJSONArray("laporan");
                                for (int i = 0; i < jsonArray.length(); i++){
                                    JSONObject laporan = jsonArray.getJSONObject(i);
                                    ListItemLaporan item  = new ListItemLaporan();
                                    item.setIdJadwal(laporan.getString("idJadwalKegiatan"));
                                    item.setIdKegiatan(laporan.getString("idKegiatan"));
                                    item.setNamaKegiatan(laporan.getString("namaKegiatan"));
                                    String tglMulai     = laporan.getString("tglMulai");
                                    String jamMulai     = laporan.getString("jamMulai").substring(0,5);
                                    String tglSelesai   = laporan.getString("tglSelesai");
                                    String jamSelesai   = laporan.getString("jamSelesai").substring(0,5);
                                    String waktu        = tglMulai + ", " + jamMulai + " - " + tglSelesai + ", " + jamSelesai;
                                    item.setWaktu(waktu);
                                    item.setNamaSatuan(laporan.getString("namaSatuan"));
                                    item.setPeserta(laporan.getString("jumlahPers") + " Orang");
                                    listItemLaporan.add(item);
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), "Tidak Terdapat Jadwal pada Periode ini", Toast.LENGTH_LONG).show();
                            }


                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MyApplication.getInstance().addToReqQueue(jsonObjectRequest);
    }


    private void showBulan(final String tahun) {
        new MaterialDialog.Builder(daftar_laporan.this)
                .title("Pilih Bulan...")
                .items(R.array.list_bulan)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        for (int i=0; i < 11; i++){
                            if (which == i){
                                title_bar = text +" "+ tahun;
                                priode.setText("Periode " + title_bar);
                                String bulan = String.valueOf(which+1);
                                String params;
                                if (bulan.length() == 1){
                                params = "0"+bulan;
                                }else {
                                params = bulan;
                                }

                                adapter.clearAdapter();
                                loadLaporan(tahun,params);
                            }
                        }
                    }
                })
                .show();

    }
}
