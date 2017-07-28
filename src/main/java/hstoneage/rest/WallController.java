package hstoneage.rest;

import hstoneage.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/{userId}/wall")
public class WallController {

    private PostController postController;

    @Autowired
    public WallController(PostController postController) {
        this.postController = postController;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Message> getUserMessages(@PathVariable String userId) {
        return postController.getUserMessages(userId);
    }

}
