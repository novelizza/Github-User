package com.example.user.bfaa_submission2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ProgressBar pbBar;
    private RecyclerView rcList;
    private SearchView svUsername;
    private LinearLayout llNotFound;
    private static final String TAG = MainActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcList = findViewById(R.id.rcList);
        pbBar = findViewById(R.id.pbBar);
        svUsername = findViewById(R.id.svUser);
        llNotFound = findViewById(R.id.llNotFound);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null){
            svUsername.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            svUsername.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    pbBar.setVisibility(View.VISIBLE);
                    getDataUser(s);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        if (item.getItemId() == R.id.action_favorite) {
            Intent intent = new Intent(this, FavoriteActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.action_reminder) {
            Intent intent = new Intent(this, ReminderActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDataUser(String username) {
        pbBar.setVisibility(View.VISIBLE);
        rcList.setVisibility(View.GONE);
        llNotFound.setVisibility(View.GONE);

        String url = "https://api.github.com/search/users?q=" + username;
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("User-Agent", "request");
        client.addHeader("Authorization", "token cc6ce4c445f88c465b4e6b13aefaa8dccbd47b84");
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // Jika koneksi berhasil
                pbBar.setVisibility(View.GONE);
                ArrayList<String> listUsername = new ArrayList<>();
                ArrayList<String> listURL = new ArrayList<>();
                ArrayList<String> listAvatar = new ArrayList<>();

                String result = new String(responseBody);
                Log.d(TAG, result);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray item = responseObject.getJSONArray("items");

                    if (item.length() > 0 ){
                        rcList.setVisibility(View.VISIBLE);
                        for (int i = 0; i < item.length(); i++) {
                            JSONObject jsonObject = item.getJSONObject(i);
                            String username = jsonObject.getString("login");
                            listUsername.add(username);
                            String URL = jsonObject.getString("html_url");
                            listURL.add(URL);
                            String Avatar = jsonObject.getString("avatar_url");
                            listAvatar.add(Avatar);
                        }
                        ReycleViewAdapter adapter = new ReycleViewAdapter(MainActivity.this, listUsername, listURL, listAvatar);
                        rcList.setAdapter(adapter);
                        rcList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    } else {
                        llNotFound.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // Jika koneksi gagal
                pbBar.setVisibility(View.INVISIBLE);
                String errorMessage;
                switch (statusCode) {
                    case 401:
                        errorMessage = statusCode + " : Bad Request";
                        break;
                    case 403:
                        errorMessage = statusCode + " : Forbidden";
                        break;
                    case 404:
                        errorMessage = statusCode + " : Not Found";
                        break;
                    default:
                        errorMessage =  statusCode + " : " + error.getMessage();
                        break;
                }
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}