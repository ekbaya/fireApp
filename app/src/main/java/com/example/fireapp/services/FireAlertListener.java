package com.example.fireapp.services;

public interface FireAlertListener {
    void onAlertSent();
    void onException(Exception e);
}
