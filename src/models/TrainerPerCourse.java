
package models;

import models.Course;
import models.Trainer;


public class TrainerPerCourse {

    private Trainer trainer;
    private Course course;

    public TrainerPerCourse(Trainer trainer, Course course) {
        this.trainer = trainer;
        this.course = course;
    }

    public TrainerPerCourse() {
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    @Override
    public String toString() {
        return  trainer + " -  Course " + course.getCid() + ".";
    } 
}
