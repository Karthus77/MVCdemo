package com.example.mvcdemo.Model;

import com.example.mvcdemo.weather;

public interface MtoV {
    void onSuccess(weather weather);
    void onError();
}
