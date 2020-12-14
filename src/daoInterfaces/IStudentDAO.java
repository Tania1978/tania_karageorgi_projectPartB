
package daoInterfaces;

import java.util.List;
import models.Student;


public interface IStudentDAO {

    public Student askStudentData();

    public List<Student> getAllStudents();

    public int insertStudent(Student s);

    public Student getStudentById(int studentid);
    
    public boolean inputStudentAlreadyExists(Student s);

}
