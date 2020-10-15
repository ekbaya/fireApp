package com.example.fireapp.controllers;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.fireapp.services.FireAlertListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class FireAlertAPI {
    private Context context;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FireAlertListener fireAlertListener;

    public FireAlertAPI(Context context) {
        this.context = context;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Alerts");
    }

    public void sendAnAlert(Map<String , Object> alert){
        String timestamp = String.valueOf(System.currentTimeMillis());
        alert.put("time", timestamp);
        reference
                .child(timestamp)
                .setValue(alert)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       if (task.isSuccessful()){
                           fireAlertListener.onAlertSent();
                       }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        fireAlertListener.onException(e);

                    }
                });
    }

    public FireAlertListener getFireAlertListener() {
        return fireAlertListener;
    }

    public void setFireAlertListener(FireAlertListener fireAlertListener) {
        this.fireAlertListener = fireAlertListener;
    }
}
