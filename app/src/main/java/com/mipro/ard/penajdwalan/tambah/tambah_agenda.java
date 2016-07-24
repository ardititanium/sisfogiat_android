package com.mipro.ard.penajdwalan.tambah;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mipro.ard.penajdwalan.MainActivity;
import com.mipro.ard.penajdwalan.R;

public class tambah_agenda extends AppCompatActivity {

    TextView title_bar;
    ImageButton m_done_btn, m_back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_agenda);

        title_bar = (TextView) findViewById(R.id.bar_title);
        m_back_btn = (ImageButton) findViewById(R.id.kembali_btn);
        m_done_btn = (ImageButton) findViewById(R.id.done_btn);

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
//                insert();
//                setID();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu_bar, menu);
        return true;

    }
}
