
package models;

import java.util.Date;
import java.time.LocalDate;


public class Assignment {
    private int asid;
    private String title;
    private String description;
    private LocalDate subDate;
 

    public Assignment() {
    }

    public Assignment(int asid, String title, String description, LocalDate subDate) {
        this.asid = asid;
        this.title = title;
        this.description = description;
        this.subDate = subDate;
 
    }
    
    public int getAsid() {
        return asid;
    }

    public void setAsid(int asid) {
        this.asid = asid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desciption) {
        this.description = desciption;
    }

    public LocalDate getSubDate() {
        return subDate;
    }

    public void setSubDate(LocalDate subDate) {
        this.subDate = subDate;
    }

    @Override
    public String toString() {
        return "Assignment id" + asid + ": " + title + ", " + description + ". Submission Date: " + subDate + ". ";
    }

}
