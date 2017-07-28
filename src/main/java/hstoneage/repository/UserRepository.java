package hstoneage.repository;

import hstoneage.exceptions.UserDoesNotExistException;
import hstoneage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private Storage db;

    @Autowired
    public UserRepository(Storage db) {
        this.db = db;
    }

    public User findById(String userId) throws UserDoesNotExistException {
        return db.getUser(userId);
    }

    public void addFollowConnection(User user, User toBeFollowed) throws UserDoesNotExistException {
        db.addFollowedUser(user, toBeFollowed);
    }
}
