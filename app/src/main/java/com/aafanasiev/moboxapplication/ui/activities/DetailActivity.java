package com.aafanasiev.moboxapplication.ui.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.aafanasiev.moboxapplication.MoboxApplicationApp;
import com.aafanasiev.moboxapplication.R;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class DetailActivity extends BaseActivity {

    ImageView iv;
    TextView tv;

    @Inject
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ((MoboxApplicationApp) getApplicationContext()).dataComponent().inject(this);

        iv = (ImageView)findViewById(R.id.image_news);
        tv = (TextView)findViewById(R.id.news_detail);


        Picasso.with(context).load(getIntent().getStringExtra("Image")).into(iv);
        tv.setText(getIntent().getStringExtra("Description"));
    }
}
