
package com.aurriola.pagination.app.engine.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Name {

    @SerializedName("first")
    @Expose
    private String first;
    @SerializedName("last")
    @Expose
    private String last;
    @SerializedName("title")
    @Expose
    private String title;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return  first.substring(0,1).toUpperCase() + first.substring(1).toLowerCase() + " " + last.substring(0,1).toUpperCase() + last.substring(1).toLowerCase() ;
    }
}
