
package daoClasses;



import daoInterfaces.IStudentDAO;
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
import models.Student;
import java.util.logging.Level;
import java.util.logging.Logger;




public class StudentDAO implements IStudentDAO {
    static Scanner sc = new Scanner(System.in);

    @Override
    public Student askStudentData() {
        Student s=null;
        try {
            s = new Student();
            s.setFirstName(FieldGetter.getStringField(sc, "Please enter Student's First Name:"));
            s.setLastName(FieldGetter.getStringField(sc, "Please enter Student's Last Name:"));
            FieldGetter.setBirthDate(sc, s);
            
        } catch (ParseException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
  
    @Override
    public List<Student> getAllStudents() {
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        
        List<Student> students = new ArrayList();
        String query= "SELECT * FROM students ";
        
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while(rs.next()){
             Student s = new Student(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getDate("date_of_birth").toLocalDate());
              students .add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } finally{
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
    public int insertStudent(Student s) {
       int result = 0;
       PreparedStatement pst = null;
       Connection con = null;
       if(inputStudentAlreadyExists(s)){
           System.out.println("Student already Exists.");
       }else {
        try {
            String sql = "INSERT INTO students VALUES(id,?,?,?)";
            con = DBConnection.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, s.getFirstName());
            pst.setString(2, s.getLastName());
            pst.setDate(3, java.sql.Date.valueOf(s.getDateOfBirth()));
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
    }
         if(result==1){
        System.out.println( "Student " + s.getFirstName() + " "+ s.getLastName() + "- Date Of Birth: " + s.getDateOfBirth() + "  successfully inserted!");
    }
       }
          return result;
}

    @Override
    public Student getStudentById(int studentid) {
         PreparedStatement pst = null;
         Connection con = null;
         Student s = null;
        String query = "SELECT * FROM students WHERE id = ?";
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement(query);
            pst.setInt(1, studentid);
             ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                s = new Student(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getDate("date_of_birth").toLocalDate());
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
        return s;
    }

    @Override
     public boolean inputStudentAlreadyExists(Student s){
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement("SELECT  *  FROM students WHERE first_name = ? AND last_name = ? AND date_of_birth = ?");
            pst.setString(1, s.getFirstName());
            pst.setString(2, s.getLastName());
            pst.setDate(3, java.sql.Date.valueOf(s.getDateOfBirth()));
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
     

  
    
    

