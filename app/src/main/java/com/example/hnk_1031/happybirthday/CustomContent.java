package com.example.hnk_1031.happybirthday;

/**
 * Created by hnk_1031 on 16/06/29.
 */
public class CustomContent {
    private String title;
    private String birthday;
    private String age;

    public CustomContent(String title,String birthday,String age){
        this.title = title;
        this.age = age;
        this.birthday=birthday;

    }

    public String getTitle(){
        return this.title;
    }

    public String getBirthday(){
        return  this.birthday;
    }

    public String getAge(){
        return this.age;
    }
}
