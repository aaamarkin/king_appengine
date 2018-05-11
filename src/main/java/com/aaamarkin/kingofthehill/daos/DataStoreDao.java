package com.aaamarkin.kingofthehill.daos;

import com.aaamarkin.kingofthehill.objects.Result;
import com.aaamarkin.kingofthehill.objects.User;

import com.google.cloud.datastore.*;

import com.google.cloud.datastore.StructuredQuery.OrderBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataStoreDao implements UserDao {

    private Datastore datastore;
    private KeyFactory keyFactory;

    public DataStoreDao() {
        datastore = DatastoreOptions.getDefaultInstance().getService(); // Authorized Datastore service
        keyFactory = datastore.newKeyFactory().setKind("User3");      // Is used for creating keys later
    }

    public User entityToUser(Entity entity) {

        return new User.Builder()
                .externalId(entity.getString(User.EXTERNAL_ID))
                .password(entity.getString(User.PASSWORD))
                .id(entity.getKey().getId())
                .creationDate(entity.getString(User.CREATION_DATE))
                .build();
    }

    @Override
    public Long createUser(User user) {
        IncompleteKey key = keyFactory.newKey();          // Key will be assigned once written
        FullEntity<IncompleteKey> incUserEntity = Entity.newBuilder(key)  // Create the Entity
                .set(User.EXTERNAL_ID, user.getExternalId())
                .set(User.PASSWORD, "test password")
                .set(User.CREATION_DATE, user.getCreationDate())
                .build();
        Entity userEntity = datastore.add(incUserEntity); // Save the Entity
        return userEntity.getKey().getId();                     // The ID of the Key
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

        Entity userEntity = datastore.get(keyFactory.newKey(id));

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
        Key key = keyFactory.newKey(user.getId());  // From a user, create a Key
        Entity entity = Entity.newBuilder(key)         // Convert User to an Entity
                .set(User.EXTERNAL_ID, user.getExternalId())
                .set(User.PASSWORD, user.getPassword())
                .set(User.CREATION_DATE, user.getCreationDate())
                .build();
        datastore.update(entity);
    }

    @Override
    public void deleteUser(Long userId) {
        Key key = keyFactory.newKey(userId);        // Create the Key
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

}
