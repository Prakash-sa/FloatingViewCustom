package com.example.floatingviewcustom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button bt;
    private static final int ID_DRAW_OTHER_APP=9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt=findViewById(R.id.framelayout_bt);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&
        !Settings.canDrawOverlays(MainActivity.this)){
            Intent intent=new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:"+getPackageName()));
            startActivity(intent);
        }else {
            floatingview();
        }


    }

    private void floatingview(){
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"adsf",Toast.LENGTH_LONG).show();
                startService(new Intent(MainActivity.this,FrameLayoutImap.class));
                finish();
            }
        });
    }
}
