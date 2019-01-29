package ftc.shift.untilstepuha.services;

import ftc.shift.untilstepuha.exceptions.UnacceptableDeltaException;
import ftc.shift.untilstepuha.exceptions.WrongPasswordException;
import ftc.shift.untilstepuha.models.db.User;
import ftc.shift.untilstepuha.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private IUserRepository userRepository;

    private int MAX_MODULE_KARMA = 5;
    private int MAX_DELTA_SUMM = 500;

    @Autowired
    public UserService(@Qualifier("inMemoryUserRepository") IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) throws WrongPasswordException {
        User user = userRepository.fetchUserByName(username);

        if (user != null){
            if(user.getPassword().equals(password)){
                return user;
            } else {
                throw new WrongPasswordException();
            }
        } else {
            user = new User(UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    username, password, 0,
                    getRequestByKarma(0), 0);
            userRepository.addUser(user);
            return user;
        }
    }

    @Override
    public User provideUserByID(String id) {
        return userRepository.fetchUserByID(id);
    }

    @Override
    public User provideUserByToken(String token) {
        return userRepository.fetchUserByToken(token);
    }

    @Override
    public List<User> provideUsers() {
        return userRepository.fetchUsers();
    }

    @Override
    public void changeUserBalance(String id, double delta, boolean changeKarma) throws UnacceptableDeltaException {
        User user = userRepository.fetchUserByID(id);

        if(user == null){
            return;
        }

        if(user.getBalance() + delta < 0){
            throw new UnacceptableDeltaException();
        } else {
            user.setBalance(user.getBalance() + delta);
        }

        if(changeKarma){
            int newKarma = changeKarma(user.getKarma(), delta);
            user.setKarma(newKarma);
            user.setMaxRequest(getRequestByKarma(newKarma));
        }

        userRepository.updateUser(user);
    }

    private double getRequestByKarma(int karma){
        int coefficient = karma + MAX_MODULE_KARMA;
        return coefficient * MAX_DELTA_SUMM;
    }

    private int changeKarma(int karma, double delta){
        int deltaKarma = (int)delta / MAX_DELTA_SUMM;
        if (karma + deltaKarma > MAX_MODULE_KARMA){
            return MAX_MODULE_KARMA;
        }
        if (karma + deltaKarma < -MAX_MODULE_KARMA){
            return -MAX_MODULE_KARMA;
        }
        return karma;
    }
}
