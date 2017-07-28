package hstoneage.repository;

import hstoneage.exceptions.UserDoesNotExistException;
import hstoneage.model.Message;
import hstoneage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostsRepository {

    private Storage db;

    @Autowired
    public PostsRepository(Storage db) {
        this.db = db;
    }

    public void addMessage(Message message) {
        db.addMessage(message);
    }

    public List<Message> getUserTimeline(User user) throws UserDoesNotExistException {
        return db.getFollowedUsersMessages(user);
    }

    public List<Message> getUserWall(User user) throws UserDoesNotExistException {
        return db.getUserMessages(user);
    }
}
