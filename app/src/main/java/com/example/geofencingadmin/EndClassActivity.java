package com.example.geofencingadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.geofencingadmin.Model.EndClassData;
import com.example.geofencingadmin.Model.FirebaseViewHolder;
import com.example.geofencingadmin.Model.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class EndClassActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<EndClassData> arrayList;
    private FirebaseRecyclerOptions<EndClassData> options;
    private FirebaseRecyclerAdapter<EndClassData, FirebaseViewHolder> adapter;
    private DatabaseReference databaseReference, databaseReference2;

    String dbName = "Ongoing";

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_class);

        recyclerView = findViewById(R.id.rc_end);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);

        String userChild = Prevalent.currentOnlineUser.getMatric();

        arrayList = new ArrayList<EndClassData>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(dbName).child(userChild);
        databaseReference.keepSynced(true);

        options = new FirebaseRecyclerOptions.Builder<EndClassData>().setQuery(databaseReference, EndClassData.class).build();

        adapter = new FirebaseRecyclerAdapter<EndClassData, FirebaseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder holder, int position, @NonNull EndClassData model) {

                holder.tv_classEndName.setText(model.getClassname());

                String className = holder.tv_classEndName.getText().toString();

                holder.btn_endClass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        deleteCurrent();

                        databaseReference2 = FirebaseDatabase.getInstance().getReference();

                        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                snapshot.child("latitude");

                                if (snapshot.child("SubjectList").child(className).exists())
                                {

                                    HashMap<String, Object> endDataMap = new HashMap<>();
                                    endDataMap.put("latitude", "");
                                    endDataMap.put("longitude", "");

                                    databaseReference2.child("SubjectList").child(className).updateChildren(endDataMap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful())
                                                    {
                                                        Toast.makeText(EndClassActivity.this, "Class end successful", Toast.LENGTH_SHORT).show();
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(EndClassActivity.this, "Unknown error! Please contact admin!", Toast.LENGTH_SHORT).show();
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

                });

            }

            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FirebaseViewHolder(LayoutInflater.from(EndClassActivity.this).inflate(R.layout.row, parent, false));
            }
        };

        recyclerView.setAdapter(adapter);


    }

    private void deleteCurrent() {

        String userChild2 = Prevalent.currentOnlineUser.getMatric();

        DatabaseReference deleteData = FirebaseDatabase.getInstance().getReference().child(dbName).child(userChild2);
        deleteData.removeValue();

    }
}