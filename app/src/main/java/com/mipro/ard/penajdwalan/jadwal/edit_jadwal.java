package com.mipro.ard.penajdwalan.jadwal;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mipro.ard.penajdwalan.MainActivity;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;
import com.mipro.ard.penajdwalan.tambah.tambah_str;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class edit_jadwal extends AppCompatActivity implements View.OnClickListener{
    String url = "http://"+ parser.IP_PUBLIC +"/ditlantas/json/jadwal/edit.php";
    TextView id_giat_tv, nama_giat_tv;
    Bundle getData;
    TextView tgl_mulai_et, jam_mulai_et, tgl_selesai_et, jam_selesai_et, idJadwal_et;
    int tahun, bulan, tgl, jam, menit, format_jam;
    ImageButton done_btn, back_btn;
    String mId_giat, mId_jadwal, mTglMulai, mTglSelesai, mJamMulai, mJamSelesai;
    ProgressDialog PD;
    String idJadwal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atur_jadwal);

        getData = getIntent().getExtras();
        id_giat_tv = (TextView) findViewById(R.id.det_id_jadwal);
        nama_giat_tv = (TextView) findViewById(R.id.det_nama_jadwal);
        idJadwal_et = (TextView) findViewById(R.id.id_jadwal);

        tgl_mulai_et = (TextView) findViewById(R.id.tgl_mulai);
        jam_mulai_et = (TextView) findViewById(R.id.jamMulai);
        tgl_selesai_et = (TextView) findViewById(R.id.tgl_selesai);
        jam_selesai_et= (TextView) findViewById(R.id.jam_selesai);
        done_btn = (ImageButton) findViewById(R.id.done_btn_jadwal);
        back_btn = (ImageButton) findViewById(R.id.back_btn_jadwal);

        idJadwal_et.setText("EDIT JADWAL");

        id_giat_tv.setText(ambilIntent("id_giat"));
        nama_giat_tv.setText(ambilIntent("nama_giat"));
        jam_mulai_et.setText(ambilIntent("jam_mulai"));
        jam_selesai_et.setText(ambilIntent("jam_selesai"));


        SimpleDateFormat myd    = new SimpleDateFormat("dd - MMM - yyyy");
        SimpleDateFormat df     = new SimpleDateFormat("yyyy-MM-dd");


        try {
            String tglMulai_con         = df.format(myd.parse(ambilIntent("tgl_mulai")));
            String tglSelesai_con         = df.format(myd.parse(ambilIntent("tgl_selesai")));

            tgl_mulai_et.setText(tglMulai_con);
            tgl_selesai_et.setText(tglSelesai_con);

        } catch (ParseException e) {
            e.printStackTrace();
        }





        tgl_mulai_et.setOnClickListener(this);
        jam_mulai_et.setOnClickListener(this);
        tgl_selesai_et.setOnClickListener(this);
        jam_selesai_et.setOnClickListener(this);
        done_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);

        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);

