package com.coffeemeetsbagel.praneethambati.meettheteam;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static java.util.Collections.sort;

public class LandingActivity extends AppCompatActivity {

    //Variable Declaration
    List<PersonModel> employeesList;
    SimpleAdapter employeesAdapter;
    private String[] imageUrls;




    //Layout related Variable
    ListView employeeLV;
    private boolean isUserScroll;
    private boolean isDataLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        //Variable Initialization
        employeesList = new ArrayList<PersonModel>();

        //Layout related Variables Initialization with findViewById
        employeeLV = (ListView) findViewById(R.id.employeeLV);


        // Reading json file from assets folder
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open(
                    "team.json")));
            String temp;
            while ((temp = br.readLine()) != null)
                sb.append(temp);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close(); // stop reading
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String myjsonstring = sb.toString();
        // Try to parse JSON
        try {

            // Creating JSONArray
            JSONArray jsonArray = new JSONArray(myjsonstring);

            imageUrls = new String[jsonArray.length()];

            // JSONArray has x JSONObject
            for (int i = 0; i < jsonArray.length(); i++) {

                // Creating JSONObject from JSONArray
                JSONObject jsonObj = jsonArray.getJSONObject(i);

                // Getting data from individual JSONObject
                int id = jsonObj.getInt("id");
                String avatar = jsonObj.getString("avatar");
                String bio = jsonObj.getString("bio");
                String firstName = jsonObj.getString("firstName");
                String lastName = jsonObj.getString("lastName");
                String title = jsonObj.getString("title");


                PersonModel personModel = new PersonModel(avatar, firstName, lastName, bio, title, id);

                employeesList.add(personModel);


                imageUrls[i] = avatar;


            }

            //Sorting employees with their firstNames or title (Which ever is better)
            Collections.sort(employeesList, new Comparator<PersonModel>() {

                @Override
                public int compare(PersonModel s1, PersonModel s2) {
                    return s1.getFirstName().compareToIgnoreCase(s2.getFirstName());
                }
            });

            //Check for correctness in Android Monitor or LogCat
            for (PersonModel personModel : employeesList) {
                System.out.println("Employees List: " + personModel.getFirstName().toString());
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //Setting up ListView to display image, firstName, Title.
       /* List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();


        for (int i = 0; i < employeesList.size(); i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", employeesList.get(i).getFirstName());
            hm.put("listview_discription", employeesList.get(i).getTitle());
          //  hm.put("listview_image", employeesList.get(i).getAvatar());


            aList.add(hm);

            System.out.println("Alist:" + aList.get(i));
        }

        System.out.println("Alist:" + aList.size());

        *//*String[] from = {"listview_image", "listview_title", "listview_discription"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};
*//*

        String[] from = {"listview_title", "listview_discription"};
        int[] to = {R.id.listview_item_title, R.id.listview_item_short_description};

        employeesAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.activity_landing_listview_layout, from, to);

        employeeLV.setAdapter(employeesAdapter);*/
        /*ImageView imageView = (ImageView) findViewById(R.id.listview_image);
        Context context = imageView.getContext();
        String url = "http://i.imgur.com/DvpvklR.png";
        for(int i=0;i<imageUrls.length;i++) {
            Picasso.with(context)
                    .load(url)
                    .resize(50,50)
                    .into(imageView);
        }*/

        ListView empListView = (ListView) findViewById(R.id.employeeLV);
        ArrayList<String> srcList = new ArrayList<String>(Arrays.asList(imageUrls));
        empListView.setAdapter(new CustomListAdapter(this, employeesList));

        empListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 1) {
                    isUserScroll = true;
                } else {
                    isUserScroll = false;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (isUserScroll) {

                    int lastInScreen = firstVisibleItem + visibleItemCount;
                    if (!isDataLoading && (lastInScreen == totalItemCount)) {
                        //load data

                        isDataLoading = true;
                    }
                }
            }
        });

        empListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(LandingActivity.this,DetailedInfoActivity.class);
                i.putExtra("Title",employeesList.get(position).getTitle());
                i.putExtra("FirstName",employeesList.get(position).getFirstName());
                i.putExtra("LastName",employeesList.get(position).getLastName());
                i.putExtra("Bio",employeesList.get(position).getBio());
                i.putExtra("Avatar",employeesList.get(position).getAvatar());
                startActivity(i);
            }
        });

    }


}



