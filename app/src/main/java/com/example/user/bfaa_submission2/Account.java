package com.example.user.bfaa_submission2;

import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {

    private String username;

    public Account(){
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    protected Account(Parcel in) {
        username = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
    }
    @Override
    public int describeContents() {
        return 0;
    }
    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }
        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };
}
