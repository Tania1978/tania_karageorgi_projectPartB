
package daoInterfaces;

import java.util.List;
import models.Trainer;



public interface ITrainerDAO {
    
    public Trainer askTrainerData();

    public List<Trainer> getAllTrainers();
    
    public Trainer getTrainerById(int tid);

    public int insertTrainer(Trainer t);
    
    public boolean trainerAlreadyExists(Trainer t);

}
