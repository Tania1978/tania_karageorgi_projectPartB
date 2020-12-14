
package daoClasses;

import daoInterfaces.ICourseDAO;
import database.DBConnection;
import database.FieldGetter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Course;



public class CourseDAO  implements ICourseDAO{
    static Scanner sc = new Scanner(System.in);
    
   @Override
    public Course askCourseData() {
        Course c = null;
        try {
            c = new Course();
            c.setTitle(FieldGetter.getStringField(sc, "Please enter the Course Title:"));
            c.setStream(FieldGetter.getStringField(sc, "Please enter the Course Stream:"));
            c.setType(FieldGetter.getEnumField(sc, "Please select the Course Type:"));
            FieldGetter.setCourseDates(sc,c);
         
        } catch (ParseException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return c;
    }

    @Override
    public List<Course> getAllCourses() {
        PreparedStatement pst = null;
        Connection con = null;
        List<Course> courses = new ArrayList();
        String query= "SELECT * FROM courses"; 
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery();
            while(rs.next()){
             Course c= new Course(rs.getInt("id"), rs.getString("title"), rs.getString("stream"), rs.getString("type"), rs.getDate("start_date").toLocalDate(), rs.getDate("end_date").toLocalDate());
             courses.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
         } finally{
          try {
              con.close();
              pst.close();
          } catch (SQLException ex) {
              Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
        return courses;
    }
    }

    @Override
    public int insertCourse(Course c) {
        int result = 0;
        Connection con = null;
        PreparedStatement pst = null;
        if(courseAlreadyExists(c)){
            System.out.println("Course already exists.");
        } else {
        try {
            String sql = "INSERT INTO courses (title, stream, type, start_date, end_date) VALUES(?,?,?,?,?)";
            con = DBConnection.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, c.getTitle());
            pst.setString(2, c.getStream());
            pst.setString(3, c.getType());
            pst.setDate(4, java.sql.Date.valueOf(c.getStartDate()));
            pst.setDate(5, java.sql.Date.valueOf(c.getEndDate()));
            result = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (result == 1) {
            System.out.println("Course " + c.getTitle() + ", " + c.getStream() + ", " + c.getType() + " Start Date " + c.getStartDate() + " End Date " + c.getEndDate() + " successfully inserted!");
        }
        }
        return result;
    }
  
    public boolean entityIdExists(int inputId, String tablename) {
        Connection con = null;
        PreparedStatement pst = null;
        String sql ="SELECT  *  FROM " + tablename +"  WHERE id = ?";
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement(sql );
            pst.setInt(1, inputId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    @Override
    public Course getCourseById(int courseid) {
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        Course c = null;
        String query = "SELECT * FROM courses WHERE id = ?";
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement(query);
            pst.setInt(1, courseid);
            rs = pst.executeQuery();
            while (rs.next()) {
                c = new Course(rs.getInt("id"), rs.getString("title"), rs.getString("stream"), rs.getString("type"), rs.getDate("start_date").toLocalDate(), rs.getDate("end_date").toLocalDate());
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } finally {
            try {
                con.close();
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return c;
    }


  public boolean courseAlreadyExists(Course c){
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement("SELECT  *  FROM courses WHERE title = ? AND stream = ? AND type = ? AND start_date=? AND end_date=?");
            pst.setString(1, c.getTitle());
            pst.setString(2, c.getStream());
            pst.setString(3, c.getType());
            pst.setDate(4, java.sql.Date.valueOf(c.getStartDate()));
            pst.setDate(5, java.sql.Date.valueOf(c.getEndDate()));
            rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}