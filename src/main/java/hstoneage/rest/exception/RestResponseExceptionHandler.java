package hstoneage.rest.exception;

import hstoneage.exceptions.ServiceException;
import hstoneage.exceptions.UserAlreadyExistException;
import hstoneage.exceptions.UserDoesNotExistException;
import hstoneage.rest.PostController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackageClasses = PostController.class)
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String response = "Request body not readable, please check json message.";
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = {ServiceException.class})
    @ResponseBody
    public ResponseEntity<Object> handleServiceException(ServiceException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(getResponseBodyMessage(ex));
    }

    @ExceptionHandler(value = {UserDoesNotExistException.class})
    @ResponseBody
    public ResponseEntity<Object> handleUserDoesNotExistException(UserDoesNotExistException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getResponseBodyMessage(ex));
    }

    @ExceptionHandler(value = {UserAlreadyExistException.class})
    @ResponseBody
    public ResponseEntity<Object> handleUserAlreadyExistException(UserAlreadyExistException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(getResponseBodyMessage(ex));
    }

    private String getResponseBodyMessage(Exception ex) {
        return "Request failed: " + ex.getMessage();
    }

}
