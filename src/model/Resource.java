package model;

import java.util.Date;

/**
 * Created by zac on 4/2/17.
 */
public class Resource {

    public String dateString;

    public Date date;


    public Resource(String dateString, Date theDate) {
        this.dateString = dateString;
        date = theDate;
    }
}
