package daoInterfaces;

import java.util.List;
import models.AssignmentsPerCourse;

/**
 *
 * @author tania
 */
public interface IAssignmentsPerCourseDAO {
    
     public List<AssignmentsPerCourse> getAssignmentsPerCourse(int courseId);

    public boolean assignmentAlreadyInCourse(int aid, int cid);

    public int linkAssignmentsToCourse(int aid, int cid);
}


