package com.example.admin.serializabletest;

import java.io.Serializable;

/**
 * Created by admin on 2018/8/29.
 */

public class User implements Serializable{
    private static  final long serialVersionUID=1L;
     public int userId;
    public String userName;
    public boolean isMale;

    public User(){

    }
    public User(int userId, String userName, boolean isMale){
        this.userId=userId;
        this.userName=userName;
        this.isMale=isMale;
    }

    public String toString(){
        return userId+" "+userName+" "+isMale;
    }

}

