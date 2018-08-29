package com.example.admin.parcelabletest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2018/8/29.
 */

public class User  implements Parcelable{
    public int userId;
    public String userName;
    public boolean isMale;



    public User() {
    }

    public User(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(userId);
        out.writeString(userName);
        out.writeInt(isMale ? 1 : 0);

    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        isMale = in.readInt() == 1;

    }

    @Override
    public String toString() {
        return userId+" "+ userName+" "+ isMale;
    }

}
