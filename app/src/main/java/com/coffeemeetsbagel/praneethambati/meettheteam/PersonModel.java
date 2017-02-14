package com.coffeemeetsbagel.praneethambati.meettheteam;

import java.util.Comparator;

/**
 * Created by Praneeth Ambati on 2/14/2017.
 */

public class PersonModel implements Comparator<PersonModel> {
    private String avatar;
    private String firstName;
    private String lastName;
    private String bio;
    private String title;
    private int id;



    public PersonModel(String avatar, String firstName, String lastName, String bio, String title, int id) {
        this.avatar = avatar;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.title = title;
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public int compare(PersonModel o1, PersonModel o2) {
        return o1.getFirstName().compareTo(o2.getFirstName());
    }
}
