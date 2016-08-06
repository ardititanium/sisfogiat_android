package com.mipro.ard.penajdwalan.tambah;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mipro.ard.penajdwalan.MainActivity;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.agenda.Agenda;
import com.mipro.ard.penajdwalan.RecyclerHandler.l.agenda.AgendaAdapter;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class tambah_agenda extends AppCompatActivity {

    public EditText desk_et, ket_et;
    public TextView jamMulai_et, jamSelesai_et;
    public Button add_to_list;

    RecyclerView recyclerView;
    AgendaAdapter adapter;
    List<Agenda> agendaList = new ArrayList<>();
    int jam, menit;

    TextView title_bar;
    ImageButton m_done_btn, m_back_btn;

    ProgressDialog PD;

    String url = "http://"+ parser.IP_PUBLIC +"/ditlantas/json/agenda/insert.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_agenda);

        desk_et         = (EditText) findViewById(R.id.desk_agenda_et);
        ket_et          = (EditText) findViewById(R.id.ket_agenda_et);
        jamMulai_et     = (TextView) findViewById(R.id.jamMulai_agenda_et);
        jamSelesai_et   = (TextView) findViewById(R.id.jamSelesai_agenda_et);
        add_to_list     = (Button) findViewById(R.id.add_agenda_btn);


        PD = new ProgressDialog(tambah_agenda.this);
        PD.setMessage("Sedang Menyimpan...");
        PD.setCancelable(false);

        recyclerView = (RecyclerView) findViewById(R.id.rec_agenda_temp);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AgendaAdapter(agendaList, tambah_agenda.this);
        recyclerView.setAdapter(adapter);

        title_bar = (TextView) findViewById(R.id.bar_title);
        m_back_btn = (ImageButton) findViewById(R.id.kembali_btn);
        m_done_btn = (ImageButton) findViewById(R.id.search_btn);

        title_bar.setText("TAMBAH AGENDA");

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
                if(agendaList.size() > 0){
                    save();
                    PD.dismiss();
                }else {
                    new MaterialDialog.Builder(tambah_agenda.this)
                            .title("Opss!!")
                            .content("Silahkan Masukkan Agenda Terlebih dahulu sebelum menyimpan")
                            .negativeText("OK")
                            .show();
                }
            }
        });


        jamMulai_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setJamMulai();
            }
        });

        jamSelesai_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setJamSelesai();
            }
        });




        add_to_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList();

            }
        });




    }

    private void addToList() {
        long selisihJam = 0, selisihMenit = 0;
        String desk_str, ket_str, mulai_str, selesai_str, durasi_str, mulai_temp, selesai_temp;
        mulai_temp   = String.valueOf(jamMulai_et.getText());
        selesai_temp = String.valueOf(jamSelesai_et.getText());

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date mulai_time, selesai_time;
        try {
            mulai_time      = new Time(df.parse(mulai_temp).getTime());
            selesai_time    = new Time(df.parse(selesai_temp).getTime());
            long diff       = mulai_time.getTime() - selesai_time.getTime();
            long difSec     = diff / 1000 % 60;
            selisihMenit    = diff / (60 * 1000) % 60;
            selisihJam      = diff / (60 * 60 * 1000);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        desk_str    = desk_et.getText().toString();
        ket_str     = ket_et.getText().toString();
        mulai_str   = mulai_temp;
        selesai_str = selesai_temp;
        if (String.valueOf(selisihJam).substring(0,1).equals("0")){
            durasi_str = String.valueOf(selisihMenit)+ " Menit";
        }else if(String.valueOf(selisihMenit).equals("0")){
            durasi_str = String.valueOf(selisihJam) + " Jam";
        }else{
            durasi_str = String.valueOf(selisihJam) + " Jam "+ selisihMenit + " Menit";
        }

        Agenda item = new Agenda(desk_str, mulai_str, selesai_str, ket_str, durasi_str.substring(1));
        agendaList.add(item);
        adapter.notifyDataSetChanged();

        desk_et.setText("");
        ket_et.setText("");
        jamMulai_et.setText("Jam Mulai");
        jamSelesai_et.setText("Jam Selesai");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu_bar, menu);
        return true;

    }



    public void setJamMulai(){
        final Calendar c = Calendar.getInstance();
        jam = c.get(Calendar.HOUR_OF_DAY);
        menit = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String formatJam = hourOfDay + ":" + minute;
                        try {
                            Date date = new SimpleDateFormat("HH:mm").parse(formatJam);
                            String jamFix = new SimpleDateFormat("HH:mm:ss.S").format(date).substring(0,5);

                            jamMulai_et.setText(jamFix);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                }, jam, menit, true);
        timePickerDialog.show();
    }

    public void setJamSelesai(){
        final Calendar c = Calendar.getInstance();
        jam = c.get(Calendar.HOUR_OF_DAY);
        menit = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String formatJam = hourOfDay + ":" + minute;
                        try {
                            Date date = new SimpleDateFormat("HH:mm").parse(formatJam);
                            String jamFix = new SimpleDateFormat("HH:mm:ss.S").format(date).substring(0,5);

                            jamSelesai_et.setText(jamFix);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                }, jam, menit, true);
        timePickerDialog.show();
    }

    public void save(){
        PD.show();
        int i;
        for (i = 0; i < agendaList.size(); i++) {
            final int finalI = i;
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jPesan = new JSONObject(response);
                                boolean pesan = jPesan.names().get(0).equals("success");
                                if (pesan == true) {

                                    Toast.makeText(getApplicationContext(),
                                            "Data Kategori Berhasil di Simpan",
                                            Toast.LENGTH_SHORT).show();
                                } else if (pesan == false) {
                                    Toast.makeText(getApplicationContext(),
                                            "Terjadi Kesalahan",
                                            Toast.LENGTH_SHORT).show();
                                }

                                Log.d("apaResponna", response);

                            } catch (JSONException e) {
                                Log.d("apanaEror", String.valueOf(e));
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),
                            "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    String desk_fix = agendaList.get(finalI).getDeskripsi();
                    String ket_fix = agendaList.get(finalI).getKeterangan();
                    String mulai_fix = agendaList.get(finalI).getJamMulai();
                    String selesai_fix = agendaList.get(finalI).getJamSelesai();
                    String durasi_fix = agendaList.get(finalI).getDurasi();

                    params.put("idjadwal", "0031");
                    params.put("desk", desk_fix);
                    params.put("mulai", mulai_fix);
                    params.put("selesai", selesai_fix);
                    params.put("durasi", durasi_fix);
                    params.put("ket", ket_fix);
                    return params;
                }
            };
            MyApplication.getInstance().addToReqQueue(postRequest);
        }
    }

    public void showDialog(Context context){
        new MaterialDialog.Builder(context)
                .title("Pilih...")
                .items(R.array.dialog_menu1)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        switch (which){
                            case 0:
                                break;
                            case 1:
                                desk_et.setText(agendaList.get(0).getDeskripsi());
                                break;
                        }
                    }
                }).show();
    }

}
