package com.example.ahmed.simplenodepade;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    static ArrayAdapter adapter;
    static ArrayList<String> arr = new ArrayList<>();
    static Set<String> set;//we save any thing in sharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);
        SharedPreferences preferences = this.getSharedPreferences("com.example.ahmed.simplenodepade", Context.MODE_PRIVATE);
        set = preferences.getStringSet("arr", null);
        arr.clear();
        if (set != null) {
            arr.addAll(set);
        } else {
            arr.add("Example node");
            set = new HashSet<>();
            set.addAll(arr);
            preferences.edit().putStringSet("arr", set).apply();
        }


        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, Edit_node_pade.class);
                i.putExtra("nodID", position);
                startActivity(i);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Are you sure")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Do you want to delete this note? ")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arr.remove(position);
                                SharedPreferences preferences = MainActivity.this.getSharedPreferences("com.example.ahmed.simplenodepade", Context.MODE_PRIVATE);
                                if (set == null) {
                                    set = new HashSet<>();
                                } else {
                                    set.clear();
                                }
                                set.addAll(arr);
                                preferences.edit().putStringSet("arr", set).apply();
                                preferences.edit().putStringSet("arr", set).apply();
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("no", null)
                        .show();


                return true;
            }
        });
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {
            arr.add("");
            SharedPreferences preferences =
                    this.getSharedPreferences("com.example.ahmed.simplenodepade", Context.MODE_PRIVATE);
            if (set == null) {
                set = new HashSet<>();
            } else {
                set.clear();
            }
            set.addAll(arr);
            adapter.notifyDataSetChanged();
            preferences.edit().remove("arr").apply();

            preferences.edit().putStringSet("arr", set).apply();
            Intent i = new Intent(MainActivity.this, Edit_node_pade.class);
            i.putExtra("nodID", arr.size() - 1);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
