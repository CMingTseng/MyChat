package com.dingmouren.mychat.ui.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.dingmouren.mychat.R;

/**
 * Created by Administrator on 2018/4/20.
 */

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    private ImageView mImgArrowBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();

        initListener();
    }

    private void initView() {
        mImgArrowBack = findViewById(R.id.img_arrow_back);
    }

    private void initListener(){
        mImgArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
