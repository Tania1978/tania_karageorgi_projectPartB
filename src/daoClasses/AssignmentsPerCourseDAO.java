package daoClasses;

import daoInterfaces.IAssignmentDAO;
import daoInterfaces.IAssignmentsPerCourseDAO;
import daoInterfaces.ICourseDAO;
import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AssignmentsPerCourse;

/**
 *
 * @author tania
 */
public class AssignmentsPerCourseDAO implements IAssignmentsPerCourseDAO {

     @Override
    public List<AssignmentsPerCourse> getAssignmentsPerCourse(int courseId) {
        PreparedStatement pst = null;
        Connection con = null;
        List<AssignmentsPerCourse> assignments = new ArrayList();
        String query = "SELECT * FROM assignmentspercourse WHERE course_id = ?";
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement(query);
            pst.setInt(1, courseId);
             ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                   ICourseDAO c =  FactoryDAO.getCourseDAO();
                   IAssignmentDAO a= FactoryDAO.getAssignmentDAO();
                   AssignmentsPerCourse ac= new AssignmentsPerCourse(c.getCourseById(courseId), a.getAssignmentById(rs.getInt("assignmentsPerCourse.assignment_id")));
                   assignments.add(ac);
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
        return assignments;
    }

    @Override
    public boolean assignmentAlreadyInCourse(int aid, int cid) {
         PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        String query = "SELECT * FROM assignmentsPerCourse WHERE assignment_id = ? AND course_id = ?";
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement(query);
            pst.setInt(1, aid);
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

    @Override
    public int linkAssignmentsToCourse(int aid, int cid) {
        int result = 0;
        PreparedStatement pst = null;
        Connection con = null;
        if (assignmentAlreadyInCourse(aid, cid)) {
            System.out.println("Assignment " + aid + " already linked to Course " + cid);
        } else {
            try {
                AssignmentsPerCourse a = new AssignmentsPerCourse();
                String sql = "INSERT INTO assignmentsPerCourse (assignment_id, course_id) VALUES(?,?)";
                con = DBConnection.getConnection();
                pst = con.prepareStatement(sql);
                pst.setInt(1, aid);
                pst.setInt(2, cid);
                result = pst.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    con.close();
                    pst.close();
                } catch (SQLException ex) {
                    Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (result == 1) {
                    System.out.println("Assignment " + aid + " successfully linked to Course " + cid);
                }
            }
        }
        return result;
    }
}
    

