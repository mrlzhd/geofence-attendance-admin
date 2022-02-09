package com.example.geofencingadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.geofencingadmin.Model.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {

    private Button btn_gotoClass, btn_gotoStart, btn_gotoEnd, btn_logout;
    TextView tv_date;

    String dbName = "SubjectListLecturer";
    String IDNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_gotoClass = findViewById(R.id.btn_gotoSubmit);
        btn_gotoStart = findViewById(R.id.btn_gotoStart);
        btn_gotoEnd = findViewById(R.id.btn_gotoEnd);

        btn_logout = findViewById(R.id.btn_logout);

        tv_date = findViewById(R.id.tv_date);

        tv_date.setVisibility(View.INVISIBLE);

        btn_gotoEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent endd = new Intent(HomeActivity.this, EndClassActivity.class);
                startActivity(endd);
            }
        });

        btn_gotoClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent submittt = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(submittt);
            }
        });

       // btn_gotoStart.setVisibility(View.INVISIBLE);

        btn_gotoStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent starttt = new Intent(HomeActivity.this, StartActivity.class);
                startActivity(starttt);
            }
        });

//        IDNum = Prevalent.currentOnlineUser.getMatric();
//        DatabaseReference databaseReference;
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//        databaseReference.keepSynced(true);
//
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.child(dbName).child(IDNum).exists())
//                {
//                    btn_gotoStart.setVisibility(View.VISIBLE);
//                    btn_gotoEnd.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout = new Intent(HomeActivity.this, LandingPage.class);
                Paper.book().destroy();
                startActivity(logout);
            }
        });




    }
}