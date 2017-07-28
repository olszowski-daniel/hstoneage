package hstoneage.rest;

import hstoneage.exceptions.ServiceException;
import hstoneage.exceptions.UserAlreadyExistException;
import hstoneage.exceptions.UserDoesNotExistException;
import hstoneage.model.Message;
import hstoneage.model.User;
import hstoneage.repository.PostsRepository;
import hstoneage.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/{userId}/timeline")
public class TimelineController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private PostsRepository postsRepository;
    private UserRepository userRepository;

    @Autowired
    public TimelineController(PostsRepository postsRepository, UserRepository userRepository) {
        this.postsRepository = postsRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Message> getFollowedUsersMessages(@PathVariable String userId) {
        try {
            User user = userRepository.findById(userId);
            List<Message> messages = postsRepository.getUserTimeline(user);
            logger.debug("Got {} messages from user {} timeline.", messages.size(), userId);
            return messages;
        } catch (UserDoesNotExistException | UserAlreadyExistException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
