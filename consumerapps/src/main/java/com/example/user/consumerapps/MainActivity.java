package com.example.user.consumerapps;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcListFav;

    private LinearLayout NN;


    ArrayList<String> listUsername = new ArrayList<>();
    ArrayList<String> listURL = new ArrayList<>();
    ArrayList<String> listAvatar = new ArrayList<>();

    private NoteHelper noteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcListFav = findViewById(R.id.rcListFavorite);

        NN = findViewById(R.id.llNotFoundFavorite);

//        noteHelper = NoteHelper.getInstance(getApplicationContext());
//        noteHelper.open();

//        Cursor result = noteHelper.queryAll();
        Cursor result = this.getContentResolver().query(DatabaseContract.AccountColumns.CONTENT_URI, null, null, null, null);

        if(result.getCount() == 0){
            NN.setVisibility(View.VISIBLE);
        }else{
            rcListFav.setVisibility(View.VISIBLE);
            while (result.moveToNext()){
                String username = result.getString(1);
                listUsername.add(username);
                String URL = result.getString(2);
                listURL.add(URL);
                String Avatar = result.getString(3);
                listAvatar.add(Avatar);
            }
        }

        ReycleViewAdapter adapter = new ReycleViewAdapter(MainActivity.this, listUsername, listURL, listAvatar);
        rcListFav.setAdapter(adapter);
        rcListFav.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }
}