
package daoInterfaces;

import java.util.List;
import models.TrainerPerCourse;


public interface ITrainerPerCourseDAO {
    
    public List<TrainerPerCourse> getTrainersPerCourse(int courseId);

    public boolean teacherAlreadyTeachesCourse(int tid, int cid);

    public int linkTrainerToCourse(int tid, int cid);
}
