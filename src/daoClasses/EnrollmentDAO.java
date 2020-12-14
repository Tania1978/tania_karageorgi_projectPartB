package daoClasses;

import daoInterfaces.ICourseDAO;
import models.Enrollment;
import daoInterfaces.IEnrollmentDAO;
import daoInterfaces.IStudentDAO;
import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Student;

/**
 *
 * @author tania
 */
public class EnrollmentDAO implements IEnrollmentDAO {
    
      @Override
    public List<Enrollment> getStudentsPerCourse(int courseId) {
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        List<Enrollment> enrollments = new ArrayList();
        String query = "SELECT  * FROM enrollments WHERE course_id=?";
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement(query);
            pst.setInt(1, courseId);
            rs = pst.executeQuery();
            while (rs.next()) {
                ICourseDAO c = FactoryDAO.getCourseDAO();
                IStudentDAO s = FactoryDAO.getStudentDAO();
                Enrollment e = new Enrollment(s.getStudentById(rs.getInt("enrollments.student_id")), c.getCourseById(courseId), rs.getDate("enrollments.enrollment_date").toLocalDate(), rs.getInt("enrollments.tuition_fees"));
                enrollments.add(e);
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
        return enrollments;
    }

      @Override
    public List<Student> getStudentsWithMoreThanOneCourse() {
        PreparedStatement pst = null;
        Connection con = null;
        List<Student> students = new ArrayList();
        String query2 = "SELECT * FROM enrollments  GROUP BY student_id HAVING COUNT(*) > 1";
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement(query2);
            ResultSet rs = pst.executeQuery();
            System.out.println("Students that are enrolled to more than one Courses:");
            while (rs.next()) {
                StudentDAO s = new StudentDAO();
                Student s1 = s.getStudentById(rs.getInt("enrollments.student_id"));
                students.add(s1);
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
        return students;
    }

    @Override
   public int enrollStudentToCourse(int sid, int cid){
       int result = 0;
       PreparedStatement pst = null;
       Connection con = null;
       if(enrollmentExists(sid,cid)){
       System.out.println("Student " + sid+ " is already enrolled to Course " + cid);
        } else {
           try {
            String sql = "INSERT INTO enrollments (student_id, course_id, enrollment_date, tuition_fees) VALUES(?,?,?,?)";
           con = DBConnection.getConnection();
            pst = con.prepareStatement(sql);
            pst.setInt(1, sid);
            pst.setInt(2, cid);
            pst.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            pst.setInt(4, 500);
            result = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
          try {
              con.close();
              pst.close();
          } catch (SQLException ex) {
              Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
           if(result==1){
        System.out.println("Student " + sid + " successfully enrolled to Course " + cid);
    }
    }
}
        return result;
   }

    @Override
    public boolean enrollmentExists(int sid, int cid) {
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        String query = "SELECT * FROM enrollments WHERE student_id = ? AND course_id = ?";
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement(query);
            pst.setInt(1, sid);
            pst.setInt(2, cid);
            rs = pst.executeQuery();
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
}
