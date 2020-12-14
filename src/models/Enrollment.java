
package models;

import java.sql.Date;
import java.time.LocalDate;
import models.Course;
import models.Student;


public class Enrollment {
    private Student student;
    private Course course;
    private LocalDate enrolldate;
    private int tuitionfees;

    public Enrollment() {
    }

    public Enrollment(Student student, Course course, LocalDate  enrolldate, int tuitionfees) {
        this.student = student;
        this.course = course;
        this.enrolldate = enrolldate;
        this.tuitionfees = tuitionfees;
    }
    
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate  getEnrolldate() {
        return enrolldate;
    }

    public void setEnrolldate(LocalDate  enrolldate) {
        this.enrolldate = enrolldate;
    }

    public int getTuitionfees() {
        return tuitionfees;
    }

    public void setTuitionfees(int tuitionfees) {
        this.tuitionfees = tuitionfees;
    }

    @Override
    public String toString() {
        return student + " Enrolled on: " + enrolldate + ". Tuitionfees: " + tuitionfees + "euros /year.'";
    }
    
}
