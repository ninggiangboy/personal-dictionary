package prj.dictionary.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthFailureConfig implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String messageError;
        if (exception instanceof LockedException) {
            messageError = "Your account has been locked!";
        } else if (exception instanceof BadCredentialsException) {
            messageError = "Invalid username or password!";
        } else {
            messageError = "Can't login!";
        }
        session.setAttribute("errorLogin", messageError);
        response.sendRedirect(request.getContextPath() + "/login?error&username=" + username);
    }
}
