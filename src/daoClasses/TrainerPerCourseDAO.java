
package daoClasses;

import daoInterfaces.ICourseDAO;
import daoInterfaces.IStudentDAO;
import daoInterfaces.ITrainerDAO;
import daoInterfaces.ITrainerPerCourseDAO;
import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.TrainerPerCourse;


public class TrainerPerCourseDAO implements  ITrainerPerCourseDAO {
    
@Override
    public List<TrainerPerCourse> getTrainersPerCourse(int courseId) {
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        List<TrainerPerCourse> trainers = new ArrayList();
        String query = "SELECT * FROM trainerspercourse WHERE course_id = ?";
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement(query);
            pst.setInt(1, courseId);
            rs = pst.executeQuery();
            while (rs.next()) {
                   ICourseDAO c =  FactoryDAO.getCourseDAO();
                   ITrainerDAO tr = FactoryDAO.getTrainerDAO();
                TrainerPerCourse t = new TrainerPerCourse(tr.getTrainerById(rs.getInt("trainerspercourse.trainer_id")), c.getCourseById(courseId));
                trainers.add(t);
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
        return trainers;
    }
    
@Override
    public boolean teacherAlreadyTeachesCourse(int tid, int cid) {
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        String query = "SELECT * FROM trainerspercourse WHERE trainer_id = ? AND course_id = ?";
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement(query);
            pst.setInt(1, tid);
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
    public int linkTrainerToCourse(int tid, int cid) {
        int result = 0;
        PreparedStatement pst = null;
        Connection con = null;
        if (teacherAlreadyTeachesCourse(tid, cid)) {
            System.out.println("Trainer " + tid + " already teaches Course " + cid);
        } else {
            try {
                TrainerPerCourse t = new TrainerPerCourse();
                String sql = "INSERT INTO trainerspercourse (trainer_id, course_id) VALUES(?,?)";
                con = DBConnection.getConnection();
                pst = con.prepareStatement(sql);
                pst.setInt(1, tid);
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
                if (result != 1) {
                    System.out.println("Link failed.");
                } else {
                    System.out.println("Trainer " + tid + " successfully linked to Course " + cid);
                }
            }
        }
        return result;
    }
}
