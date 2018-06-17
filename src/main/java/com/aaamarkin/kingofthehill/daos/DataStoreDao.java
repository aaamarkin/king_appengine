package com.aaamarkin.kingofthehill.daos;

import com.aaamarkin.kingofthehill.objects.MapObject;
import com.aaamarkin.kingofthehill.objects.Result;
import com.aaamarkin.kingofthehill.objects.User;

import com.google.cloud.datastore.*;

import com.google.cloud.datastore.StructuredQuery.OrderBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataStoreDao implements UserDao, MapObjectDao {

    private Datastore datastore;
    private KeyFactory userKeyFactory;
    private KeyFactory mapObjectKeyFactory;

    public DataStoreDao() {
        datastore = DatastoreOptions.getDefaultInstance().getService(); // Authorized Datastore service
        userKeyFactory = datastore.newKeyFactory().setKind("User3");      // Is used for creating keys later
        mapObjectKeyFactory = datastore.newKeyFactory().setKind("MapObject3");
    }


    // User Dao implementation

    public User entityToUser(Entity entity) {

        return new User.Builder()
                .externalId(entity.getString(User.EXTERNAL_ID))
                .password(entity.getString(User.PASSWORD))
                .id(entity.getKey().getId())
                .creationDate(entity.getString(User.CREATION_DATE))
                .xCoordinate(entity.getLong(User.X_COORDINATE))
                .yCoordinate(entity.getLong(User.Y_COORDINATE))
                .build();
    }

    @Override
    public User createUser(User user) {
        IncompleteKey key = userKeyFactory.newKey();          // Key will be assigned once written
        FullEntity<IncompleteKey> incUserEntity = Entity.newBuilder(key)  // Create the Entity
                .set(User.EXTERNAL_ID, user.getExternalId())
                .set(User.PASSWORD, user.getPassword())
                .set(User.CREATION_DATE, user.getCreationDate())
                .set(User.X_COORDINATE, user.getXCoordinate())
                .set(User.Y_COORDINATE, user.getYCoordinate())
                .build();
        Entity userEntity = datastore.add(incUserEntity); // Save the Entity
        return entityToUser(userEntity);
    }

    @Override
    public Optional<User> getUserByExternalId(String userExternalId) {

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("User3")
                .setFilter(StructuredQuery.PropertyFilter.eq(User.EXTERNAL_ID, userExternalId)                )
                .build();

        QueryResults<Entity> resultList = datastore.run(query);
        if(resultList.hasNext()){
            return Optional.of(entityToUser(resultList.next()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public User getUserById(Long id) {

        Entity userEntity = datastore.get(userKeyFactory.newKey(id));

        return entityToUser(userEntity);

    }

    @Override
    public Optional<Key> getUserKeyByExternalId(String userExternalId) {
        Query<Key> query = Query.newKeyQueryBuilder()
                .setKind("User3")
                .setFilter(StructuredQuery.PropertyFilter.eq(User.EXTERNAL_ID, userExternalId)                )
                .build();

        QueryResults<Key> resultList = datastore.run(query);
        if(resultList.hasNext()){
            return Optional.of(resultList.next());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<User> readUsers(String userLogin) {


        Query<Entity> query = Query.newEntityQueryBuilder()       // Build the Query
                .setKind("User3")
                .setLimit(10)
                .build();
        QueryResults<Entity> resultList = datastore.run(query);   // Run the query
        return entitiesToUsers(resultList);
    }

    @Override
    public void updateUser(User user) {
        Key key = userKeyFactory.newKey(user.getId());  // From a user, create a Key
        Entity entity = Entity.newBuilder(key)         // Convert User to an Entity
                .set(User.EXTERNAL_ID, user.getExternalId())
                .set(User.PASSWORD, user.getPassword())
                .set(User.CREATION_DATE, user.getCreationDate())
                .set(User.X_COORDINATE, user.getXCoordinate())
                .set(User.Y_COORDINATE, user.getYCoordinate())
                .build();
        datastore.update(entity);
    }

    @Override
    public void deleteUser(Long userId) {
        Key key = userKeyFactory.newKey(userId);        // Create the Key
        datastore.delete(key);
    }

    public List<User> entitiesToUsers(QueryResults<Entity> resultList) {
        List<User> resultUsers = new ArrayList<>();
        while (resultList.hasNext()) {  // We still have data
            resultUsers.add(entityToUser(resultList.next()));      // Add the Book to the List
        }
        return resultUsers;
    }

    @Override
    public Result<User> listUsers(String startCursorString) {
        Cursor startCursor = null;
        if (startCursorString != null && !startCursorString.equals("")) {
            startCursor = Cursor.fromUrlSafe(startCursorString);    // Where we left off
        }
        Query<Entity> query = Query.newEntityQueryBuilder()       // Build the Query
                .setKind("User3")                                     // We only care about Users
                .setLimit(10)                                         // Only show 10 at a time
                .setStartCursor(startCursor)                          // Where we left off
                .setOrderBy(OrderBy.asc(User.PASSWORD))                  // Use default Index "title"
                .build();
        QueryResults<Entity> resultList = datastore.run(query);   // Run the query
        List<User> resultUsers = entitiesToUsers(resultList);     // Retrieve and convert Entities
        Cursor cursor = resultList.getCursorAfter();              // Where to start next time
        if (cursor != null && resultUsers.size() == 10) {         // Are we paging? Save Cursor
            String cursorString = cursor.toUrlSafe();               // Cursors are WebSafe
            return new Result<>(resultUsers, cursorString);
        } else {
            return new Result<>(resultUsers);
        }
    }


    // Map Object Dao implementation

    public MapObject entityToMapObject(Entity entity) {

        return new MapObject.Builder()
                .type(Short.parseShort(entity.getString(MapObject.TYPE)))
                .xCoordinate(entity.getLong(MapObject.X_COORDINATE))
                .yCoordinate(entity.getLong(MapObject.Y_COORDINATE))
                .build();
    }

    @Override
    public MapObject createMapObject(MapObject mapObject) {
        IncompleteKey key = mapObjectKeyFactory.newKey();          // Key will be assigned once written
        FullEntity<IncompleteKey> incMapObjectEntity = Entity.newBuilder(key)  // Create the Entity
                .set(MapObject.TYPE, mapObject.getType())
                .set(MapObject.X_COORDINATE, mapObject.getXCoordinate())
                .set(MapObject.Y_COORDINATE, mapObject.getYCoordinate())
                .build();
        Entity mapObjectEntity = datastore.add(incMapObjectEntity); // Save the Entity
        return entityToMapObject(mapObjectEntity);
    }

    @Override
    public Optional<MapObject> getMapObjectByCoordinates(Long xCoordinate, Long yCoordinate);

    @Override
    public List<MapObject> getMapObjectsByCoordinates(Long xCoordinateStart, Long xCoordinateFinish,
                                               Long yCoordinateStart, Long yCoordinateFinish);
    @Override
    public void updateMapObject(MapObject mapObject);

    @Override
    public void deleteMapObject(Long xCoordinate, Long yCoordinate);
}
