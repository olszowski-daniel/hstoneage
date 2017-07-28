package hstoneage.rest;

import hstoneage.exceptions.ServiceException;
import hstoneage.exceptions.UserAlreadyExistException;
import hstoneage.exceptions.UserDoesNotExistException;
import hstoneage.model.Message;
import hstoneage.model.User;
import hstoneage.model.dto.MessageDTO;
import hstoneage.repository.PostsRepository;
import hstoneage.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/{userId}/posts")
public class PostController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private PostsRepository postsRepository;
    private UserRepository userRepository;

    @Autowired
    public PostController(PostsRepository postsRepository, UserRepository userRepository) {
        this.postsRepository = postsRepository;
        this.userRepository = userRepository;
    }

    //having this method synchronized we are sure that messages collection is always ordered
    @RequestMapping(method = RequestMethod.POST)
    public synchronized ResponseEntity<?> createMessage(@PathVariable String userId, @RequestBody @NotNull MessageDTO messageBody) {
        try {
            String msg = messageBody.getMessage();
            if (msg != null && !msg.trim().isEmpty() && msg.length() <= 140) {
                User user = new User(userId);
                Message message = new Message(user, messageBody.getMessage());
                postsRepository.addMessage(message);
                logger.debug("Successfully added message for user {}", userId);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().body("Message is empty or too long.");
            }
        } catch (Exception e) {
            logger.warn(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Message> getUserMessages(@PathVariable String userId) {
        try {
            User user = userRepository.findById(userId);
            List<Message> messages = postsRepository.getUserWall(user);
            logger.debug("Got {} messages from users' {} wall.", messages.size(), userId);
            return messages;
        } catch (UserDoesNotExistException | UserAlreadyExistException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }

    }

}
