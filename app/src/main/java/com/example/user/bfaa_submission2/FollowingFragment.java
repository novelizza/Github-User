package com.example.user.bfaa_submission2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FollowerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FollowingFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private ProgressBar pbBar_Following;

    private RecyclerView rcFollowing;
    private static final String TAG = MainActivity.class.getSimpleName();

    public static FollowingFragment newInstance(int index) {
        FollowingFragment fragment = new FollowingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Account account = Objects.requireNonNull(getActivity()).getIntent().getParcelableExtra(DetailActivity.EXTRA_ACCOUNT);

        rcFollowing = view.findViewById(R.id.rcList_Following);
        pbBar_Following = view.findViewById(R.id.pbBar_Following);

        String url = account.getUsername() + "/following";
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("User-Agent", "request");
        client.addHeader("Authorization", "token cc6ce4c445f88c465b4e6b13aefaa8dccbd47b84");
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // Jika koneksi berhasil
                pbBar_Following.setVisibility(View.GONE);
                rcFollowing.setVisibility(View.VISIBLE);

                ArrayList<String> listUsername = new ArrayList<>();
                ArrayList<String> listURL = new ArrayList<>();
                ArrayList<String> listAvatar = new ArrayList<>();

                String result = new String(responseBody);
                Log.d(TAG, result);
                try {
                    JSONArray responseObject = new JSONArray(result);

                    for (int i = 0; i < responseObject.length(); i++) {
                        JSONObject jsonObject = responseObject.getJSONObject(i);
                        String username = jsonObject.getString("login");
                        listUsername.add(username);
                        String URL = jsonObject.getString("html_url");
                        listURL.add(URL);
                        String Avatar = jsonObject.getString("avatar_url");
                        listAvatar.add(Avatar);
                    }
                    ReycleViewAdapter adapter = new ReycleViewAdapter(getActivity(), listUsername, listURL, listAvatar);
                    rcFollowing.setAdapter(adapter);
                    rcFollowing.setLayoutManager(new LinearLayoutManager(getActivity()));
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}