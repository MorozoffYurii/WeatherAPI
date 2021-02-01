package com.example.a01022021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btn1, btn2, btn3;
    private TextView tv;
    private Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        h = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                //tv.setText("Прошло операций: "+msg.what);
                tv.setText(msg.obj.toString());
            }
        };
        btn1 = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        tv = findViewById(R.id.textView);
        btn3 = findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread weather = new Thread(new HTTPRequest(h));
                weather.start();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=1;i<=10;i++){
                            doSlow();
                            h.sendEmptyMessage(i);
                            // tv.setText("Прошло операций: "+i);
                            Log.d("RRRR", "Прошло операций: "+i);
                        }
                    }
                });
                thread.start();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RRRR", "ВТОРАЯ КНОПКА");
            }
        });

    }

    public void doSlow(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}