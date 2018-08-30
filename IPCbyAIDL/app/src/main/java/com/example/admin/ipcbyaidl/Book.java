package com.example.admin.ipcbyaidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2018/8/30.
 * Book实现序列化
 */

public class Book implements Parcelable {

    public int bookId;
    public String bookName;

    public Book(int bookId,String bookName){
        this.bookId=bookId;
        this.bookName=bookName;
    }
    //内容描述
    @Override
    public int describeContents() {
        return 0;
    }

    //序列化
    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(bookId);
        out.writeString(bookName);
    }

    //反序列化
    public  static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>(){
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
    public Book(Parcel in) {
        bookId=in.readInt();
        bookName=in.readString();

    }
    public String toString() {
        return String.format("[bookId:%s, bookName:%s]", bookId, bookName);
    }
}
