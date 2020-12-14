
package models;


public class ScoreCard {
    private Student s;
    private Course c;
    private Assignment a;
    private int oralGrade;
    private int totalGrade;

    public ScoreCard() {
    }

    public ScoreCard(Student s, Course c,  Assignment a, int oralGrade, int totalGrade) {
        this.c = c;
        this.s = s;
        this.a = a;
        this.oralGrade = oralGrade;
        this.totalGrade = totalGrade;
    }

    public Course getC() {
        return c;
    }

    public void setC(Course c) {
        this.c = c;
    }

    public Student getS() {
        return s;
    }

    public void setS(Student s) {
        this.s = s;
    }

    public Assignment getA() {
        return a;
    }

    public void setA(Assignment a) {
        this.a = a;
    }

    public int getOralGrade() {
        return oralGrade;
    }

    public void setOralGrade(int oralGrade) {
        this.oralGrade = oralGrade;
    }

    public int getTotalGrade() {
        return totalGrade;
    }

    public void setTotalGrade(int totalGrade) {
        this.totalGrade = totalGrade;
    }

    @Override
    public String toString() {
        return  s.getFirstName() + " " + s.getLastName() + "- " + "Course Id " + c.getCid() + ". " + c.getTitle() + ". " + c.getStream() + ", " + c.getType() + ". "+ "Assignment id: " + a.getAsid() + ". " + a.getTitle() + ", " + a.getDescription() + 
                " Oral Mark: " + oralGrade + " Total Mark: " + totalGrade + ".";
    }
    
    
    
}
