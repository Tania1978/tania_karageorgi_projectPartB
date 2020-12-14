
package daoClasses;

import daoInterfaces.IAssignmentDAO;
import daoInterfaces.IAssignmentsPerCourseDAO;
import daoInterfaces.ICourseDAO;
import database.DBConnection;
import database.FieldGetter;
import database.UserMenu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Assignment;


public class AssignmentDAO implements IAssignmentDAO{
static Scanner sc = new Scanner(System.in);
IAssignmentsPerCourseDAO apc = FactoryDAO.getAssignmentsPerCourseDAO();
  ICourseDAO cd = FactoryDAO.getCourseDAO();


    @Override
    public Assignment askAssignmentData() {
        UserMenu um = new UserMenu();
        Assignment a = null;
        try {
            a = new Assignment();
            System.out.println("To which course will this assignment belong?");
            int courseId = um.chooseEntity(sc, cd.getAllCourses(), "courses");
            a.setTitle(FieldGetter.getStringField(sc, "Please enter the Assignment Title:"));
            a.setDescription(FieldGetter.getStringField(sc, "Please enter the Assignment Description:"));
            FieldGetter.setSubmissionDate(sc, a, cd.getCourseById(courseId));
            insertAssignment(a);
            int asid = getIdOfCreatedAssignment(a.getTitle(),a.getDescription(),a.getSubDate());
            apc.linkAssignmentsToCourse(asid,courseId);
        } catch (ParseException ex) {
            Logger.getLogger(AssignmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }

    @Override
    public List<Assignment> getAllAssignments() {
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        
        List<Assignment> assignments = new ArrayList();
        String query= "SELECT * FROM assignments";
        
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while(rs.next()){
             Assignment a= new Assignment(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getDate("submission_date").toLocalDate());
            assignments.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
         } finally{
          try {
              con.close();
              pst.close();
          } catch (SQLException ex) {
              Logger.getLogger(AssignmentDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
        return assignments;
    }
    }

    @Override
    public int insertAssignment(Assignment a) {
         int result = 0;
        Connection con = null;
        PreparedStatement pst = null;
        if (assignmentAlreadyExists(a)){
            System.out.println("Assignment Already Exists");
        } else {
        try {
            String sql = "INSERT INTO assignments VALUES(id,?,?,?)";
            con = DBConnection.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, a.getTitle());
            pst.setString(2, a.getDescription());
            pst.setDate(3,java.sql.Date.valueOf(a.getSubDate()));
            result = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
          try {
              con.close();
              pst.close();
          } catch (SQLException ex) {
              Logger.getLogger(AssignmentDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
         if(result==1){
        System.out.println("Assignment " + a.getTitle() + "," + a.getDescription() + ". Submission Date: " + a.getSubDate()  + " successfully inserted!");
    }
    }
                  return result;
    }

    @Override
    public Assignment getAssignmentById(int id) {
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        Assignment a = null;

        String query = "SELECT * FROM assignments WHERE id = ?";
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                a =  new Assignment(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getDate("submission_date").toLocalDate());
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
        return a;
    }
    
    @Override
    public boolean assignmentAlreadyExists(Assignment a){
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement("SELECT  *  FROM assignments WHERE title = ? AND description = ? AND submission_date = ?");
            pst.setString(1, a.getTitle());
            pst.setString(2, a.getDescription());
            pst.setDate(3, java.sql.Date.valueOf(a.getSubDate()));
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
    
@Override
    public int getIdOfCreatedAssignment(String title, String subscription, LocalDate subDate){
        PreparedStatement pst = null;
        Connection con = null;
        int asid = 0;
        try {
           con = DBConnection.getConnection();
            pst = con.prepareStatement("SELECT  id  FROM assignments WHERE title = ? AND description = ? AND submission_date = ?");
            pst.setString(1, title);
            pst.setString(2, subscription);
            pst.setDate(3, java.sql.Date.valueOf(subDate));
           ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                asid = rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
         } finally{
          try {
              con.close();
              pst.close();
          } catch (SQLException ex) {
              Logger.getLogger(AssignmentDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
        return asid;
    }
    }
}

    
    

