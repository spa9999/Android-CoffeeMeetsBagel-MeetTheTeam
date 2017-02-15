package com.coffeemeetsbagel.praneethambati.meettheteam;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;




public class LandingActivity extends AppCompatActivity{

    //Variable Declaration
    List<PersonModel> employeesList;
    private String[] imageUrls;

    //Layout related Variable
    ListView employeeLV;
    private boolean isUserScroll;
    private boolean isDataLoading;
    SearchView search;
    CustomListAdapter customListAdapter;

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


        //Setting up the ListView containing FirstName, Avatar, Title
        ListView empListView = (ListView) findViewById(R.id.employeeLV);
        ArrayList<String> srcList = new ArrayList<String>(Arrays.asList(imageUrls));
       customListAdapter= new CustomListAdapter(this, employeesList);
        empListView.setAdapter(customListAdapter);

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

        //On item click, Collecting information of that particular cell and passing the next Activity
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

        search = (SearchView) findViewById(R.id.searchView1);
        //*** setOnQueryTextListener **
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customListAdapter.filter(newText);
                return false;
            }
        });

    }



}



