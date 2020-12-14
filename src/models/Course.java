
package models;

import java.util.Date;
import java.time.LocalDate;


public class Course {
    private int cid;
     private String title; //eg CB8
    private String stream;  // eg JAVA
    private String type; // part time and full time
    private LocalDate startDate;
    private LocalDate  endDate;

    public Course(int cid, String title, String stream, String type, LocalDate  startDate, LocalDate  endDate) {
        this.cid = cid;
        this.title = title;
        this.stream = stream;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Course() {
    }

   
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate  getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate  startDate) {
        this.startDate = startDate;
    }

    public LocalDate  getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate  endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Course id " + cid + ".  " + title + ", " + stream + ", " + type + ". StartDate: " + startDate + ". EndDate: " + endDate + ".";
    }
    
    
}
