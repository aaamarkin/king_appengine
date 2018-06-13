package com.aaamarkin.kingofthehill;

import com.aaamarkin.kingofthehill.daos.UserDao;
import com.aaamarkin.kingofthehill.objects.Result;
import com.aaamarkin.kingofthehill.objects.User;
import com.aaamarkin.kingofthehill.services.MapService;
import com.google.cloud.datastore.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@RestController
public class Controller {

    @Autowired
    ServletContext context;

    @RequestMapping("/user/")
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
//
//    @RequestMapping("/signIn")
//    public String signIn(@RequestHeader("deviceId") String deviceId) {
//        // Message body required though ignored
//        UserDao dao = (UserDao) context.getAttribute("dao");
//
//        Optional<Key> keyOpt = dao.getUserKeyByExternalId(deviceId);
//
//        if (!keyOpt.isPresent()) {
//
//        }
//    }

//    @RequestMapping("/user/db")
//    public String dbCheck(Principal principal) {
//        // Message body required though ignored
////        validateUser(principal);
//
//        UserDao dao = (UserDao) context.getAttribute("dao");
//        // [START bookBuilder]
//        User user = new User.Builder()
//                .password("login1")
//                .creationDate((String) context.getAttribute("publishedDate"))
//                .build();
//        // [END bookBuilder]
//        Long id = dao.createUser(user);
//
//        return "Checking DB. Id = " + id;
//    }

    @RequestMapping("/user/{userId}/insert")
    public String insert(@PathVariable String userId) {
        // Message body required though ignored
//        validateUser(principal);

        UserDao dao = (UserDao) context.getAttribute("dao");
        // [START bookBuilder]
        User user = new User.Builder()
                .externalId(userId)
                .creationDate((String) context.getAttribute("publishedDate"))
                .build();
        // [END bookBuilder]
        Long id = dao.createUser(user).getId();

        return "Checking DB. Id = " + id;
    }

    @RequestMapping("/user/get")
    public String dbGet() {
        // Message body required though ignored

        UserDao dao = (UserDao) context.getAttribute("dao");
        // [START bookBuilder]

        Result<User> userResult = dao.listUsers(null);

        return userResult.result.toString();
    }

    @RequestMapping("/user/getMap")
    public byte[] downloadMap(Principal principal) {
        // Message body required though ignored

        UserDao dao = (UserDao) context.getAttribute("dao");

        Optional<User> userOpt = dao.getUserByExternalId(principal.getName());


        return MapService.getMapAsByteArray(MapService.getMap(userOpt.get().getXCoordinate(), userOpt.get().getYCoordinate()));

    }


    @RequestMapping("/user/anon_login")
    public String anonymousLogin(Principal principal) {
//        validateUser(principal);
//        UserDao dao = (UserDao) context.getAttribute("dao");
//        dao.getUserKeyByExternalId(deviceId).map(
//                key -> {
//
//                }
//        );
        return "A STUB";
    }


    @RequestMapping("/user/{userId}/test")
    public String test(@PathVariable Long userId) {

        UserDao dao = (UserDao) context.getAttribute("dao");

        User user = dao.getUserById(userId);

        return "User = " + user.toString();
    }

//    private void validateUser(String externalUserId) {
//        UserDao dao = (UserDao) context.getAttribute("dao");
//        dao.getUserKeyByExternalId(externalUserId).orElseThrow(
//                () -> new UserNotFoundException(externalUserId));
//    }

    @RequestMapping("/user/{userId}/delete")
    public String delete(@PathVariable Long userId) {

        UserDao dao = (UserDao) context.getAttribute("dao");

        dao.deleteUser(userId);

        return "Deleted? ";
    }

//    private void validateUser(String externalUserId) {
//        UserDao dao = (UserDao) context.getAttribute("dao");
//        dao.getUserKeyByExternalId(externalUserId).orElseThrow(
//                () -> new UserNotFoundException(externalUserId));
//    }

    private void validateUser(Principal principal) {
        System.out.println("validateUser = " + principal.getName());
        String userExternalId = principal.getName();
        UserDao dao = (UserDao) context.getAttribute("dao");
        dao.getUserKeyByExternalId(userExternalId)
                .orElseThrow(
                        () -> new UserNotFoundException(userExternalId));
    }

}
