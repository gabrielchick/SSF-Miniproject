package gabriel.investmentportfolio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle specific exception
    @ExceptionHandler(PortfolioNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlePortfolioNotFoundException(PortfolioNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404"; // Return a custom 404 error page
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleGeneralException(Exception ex) {
        ModelAndView mav = new ModelAndView("error/500"); // Custom 500 error page
        mav.addObject("errorMessage", "An unexpected error occurred. Please try again later.");
        return mav;
    }
}
