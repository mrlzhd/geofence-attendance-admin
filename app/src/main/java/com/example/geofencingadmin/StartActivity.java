package com.example.geofencingadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geofencingadmin.Model.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    Spinner spinner_register;
    DatabaseReference databaseReference;
    String dbName = "SubjectListLecturer";
    private Button btn_submitRegClass;

    String classSubmitName, matricDbName, IDNum;
    String userClassList = "StudentClassList";

    List<String> classNames;
    ArrayAdapter<String> arrayAdapter;

    TextView tv_startLat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        spinner_register = findViewById(R.id.spinner_register);
        btn_submitRegClass = findViewById(R.id.btn_submitRegClass);

        classNames = new ArrayList<>();

        IDNum = Prevalent.currentOnlineUser.getMatric();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(dbName).child(IDNum).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {

                    String spinnerData = childSnapshot.child("namaClass").getValue(String.class);
                    classNames.add(spinnerData);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(StartActivity.this, android.R.layout.simple_spinner_item, classNames);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spinner_register.setAdapter(arrayAdapter);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        btn_submitRegClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });


    }

    private void submitData() {

        classSubmitName = spinner_register.getSelectedItem().toString();

        sendData();

    }

    private void sendData() {

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        String lat = String.valueOf(latitude);
        String lon = String.valueOf(longitude);

        final DatabaseReference regRef;
        regRef = FirebaseDatabase.getInstance().getReference();

        matricDbName = Prevalent.currentOnlineUser.getMatric();

        regRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if ((snapshot).child("SubjectList").child(classSubmitName).exists())
                {
                    HashMap<String, Object> regDataMap = new HashMap<>();
                    regDataMap.put("latitude", lat);
                    regDataMap.put("longitude", lon);

                    regRef.child("SubjectList").child(classSubmitName).updateChildren(regDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(StartActivity.this, "Class is starting..", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        regRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!(snapshot).child("Ongoing").child(IDNum).child(classSubmitName).exists())
                {
                    HashMap<String, Object> regDataMap = new HashMap<>();
                    regDataMap.put("lecturer", IDNum);
                    regDataMap.put("classname", classSubmitName);

                    regRef.child("Ongoing").child(IDNum).child(classSubmitName).updateChildren(regDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(StartActivity.this, "Your class now starting", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}