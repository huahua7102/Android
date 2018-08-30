package com.example.admin.ipcbycontentprovider;

/**
 * Created by admin on 2018/8/30.
 */

public class User {

    public  int userId;
    public String userName;
    public boolean isMale;
    public User(){

    }
    public String toString(){
        return userId+" "+userName+" "+isMale+"\n";
    }
}
