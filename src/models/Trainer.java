
package models;


public class Trainer {
    private int tid;
     private String firstName;
    private String lastName;
    private String subject;

    public Trainer(int tid, String firstName, String lastName, String subject) {
        this.tid = tid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
    }

    public Trainer() {
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Trainer " + tid + ":  " + firstName + ", " + lastName + ". Subject: " + subject ;
    }
    
    
}
