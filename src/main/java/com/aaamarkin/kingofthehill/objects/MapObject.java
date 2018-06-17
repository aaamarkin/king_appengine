package com.aaamarkin.kingofthehill.objects;

public class MapObject {

    private Long xCoordinate;
    private Long yCoordinate;
    private Short type;


    public static final String X_COORDINATE = "a";
    public static final String Y_COORDINATE = "b";
    public static final String TYPE = "c";

    private MapObject(Builder builder) {
        this.xCoordinate = builder.xCoordinate;
        this.yCoordinate = builder.yCoordinate;
        this.type = builder.type;

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

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MapObject{" +
                "xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                ", type=" + type +
                '}';
    }

    public static class Builder {
        private Long xCoordinate;
        private Long yCoordinate;
        private Short type;

        public Builder xCoordinate(Long xCoordinate) {
            this.xCoordinate = xCoordinate;
            return this;
        }

        public Builder yCoordinate(Long yCoordinate) {
            this.yCoordinate = yCoordinate;
            return this;
        }

        public Builder type(Short type) {
            this.type = type;
            return this;
        }

        public MapObject build() {
            return new MapObject(this);
        }
    }
}
