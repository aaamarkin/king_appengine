package com.aaamarkin.kingofthehill;

import com.aaamarkin.kingofthehill.daos.UserDao;
import com.aaamarkin.kingofthehill.objects.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.security.Principal;

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
    public String dbCheck(Principal principal) {
        // Message body required though ignored
        validateUser(principal);

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

//        UserDao dao = (UserDao) context.getAttribute("dao");
//        // [START bookBuilder]
//        User user = new User.Builder()
//                .login("login1")
//                .creationDate((String) context.getAttribute("publishedDate"))
//                .build();
//        // [END bookBuilder]
//        Long id = dao.createUser(user);

//        User user = dao.readUser( 1L);
        return "A STUB";
    }

//    @RequestMapping("/verify")
//    public String verify(@RequestHeader("oauthToken") String idToken) {
//
//        String uid;
//
//        try {
//            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdTokenAsync(idToken).get();
//            uid = decodedToken.getUid();
//
//        } catch (Exception e) {
//            uid = "Exception caught";
//        }
//
//
//        return "Token = " + uid;
//    }

    @RequestMapping("/login")
    public String anonymousLogin(@RequestHeader("deviceId") String deviceId) {

        return "A STUB";
    }

//    private void validateUser(String externalUserId) {
//        UserDao dao = (UserDao) context.getAttribute("dao");
//        dao.readUserKey(externalUserId).orElseThrow(
//                () -> new UserNotFoundException(externalUserId));
//    }

    private void validateUser(Principal principal) {
        String userExternalId = principal.getName();
        UserDao dao = (UserDao) context.getAttribute("dao");
        dao.readUserKey(userExternalId)
                .orElseThrow(
                        () -> new UserNotFoundException(userExternalId));
    }

}
