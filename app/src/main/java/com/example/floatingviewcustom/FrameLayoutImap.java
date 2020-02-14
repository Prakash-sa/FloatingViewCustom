package com.example.floatingviewcustom;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

public class FrameLayoutImap extends Service {

    private View floatingview;
    private WindowManager windowManager;
    private WindowManager.LayoutParams parameters;
    private View expandedview,collapsedview,rootview;
    int startxpos;
    int startypos;
    float starttouchx;
    float starttouchy;

    @Override
    public void onCreate() {
        super.onCreate();
        floatingview= LayoutInflater.from(FrameLayoutImap.this).inflate(R.layout.float_view_layout,null);
        windowManager=(WindowManager) getSystemService(WINDOW_SERVICE);
        parameters=new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );
        parameters.gravity= Gravity.CENTER|Gravity.CENTER;
        parameters.x=200;
        parameters.y=200;
        windowManager.addView(floatingview,parameters);

        collapsedview=floatingview.findViewById(R.id.collapsed_view);
        ImageView circle=floatingview.findViewById(R.id.circle_bt);

        expandedview=floatingview.findViewById(R.id.expanded_view);
        ImageView camera=floatingview.findViewById(R.id.camera_bt);
        ImageView back_bt=floatingview.findViewById(R.id.back_bt);

        rootview=floatingview.findViewById(R.id.root_view);

        rootview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startxpos=parameters.x;
                        startypos=parameters.y;
                        starttouchx=event.getRawX();
                        starttouchy=event.getRawY();

                        return true;

                    case MotionEvent.ACTION_UP:
                        int starttoendx=(int)(event.getRawX()-starttouchx);
                        int starttoendy=(int)(event.getRawY()-starttouchy);
                        if(starttoendx<5&&starttoendy<5){
                            if(isWidgetCollapsed()){
                                collapsedview.setVisibility(View.GONE);
                                expandedview.setVisibility(View.VISIBLE);
                            }
                        }
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        parameters.x=startxpos+(int)(event.getRawX()-starttouchx);
                        parameters.y=startypos+(int)(event.getRawY()-starttouchy);
                        windowManager.updateViewLayout(floatingview,parameters);
                        return true;
                }


                return false;
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FrameLayoutImap.this,"Camera button is click",Toast.LENGTH_LONG).show();
            }
        });
        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collapsedview.setVisibility(View.VISIBLE);
                expandedview.setVisibility(View.INVISIBLE);
            }
        });



    }


    private boolean isWidgetCollapsed(){
        return collapsedview.getVisibility()==View.VISIBLE;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }
}
