package ftc.shift.untilstepuha.api;

import ftc.shift.untilstepuha.exceptions.UnacceptableDeltaException;
import ftc.shift.untilstepuha.exceptions.WrongPasswordException;
import ftc.shift.untilstepuha.models.db.Request;
import ftc.shift.untilstepuha.models.db.User;
import ftc.shift.untilstepuha.models.dto.*;
import ftc.shift.untilstepuha.services.IRequestService;
import ftc.shift.untilstepuha.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
public class Controller {

    private static final String LOGIN_PATH = "/login";
    private static final String REQUESTS_PATH = "/requests";
    private static final String USERS_PATH = "/users";
    private static final String BALANCE_PATH = "/balance";

    @Autowired
    private IUserService userService;

    @Autowired
    private IRequestService requestService;

    //1
    @PostMapping(LOGIN_PATH)
    public ResponseEntity<LoginInfo> login(@RequestBody User user){
        User newUser;
        try {
            newUser = userService.login(user.getName(), user.getPassword());
        } catch (WrongPasswordException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(new LoginInfo(newUser.getId(), newUser.getToken()));
    }

    //2
    @PostMapping(REQUESTS_PATH)
    public ResponseEntity<Request> createRequest(@RequestBody RequestCreation request,  @RequestHeader(value = "WWW-Authenticate") String token) {
        User user = userService.provideUserByToken(token);
        if( null != user) {
            Request result = requestService.createRequest(new Request(null, request.getName(), request.getDescription(), user.getId(), request.getValue(), 0));
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    //3
    @GetMapping(REQUESTS_PATH)
    public  ResponseEntity<Collection<Request>> getRequestsByUserId(@RequestParam(value = "userId", required = false) String userId){
        if (userId == null) {
            return requests();
        } else {
            if(null == userService.provideUserByID(userId)){
                return ResponseEntity.notFound().build();
            }
            Collection<Request> requests = requestService.provideRequestsByUserID(userId);
            return ResponseEntity.ok(requests);
        }
    }

    //4
    @GetMapping(REQUESTS_PATH + "/{id}")
    public ResponseEntity<Request> getRequestByID(@PathVariable String id){
        Request request = requestService.provideRequest(id);
        if( null == request ){
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(request);
    }

    //5
    @PatchMapping(REQUESTS_PATH + "/{id}")
    public ResponseEntity<?> donate(@PathVariable String id,@RequestBody DonateInfo donateInfo, @RequestHeader(value = "WWW-Authenticate") String token) {
        User user = userService.provideUserByToken(token);
        double donate = Double.parseDouble(donateInfo.getValue());
        if(null == user){
            return ResponseEntity.notFound().build();
        }
        if( user.getBalance() >= donate){
            try {
                Double result = requestService.donate(id, donate);
                userService.changeUserBalance(user.getId(), -result, true);
            } catch (UnacceptableDeltaException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        return ResponseEntity.ok().build();
    }

    //6
    @GetMapping(USERS_PATH)
    public ResponseEntity<Collection<OtherUserInfo>> users(){
        Collection<User> users = userService.provideUsers();
        Collection<OtherUserInfo> result = new ArrayList<>();
        for(User user : users){
            result.add(new OtherUserInfo(user.getId(), user.getName(), user.getKarma(), user.getMaxRequest()));
        }
        return ResponseEntity.ok(result);
    }

    //7
    @GetMapping(USERS_PATH + "/{id}")
    public ResponseEntity<IUserInfo> getUserById(@PathVariable String id, @RequestHeader(value = "WWW-Authenticate", required = false) String token){
        User user = userService.provideUserByID(id);
        if( null == user ){
            return ResponseEntity.notFound().build();
        }
        if ( user.getToken().equals(token)){
            return ResponseEntity.ok(new MyUserInfo(user.getId(), user.getName(), user.getKarma(), user.getMaxRequest(), user.getBalance()));
        }
        return ResponseEntity.ok(new OtherUserInfo(user.getId(), user.getName(), user.getKarma(), user.getMaxRequest()));
    }

    //8
    @DeleteMapping(REQUESTS_PATH + "/{id}")
    public ResponseEntity<?> deleteRequest(@PathVariable String id, @RequestHeader(value = "WWW-Authenticate") String token) {
        User user = userService.provideUserByToken(token);
        if( null != user){
            Request request = requestService.provideRequest(id);
            if (null == request) {
                return ResponseEntity.notFound().build();
            }
            if (user.getId().equals(request.getAuthorID())){
               Double money = requestService.deleteRequest(id);
                try {
                    userService.changeUserBalance(user.getId(), money, true);
                } catch (UnacceptableDeltaException e) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }
            else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        return ResponseEntity.ok().build();
    }

    //9
    @PatchMapping(BALANCE_PATH)
    public ResponseEntity<?> changeBalance(@RequestHeader(value = "WWW-Authenticate") String token, @RequestBody DonateInfo donateInfo){
        User user = userService.provideUserByToken(token);
        if( null != user){
            try {
                userService.changeUserBalance(user.getId(), Double.valueOf(donateInfo.getValue()), false);
            } catch (UnacceptableDeltaException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        return ResponseEntity.ok().build();
    }

    //10
    private ResponseEntity<Collection<Request>> requests() {
        Collection<Request> requests = requestService.provideRequests();
        return ResponseEntity.ok(requests);
    }

}
