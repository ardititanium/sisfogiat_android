package com.mipro.ard.penajdwalan.jadwal;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.str.StrList;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.str.StrAdapter;
import com.mipro.ard.penajdwalan.jadwal.RecylerPersTerlibat.StrFull_fragment;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class lihat_str extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<StrList> strLists = new ArrayList<>();
    ProgressDialog PD;
    StrAdapter adapter;
    String url, idJadwal;
    TextView idJadwal_tv, jumlah_tv, title_bar;
    ImageButton back_btn, unduh_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_str);

        recyclerView = (RecyclerView) findViewById(R.id.str_rec_view);
        idJadwal_tv = (TextView) findViewById(R.id.idJadwal_str);
        jumlah_tv = (TextView) findViewById(R.id.jumlah_str);
        title_bar = (TextView) findViewById(R.id.bar_title);
        back_btn = (ImageButton) findViewById(R.id.kembali_btn);
        unduh_btn = (ImageButton) findViewById(R.id.down_str);

        adapter = new StrAdapter(strLists, this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        Bundle getId = getIntent().getExtras();
        idJadwal = getId.getString("idJadwal");
        idJadwal_tv.setText(idJadwal);

        title_bar.setText("Surat Perintah");

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.addOnItemTouchListener(new StrAdapter.RecylerTouchListener(getApplicationContext(), recyclerView, new StrAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("strLists", strLists);
                bundle.putInt("position", position);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                StrFull_fragment newFragment = StrFull_fragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slide");

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        initView();
    }

    public void initView(){
        PD = new ProgressDialog(this);
        PD.setMessage("Sedang Memuat Surat Perintah");
        PD.setCancelable(false);
        PD.show();

        url = "http://"+ parser.IP_PUBLIC +"/ditlantas/json/surat/view_str.php?idj="+idJadwal;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            PD.dismiss();
                            JSONObject jsonObject = response.getJSONObject("data");
                            JSONArray jsonArray = jsonObject.getJSONArray("str");
                            for (int i=0; i < jsonArray.length(); i++){
                                JSONObject isi = jsonArray.getJSONObject(i);
                                StrList item = new StrList();
                                String path = isi.getString("path");
                                item.setIdJadwal(isi.getString("idJadwalKegiatan"));
                                item.setNamaStr(isi.getString("namaStr"));
                                item.setUrlStr("http://"+ parser.IP_PUBLIC+"/ditlantas/"+path);
                                strLists.add(item);
                            }
                            jumlah_tv.setText(String.valueOf(jsonArray.length()));
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Eror2", error.toString());
            }
        });
        MyApplication.getInstance().addToReqQueue(jsonObjectRequest);
    }

    public String hapusStr(){
        return idJadwal;

    }
}
