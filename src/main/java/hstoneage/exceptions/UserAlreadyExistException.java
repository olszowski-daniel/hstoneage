package hstoneage.exceptions;

public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(String userId) {
        super(String.format("User %s exist", userId));
    }

}
