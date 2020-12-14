
package database;


import daoClasses.FactoryDAO;
import daoInterfaces.IAssignmentDAO;
import daoInterfaces.IAssignmentsPerCourseDAO;
import daoInterfaces.ICourseDAO;
import daoInterfaces.IEnrollmentDAO;
import daoInterfaces.IScoreCardDAO;
import daoInterfaces.IStudentDAO;
import daoInterfaces.ITrainerDAO;
import daoInterfaces.ITrainerPerCourseDAO;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;


public class UserMenu {
    ICourseDAO c = FactoryDAO.getCourseDAO();
    IStudentDAO s = FactoryDAO.getStudentDAO();
    ITrainerDAO t = FactoryDAO.getTrainerDAO();
    IAssignmentDAO a = FactoryDAO.getAssignmentDAO();
    IEnrollmentDAO e = FactoryDAO.getEnrollmentDAO();
    IScoreCardDAO scr = FactoryDAO.getScoreCardDAO();
    ITrainerPerCourseDAO tpc = FactoryDAO.getTrainerPerCourseDAO();
    IAssignmentsPerCourseDAO apc = FactoryDAO.getAssignmentsPerCourseDAO();

    public void startProgram(Scanner sc) throws SQLException, ParseException {
        boolean mainchoice = true;
        while (mainchoice) {
            int choice = choosePath(sc);
            switch (choice) {
                case 0:
                    System.out.println("Thank you! Exiting program.");
                    System.exit(0);
                case 1:
                    printMenu(sc);
                    mainchoice = false;
                    break;
                case 2:
                    inputMenu(sc);
                    mainchoice = false;
                    break;
                default:
                    System.out.println("Invalid Input");
                    break;
            }
        }
    }

    public int choosePath(Scanner sc) {
        System.out.println("Welcome to the School's Management System.");
        System.out.println("Please choose one of the following options:");
        System.out.println("0 - to exit program");
        System.out.println("1 - to see the school lists");
        System.out.println("2 - to input your own data");
        System.out.println("--------------------");

        String input = sc.nextLine();

        if (input.equals("0")) {
            return 0;
        } else if (input.equals("1")) {
            return 1;
        } else if (input.equals("2")) {
            return 2;
        } else {
            return 3;
        }
    }

    public void printMenu(Scanner sc) throws SQLException, ParseException {

        boolean choose = true;
        int cid = 0;
        int sid = 0;

        while (choose) {
            System.out.println("\nPlease insert one of the following keys to choose:");
            System.out.println("0 -  to exit program");
            System.out.println("1  - to print all courses");
            System.out.println("2  - to print all students");
            System.out.println("3 -  to print all trainers");
            System.out.println("4 -  to print all assignments");
            System.out.println("5 -  to print students per course");
            System.out.println("6 -  to print trainers per course");
            System.out.println("7  - to print assignments per course");
            System.out.println("8 -  to print assignments per course per student");
            System.out.println("9  - to print all students who are enrolled to more than one courses");
            System.out.println("Any other key - to go back to main menu");
            System.out.println("-------------");

            String choice = sc.nextLine();

            switch (choice) {
                case "0":
                    System.out.println("Thank you! Exiting program.");
                    System.exit(0);
                case "1":
                    FieldGetter.printArrayList(c.getAllCourses());
                    break;
                case "2":
                    FieldGetter.printArrayList(s.getAllStudents());
                    break;
                case "3":
                    FieldGetter.printArrayList(t.getAllTrainers());
                    break;
                case "4":
                    FieldGetter.printArrayList(a.getAllAssignments());
                    break;
                case "5":
                      cid = chooseEntity(sc,c.getAllCourses(),"courses");
                    if (e.getStudentsPerCourse(cid).isEmpty()) {
                        System.out.println("Course" + cid + " has no students. Go back to the Main menu to enroll a Student to the Course.");
                    } else {
                        FieldGetter.printArrayList(e.getStudentsPerCourse(cid));
                    }
                    break;
                case "6":
                      cid = chooseEntity(sc,c.getAllCourses(),"courses");
                    if (tpc.getTrainersPerCourse(cid).isEmpty()) {
                        System.out.println("Course" + cid + " has no trainers linked. Go back to the Main menu to link a Trainer to the Course.");
                    } else {
                        FieldGetter.printArrayList(tpc.getTrainersPerCourse(cid));
                    }
                    break;
                case "7":
                      cid = chooseEntity(sc,c.getAllCourses(),"courses");
                    if (apc.getAssignmentsPerCourse(cid).isEmpty()) {
                        System.out.println("Course" + cid + " has no assignments. Go back to the Main menu to create an Assignment for the Course.");
                    } else {
                        FieldGetter.printArrayList(apc.getAssignmentsPerCourse(cid));
                    }
                    break;
                case "8":
                    cid = chooseEntity(sc,c.getAllCourses(),"courses");
                    sid = chooseStudentFromCourse(sc, cid);
                    if (scr.getAssignmentsPerCourseStudent(cid, sid).isEmpty()) {
                        System.out.println("Student " + sid + " has not submitted any assignments. Go back to the Main menu to submit an assignment for the Student.");
                    } else {
                        FieldGetter.printArrayList(scr.getAssignmentsPerCourseStudent(cid, sid));
                    }
                    break;
                case "9":
                    FieldGetter.printArrayList(e.getStudentsWithMoreThanOneCourse());
                    break;
                case "":
                    break;
                default:
                    startProgram(sc);
                    break;
            }
        }
    }

