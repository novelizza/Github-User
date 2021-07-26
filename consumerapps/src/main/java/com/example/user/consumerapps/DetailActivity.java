package com.example.user.consumerapps;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import cz.msebera.android.httpclient.Header;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_ACCOUNT = "extra_account";

    private static final String TAG = DetailActivity.class.getSimpleName();

    private TextView tvUsernameDetail;
    private TextView tvNameDetail;
    private TextView tvFollowerDetail;
    private TextView tvFollowingDetail;
    private TextView tvProjectDetail;
    private TextView tvCompanyDetail;
    private TextView tvLocationDetail;

    private LinearLayout llFollower;
    private LinearLayout llFollowing;
    private LinearLayout llProject;
    private LinearLayout llCompany;
    private LinearLayout llLocation;

    private FloatingActionButton fbFav;

    private String Username;
    private String dataUsername;
    private String dataName;
    private String dataFollower;
    private String dataFollowing;
    private String dataProject;
    private String dataCompany;
    private String dataLocation;
    private String dataPP;

    private ProgressBar pbBarDetail;

    private ImageView ivPPDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvUsernameDetail = findViewById(R.id.tvUsername_Detail);
        tvNameDetail = findViewById(R.id.tvName_Detail);
        tvFollowerDetail = findViewById(R.id.tvFollower_Detail);
        tvFollowingDetail = findViewById(R.id.tvFollowing_Detail);
        tvProjectDetail = findViewById(R.id.tvProject_Detail);
        tvCompanyDetail = findViewById(R.id.tvCompany_Detail);
        tvLocationDetail = findViewById(R.id.tvLocation_Detail);

        ivPPDetail = findViewById(R.id.ivPPDetail);

        llFollower = findViewById(R.id.llFollower);
        llFollowing = findViewById(R.id.llFollowing);
        llProject = findViewById(R.id.llProject);
        llCompany = findViewById(R.id.llCompany);
        llLocation = findViewById(R.id.llLocation);

        pbBarDetail = findViewById(R.id.pbBar_Detail);
        pbBarDetail.setVisibility(View.VISIBLE);

        Account account = getIntent().getParcelableExtra(EXTRA_ACCOUNT);
        Username = account.getUsername();


        getData();


        SectionPagerAdapter sectionsPagerAdapter = new SectionPagerAdapter(this, getSupportFragmentManager());
        sectionsPagerAdapter.username = Username;
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    private void getData() {
        String url1 = Username;
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("User-Agent", "request");
//        client.addHeader("Authorization", "BuildConfig.GITHUB_TOKEN");
        client.addHeader("Authorization", "token cc6ce4c445f88c465b4e6b13aefaa8dccbd47b84");
        client.get(url1, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // Jika koneksi berhasil
                pbBarDetail.setVisibility(View.GONE);
                String result = new String(responseBody);
                Log.d(TAG, result);
                try {
                    JSONObject responseObject = new JSONObject(result);

//                    String receiveDataUsername = responseObject.getString("login");
                    dataUsername = responseObject.getString("login");
                    tvUsernameDetail.setText(dataUsername);
//                    dataLink = "https://github.com/" + dataUsername;

//                    String receiveDataName = responseObject.getString("name");
                    dataName = responseObject.getString("name");
                    if(dataName != null && !dataName.isEmpty() && !dataName.equals("null")){
                        tvNameDetail.setVisibility(View.VISIBLE);
                        tvNameDetail.setText(dataName);
                    }

//                    String receiveDataFollower = responseObject.getString("followers");
                    dataFollower = responseObject.getString("followers");
                    if(dataFollower != null && !dataFollower.isEmpty() && !dataFollower.equals("null")){
                        llFollower.setVisibility(View.VISIBLE);
                        tvFollowerDetail.setText(dataFollower);
                    }

//                    String receiveDataFollowing = responseObject.getString("following");
                    dataFollowing = responseObject.getString("following");
                    if(dataFollowing != null && !dataFollowing.isEmpty() && !dataFollowing.equals("null")){
                        llFollowing.setVisibility(View.VISIBLE);
                        tvFollowingDetail.setText(dataFollowing);
                    }

//                    String receiveDataProject = responseObject.getString("public_repos");
                    dataProject = responseObject.getString("public_repos");
                    if(dataProject != null && !dataProject.isEmpty() && !dataProject.equals("null")){
                        llProject.setVisibility(View.VISIBLE);
                        tvProjectDetail.setText(dataProject);
                    }

//                    String receiveDataCompany = responseObject.getString("company");
                    dataCompany = responseObject.getString("company");
                    if(dataCompany != null && !dataCompany.isEmpty() && !dataCompany.equals("null")){
                        llCompany.setVisibility(View.VISIBLE);
                        tvCompanyDetail.setText(dataCompany);
                    }

//                    String receiveDataLocation = responseObject.getString("location");
                    dataLocation = responseObject.getString("location");
                    if(dataLocation != null && !dataLocation.isEmpty() && !dataLocation.equals("null")){
                        llLocation.setVisibility(View.VISIBLE);
                        tvLocationDetail.setText(dataLocation);
                    }

//                    String receiveDataPP = responseObject.getString("avatar_url");
                    dataPP = responseObject.getString("avatar_url");
                    Picasso.get()
                            .load(dataPP)
                            .resize(110,110)
                            .centerCrop()
                            .into(ivPPDetail);
                } catch (Exception e) {
                    Toast.makeText(DetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // Jika koneksi gagal
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
                Toast.makeText(DetailActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

}