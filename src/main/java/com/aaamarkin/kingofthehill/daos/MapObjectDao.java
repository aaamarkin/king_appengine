package com.aaamarkin.kingofthehill.daos;

import com.aaamarkin.kingofthehill.objects.MapObject;

import java.util.List;
import java.util.Optional;

public interface MapObjectDao {

    MapObject createMapObject(MapObject mapObject);

    MapObject getMapObjectById(Long id);

    Optional<MapObject> getMapObjectByCoordinates(Long xCoordinate, Long yCoordinate);

    List<MapObject> getMapObjectsByCoordinates(Long xCoordinateStart, Long xCoordinateFinish,
                                               Long yCoordinateStart, Long yCoordinateFinish);

    void updateMapObject(MapObject mapObject);

    void deleteMapObject(Long userId);

}
