package taniakarageorgiprojectpartb;

import database.UserMenu;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TaniakarageorgiProjectPartB {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            UserMenu cm = new UserMenu();
            try {
                cm.startProgram(sc);
            } catch (SQLException ex) {
                Logger.getLogger(TaniakarageorgiProjectPartB.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(TaniakarageorgiProjectPartB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
    

       
       
       
       
       
       
       
     
        

 
