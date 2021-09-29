package me.jansv.challenge.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Repos implements Parcelable {

    @SerializedName("html_url")
    @Expose
    private String htmlUrl;
    @SerializedName("owner")
    @Expose
    private User owner;

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    protected Repos(Parcel in) {
        htmlUrl = in.readString();
        owner = in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(htmlUrl);
        dest.writeParcelable(owner, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Repos> CREATOR = new Creator<Repos>() {
        @Override
        public Repos createFromParcel(Parcel in) {
            return new Repos(in);
        }

        @Override
        public Repos[] newArray(int size) {
            return new Repos[size];
        }
    };
}
