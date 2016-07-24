package com.mipro.ard.penajdwalan.tambah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewDebug;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mipro.ard.penajdwalan.MainActivity;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class tambah_personil extends AppCompatActivity  {
    String url = "http://" + parser.IP_PUBLIC + "/ditlantas/json/personil/insert.php";
    String mNrp, mNama, mJk, mPangkat, mSatuan, mPass, mAkses;
    EditText et_nrp, et_nama, et_pass;
    TextView title_bar;
    ImageButton m_done_btn, m_back_btn;
    Spinner jk_spin, pangkat_spin, satuan_spin, akses_spin;
    ArrayAdapter<CharSequence> jk_adapter, pangkat_adapter, satuan_adapter, akses_adapter;
    ProgressDialog PD;

    private ArrayList<String> namaSatuan, idSatuan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_personil);





        namaSatuan = new ArrayList<String>();
        idSatuan = new ArrayList<String>();


        title_bar = (TextView) findViewById(R.id.bar_title);
        m_back_btn = (ImageButton) findViewById(R.id.kembali_btn);
        m_done_btn = (ImageButton) findViewById(R.id.done_btn);

        title_bar.setText("TAMBAH PERSONIL");

        m_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(back_home);
            }
        });

        m_done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });




        initViews();


    }

    void initViews() {
        jk_spin = (Spinner) findViewById(R.id.pers_jk);
        pangkat_spin = (Spinner) findViewById(R.id.pers_pangkat);
        satuan_spin = (Spinner) findViewById(R.id.pers_satuan);
        akses_spin = (Spinner) findViewById(R.id.pers_akses);

        jk_adapter = ArrayAdapter.createFromResource(this,R.array.jk, android.R.layout.simple_spinner_item);
        jk_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        pangkat_adapter = ArrayAdapter.createFromResource(this, R.array.pangkat, android.R.layout.simple_spinner_item);
        pangkat_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);


        akses_adapter = ArrayAdapter.createFromResource(this, R.array.akses, android.R.layout.simple_spinner_item);
        akses_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        jk_spin.setAdapter(jk_adapter);
        pangkat_spin.setAdapter(pangkat_adapter);
        akses_spin.setAdapter(akses_adapter);

        et_nrp = (EditText) findViewById(R.id.pers_nrp);
        et_nama = (EditText) findViewById(R.id.pers_nama);
        et_pass = (EditText) findViewById(R.id.pers_pas);

        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);

        getData();


    }

    public void insert() {

        PD.show();
        mNrp = et_nrp.getText().toString();
        mNama = et_nama.getText().toString();
        mJk = jk_spin.getSelectedItem().toString();
        mPangkat = pangkat_spin.getSelectedItem().toString();
        mPass = et_pass.getText().toString();
        mAkses = akses_spin.getSelectedItem().toString();

        int satuanPos = satuan_spin.getSelectedItemPosition() + 1;
        if(String.valueOf(satuanPos).length() == 1) {
            String satuanID = "S0" + String.valueOf(satuanPos);
            mSatuan = satuanID;
        }else{
            String satuanID = "S" + String.valueOf(satuanPos);
            mSatuan = satuanID;
        }


        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.names().get(0).equals("pesan")){
                                PD.dismiss();
                                String jPesan = jsonObject.getString("pesan");
                                Toast.makeText(getApplicationContext(), jPesan, Toast.LENGTH_LONG).show();
                            }else {
                                PD.dismiss();
                                et_nrp.setText("");
                                et_nama.setText("");
                                jk_spin.setSelection(0);
                                pangkat_spin.setSelection(0);
                                satuan_spin.setSelection(0);
                                et_pass.setText("");
                                akses_spin.setSelection(0);
                                Toast.makeText(getApplicationContext(),
                                        "Data Inserted Successfully",
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
                        "failed to insert", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nrp", mNrp);
                params.put("nama", mNama);
                params.put("kelamin", mJk);
                params.put("satuan", mSatuan);
                params.put("pangkat", mPangkat);
                params.put("password", mPass);
                params.put("akses", mAkses);


                return params;
            }
        };

        // Adding request to request queue
        MyApplication.getInstance().addToReqQueue(postRequest);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu_bar, menu);
        return true;

    }

    public void getData(){
        StringRequest stringRequest = new StringRequest(parser.DATA_SATLANATAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {

                            j = new JSONObject(response);
                            JSONArray result = j.getJSONArray(parser.SATLANTAS_JSON);
                            getSatlantas(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void getSatlantas(JSONArray j){
        for (int i =0; i<j.length(); i++){
            try {
                JSONObject json = j.getJSONObject(i);
                namaSatuan.add(json.getString(parser.NAMA_SATUAN));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        satuan_spin.setAdapter(new ArrayAdapter<String>(tambah_personil.this, android.R.layout.simple_spinner_dropdown_item, namaSatuan));

    }





}
