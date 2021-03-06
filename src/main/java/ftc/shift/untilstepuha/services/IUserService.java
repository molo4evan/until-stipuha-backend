package ftc.shift.untilstepuha.services;


import ftc.shift.untilstepuha.exceptions.UnacceptableDeltaException;
import ftc.shift.untilstepuha.exceptions.CannotAuthenticateException;
import ftc.shift.untilstepuha.models.db.User;

import java.util.List;

public interface IUserService extends IService {
    User login(String username, String password)throws CannotAuthenticateException;

    User provideUserByID(String id);

    User provideUserByToken(String token);  //Null if no token

    List<User> provideUsers();

    void changeUserBalance(String id, double delta, boolean changeKarma) throws UnacceptableDeltaException;
}
