package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {

    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editText=(EditText)findViewById((R.id.editTextTextMultiLine));

        Intent intent=getIntent();
        noteId=intent.getIntExtra("noteId", -1);

        if(noteId!= -1)
        {
            editText.setText(com.example.notesapp.MainActivity.notes.get(noteId));
        }
        else {
            com.example.notesapp.MainActivity.notes.add("");
            noteId= com.example.notesapp.MainActivity.notes.size()-1;
            com.example.notesapp.MainActivity.arrayAdapter.notifyDataSetChanged();
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2 ) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                com.example.notesapp.MainActivity.notes.set(noteId,String.valueOf(charSequence));
                com.example.notesapp.MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.noteforapp", Context.MODE_PRIVATE);
                HashSet<String> set=new HashSet(com.example.notesapp.MainActivity.notes);

                sharedPreferences.edit().putStringSet("notes",set);


            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


    }
}