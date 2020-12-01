package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

public class MainFragment extends Fragment implements View.OnClickListener{

    private final static int KEY_UPVOTES = 0;

    Button mIvlebronUp;

    TextView mTvlebronUp;

    Button mIvstephenUp;

    TextView mTvstephenUp;

    Button mIvkevinUp;

    TextView mTvkevinUp;



    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // IMAGE VIEWS
        mIvlebronUp = view.findViewById(R.id.iv_lebron_upvote);

        mIvstephenUp = view.findViewById(R.id.iv_stephen_upvote);

        mIvkevinUp = view.findViewById(R.id.iv_kevin_upvote);


        mIvlebronUp.setOnClickListener(this);

        mIvstephenUp.setOnClickListener(this);

        mIvkevinUp.setOnClickListener(this);


        // TEXT VIEWS
        mTvlebronUp = view.findViewById(R.id.tv_lebron_upvote);

        mTvstephenUp = view.findViewById(R.id.tv_stephen_upvote);

        mTvkevinUp = view.findViewById(R.id.tv_kevin_upvote);



    }

    @Override
    public void onClick(View view) {

        String base_url = "https://skannega1.sites.tjhsst.edu";
        TextView targetTV = null;
        int key = -1;

        switch (view.getId()) {
            case R.id.iv_lebron_upvote:
                base_url += "/upvoteById?id=0";
                targetTV = mTvlebronUp;
                key = KEY_UPVOTES;
                break;
            case R.id.iv_stephen_upvote:
                base_url += "/upvoteById?id=1";
                targetTV = mTvstephenUp;
                key = KEY_UPVOTES;
                break;
            case R.id.iv_kevin_upvote:
                base_url += "/upvoteById?id=2";
                targetTV = mTvkevinUp;
                key = KEY_UPVOTES;
                break;
        }

        MyResponseHelper myResponseHelper = new MyResponseHelper(targetTV, key);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                base_url,
                myResponseHelper,
                myResponseHelper
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
    protected class MyResponseHelper implements
            Response.Listener<String>, Response.ErrorListener {

        TextView mTarget;
        int mUpDownKey;

        public MyResponseHelper (TextView tv, int key) {
            mTarget = tv;
            mUpDownKey = key;
        }

        @Override
        public void onResponse(String response) {
            Log.i("TARGET",response);
            Gson gson = new GsonBuilder().create();
            Ratings ratings = gson.fromJson(response, Ratings.class);

            if (mUpDownKey==KEY_UPVOTES) {
                mTarget.setText(String.valueOf(ratings.upvotes));}
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
