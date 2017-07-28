package hstoneage.repository;

import hstoneage.exceptions.UserAlreadyExistException;
import hstoneage.exceptions.UserDoesNotExistException;
import hstoneage.model.Message;
import hstoneage.model.User;
import org.springframework.core.task.support.ConcurrentExecutorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
class Storage {
    private Map<User, Deque<Message>> messages;
    private Map<User, Set<User>> follows;

    public Storage() {
        this.messages = new ConcurrentHashMap<>();
        this.follows = new ConcurrentHashMap<>();
    }

    // no need to be synchronized until PostController#createMessage() is synchronized
    void addMessage(Message message) {
        User user = message.getUser();
        try {
            addUser(user);
        } catch (UserAlreadyExistException e) {
            //intentionally left blank
        }
        this.messages.get(user).addFirst(message);
    }

    User getUser(String userId) throws UserDoesNotExistException {
        User u = new User(userId);
        if (userExist(u)) {
            return u;
        } else {
            throw new UserDoesNotExistException(userId);
        }
    }

    // no need to be synchronized until FollowController#addConnection() is synchronized
    void addFollowedUser(User user, User toBeFollowed) throws UserDoesNotExistException {
        if (!userExist(toBeFollowed)) {
            throw new UserDoesNotExistException(toBeFollowed.getUserId());
        }
        if (!userExist(user)) {
            throw new UserDoesNotExistException(user.getUserId());
        }
        follows.get(user).add(toBeFollowed);
    }

    synchronized List<Message> getUserMessages(User user) throws UserDoesNotExistException {
        if (!userExist(user)) {
            throw new UserDoesNotExistException(user.getUserId());
        }

        return new ArrayList<>(messages.get(user));
    }

    synchronized List<Message> getFollowedUsersMessages(User user) throws UserDoesNotExistException {
        if (!userExist(user)) {
            throw new UserDoesNotExistException(user.getUserId());
        }

        List<Message> messages = new ArrayList<>();
        Set<User> followed = follows.get(user);

        if (followed == null) { //no mappings
            followed = new HashSet<>();
        }

        for (User u : followed) {
            messages.addAll(this.messages.get(u));
        }

        Collections.sort(messages, Message::compareTo);

        return messages;
    }

    private synchronized void addUser(User user) throws UserAlreadyExistException {
        if (userExist(user)) {
            throw new UserAlreadyExistException(user.getUserId());
        }
        messages.put(user, new ConcurrentLinkedDeque<>());
        try {
            follows.put(user, new CopyOnWriteArraySet<>());
        } catch (Throwable t) {
            messages.remove(user);
            throw t;
        }
    }

    private boolean userExist(User user) {
        return messages.keySet().contains(user);
    }

}
