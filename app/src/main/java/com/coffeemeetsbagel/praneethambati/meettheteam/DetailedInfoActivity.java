package com.coffeemeetsbagel.praneethambati.meettheteam;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailedInfoActivity extends AppCompatActivity {
    //Layout Variables
    TextView detFullNameTV,detTitleTV,detBioTV;
    ImageView detAvatarIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_info);

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Italic.otf");
        Typeface myCustomFont1 = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf");

        //Initializing Layout variables
        detFullNameTV = (TextView) findViewById(R.id.detFullNameTV);
        detBioTV = (TextView) findViewById(R.id.detBioTV);
        detTitleTV = (TextView) findViewById(R.id.detTitleTV);
        detAvatarIV = (ImageView) findViewById(R.id.detAvatarIV);

        //Getting data from Intents
        Intent i = getIntent();
        String FirstName = i.getStringExtra("FirstName");
        String LastName = i.getStringExtra("LastName");
        String Bio = i.getStringExtra("Bio");
        String Avatar = i.getStringExtra("Avatar");
        String Title = i.getStringExtra("Title");

        //Setting up values or text to all fields on layout
        detBioTV.setTypeface(myCustomFont);
        //detBioTV.setTextSize(40);
        detFullNameTV.setText(FirstName+" "+LastName);
        detTitleTV.setTypeface(myCustomFont1);
        detTitleTV.setText(Title);
        detBioTV.setText(Bio);

        //Picasso which helps in parsing the From URL To ImageView.
        Context context = detAvatarIV.getContext();
        Picasso.with(context)
                .load(Avatar)
                .centerCrop()
                .resize(200,200)
                .into(detAvatarIV);

    }
}
