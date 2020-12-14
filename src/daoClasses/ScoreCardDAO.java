
package daoClasses;

import daoInterfaces.ICourseDAO;
import daoInterfaces.IEnrollmentDAO;
import daoInterfaces.IScoreCardDAO;
import daoInterfaces.IStudentDAO;
import database.DBConnection;
import database.FieldGetter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Assignment;
import models.ScoreCard;


public class ScoreCardDAO implements IScoreCardDAO {

    @Override
    public List<ScoreCard> getAssignmentsPerCourseStudent(int cid, int sid) {
        PreparedStatement pst = null;
        Connection con = null;
        ScoreCard score = null;
        List<ScoreCard> scorecard = new ArrayList();
        String query2 = "SELECT * FROM scorescard WHERE student_id = ? AND course_id = ?";
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement(query2);
            pst.setInt(1, sid);
            pst.setInt(2, cid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ICourseDAO c = FactoryDAO.getCourseDAO();
                IStudentDAO s = FactoryDAO.getStudentDAO();
                AssignmentDAO adao = new AssignmentDAO();
                score = new ScoreCard(s.getStudentById(rs.getInt("student_id")), c.getCourseById(rs.getInt("course_id")), adao.getAssignmentById(rs.getInt("assignment_id")), rs.getInt("oral_grade"), rs.getInt("total_grade"));
                scorecard.add(score);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } finally {
            try {
                con.close();
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return scorecard;
    }
    
     @Override
    public boolean assignmentSubExists(int cid, int sid, int aid){
        PreparedStatement pst = null;
        Connection con = null;
        String query = "SELECT * FROM scorescard WHERE student_id = ? AND course_id = ? AND assignment_id = ?";
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement(query);
            pst.setInt(1, sid);
            pst.setInt(2, cid);
            pst.setInt(3, aid);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } finally {
            try {
                con.close();
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
   @Override
    public int submitAssignmentForStudent(int sid, int cid, int aid) {
        int result = 0;
        Connection con = null;
        PreparedStatement pst = null;
        if (assignmentSubExists(cid, sid, aid)) {
                System.out.println("Assignment " + aid + " has already been submitted for Student " + sid);
            } else {
                try {
                    String sql = "INSERT INTO scorescard (student_id, course_id, assignment_id, oral_grade, total_grade) VALUES(?,?,?,?,?)";
                    con = DBConnection.getConnection();
                    pst = con.prepareStatement(sql);
                    pst.setInt(1, sid);
                    pst.setInt(2, cid);
                    pst.setInt(3, aid);
                    pst.setInt(4, 0);
                    pst.setInt(5, 0);
                    pst.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        con.close();
                        pst.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("Assignment " + aid + " successfully submitted for Student" + sid);
                }
            }
            return result;
        }
    


   
    @Override
    public int[] askAssignmentGrades(Scanner sc) {
         ScoreCard scard = new ScoreCard();
         int[] grades = new int[2];
         while(true){
         int oral=FieldGetter.getIntField(sc, "Please enter Oral Grade by entering an integer from 1-20. If assignment has just been submitted please enter 0");
         int total = FieldGetter.getIntField(sc, "Please enter the Total Grade by entering an integer from 1-80. If assignment has just been submitted please enter 0");
         if(((oral>=0)&&(oral<=20)) && ((total>=0)&&(total<=80)) ){
         scard.setOralGrade(oral);
         scard.setTotalGrade(total);
         grades[0]=oral;
         grades[1]=total;
         break;
         } else {
             System.out.println("Integer numbers entered are outside the allowed range.");
         }
    }
         return grades;
}
    
    @Override
   public void gradeAssignment(int sid, int cid, int aid,int[] grades){
        Connection con = null;
        PreparedStatement pst = null;
                try {
                    String sql = "UPDATE  scorescard SET oral_grade = ? , total_grade = ? WHERE  student_id= ? AND course_id=? and assignment_id = ?";
                    con = DBConnection.getConnection();
                    pst = con.prepareStatement(sql);
                    pst.setInt(1, grades[0]);
                    pst.setInt(2, grades[1]);
                    pst.setInt(3, sid);
                    pst.setInt(4,cid);
                    pst.setInt(5, aid);
                    pst.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        con.close();
                        pst.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("Assignment " + aid + " was successfully submitted for Student with Id " + sid);
                }
            }
   
   public boolean assignmentAlreadyGraded (int sid, int cid, int aid){
        PreparedStatement pst = null;
        Connection con = null;
        String query = "SELECT oral_grade, total_grade FROM scorescard WHERE student_id = ? AND course_id = ? AND assignment_id = ?";
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement(query);
            pst.setInt(1, sid);
            pst.setInt(2, cid);
            pst.setInt(3, aid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
               int oralgrade = rs.getInt("oral_grade");
                int totalgrade = rs.getInt("total_grade");
                if ((oralgrade != 0) || (totalgrade != 0)) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } finally {
            try {
                con.close();
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
   

}
