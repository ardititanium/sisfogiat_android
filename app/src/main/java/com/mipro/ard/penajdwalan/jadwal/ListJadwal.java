package com.mipro.ard.penajdwalan.jadwal;

import android.content.Context;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.daftar.daftar_kegiatan;
import com.mipro.ard.penajdwalan.jadwal.RecyclerJadwalKegiatan.JadwalRecyclerAdapter;
import com.mipro.ard.penajdwalan.jadwal.RecyclerJadwalKegiatan.ListItemJadwal;
import com.mipro.ard.penajdwalan.jadwal.RecylerNavC.ListItemTanggal;
import com.mipro.ard.penajdwalan.jadwal.RecylerNavC.ListViewHolderTanggal;
import com.mipro.ard.penajdwalan.jadwal.RecylerNavC.NavRecyclerAdapter;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListJadwal extends AppCompatActivity {
    public Context ctx;
    RecyclerView navRec, contentRec;
    NavRecyclerAdapter navAdapter;
    JadwalRecyclerAdapter conAdapter;
    List<ListItemJadwal> jadwalList = new ArrayList<>();
    List<ListItemTanggal> tanggalList = new ArrayList<>();
    ImageButton back_btn, add_btn;


    Calendar calendar = Calendar.getInstance();
    int tahunsek = calendar.get(Calendar.YEAR);
    int bulan = calendar.get(Calendar.MONTH);
    final String tahun = String.valueOf(calendar.get(Calendar.YEAR));
    String[] daftarBulan;
    List<String> bulanArr;
    String bulanSekarang;
    String url;

    public TextView pilihBln;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_jadwal);

        pilihBln = (TextView) findViewById(R.id.pilih_bulan);
        back_btn = (ImageButton) findViewById(R.id.kembali_btn);
        add_btn = (ImageButton) findViewById(R.id.add_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intGiat = new Intent(ListJadwal.this, daftar_kegiatan.class);
                startActivity(intGiat);
            }
        });



        daftarBulan = getResources().getStringArray(R.array.list_bulan);
        bulanArr = Arrays.asList(daftarBulan).subList(bulan, 12);
        bulanSekarang = Arrays.asList(daftarBulan).get(bulan);


        navRec      = (RecyclerView) findViewById(R.id.nav_rec);
        contentRec  = (RecyclerView) findViewById(R.id.con_rec);

        navAdapter = new NavRecyclerAdapter(getApplicationContext(), tanggalList);
        navRec.setAdapter(navAdapter);

        conAdapter = new JadwalRecyclerAdapter(getApplicationContext(), jadwalList);
        contentRec.setAdapter(conAdapter);

        final LinearLayoutManager layoutManagerCon = new LinearLayoutManager(this);
        contentRec.setLayoutManager(layoutManagerCon);

        final LinearLayoutManager layoutManagerNav = new LinearLayoutManager(this);
        navRec.setLayoutManager(layoutManagerNav);



        setNav(String.valueOf(tahunsek),0, String.valueOf(bulan+1));
        setOnLoad();
        setBulanOnLoad();

        pilihBln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBulan();


            }
        });


    }

    private void setBulanOnLoad() {
        pilihBln.setText(bulanSekarang + "" + tahun);
    }

    private void showBulan() {
        new MaterialDialog.Builder(ListJadwal.this)
                .title("Pilih Bulan...")
                .items(bulanArr)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        for (int i=0; i < 12-bulan; i++){
                            if (which == i){
                                pilihBln.setText(text +" "+ tahun);
                                navAdapter.clearAdapter();

                                int params = which + bulan + 1;
                                String bulannum = String.valueOf(params);
                                setNav("2016",which, bulannum);

                                conAdapter.clearAdapter();
                                setContentByBulan(params);
                            }
                        }
                    }
                })
                .show();

    }


    public void setNav(String thnFix, int blnFix, String blnNum){

        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        String sdate1 = blnFix+"-1-"+thnFix+" 00:00:00";
        try {
            Date date = df.parse(sdate1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int days = calendar.getActualMaximum(calendar.DAY_OF_MONTH);


            for (int i = 0; i < days; i++){
                ListItemTanggal tgl = new ListItemTanggal();
                tgl.setTanggal(String.valueOf(i+1));
                tgl.setBulan(bulanArr.get(blnFix).substring(0,3));
                tgl.setBln_num(blnNum);
                tanggalList.add(tgl);
                navAdapter.notifyDataSetChanged();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public void setOnLoad(){
        String bulanAwal = String.valueOf(bulan+1);
        url = "http://"+ parser.IP_PUBLIC+"/ditlantas/json/jadwal/view_bulan.php?thn="+ tahunsek +"&bln="+bulanAwal;
        setContent();
    }

    public void setContentByBulan(int bulan){
        url = "http://"+ parser.IP_PUBLIC+"/ditlantas/json/jadwal/view_bulan.php?thn="+ tahunsek +"&bln="+bulan;
        Log.d("isiURLbyBulan", url);
        setContent();
    }

    public void setContent(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jPesan = null;
                        try {
                            jPesan = response.getJSONObject("data");
                            JSONArray jsonArray = jPesan.getJSONArray("kegiatan");
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject listJad = jsonArray.getJSONObject(i);
                                ListItemJadwal item = new ListItemJadwal();
                                item.setIdJadwal(listJad.getString("idJadwalKegiatan"));
                                item.setIdKegiatan(listJad.getString("idKegiatan"));
                                item.setNamaKegiatan(listJad.getString("namaKegiatan"));
                                item.setNamaLokasi(listJad.getString("namaLokasi"));
                                item.setTglMulai(listJad.getString("tglMulai"));
                                item.setJamMulai(listJad.getString("jamMulai").substring(0,5));
                                jadwalList.add(item);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        conAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MyApplication.getInstance().addToReqQueue(jsonObjectRequest);
    }

    public String setTgl(String tgl, String bulanum){
        url = "http://"+ parser.IP_PUBLIC+"/ditlantas/json/jadwal/view_bulan.php?thn="+ tahunsek +"&bln="+bulanum+"&tgl="+tgl;
        return url;
    }

    public void clickListener(){

    }


}
