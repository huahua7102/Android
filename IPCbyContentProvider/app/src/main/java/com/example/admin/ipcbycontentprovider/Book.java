package com.example.admin.ipcbycontentprovider;

/**
 * Created by admin on 2018/8/30.
 */

public class Book {
    public  int bookId;
    public String bookName;

    public Book(){

    }

    public String toString(){
        return bookId+" "+bookName+"\n";
    }
}
