package com.everybody.model;

import java.io.Serializable;

/**
 * Created by Natanael Fonseca on 27/06/13.
 */
public class Contact implements Serializable {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phoneNumer;
    private String webSite;
   private Long photoId;

    public Contact(Long id, String name, String lastName, String email, String phoneNumer,String webSite) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumer = phoneNumer;
        this.webSite = webSite;
    }

    public Contact(Long id, String name, String lastName, String email) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumer() {
        return phoneNumer;
    }

    public void setPhoneNumer(String phoneNumer) {
        this.phoneNumer = phoneNumer;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
}
