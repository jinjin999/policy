package com.aqua.anroid.policynoticeapp.Favorite;


public class FavoriteData {
    public String item_name;
    public String item_content;


    public FavoriteData( String item_name, String item_content) {
        this.item_name = item_name;
        this.item_content = item_content;
    }


    public String getItem_name() { return item_name; }

    public String getItem_content() {
        return item_content;
    }


    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public void setItem_content(String item_content) {
        this.item_content = item_content;
    }


}