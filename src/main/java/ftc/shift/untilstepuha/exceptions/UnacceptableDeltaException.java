package ftc.shift.untilstepuha.exceptions;

public class UnacceptableDeltaException extends Exception {
    public UnacceptableDeltaException(){
        super("User balance cannot be less than 0.00!");
    }
}
