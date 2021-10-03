package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    private Object DatabaseReference;
    private Object ListView;
    ListView listView;
    static ArrayList<String> notes=new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    private Object ArrayList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId()==R.id.add_note)
        {
            Intent intent=new Intent(getApplicationContext(),NoteEditorActivity.class);
            startActivity(intent);

            return true;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView=(ListView)findViewById(R.id.ListView);
        DatabaseReference= FirebaseDatabase.getInstance().getReference("https://notesapp-54a04.firebaseio.com/");

        ListView listView1=(ListView)findViewById(R.id.ListView);
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        listView.setAdapter(arrayAdapter);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String value =dataSnapshot.getValue(NoteEditorActivity.class).toString();
                ArrayList.equals(value);
                arrayAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.noteforapp", Context.MODE_PRIVATE);
        HashSet<String> set=(HashSet<String>)sharedPreferences.getStringSet("notes", null);

        if(set==null) {
            notes.add("Example note");
        }
        else {
            notes = new ArrayList(set);
        }


        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
                intent.putExtra("noteId", i);
                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int itemToDelete = i;

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        notes.remove(itemToDelete);
                                        arrayAdapter.notifyDataSetChanged();

                                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.noteforapp", Context.MODE_PRIVATE);
                                        HashSet<String> set = new HashSet(MainActivity.notes);

                                        sharedPreferences.edit().putStringSet("notes", set);

                                    }
                                }
                        )
                        .setNegativeButton("No", null)
                        .show();
                return true;

            }

            ;
        });
    }
}