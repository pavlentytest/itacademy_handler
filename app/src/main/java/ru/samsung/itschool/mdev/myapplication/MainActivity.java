package ru.samsung.itschool.mdev.myapplication;

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

    private TextView tv;
    private Button btn1,btn2;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        btn1 = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        // Handler позволяет отправлять сообщения в другие потоки
        // Looper запускает некий цикл по обработке сообщений, поступившых из др. потоков
        // getMainLooper() - метод, который запускается в главном потоке (UI)
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                tv.setText("Шаг "+msg.what);
            }
        };

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i<10; i++) {
                            doSlow();
                            handler.sendEmptyMessage(i);
                            // так делать нельзя
                            //tv.setText("Шаг "+msg.arg1);
                        }

                    }
                });
                thread.start();

                Log.d("RRR","Button1 click");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RRR","Button2 click");
            }
        });
    }

    public void doSlow() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}