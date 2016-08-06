package com.mipro.ard.penajdwalan.tambah;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.PhotoLoader;
import com.mipro.ard.penajdwalan.MainActivity;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.MyCommand;
import com.mipro.ard.penajdwalan.json_handler.parser;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class tambah_str extends AppCompatActivity {
    Button btn_camera, btn_galery;
    LinearLayout linearMain;
    GalleryPhoto galleryPhoto;
    final int GALERY_REQUEST = 1200;
    final String TAG = this.getClass().getSimpleName();

    ArrayList<String> imageList = new ArrayList<>();
    TextView title_bar;
    ImageButton m_done_btn, m_back_btn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_str);

        linearMain = (LinearLayout) findViewById(R.id.linearMain);
        btn_camera = (Button) findViewById(R.id.btn_cam_up_sp);
        btn_galery = (Button) findViewById(R.id.btn_galery_up_sp);



        title_bar = (TextView) findViewById(R.id.bar_title);
        m_back_btn = (ImageButton) findViewById(R.id.kembali_btn);
        m_done_btn = (ImageButton) findViewById(R.id.search_btn);

        title_bar.setText("TAMBAH KATEGORI");

        m_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(back_home);
            }
        });

        final MyCommand myCommand = new MyCommand();

        m_done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String imagePath : imageList){
                    try {
                        Bitmap bitmap = PhotoLoader.init().from(imagePath).requestSize(1024, 1024).getBitmap();
                        final String encodedString = ImageBase64.encode(bitmap);
                        String url = "http://"+ parser.IP_PUBLIC +"/ditlantas/json/surat/insert_str.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "Terjadi Kesalahan saat Mengambil Gamabar", Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> params = new HashMap<String, String>();
                                params.put("idJadwal", "J00001");
                                params.put("image", encodedString);
                                return params;
                            }
                        };

                        myCommand.add(stringRequest);

                    } catch (FileNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "Error While Loading Image ", Toast.LENGTH_SHORT).show();

                    }

                }
                myCommand.execute();
            }
        });



        galleryPhoto = new GalleryPhoto(tambah_str.this);
        btn_galery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galInt = galleryPhoto.openGalleryIntent();
                startActivityForResult(galInt, GALERY_REQUEST);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == GALERY_REQUEST){
                galleryPhoto.setPhotoUri(data.getData());
                String photoPath = galleryPhoto.getPath();

                imageList.add(photoPath);

                Log.d(TAG, galleryPhoto.getPath());

                try {
                    Bitmap bitmap = PhotoLoader.init().from(photoPath).requestSize(1024, 1024).getBitmap();
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    ImageView imageView = new ImageView(getApplicationContext());
                    imageView.setLayoutParams(layoutParams);
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageView.setPadding(0,0,0,10);
                    imageView.setAdjustViewBounds(true);
                    imageView.setImageBitmap(bitmap);
                    linearMain.addView(imageView);


                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Error While Loading Image ", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
