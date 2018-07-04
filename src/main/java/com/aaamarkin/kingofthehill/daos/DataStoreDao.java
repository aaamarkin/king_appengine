package com.aaamarkin.kingofthehill.daos;

import com.aaamarkin.kingofthehill.objects.MapObject;
import com.aaamarkin.kingofthehill.objects.Result;
import com.aaamarkin.kingofthehill.objects.User;

import com.aaamarkin.kingofthehill.util.EntityKinds;
import com.aaamarkin.kingofthehill.util.PropNames;
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
        userKeyFactory = datastore.newKeyFactory().setKind(EntityKinds.USER_KIND);      // Is used for creating keys later
        mapObjectKeyFactory = datastore.newKeyFactory().setKind(EntityKinds.MAP_OBJECT_KIND);
    }


    // User Dao implementation

    public User entityToUser(Entity entity) {

        return new User.Builder()
                .externalId(entity.getString(PropNames.EXTERNAL_ID))
                .password(entity.getString(PropNames.PASSWORD))
                .id(entity.getKey().getId())
                .creationDate(entity.getString(PropNames.CREATION_DATE))
                .updateDate(entity.getString(PropNames.UPDATE_DATE))
                .xCoordinate(entity.getLong(PropNames.X_COORDINATE))
                .yCoordinate(entity.getLong(PropNames.Y_COORDINATE))
                .build();
    }

    @Override
    public User createUser(User user) {
        IncompleteKey key = userKeyFactory.newKey();          // Key will be assigned once written
        FullEntity<IncompleteKey> incUserEntity = Entity.newBuilder(key)  // Create the Entity
                .set(PropNames.EXTERNAL_ID, user.getExternalId())
                .set(PropNames.PASSWORD, user.getPassword())
                .set(PropNames.CREATION_DATE, user.getCreationDate())
                .set(PropNames.UPDATE_DATE, user.getUpdateDate())
                .set(PropNames.X_COORDINATE, user.getXCoordinate())
                .set(PropNames.Y_COORDINATE, user.getYCoordinate())
                .build();
        Entity userEntity = datastore.add(incUserEntity); // Save the Entity
        return entityToUser(userEntity);
    }

    @Override
    public Optional<User> getUserByExternalId(String userExternalId) {

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(EntityKinds.USER_KIND)
                .setFilter(StructuredQuery.PropertyFilter.eq(PropNames.EXTERNAL_ID, userExternalId)                )
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
                .setKind(EntityKinds.USER_KIND)
                .setFilter(StructuredQuery.PropertyFilter.eq(PropNames.EXTERNAL_ID, userExternalId)                )
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
                .setKind(EntityKinds.USER_KIND)
                .setLimit(10)
                .build();
        QueryResults<Entity> resultList = datastore.run(query);   // Run the query
        return entitiesToUsers(resultList);
    }

    @Override
    public void updateUser(User user) {
        Key key = userKeyFactory.newKey(user.getId());  // From a user, create a Key
        Entity entity = Entity.newBuilder(key)         // Convert User to an Entity
                .set(PropNames.EXTERNAL_ID, user.getExternalId())
                .set(PropNames.PASSWORD, user.getPassword())
                .set(PropNames.CREATION_DATE, user.getCreationDate())
                .set(PropNames.UPDATE_DATE, user.getUpdateDate())
                .set(PropNames.X_COORDINATE, user.getXCoordinate())
                .set(PropNames.Y_COORDINATE, user.getYCoordinate())
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
                .setKind(EntityKinds.USER_KIND)                                     // We only care about Users
                .setLimit(10)                                         // Only show 10 at a time
                .setStartCursor(startCursor)                          // Where we left off
                .setOrderBy(OrderBy.asc(PropNames.PASSWORD))                  // Use default Index "title"
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
                .type(entity.getString(PropNames.MAP_OBJECT_TYPE))
                .xCoordinate(entity.getLong(PropNames.X_COORDINATE))
                .yCoordinate(entity.getLong(PropNames.Y_COORDINATE))
                .build();
    }

    public List<MapObject> entitiesToMapObjects(QueryResults<Entity> resultList) {
        List<MapObject> resultMapObjects = new ArrayList<>();
        while (resultList.hasNext()) {  // We still have data
            resultMapObjects.add(entityToMapObject(resultList.next()));      // Add the Book to the List
        }
        return resultMapObjects;
    }

    @Override
    public MapObject createMapObject(MapObject mapObject) {


        IncompleteKey key = mapObjectKeyFactory.newKey();          // Key will be assigned once written
        FullEntity<IncompleteKey> incMapObjectEntity = Entity.newBuilder(key)  // Create the Entity
                .set(PropNames.MAP_OBJECT_TYPE, mapObject.getType())
                .set(PropNames.X_COORDINATE, mapObject.getXCoordinate())
                .set(PropNames.Y_COORDINATE, mapObject.getYCoordinate())
                .build();
        Entity mapObjectEntity = datastore.add(incMapObjectEntity); // Save the Entity
        return entityToMapObject(mapObjectEntity);
    }

    @Override
    public MapObject getMapObjectById(Long id) {

        Entity mapObjectEntity = datastore.get(mapObjectKeyFactory.newKey(id));

        return entityToMapObject(mapObjectEntity);

    }

    @Override
    public Optional<MapObject> getMapObjectByCoordinates(Long xCoordinate, Long yCoordinate){

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(EntityKinds.MAP_OBJECT_KIND)
                .setFilter(StructuredQuery.PropertyFilter.eq(PropNames.X_COORDINATE, xCoordinate))
                .setFilter(StructuredQuery.PropertyFilter.eq(PropNames.Y_COORDINATE, yCoordinate))
                .build();

        QueryResults<Entity> resultList = datastore.run(query);

        if(resultList.hasNext()){
            return Optional.of(entityToMapObject(resultList.next()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<MapObject> getMapObjectsByCoordinates(Long xCoordinateStart, Long xCoordinateFinish,
                                               Long yCoordinateStart, Long yCoordinateFinish){

        Query<Entity> query = Query.newEntityQueryBuilder()       // Build the Query
                .setKind(EntityKinds.MAP_OBJECT_KIND)
                .setFilter(StructuredQuery.PropertyFilter.gt(PropNames.X_COORDINATE, xCoordinateStart))
                .setFilter(StructuredQuery.PropertyFilter.gt(PropNames.Y_COORDINATE, yCoordinateStart))
                .setFilter(StructuredQuery.PropertyFilter.lt(PropNames.X_COORDINATE, xCoordinateFinish))
                .setFilter(StructuredQuery.PropertyFilter.lt(PropNames.Y_COORDINATE, yCoordinateFinish))
                .build();
        QueryResults<Entity> resultList = datastore.run(query);   // Run the query
        return entitiesToMapObjects(resultList);

    }

    @Override
    public void updateMapObject(MapObject mapObject){

        Key key = mapObjectKeyFactory.newKey(mapObject.getId());  // From a user, create a Key
        Entity entity = Entity.newBuilder(key)         // Convert User to an Entity
                .set(PropNames.MAP_OBJECT_TYPE, mapObject.getType())
                .set(PropNames.X_COORDINATE, mapObject.getXCoordinate())
                .set(PropNames.Y_COORDINATE, mapObject.getYCoordinate())
                .build();
        datastore.update(entity);

    }

    @Override
    public void deleteMapObject(Long userId){

        Key key = mapObjectKeyFactory.newKey(userId);        // Create the Key
        datastore.delete(key);
    }
}
