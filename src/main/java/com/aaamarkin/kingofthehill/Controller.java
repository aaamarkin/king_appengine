package com.aaamarkin.kingofthehill;

import com.aaamarkin.kingofthehill.daos.DataStoreDao;
import com.aaamarkin.kingofthehill.daos.MapObjectDao;
import com.aaamarkin.kingofthehill.daos.UserDao;
import com.aaamarkin.kingofthehill.objects.MapObject;
import com.aaamarkin.kingofthehill.objects.Result;
import com.aaamarkin.kingofthehill.objects.User;
import com.aaamarkin.kingofthehill.objects.UserInfo;
import com.aaamarkin.kingofthehill.services.MapService;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class Controller {

    @Autowired
    ServletContext context;

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


    @RequestMapping("/user/db")
    public String createFakeMap(Principal principal) {
        // Message body required though ignored


        MapObjectDao dao = (MapObjectDao) context.getAttribute("dao");
        // [START bookBuilder]

        MapObject mapObject = new MapObject.Builder()
                .type("1").xCoordinate(1L).yCoordinate(1L).build();

        dao.createMapObject(mapObject);

        mapObject = new MapObject.Builder()
                .type("2").xCoordinate(-1L).yCoordinate(-2L).build();

        dao.createMapObject(mapObject);

        mapObject = new MapObject.Builder()
                .type("3").xCoordinate(-3L).yCoordinate(-2L).build();

        dao.createMapObject(mapObject);

        mapObject = new MapObject.Builder()
                .type("4").xCoordinate(-2L).yCoordinate(-2L).build();

        dao.createMapObject(mapObject);

        mapObject = new MapObject.Builder()
                .type("5").xCoordinate(0L).yCoordinate(-2L).build();

        dao.createMapObject(mapObject);


        return "createFakeMap";
    }


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
//        validateUser(principal);
        UserDao dao = (UserDao) context.getAttribute("dao");
        // [START bookBuilder]

        Result<User> userResult = dao.listUsers(null);

        return userResult.result.toString();
    }

    @RequestMapping("/user/getMap")
    public byte[] downloadMap(Principal principal, @RequestParam("xCoord") Long xCoord, @RequestParam("yCoord") Long yCoord, @RequestParam("delta") Integer delta) {
        //        validateUser(principal);
        // Message body required though ignored

        DataStoreDao dao = (DataStoreDao) context.getAttribute("dao");

        Optional<User> userOpt = dao.getUserByExternalId(principal.getName());

        List<MapObject> mapObjects = dao.getMapObjectsByCoordinates(xCoord - delta,
                xCoord + delta, yCoord - delta,
                yCoord + delta);

        return MapService.getMapAsByteArray(MapService.getMap(mapObjects));

    }

    @RequestMapping(value = "/user/getInfo", method = RequestMethod.GET, produces = "application/json")
    public UserInfo getUserInfo(Principal principal) {
        //        validateUser(principal);
        // Message body required though ignored

        DataStoreDao dao = (DataStoreDao) context.getAttribute("dao");

        Optional<User> userOpt = dao.getUserByExternalId(principal.getName());

        return userOpt.get().getUserInfo();

    }

    @RequestMapping(value = "/user/setInfo", method = RequestMethod.PUT)
    public String setUserInfo(Principal principal, @RequestBody UserInfo userInfo) {
        //        validateUser(principal);
        // Message body required though ignored

        DataStoreDao dao = (DataStoreDao) context.getAttribute("dao");

        User user = dao.getUserByExternalId(principal.getName()).get();

        user.updateUserFields(userInfo);

        dao.updateUser(user);

        return user.getUpdateDate();
    }


    @RequestMapping("/user/{userId}/test")
    public String test(@PathVariable Long userId) {

        UserDao dao = (UserDao) context.getAttribute("dao");

        User user = dao.getUserById(userId);

        return "User = " + user.toString();
    }


    @RequestMapping("/user/{userId}/delete")
    public String delete(@PathVariable Long userId) {

        UserDao dao = (UserDao) context.getAttribute("dao");

        dao.deleteUser(userId);

        return "Deleted? ";
    }


    private void validateUser(Principal principal) {
        System.out.println("validateUser = " + principal.getName());
        String userExternalId = principal.getName();
        UserDao dao = (UserDao) context.getAttribute("dao");
        dao.getUserKeyByExternalId(userExternalId)
                .orElseThrow(
                        () -> new UserNotFoundException(userExternalId));
    }

}
