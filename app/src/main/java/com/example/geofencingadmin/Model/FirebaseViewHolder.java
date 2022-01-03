package com.example.geofencingadmin.Model;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geofencingadmin.R;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_classEndName;
    public Button btn_endClass;


    public FirebaseViewHolder(@NonNull View itemView) {
        super(itemView);

        tv_classEndName = itemView.findViewById(R.id.tv_classEndName);
        btn_endClass = itemView.findViewById(R.id.btn_endClass);


    }
}
