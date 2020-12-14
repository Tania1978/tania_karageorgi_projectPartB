
package daoInterfaces;

import java.util.List;
import java.util.Scanner;
import models.Assignment;
import models.ScoreCard;


public interface IScoreCardDAO {
    
  
      public List<ScoreCard> getAssignmentsPerCourseStudent(int cid, int sid);
      
      public boolean assignmentSubExists(int cid, int sid, int aid);
      
      public int submitAssignmentForStudent(int sid, int cid, int aid);
      
     public int[] askAssignmentGrades(Scanner sc);
     
     public void gradeAssignment(int sid, int cid, int aid,int[] grades);
     
     public boolean assignmentAlreadyGraded (int sid, int cid, int aid);
}
