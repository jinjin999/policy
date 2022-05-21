package com.aqua.anroid.policynoticeapp;

public class FavoriteData {
    /*private String title;
    private String content;
    //private int resId;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }*/
    private String member_id;
    private String member_name;
    private String member_content;


    public String getMember_id() {
        return member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public String getMember_content() {
        return member_content;
    }


    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public void setMember_content(String member_content) {
        this.member_content = member_content;
    }
}