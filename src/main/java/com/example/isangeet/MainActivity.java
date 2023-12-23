package com.example.isangeet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.function.ToDoubleBiFunction;

public class MainActivity extends AppCompatActivity {

    ListView listview ;
    ProgressBar progressBar;
    ArrayAdapter<String> arrayAdapter;
    String [] name = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,name);
        listview = findViewById(R.id.listview);
         progressBar = findViewById(R.id.progressBar);

            if(progressBar.getVisibility()== View.GONE){
                progressBar.setVisibility(View.VISIBLE);
            }


        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        //Toast.makeText(MainActivity.this, "Runtime Permission given", Toast.LENGTH_SHORT).show();
                        ArrayList<File> mySongs = fetchsongs(Environment.getExternalStorageDirectory());
                        String[] items = new String[mySongs.size()];
                        for (int i = 0; i < mySongs.size(); i++)
                        {
                            items[i] = mySongs.get(i).getName().replace("mp3", "");
//
                        }

                        arrayAdapter  = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1 , items);
                        listview.setAdapter(arrayAdapter);

                            progressBar.setVisibility(View.GONE);


                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?>  parent, View view, int position, long l) {
                                Intent intent = new Intent(MainActivity.this , PlaySong.class);
                                String currentsong = (String)parent.getItemAtPosition(position);
                                intent.putExtra("songlist", mySongs);
                                intent.putExtra("currentsong", currentsong);
                                intent.putExtra("position", position);
                                startActivity(intent);


                            }
                        });
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu2, menu);
        MenuItem menuItem = menu.findItem(R.id.searchIcon);
        SearchView searchView1 = (SearchView) menuItem.getActionView();
        searchView1.setIconified(false);
        searchView1.setQueryHint("Search");


        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                arrayAdapter.getFilter().filter(s);

                return false;
            }
        });

        return true;
    }


    public ArrayList<File> fetchsongs(File file){
        if(progressBar.getVisibility()==View.GONE){
            progressBar.setVisibility(View.VISIBLE);
        }
       ArrayList arraylist = new ArrayList();
       File[] songs = file.listFiles();

       if(songs!= null)
       {
           for(File myFile : songs)
           {
               if(!myFile.isHidden()&&myFile.isDirectory())
               {
                   arraylist.addAll(fetchsongs(myFile));
               }

               else{
                   if(myFile.getName().endsWith(".mp3")&&!myFile.getName().startsWith("."))
                   {
                       arraylist.add(myFile);
                   }
               }
           }
       }

       return arraylist;

    }






}