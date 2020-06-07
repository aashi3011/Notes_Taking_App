package com.example.notestakingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class ExampleActivity extends AppCompatActivity {
    EditText t;
    int r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        Intent i=getIntent();
        r= i.getIntExtra("number",-1);
        t = (EditText) findViewById(R.id.text);
       if(r!=-1) {

           String m = MainActivity.as.get(r);
           t.setText(m);

       }
     else{
         r=MainActivity.as.size();


       }
     t.addTextChangedListener(new TextWatcher()
     {

         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

         }

         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {
             MainActivity.al.clear();
             try {
                 MainActivity.al. addAll((ArrayList<String> )ObjectSerializer.deserialize(MainActivity.sp.getString("Notes",ObjectSerializer.serialize(new ArrayList<String>()))));
             } catch (IOException e) {
                 e.printStackTrace();
             }
             if(r<MainActivity.as.size()) {
                 MainActivity.al.set(r, s.toString());
             }
             else
                 MainActivity.al.add(r, s.toString());
             try {
                 MainActivity.sp.edit().putString("Notes",ObjectSerializer.serialize(MainActivity.al)).apply();
             } catch (IOException e) {
                 e.printStackTrace();
             }
             try {
                 MainActivity.as.clear();
                 MainActivity.dum.clear();
                 MainActivity.as.addAll((ArrayList<String> )ObjectSerializer.deserialize(MainActivity.sp.getString("Notes",ObjectSerializer.serialize(new ArrayList<String>()))));
                 for(int i=0;i<MainActivity.as.size();i++)
                 {
                     String rm=MainActivity.as.get(i);
                     if(rm.length()>50)
                     {
                         String hm=rm.substring(0,51);
                         hm=hm+"...";
                         MainActivity.dum.add(hm);
                     }
                     else
                     {
                         MainActivity.dum.add(rm);
                     }
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
             MainActivity.am.notifyDataSetChanged();


         }

         @Override
         public void afterTextChanged(Editable s) {
         }
     });



    }
}
