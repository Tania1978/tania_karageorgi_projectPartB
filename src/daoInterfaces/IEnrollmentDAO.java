
package daoInterfaces;

import models.Enrollment;
import java.util.List;
import models.Student;


public interface IEnrollmentDAO {
    
    public List<Enrollment> getStudentsPerCourse(int courseId);
    
    public List <Student> getStudentsWithMoreThanOneCourse();
    
    public int enrollStudentToCourse(int sid, int cid);
    
    public boolean enrollmentExists(int sid, int cid);
    
}
