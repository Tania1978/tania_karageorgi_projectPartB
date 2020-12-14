
package daoClasses;

import daoInterfaces.IAssignmentDAO;
import daoInterfaces.IAssignmentsPerCourseDAO;
import daoInterfaces.ICourseDAO;
import daoInterfaces.IEnrollmentDAO;
import daoInterfaces.IScoreCardDAO;
import daoInterfaces.IStudentDAO;
import daoInterfaces.ITrainerDAO;
import daoInterfaces.ITrainerPerCourseDAO;

public class FactoryDAO  {
   

    public static  ICourseDAO getCourseDAO() {
        return new CourseDAO();
    }
    

    public  static IStudentDAO getStudentDAO() {
        return new StudentDAO();
    }
    

    public static  ITrainerDAO getTrainerDAO() {
        return new TrainerDAO();
    }
    

    public  static IAssignmentDAO getAssignmentDAO() {
        return new AssignmentDAO();
    }
    

    public  static IEnrollmentDAO getEnrollmentDAO() {
        return new EnrollmentDAO();
    }
    

    public static  IScoreCardDAO getScoreCardDAO() {
        return new ScoreCardDAO();
    }
    

    public static ITrainerPerCourseDAO getTrainerPerCourseDAO(){
        return new TrainerPerCourseDAO();
}
    
    public  static IAssignmentsPerCourseDAO getAssignmentsPerCourseDAO() {
        return new AssignmentsPerCourseDAO();
    }
}