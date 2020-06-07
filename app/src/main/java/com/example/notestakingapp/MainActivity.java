package com.example.notestakingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Intent i;
    static ArrayList<String> dum;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.main_menu,menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);

        i=new Intent(getApplicationContext(),ExampleActivity.class);
         i.putExtra("number",-1);
         startActivity(i);

return true;

    }
    static ArrayAdapter<String> am;
    static ArrayList<String>  al;
    static ArrayList<String>  as;
     static SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ListView li=(ListView)findViewById(R.id.list);
         al=new  ArrayList<String>();
al.add("Example Activity");
         sp=getSharedPreferences("com.example.notestakingapp", Context.MODE_PRIVATE);
        ArrayList<String> n=new ArrayList<String>();
        try {
            n=(ArrayList<String>) ObjectSerializer.deserialize(sp.getString("Notes", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(n.size()==0) {
            try {
                sp.edit().putString("Notes", ObjectSerializer.serialize(al)).apply();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
             as = new ArrayList<String>();
             try {
                 as = (ArrayList<String>) ObjectSerializer.deserialize(sp.getString("Notes", ObjectSerializer.serialize(new ArrayList<String>())));
             } catch (IOException e) {
                 e.printStackTrace();
             }
         dum = new ArrayList<String>();
for(int i=0;i<as.size();i++)
{
    String rm=as.get(i);
    if(rm.length()>50)
    {
        String hm=rm.substring(0,51);
        hm=hm+"...";
       dum.add(hm);
    }
    else
    {
        dum.add(rm);
    }
}

         am= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dum);

        li.setAdapter(am);
li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         i=new Intent(getApplicationContext(),ExampleActivity.class);
       i.putExtra("number",position);
        startActivity(i);
    }
});
li.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final int r=position;
       new AlertDialog.Builder(MainActivity.this)
               .setIcon(android.R.drawable.ic_delete)
               .setTitle("Do you want to delete it")
               .setMessage("Are you sure")
               .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       MainActivity.al.clear();
                       try {
                           MainActivity.al. addAll((ArrayList<String> )ObjectSerializer.deserialize(MainActivity.sp.getString("Notes",ObjectSerializer.serialize(new ArrayList<String>()))));
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                       MainActivity.al.remove(r);
                       try {
                           MainActivity.sp.edit().putString("Notes",ObjectSerializer.serialize(MainActivity.al)).apply();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                       try {
                           MainActivity.as.clear();
                           dum.clear();
                           MainActivity.as.addAll((ArrayList<String> )ObjectSerializer.deserialize(MainActivity.sp.getString("Notes",ObjectSerializer.serialize(new ArrayList<String>()))));
                           for(int i=0;i<as.size();i++)
                           {
                               String rm=as.get(i);
                               if(rm.length()>50)
                               {
                                   String hm=rm.substring(0,51);
                                   hm=hm+"...";
                                   dum.add(hm);
                               }
                               else
                               {
                                   dum.add(rm);
                               }
                           }
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                       MainActivity.am.notifyDataSetChanged();
                   }
               })
               .setNegativeButton("No",null)
               .show();



        return true;
    }
});

    }
}
