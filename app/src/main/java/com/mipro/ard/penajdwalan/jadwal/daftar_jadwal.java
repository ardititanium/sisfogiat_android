package com.mipro.ard.penajdwalan.jadwal;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.kegiatan.KegiatanRecyclerAdapter;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.kegiatan.ListItemKegiatan;
import com.mipro.ard.penajdwalan.jadwal.RecyclerJadwalKegiatan.JadwalRecyclerAdapter;
import com.mipro.ard.penajdwalan.jadwal.RecyclerJadwalKegiatan.ListItemJadwal;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class daftar_jadwal extends AppCompatActivity {
    CalendarView calendarView;
    TextView indikator_tgl, tdk_ada;

    private List<ListItemJadwal> listItemJadwals = new ArrayList<ListItemJadwal>();

    private RecyclerView recyclerView;
    private JadwalRecyclerAdapter adapter;
    String url = "http://"+parser.IP_PUBLIC+"/ditlantas/json/jadwal/view.php?thn=2016&bln=06&tgl=29";

    ProgressDialog PD;
    String params_bln, params_thn, params_tgl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_jadwal);
        indikator_tgl = (TextView) findViewById(R.id.indikator_tgl);
        indikator_tgl.setText(getCurrentDate());
        Log.d("TANGGAL", getCurrentDate());
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String temp_bln = String.valueOf(month);
                params_thn = String.valueOf(year);
                params_bln = namaBulan(temp_bln);
                params_tgl = String.valueOf(dayOfMonth);
                indikator_tgl.setText("Tanggal "+params_tgl+" "+params_bln+" "+params_thn);

                if (temp_bln.length() == 1){
                    temp_bln = "0"+temp_bln;
                }

                if (params_tgl.length() == 1){
                    params_tgl = "0"+params_tgl;
                }

                url = "http://"+parser.IP_PUBLIC+"/ditlantas/json/jadwal/view.php?thn="+params_thn+"&bln="+temp_bln+"&tgl="+params_tgl;
                updateList();
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.rec_calender);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);

        updateList();
        Log.d("URLJSON", url);
    }

    public String namaBulan(String bulan){
        String[] bulan_arr = getResources().getStringArray(R.array.list_bulan);
        String trans_bulan = null;
        int n = Integer.parseInt(bulan);

        switch (bulan){
            case "0":
                trans_bulan = bulan_arr[n];
                break;
            case "1":
                trans_bulan = bulan_arr[n];
                break;
            case "2":
                trans_bulan = bulan_arr[n];
                break;
            case "3":
                trans_bulan = bulan_arr[n];
                break;
            case "4":
                trans_bulan = bulan_arr[n];
                break;
            case "5":
                trans_bulan = bulan_arr[n];
                break;
            case "6":
                trans_bulan = bulan_arr[n];
                break;
            case "7":
                trans_bulan = bulan_arr[n];
                break;
            case "8":
                trans_bulan = bulan_arr[n];
                break;
            case "9":
                trans_bulan = bulan_arr[n];
                break;
            case "10":
                trans_bulan = bulan_arr[n];
                break;
            default:
                trans_bulan = bulan_arr[n];
                break;
        }
        return trans_bulan;
    }

    private void updateList() {


        PD.show();


        adapter = new JadwalRecyclerAdapter(this, listItemJadwals);
        recyclerView.setAdapter(adapter);
        adapter.clearAdapter();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                PD.dismiss();

                try {
                    JSONObject jPesan = response.getJSONObject("data");
                    JSONArray jsonArray = jPesan.getJSONArray("kegiatan");

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject listGiat = jsonArray.getJSONObject(i);
                        ListItemJadwal item = new ListItemJadwal();

                        item.setIdKegiatan(listGiat.getString("idKegiatan"));
                        item.setIdJadwal(listGiat.getString("idJadwalKegiatan"));
                        item.setNamaKegiatan(listGiat.getString("namaKegiatan"));
                        item.setTglMulai(listGiat.getString("tglMulai"));
                        item.setNamaLokasi(listGiat.getString("namaLokasi"));
                        item.setJamMulai(listGiat.getString("jamMulai").substring(0,8));

                        listItemJadwals.add(item);

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
                        "Tidak ada Jadwal Kegiatan", Toast.LENGTH_SHORT).show();


            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToReqQueue(jsonObjReq);

    }


    public String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        String tgl, bln, tahun, bln_real;
        tgl = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        bln = String.valueOf(calendar.get(Calendar.MONTH));
        tahun = String.valueOf(calendar.get(Calendar.YEAR));
        bln_real = namaBulan(bln);

        return "Tanggal "+tgl+" "+bln_real+" "+tahun;

    }
}
