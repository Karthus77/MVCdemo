package com.example.mvcdemo.Controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvcdemo.Model.IntConnect;
import com.example.mvcdemo.Model.MtoV;
import com.example.mvcdemo.R;
import com.example.mvcdemo.weather;

public class MainActivity extends AppCompatActivity {
    EditText cityIN;
    Button search;
    TextView city;
    TextView temperature;
    TextView weather1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);
        final MtoV mtoV=new MtoV() {
            @Override
            public void onSuccess(final com.example.mvcdemo.weather weather) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        city.setText(weather.getResult().getCity());
                        temperature.setText(weather.getResult().getFuture().get(1).getTemperature());
                        weather1.setText(weather.getResult().getFuture().get(1).getWeather());
                    }
                });

            }

            @Override
            public void onError() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "出错了哦！", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };
        final IntConnect weatherModel = new IntConnect();
        cityIN=findViewById(R.id.cityIn);
        search=findViewById(R.id.search);
        temperature=findViewById(R.id.temperature);
        weather1=findViewById(R.id.weather);
        city=findViewById(R.id.city);
        search.setOnClickListener((new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String name = cityIN.getText().toString();
                if (!name.equals("")) {

                    weatherModel.getWeather(name, mtoV  );
                } else {
                    Toast.makeText(MainActivity.this,"输入的城市不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        }));



    }


}