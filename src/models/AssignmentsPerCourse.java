
package models;

/**
 *
 * @author tania
 */
public class AssignmentsPerCourse {
    private Course c;
    private Assignment a;

    public AssignmentsPerCourse() {
    }

    public AssignmentsPerCourse(Course c, Assignment a) {
        this.c = c;
        this.a = a;
    }

    public Course getC() {
        return c;
    }

    public void setC(Course c) {
        this.c = c;
    }

    public Assignment getA() {
        return a;
    }

    public void setA(Assignment a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return  a +  " - Course id: " + c.getCid();
    }
    
    
}
