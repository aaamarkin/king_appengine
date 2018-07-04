package com.aaamarkin.kingofthehill.objects;

public class UserInfo {

    private String creationDate;
    private String updateDate;

    private Long xCoordinate;
    private Long yCoordinate;

    public UserInfo(){}

    UserInfo(String creationDate, String updateDate, Long xCoordinate, Long yCoordinate){

        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Long getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(Long xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public Long getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(Long yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "creationDate='" + creationDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                '}';
    }
}
