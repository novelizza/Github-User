package com.example.user.bfaa_submission2.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sidiqpermana on 11/23/16.
 */

public class AccountEntity implements Parcelable {
    private int id;
    private String username;
    private String link;
    private String avatar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.username);
        dest.writeString(this.link);
        dest.writeString(this.avatar);
    }

    public AccountEntity() {
    }

    public AccountEntity(int id, String title, String description, String date) {
        this.id = id;
        this.username = title;
        this.link = description;
        this.avatar = date;
    }

    private AccountEntity(Parcel in) {
        this.id = in.readInt();
        this.username = in.readString();
        this.link = in.readString();
        this.avatar = in.readString();
    }

    public static final Creator<AccountEntity> CREATOR = new Creator<AccountEntity>() {
        @Override
        public AccountEntity createFromParcel(Parcel source) {
            return new AccountEntity(source);
        }

        @Override
        public AccountEntity[] newArray(int size) {
            return new AccountEntity[size];
        }
    };
}