    public void inputMenu(Scanner sc) throws SQLException, ParseException {
        int cid = 0;
        int sid = 0;
        int tid = 0;
        int aid = 0;
        boolean choose = true;

        while (choose) {
            System.out.println("\nPlease insert one of the following keys to choose:");
            System.out.println("0 -  to exit program");
            System.out.println("1  - to add a course");
            System.out.println("2  - to add a student");
            System.out.println("3 -  to add a trainer");
            System.out.println("4 -  to add an assignment to a Course");
            System.out.println("5 -  to enroll a student to a course");
            System.out.println("6 -  to link a trainer to a course");
            System.out.println("7  - to submit an assignment for a student");
            System.out.println("B  - to go back to main menu");
            System.out.println("-------------");

            String choice = sc.nextLine();

            switch (choice) {
                case "0":
                    System.out.println("Thank you! Exiting program.");
                    System.exit(0);
                case "1":
                    c.insertCourse(c.askCourseData());
                    break;
                case "2":
                    s.insertStudent(s.askStudentData());
                    break;
                case "3":
                    t.insertTrainer(t.askTrainerData());
                    break;
                case "4":
                   a.askAssignmentData();
                    break;
                case "5":
                    cid = chooseEntity(sc,c.getAllCourses(),"courses");
                    sid = chooseEntity(sc,s.getAllStudents(),"students");
                    e.enrollStudentToCourse(sid, cid);
                    break;
                case "6":
                    cid = chooseEntity(sc,c.getAllCourses(),"courses");
                    tid = chooseEntity(sc,t.getAllTrainers(),"trainers");
                    tpc.linkTrainerToCourse(tid, cid);
                    break;
                case "7":
                    cid = chooseEntity(sc,c.getAllCourses(),"courses");
                    if (e.getStudentsPerCourse(cid).isEmpty()) {
                        System.out.println("There are no Students enrolled in Course " + cid + ". Choose option 5 to Enroll Students to the Course.");
                    } else {
                            sid = chooseStudentFromCourse(sc, cid);
                    }
                    if (apc.getAssignmentsPerCourse(cid).isEmpty()) {
                        System.out.println("There are no Assignments associated with Course " + cid + ".Please select option 4 to add an Assignment to the Course.");
                    } else {
                        aid = chooseAssignmentFromCourse(sc,cid);
                        scr.submitAssignmentForStudent(sid, cid, aid);
                    }
                        if (scr.assignmentAlreadyGraded(sid, cid, aid)) {
                            System.out.println("Assignment " + aid + " for Student " + sid + "  has already been graded.");
                            askGradesMenu(sc, sid, cid, aid);
                        } else {
                            scr.gradeAssignment(sid, cid, aid, scr.askAssignmentGrades(sc));
                        }
                    break;
                case "B":
                case "b":
                    startProgram(sc);
                    choose = false;
                    break;
                case "":
                    break;
                default:
                    System.out.println("Invalid Choice");
                    choose = true;
            }
        }
    }
    
    public void askGradesMenu(Scanner sc, int sid, int cid, int aid) throws SQLException, ParseException {
        System.out.println("Please insert one of the following keys:");
        System.out.println("Enter 1 -  To Update the Grades for the Student's Assignment");
        System.out.println("Any other key - To go back");
        System.out.println("-------------");
        String choice = sc.nextLine();
        switch (choice) {
            case "1":
                scr.gradeAssignment(sid, cid, aid, scr.askAssignmentGrades(sc));
                break;
            default:
                inputMenu(sc);
        }
    }
    
    public int chooseEntity(Scanner sc, List list, String tablename){
       int id = 0;
        boolean chosen = false;
        while (!chosen) {
            System.out.println("\nPlease choose one of the following " + tablename  + " by entering its id.");
            FieldGetter.printArrayList(list);
            String choice = sc.nextLine();
            if (isInteger(choice)) {
                id = Integer.parseInt(choice);
                if (c.entityIdExists(id, tablename)) {
                    chosen = true;
                } else {
                    System.out.println("There is no id " + id  + " in the list of " + tablename + ". Please choose another  id" + id);
                    chosen = false;
                }
            } else {
                System.out.println("Invalid Input");
                chosen = false;
            }
        }
        return id;
    }
  
    public static boolean isInteger(String str) {
        String trimmed = str.trim();
        if (trimmed == null) {
            return false;
        }
        if (trimmed.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
    
    public int chooseStudentFromCourse(Scanner sc, int cid){
        boolean loop=true;
        int sid=0;
        while(loop){
            sid = chooseEntity(sc, e.getStudentsPerCourse(cid), "students");
            if(!e.enrollmentExists(sid, cid)){
                System.out.println("Student " + sid + " is not enrolled in Course " + cid);
                loop=true;
            } else {
                loop=false;
            }
        }
        return sid;
    }
    
     public int chooseAssignmentFromCourse(Scanner sc, int cid){
        boolean loop=true;
        int aid=0;
        while(loop){
            aid = chooseEntity(sc, apc.getAssignmentsPerCourse(cid), "assignments");
            if(!apc.assignmentAlreadyInCourse(aid, cid)){
                System.out.println("Assignment " + aid + " is not in the Course List");
                loop=true;
            } else {
                loop=false;
            }
        }
        return aid;
    }

}
