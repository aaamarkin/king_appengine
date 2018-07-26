package com.aaamarkin.kingofthehill.objects;

import com.aaamarkin.kingofthehill.util.Tuple;

public class MapObject {

    private Long xCoordinate;
    private Long yCoordinate;
    private String type;

    private Long id;



    private MapObject(Builder builder) {
        this.xCoordinate = builder.xCoordinate;
        this.yCoordinate = builder.yCoordinate;
        this.type = builder.type;
        this.id = builder.id;
    }

    public Long getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(Long xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public Long getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(Long yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Tuple<Long, Long> getCoordinates(){
        return new Tuple<>(xCoordinate, yCoordinate);
    }

    @Override
    public String toString() {
        return "MapObject{" +
                "xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                ", type='" + type + '\'' +
                ", id=" + id +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static class Builder {
        private Long xCoordinate;
        private Long yCoordinate;
        private String type;
        private Long id;

        public Builder xCoordinate(Long xCoordinate) {
            this.xCoordinate = xCoordinate;
            return this;
        }

        public Builder yCoordinate(Long yCoordinate) {
            this.yCoordinate = yCoordinate;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public MapObject build() {
            return new MapObject(this);
        }
    }
}