//        setID();


       }



    String ambilIntent(String giat){
        giat = String.valueOf(getData.getCharSequence(giat));
        String convert = WordUtils.capitalize(giat);
        return convert;
    }

    @Override
    public void onClick(View v) {
            if(v == tgl_mulai_et){
                setTglMulai();
            }else if(v == tgl_selesai_et){
                setTglSelesai();
            }else if(v == jam_mulai_et){
                setJamMulai();
            }else if (v == jam_selesai_et){
                setJamSelesai();

            }else if(v == done_btn){
                insert();
            }else if(v == back_btn){
                showAlert();
            }
    }

    private void showAlert() {
        new MaterialDialog.Builder(edit_jadwal.this)
                .title("Anda Yakin?")
                .content("Semua perubahan, akan terhapus")
                .negativeText("TIDAK")
                .positiveText("YA")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                }).show();
    }

    public void setTglMulai(){
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Makassar");
        final Calendar c = Calendar.getInstance(timeZone);
        tahun = c.get(Calendar.YEAR);
        bulan = c.get(Calendar.MONTH);
        tgl = c.get(Calendar.DAY_OF_MONTH);



        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String formatTgl = year + "-" + String.valueOf(monthOfYear+1) + "-" + dayOfMonth;
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(formatTgl);
                    String dateFix = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    tgl_mulai_et.setText(dateFix);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }, tahun, bulan, tgl);

        datePickerDialog.show();

    }

    public void setTglSelesai(){
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Makassar");
        final Calendar c = Calendar.getInstance(timeZone);
        tahun = c.get(Calendar.YEAR);
        bulan = c.get(Calendar.MONTH);
        tgl = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String formatTgl = year + "-" + String.valueOf(monthOfYear+1) + "-" + dayOfMonth;
                        try {
                            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(formatTgl);
                            String dateFix = new SimpleDateFormat("yyyy-MM-dd").format(date);
                            tgl_selesai_et.setText(dateFix);
                            cekTanggal();


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, tahun, bulan, tgl);

        datePickerDialog.show();

    }


    public void setJamMulai(){
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Makassar");
        final Calendar c = Calendar.getInstance(timeZone);
        jam = c.get(Calendar.HOUR_OF_DAY);
        menit = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String formatJam = hourOfDay + ":" + minute;
                        try {
                            Date date = new SimpleDateFormat("HH:mm").parse(formatJam);
                            String jamFix = new SimpleDateFormat("HH:mm:ss.S").format(date);

                            jam_mulai_et.setText(jamFix);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                }, jam, menit, true);
        timePickerDialog.show();
    }

    public void setJamSelesai(){
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Makassar");
        final Calendar c = Calendar.getInstance(timeZone);
        jam = c.get(Calendar.HOUR_OF_DAY);
        menit = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String formatJam = hourOfDay + ":" + minute;
                        try {
                            Date date = new SimpleDateFormat("HH:mm").parse(formatJam);
                            String jamFix = new SimpleDateFormat("HH:mm:ss.S").format(date);
                            jam_selesai_et.setText(jamFix);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                }, jam, menit, true);
        timePickerDialog.show();
    }


    public void cekTanggal(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Makassar"));
        String mulaiString = tgl_mulai_et.getText().toString();
        String selesaiString = tgl_selesai_et.getText().toString();

        Date mulaiDate = null;
        Date selesaiDate = null;
        try {
            mulaiDate = formatter.parse(mulaiString);
            selesaiDate = formatter.parse(selesaiString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        String jamMulai = jam_mulai_et.getText().toString();
        String jamSelesai = jam_selesai_et.getText().toString();


        String hasil = String.valueOf(mulaiDate.compareTo(selesaiDate));

        if(hasil.equals("1")){
            new MaterialDialog.Builder(edit_jadwal.this)
                    .title("Terjadi Kesalahan")
                    .content("Pastikan Tanggal Selesai tidak lebih kecil dari Tanggal Mulai")
                    .positiveText("Ulangi")
                    .show();
        }else if (hasil.equals("0") && (jamMulai.equals("Jam Mulai")) && (jamSelesai.equals("Jam Selesai"))){
            new MaterialDialog.Builder(edit_jadwal.this)
                    .title("Terjadi Kesalahan")
                    .content("Pastikan Anda Telah Mengisi Jam Mulai Sebelum Mengubah Tanggal Selesai")
                    .positiveText("OKE")
                    .show();

        }
    }


    public void cekJam(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String mulaiString = tgl_mulai_et.getText().toString();
        String selesaiString = tgl_selesai_et.getText().toString();

        Date mulaiDate = null;
        Date selesaiDate = null;
        try {
            mulaiDate = formatter.parse(mulaiString);
            selesaiDate = formatter.parse(selesaiString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String hasil = String.valueOf(mulaiDate.compareTo(selesaiDate));
        if (hasil.equals("0")){
            String jamMulai = jam_mulai_et.getText().toString();
            String jamSelesai = jam_selesai_et.getText().toString();

            int strJamMulai = Integer.parseInt(jamMulai.substring(0,2));
            int strJamSelesai = Integer.parseInt(jamSelesai.substring(0,2));

            if (strJamSelesai < strJamMulai ){
                new MaterialDialog.Builder(edit_jadwal.this)
                        .title("Terjadi Kesalahan")
                        .content("Jika Kegiatan dilaksanakan hanya 1 hari, pastikan anda mengisi JAM SELESAI lebih Besar dari JAM MULAI")
                        .positiveText("OKE")
                        .show();
            }

        }
    }


    public void insert(){
        mId_jadwal = idJadwal_et.getText().toString();
        mId_giat = id_giat_tv.getText().toString();
        mTglMulai = tgl_mulai_et.getText().toString();
        mTglSelesai = tgl_selesai_et.getText().toString();
        mJamMulai = jam_mulai_et.getText().toString();
        mJamSelesai = jam_selesai_et.getText().toString();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jPesan = new JSONObject(response);
                            boolean pesan = jPesan.names().get(0).equals("success");
                            if (pesan == true){
                                PD.dismiss();

                                savedSucced();

                            }else if(pesan == false){
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
                params.put("id-kegiatan", mId_giat);
                params.put("tglMulai", mTglMulai);
                params.put("jamMulai", mJamMulai);
                params.put("tglSelesai", mTglSelesai);
                params.put("jamSelesai", mJamSelesai);
                return params;
            }
        };

        MyApplication.getInstance().addToReqQueue(postRequest);
        Log.d("isiParam", mId_jadwal+" "+mTglMulai+" "+mJamMulai+" "+mTglSelesai+" "+mJamSelesai);
    }

    public void setID(){
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.names().get(0).equals("id")){
                        String id = jsonObject.getString("id");
                        idJadwal_et.setText(id);
                        mId_jadwal = idJadwal_et.getText().toString();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MyApplication.getInstance().addToReqQueue(request);
    }


    public void savedSucced(){
        new MaterialDialog.Builder(edit_jadwal.this)
                .title("Edit Berhasil")
                .negativeText("Lewati")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                })
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        switch(which){
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), pilih_personil.class);
                                intent.putExtra("idJadwal", mId_jadwal);
                                startActivity(intent);
                                finish();
                                break;
                            case 1:
                                Intent intSurat = new Intent(getApplicationContext(), tambah_str.class);
                                intSurat.putExtra("idJadwal", mId_jadwal);
                                startActivity(intSurat);
                                finish();
                        }
                    }
                })
                .show();
    }
}



