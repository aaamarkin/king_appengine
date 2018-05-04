package com.example.java.gettingstarted;

import com.example.java.gettingstarted.daos.UserDao;
import com.example.java.gettingstarted.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@RestController
public class Controller {

    @Autowired
    ServletContext context;

    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }

    /**
     * (Optional) App Engine health check endpoint mapping.
     * @see <a href="https://cloud.google.com/appengine/docs/flexible/java/how-instances-are-managed#health_checking"></a>
     * If your app does not handle health checks, a HTTP 404 response is interpreted
     *     as a successful reply.
     */
    @RequestMapping("/_ah/health")
    public String healthy() {
        // Message body required though ignored
        return "Still surviving.";
    }

    @RequestMapping("/db")
    public String dbCheck() {
        // Message body required though ignored

        UserDao dao = (UserDao) context.getAttribute("dao");
        // [START bookBuilder]
        User user = new User.Builder()
                .login("login1")
                .creationDate((String) context.getAttribute("publishedDate"))
                .build();
        // [END bookBuilder]
        Long id = dao.createUser(user);

        return "Checking DB. Id = " + id;
    }

    @RequestMapping("/get")
    public String dbGet() {
        // Message body required though ignored

        UserDao dao = (UserDao) context.getAttribute("dao");
//        // [START bookBuilder]
//        User user = new User.Builder()
//                .login("login1")
//                .creationDate((String) context.getAttribute("publishedDate"))
//                .build();
//        // [END bookBuilder]
//        Long id = dao.createUser(user);

        User user = dao.readUser( 1L);
        return "User = " + user.toString();
    }
}
