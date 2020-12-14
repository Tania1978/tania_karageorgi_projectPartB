
package models;

import java.time.LocalDate;
import java.util.Date;


public class Student {
    private int sid;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    public Student() {
    }

    public Student(int sid, String firstName, String lastName, LocalDate dateOfBirth) {
        this.sid = sid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Student id " + getSid() + ". " + firstName + " " + lastName + ". Date Of Birth: " + dateOfBirth + '.';
    }
    
    
}
