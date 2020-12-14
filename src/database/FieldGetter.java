
package database;


import static database.UserMenu.isInteger;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import models.Assignment;
import models.Course;
import models.Student;


public class FieldGetter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("[dd/MM/yyyy][d/M/yyyy][d/M/yy]");

    public static String getStringField(Scanner sc, String message) {
        System.out.println(message);
        String value = sc.nextLine().trim();
        return (value);
    }
    
    public static int getIntField(Scanner sc, String message) {
        System.out.println(message);
        String input = sc.nextLine();
        int inputN = 0;
        boolean number = false;
        while (!number) {
            if (isInteger(input)) {
                inputN = Integer.parseInt(input);
                number = true;
            } else {
                System.out.println("Please enter an integer number");
            }
        }
        return inputN;
    }

    public static String getEnumField(Scanner sc, String message) {
        String choice = null;
        String enums = null;
        boolean type = false;
        while (!type) {
            System.out.println(message);
            System.out.println("1. Full-Time");
            System.out.println("2. Part-Time");
            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    enums = "full_time";
                    type = true;
                    break;
                case "2":
                    enums = "part_time";
                    type = true;
                    break;
                default:
                    System.out.println("Wrong input");
                    type = false;
                    break;
            }
        }
        return enums;
    }

    public static boolean isDateStringParsable(String dateStr) {
        try {
            LocalDate.parse(dateStr, FORMATTER);
            return true;
        } catch (Exception ex) {
            System.out.println("Invalid Date");
        }
        return false;
    }

    public static LocalDate getDateField(Scanner sc, String whichDate) throws ParseException {
        String msg = "Please enter " + whichDate + " Date:\nValid dates must strictly follow the format \"dd/MM/yyyy\".";
        String s = "";
        boolean loop = true;
        while (loop) {
            s = getStringField(sc, msg);
            if (!isDateStringParsable(s)) {
                loop = true;
            } else {
                loop = false;
            }
        }
        return LocalDate.parse(s, FORMATTER);
    }

    public static boolean isSubmissionDateWithinCourseDates(LocalDate subDate, Course c) {
        if (subDate.isAfter(c.getStartDate()) && (subDate.isBefore(c.getEndDate()))) {
            return true;
        } else {
            return false;
        }
    }

    public static LocalDate setSubmissionDate(Scanner sc, Assignment a, Course c) throws ParseException {
        LocalDate sub = null;
        boolean loop = true;
        while (loop) {
            sub = getDateField(sc, "submission");
            if (isSubmissionDateWithinCourseDates(sub, c)) {
                a.setSubDate(sub);
                loop = false;
            } else {
                System.out.println("Students should submit the Assignment within the duration of the Course. Please enter a Submisiion Date between " + c.getStartDate() + " and " + c.getEndDate());
                loop = true;
            }
        }
        return sub;
    }

    public static boolean isEndDateAfterStartDate(LocalDate startDate, LocalDate endDate) {
        int comparison = startDate.compareTo(endDate);
        if (comparison >= 0) {
            return false;
        } else {
            return true;
        }
    }

    public static void setCourseDates(Scanner sc, Course c) throws ParseException {
        LocalDate start = getDateField(sc, "start");
        c.setStartDate(start);
        boolean loop = true;
        while (loop) {
            LocalDate end = getDateField(sc, "end");
            if (isEndDateAfterStartDate(start, end)) {
                c.setEndDate(end);
                loop = false;
            } else {
                System.out.println("End Date must be after Start Date!");
                loop = true;
            }
        }
    }

    public static boolean isBirthDateWithinAgeLimits(LocalDate dob) {
        Period period = Period.between(dob, LocalDate.now());
        int age = period.getYears();
        if ((age >= 18) && (age <= 60)) {
            return true;
        } else {
            return false;
        }
    }

    public static LocalDate setBirthDate(Scanner sc, Student s) throws ParseException {
        LocalDate dob = null;
        boolean loop = true;
        while (loop) {
            dob = getDateField(sc, "Date of Birth");
            if (isBirthDateWithinAgeLimits(dob)) {
                s.setDateOfBirth(dob);
                loop = false;
            } else {
                System.out.println("Only Students between 18 - 60 years old are accepted");
                loop = true;
            }
        }
        return dob;
    }

    public static <T extends List> void printArrayList(T list) {
        list.forEach((t) -> {
            System.out.println(t);
        });
    }


}
