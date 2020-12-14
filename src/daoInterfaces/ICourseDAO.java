
package daoInterfaces;

import java.util.List;
import models.Course;


public interface ICourseDAO {
   
  public Course askCourseData();
 
  public List<Course> getAllCourses();
    
  public int insertCourse(Course c);
   
  public Course getCourseById(int courseid);
  
  public boolean entityIdExists(int inputId, String tablename);
  
}
