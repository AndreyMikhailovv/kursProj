package com.andymproj.technologynewsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FullTNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_tnews);

        Bundle arguments = getIntent().getExtras();
        String titleTN = arguments.get("titleTN").toString();
        String commentTN = arguments.get("commentTN").toString();
        String contentTN = arguments.get("contentTN").toString();

        TextView titleTNewsTV = findViewById(R.id.titleTNewsTV);
        TextView commentTNewsTV = findViewById(R.id.commentTNewsTV);
        TextView contentTNewsTV = findViewById(R.id.contentTNewsTV);

        if (arguments != null){
            titleTNewsTV.setText(titleTN);
            commentTNewsTV.setText(commentTN);
            contentTNewsTV.setText(contentTN);
        }
    }

    @Override
    public void onBackPressed() {
        goingBack();
    }

    public void goingBack() {
        try {
            Intent intent = new Intent(FullTNewsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        catch (Exception e) {
        }
    }
}