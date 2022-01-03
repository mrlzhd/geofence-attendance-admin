package com.example.geofencingadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.geofencingadmin.Model.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    
    private Button btn_submitClass;
    private EditText et_className, et_matricStudent;
    
    String idLecturer = "GA123";

    String namaSubjek, matrik;

    String latitude = "";
    String longitude = "";

    String parentDBName = "SubjectList";
    String parentDBName2 = "SubjectListLecturer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btn_submitClass = findViewById(R.id.btn_submitClass);
        et_className = findViewById(R.id.et_className);
//        et_matricStudent = findViewById(R.id.et_matricStudent);
        
        
        btn_submitClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sahkanData();

                
            }
        });
    }

    private void sahkanData() {

        namaSubjek = et_className.getText().toString();
//        matrik = et_matricStudent.getText().toString();

        if (TextUtils.isEmpty(namaSubjek))
        {
            Toast.makeText(MainActivity.this, "Please fill in", Toast.LENGTH_SHORT).show();

        }

        else
        {
            simpanData();
        }




    }

    private void simpanData() {

        final DatabaseReference finalRef;
        finalRef = FirebaseDatabase.getInstance().getReference();

        matrik = Prevalent.currentOnlineUser.getMatric();

        finalRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (!(snapshot).child(parentDBName2).child(matrik).child(namaSubjek).exists()) {

                    HashMap<String, Object> subjectDataMap = new HashMap<>();
                    subjectDataMap.put("namaClass", namaSubjek);
                    subjectDataMap.put("matrik", matrik);

                    finalRef.child(parentDBName2).child(matrik).child(namaSubjek).updateChildren(subjectDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(MainActivity.this, "Data untuk matrik dah update", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(MainActivity.this, "Data untuk matrik tak boleh update", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Subjek dah ada", Toast.LENGTH_SHORT).show();
                }

                if (!(snapshot).child(parentDBName).child(namaSubjek).exists())
                {
                    HashMap<String, Object> latLongData = new HashMap<>();
//                    latLongData.put("latitude", latitude);
//                    latLongData.put("longitude", longitude);
                    latLongData.put("name", namaSubjek);

                    finalRef.child(parentDBName).child(namaSubjek).updateChildren(latLongData)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(MainActivity.this, "Data untuk latlong dah update", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(MainActivity.this, "Data untuk latlong tak boleh update", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
                }

                else
                {
                    Toast.makeText(MainActivity.this, "Latitude longitude dah ada", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}