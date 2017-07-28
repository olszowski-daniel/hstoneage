package hstoneage.rest;

import hstoneage.exceptions.ServiceException;
import hstoneage.exceptions.UserAlreadyExistException;
import hstoneage.exceptions.UserDoesNotExistException;
import hstoneage.model.User;
import hstoneage.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{userId}/follow/{toBeFollowedUserId}")
public class FollowController {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private UserRepository userRepository;

    @Autowired
    public FollowController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public synchronized ResponseEntity<?> addConnection(@PathVariable String userId, @PathVariable String toBeFollowedUserId) {
        try {
            User user1 = userRepository.findById(userId);
            User user2 = userRepository.findById(toBeFollowedUserId);

            userRepository.addFollowConnection(user1, user2);
            logger.info("Successfully added connection: {} follows {}", userId, toBeFollowedUserId);
            return ResponseEntity.ok().build();
        } catch (UserDoesNotExistException | UserAlreadyExistException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
