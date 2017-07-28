package hstoneage.exceptions;

public class UserDoesNotExistException extends RuntimeException {

    public UserDoesNotExistException(String userId) {
        super(String.format("User %s does not exist", userId));
    }

}
