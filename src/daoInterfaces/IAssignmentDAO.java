
package daoInterfaces;

import java.time.LocalDate;
import java.util.List;
import models.Assignment;



public interface IAssignmentDAO {

    public Assignment askAssignmentData();

    public List<Assignment> getAllAssignments();

    public int insertAssignment(Assignment a);

    public Assignment getAssignmentById(int id);

    public boolean assignmentAlreadyExists(Assignment a);

    public int getIdOfCreatedAssignment(String title, String subscription, LocalDate subDate);

}
