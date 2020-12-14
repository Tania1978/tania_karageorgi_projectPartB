
package daoClasses;


import daoInterfaces.ITrainerDAO;
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
import models.Trainer;


public class TrainerDAO implements ITrainerDAO{
     static Scanner sc = new Scanner(System.in);
    
     
    @Override
    public Trainer askTrainerData() {
       
           Trainer  t = new Trainer();
            t.setFirstName(FieldGetter.getStringField(sc, "Please enter Trainer's First Name:"));
            t.setLastName(FieldGetter.getStringField(sc, "Please enter Trainer's Last Name:"));
            t.setSubject(FieldGetter.getStringField(sc, "Please enter Trainer's Subject:"));
        return t;
    }

    @Override
    public List<Trainer> getAllTrainers() {
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        
        List<Trainer> trainers = new ArrayList();
        String query= "SELECT * FROM trainers";
        
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while(rs.next()){
             Trainer t = new Trainer(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("Subject"));
              trainers.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } finally{
          try {
              con.close();
              pst.close();
          } catch (SQLException ex) {
              Logger.getLogger(TrainerDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
        return trainers;
    }
    
    @Override
    public int insertTrainer(Trainer t) {
      int result = 0;
       PreparedStatement pst = null;
       Connection con = null;
       if(trainerAlreadyExists(t)){
           System.out.println("Trainer already exists");
       } else {
        try {
            String sql = "INSERT INTO trainers (first_name, last_name, subject) VALUES(?,?,?)";
            con = DBConnection.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, t.getFirstName());
            pst.setString(2, t.getLastName());
            pst.setString(3, t.getSubject());
            result = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TrainerDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
          try {
              con.close();
              pst.close();
          } catch (SQLException ex) {
              Logger.getLogger(TrainerDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
         if(result==1){
        System.out.println("Trainer " + t.getFirstName() + "," + t.getLastName() + " successfully inserted!");
    }
       }
          return result;
    }

    @Override
    public Trainer getTrainerById(int tid) {
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        Trainer t = null;

        String query = "SELECT * FROM trainers WHERE id = ?";

        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement(query);
            pst.setInt(1, tid);
            rs = pst.executeQuery();
            while (rs.next()) {
                t = new Trainer(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("Subject"));
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
        return t;
    }
    
  @Override
     public boolean trainerAlreadyExists(Trainer t){
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement("SELECT  *  FROM trainers WHERE first_name = ? AND last_name = ? AND subject = ?");
            pst.setString(1, t.getFirstName());
            pst.setString(2, t.getLastName());
            pst.setString(3, t.getSubject());
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
}
    

