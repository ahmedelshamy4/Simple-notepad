package com.example.ahmed.simplenodepade;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.HashSet;

import static com.example.ahmed.simplenodepade.MainActivity.arr;
import static com.example.ahmed.simplenodepade.MainActivity.set;

public class Edit_node_pade extends AppCompatActivity implements TextWatcher {
    EditText editText;
    int nodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_node_pade);
        editText = (EditText) findViewById(R.id.edit_node);
        Intent intent = getIntent();
        nodId = intent.getIntExtra("nodID", -1);
        if (nodId != -1) {
            editText.setText(arr.get(nodId));
        }
        editText.addTextChangedListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        arr.set(nodId, String.valueOf(s));
        MainActivity.adapter.notifyDataSetChanged();

        SharedPreferences preferences = this.getSharedPreferences("com.example.ahmed.simplenodepade", Context.MODE_PRIVATE);
        if(set==null){
            set=new HashSet<>();
        }else {
            set.clear();
        }
            set.addAll(arr);
        preferences.edit().remove("arr").apply();
            preferences.edit().putStringSet("arr", set).apply();

        }

        @Override
        public void afterTextChanged (Editable s){

        }
    }
