package net.therap.therapshop.controller;

import net.therap.therapshop.exception.NoAccessException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * @author aditya.chakma
 * @since 6/21/21
 */
@ControllerAdvice
public class ErrorController {

    private static final Logger logger = Logger.getLogger("net.therap.therapshop");

    private static final String COMMAND_MESSAGE = "message";

    private static final String VIEW_ERROR = "error";

    @ExceptionHandler(SQLException.class)
    public ModelAndView handleSqlException(HttpServletRequest request,
                                           SQLException ex) {

        logger.info("SQL Exception Occurred:" + request.getRequestURI());
        logger.error("SQL Error: " + ex.getErrorCode() + ":" + ex.getMessage());
        logger.trace("Trace: " + Arrays.toString(ex.getStackTrace()));

        ModelAndView model = new ModelAndView();
        model.setViewName(VIEW_ERROR);
        model.addObject(COMMAND_MESSAGE, ex.getMessage());

        return model;
    }

    @ExceptionHandler(NoAccessException.class)
    public ModelAndView handleAccessException(HttpServletRequest request,
                                              NoAccessException ex) {

        logger.error(request.getRequestURI() + ": User have no Access");

        ModelAndView model = new ModelAndView();
        model.setViewName(VIEW_ERROR);
        model.addObject(COMMAND_MESSAGE, ex.getMessage());

        return model;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IO Exception occurred")
    @ExceptionHandler(IOException.class)
    public ModelAndView handleIOError(HttpServletRequest request,
                                      IOException ex) {

        logger.info("IO Error occurred:" + request.getRequestURI());
        logger.error("IO Error: " + ex.getMessage());
        logger.trace("Trace: " + Arrays.toString(ex.getStackTrace()));

        ModelAndView model = new ModelAndView();
        model.setViewName(VIEW_ERROR);
        model.addObject(COMMAND_MESSAGE, ex.getMessage());

        return model;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleUnknownException(HttpServletRequest request,
                                               Exception e) {
        logger.info("Unknown exception occurred:" + request.getRequestURI());
        logger.error("Unknown exception: " + e.getMessage());
        logger.trace("Trace: " + Arrays.toString(e.getStackTrace()));

        ModelAndView model = new ModelAndView();
        model.setViewName(VIEW_ERROR);
        model.addObject(COMMAND_MESSAGE, "unknown:" + e.getMessage());

        return model;
    }
}
