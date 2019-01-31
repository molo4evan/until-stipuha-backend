package ftc.shift.untilstepuha.exceptions;

public class CannotAuthenticateException extends Exception {
    public CannotAuthenticateException(){
        super("Wrong password!");
    }
}
