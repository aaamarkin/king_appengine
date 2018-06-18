package com.aaamarkin.kingofthehill.services;

import com.aaamarkin.kingofthehill.objects.MapObject;
import com.aaamarkin.kingofthehill.util.Tuple;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.List;

public class MapService {

    public static Tuple<Long, Long> GenerateInitialUserCoordinates(){

        return new Tuple<Long, Long>(0L, 0L);
    }

    public static HashMap<Tuple<Long, Long>, Short> getMap(List<MapObject> mapObjects){

        HashMap<Tuple<Long, Long>, Short> map = new HashMap<>();

        for (MapObject mapObject : mapObjects) {

            map.put(mapObject.getCoordinates(), Short.parseShort(mapObject.getType(), 10) );
        }

        return map;
    }

    public static HashMap<Tuple<Long, Long>, Short> getMap(Long xUserCoord, Long yUserCoord){

        HashMap<Tuple<Long, Long>, Short> map = new HashMap<>();



        for (long i = -200; i < 200; i++){

            for (long j = -200; j < 200; j++)
            {
                Tuple<Long, Long> tuple = new Tuple<Long, Long>(i, j);

                if (i == 1 && j == 1)
                {
                    map.put(tuple, (short) 1);

                } else if (i == -1 && j == -2)
                {
                    map.put(tuple, (short) 2);

                } else if (i == -3 && j == -2)
                {
                    map.put(tuple, (short) 3);

                } else if (i == -2 && j == -2)
                {
                    map.put(tuple, (short) 4);

                } else if (i == 0 && j == -2)
                {
                    map.put(tuple, (short) 5);

                }

            }
        }

        return map;
    }

    public static byte[] getMapAsByteArray(HashMap<Tuple<Long, Long>, Short> map){

        int myIncrement = 0;

        int mapLength = (map.size());

        byte[] mapAsByteArray = new byte[mapLength * (8 + 8 + 2) + 4];


        byte[] mapLengthBytes = WrightByte(mapLength);

        for (int j = 0; j < 4; j++)
        {
            mapAsByteArray[j] = mapLengthBytes[j];

            myIncrement += 1;
        }

        for (Tuple<Long, Long> coordinates : map.keySet()) {

            byte[] xCoordinate = WrightByte(coordinates._1);

            byte[] yCoordinate = WrightByte(coordinates._2);

            byte[] objCode = WrightByte(map.get(coordinates));

            for (int j = 0; j < 8; j++)
            {
                mapAsByteArray[myIncrement] = xCoordinate[j];

                myIncrement += 1;
            }

            for (int j = 0; j < 8; j++)
            {
                mapAsByteArray[myIncrement] = yCoordinate[j];

                myIncrement += 1;
            }

            for (int j = 0; j < 2; j++)
            {
                mapAsByteArray[myIncrement] = objCode[j];

                myIncrement += 1;
            }
        }

        return  mapAsByteArray;
    }

    private static byte[] WrightByte(short x)
    {
        byte[] intBytes = new byte[2];

        //little endian
//        intBytes[0] = (byte)(x & 0xff);
//        intBytes[1] = (byte)((x >> 8) & 0xff);

//        big endian
        intBytes[0] = (byte)((x >> 8) & 0xFF);
        intBytes[1] = (byte)(x & 0xFF);

        return intBytes;
    }

    public static byte[] WrightByte(int myInteger){
        return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(myInteger).array();
    }

    public static byte[] WrightByte(long myLong){
        return ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN).putLong(myLong).array();
    }

    public static int ReadInt(byte [] byteBarray){
        return ByteBuffer.wrap(byteBarray).order(ByteOrder.BIG_ENDIAN).getInt();
    }
}
