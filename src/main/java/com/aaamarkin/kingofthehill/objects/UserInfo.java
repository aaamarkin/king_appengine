package com.aaamarkin.kingofthehill.objects;

public class UserInfo {

    private String creationDate;
    private String updateDate;

    private Long xCoordinate;
    private Long yCoordinate;

    private Long maxBallHealth;
    private Long currentBallHealth;

    private Long maxTeaterHealth;
    private Long currentTeaterHealth;

    private Long maxBearingHealth;
    private Long currentBearingHealth;

    private Long maxEnergy;
    private Long currentEnergy;

    private Long woodCapacity;
    private Long rubberCapacity;
    private Long metalCapacity;

    public UserInfo(){}

    UserInfo(User user){

        this.creationDate = user.getCreationDate();
        this.updateDate = user.getUpdateDate();
        this.xCoordinate = user.getXCoordinate();
        this.yCoordinate = user.getYCoordinate();

        this.maxBallHealth = user.getMaxBallHealth();
        this.currentBallHealth = user.getCurrentBallHealth();
        this.maxTeaterHealth = user.getMaxTeaterHealth();
        this.currentTeaterHealth = user.getCurrentTeaterHealth();
        this.maxBearingHealth = user.getMaxBearingHealth();
        this.currentBearingHealth = user.getCurrentBearingHealth();
        this.maxEnergy = user.getMaxEnergy();
        this.currentEnergy = user.getCurrentEnergy();

        this.woodCapacity = user.getWoodCapacity();
        this.rubberCapacity = user.getRubberCapacity();
        this.metalCapacity = user.getMetalCapacity();

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

    public Long getMaxBallHealth() {
        return maxBallHealth;
    }

    public void setMaxBallHealth(Long maxBallHealth) {
        this.maxBallHealth = maxBallHealth;
    }

    public Long getCurrentBallHealth() {
        return currentBallHealth;
    }

    public void setCurrentBallHealth(Long currentBallHealth) {
        this.currentBallHealth = currentBallHealth;
    }

    public Long getMaxTeaterHealth() {
        return maxTeaterHealth;
    }

    public void setMaxTeaterHealth(Long maxTeaterHealth) {
        this.maxTeaterHealth = maxTeaterHealth;
    }

    public Long getCurrentTeaterHealth() {
        return currentTeaterHealth;
    }

    public void setCurrentTeaterHealth(Long currentTeaterHealth) {
        this.currentTeaterHealth = currentTeaterHealth;
    }

    public Long getMaxBearingHealth() {
        return maxBearingHealth;
    }

    public void setMaxBearingHealth(Long maxBearingHealth) {
        this.maxBearingHealth = maxBearingHealth;
    }

    public Long getCurrentBearingHealth() {
        return currentBearingHealth;
    }

    public void setCurrentBearingHealth(Long currentBearingHealth) {
        this.currentBearingHealth = currentBearingHealth;
    }

    public Long getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(Long maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public Long getCurrentEnergy() {
        return currentEnergy;
    }

    public void setCurrentEnergy(Long currentEnergy) {
        this.currentEnergy = currentEnergy;
    }

    public Long getWoodCapacity() {
        return woodCapacity;
    }

    public void setWoodCapacity(Long woodCapacity) {
        this.woodCapacity = woodCapacity;
    }

    public Long getRubberCapacity() {
        return rubberCapacity;
    }

    public void setRubberCapacity(Long rubberCapacity) {
        this.rubberCapacity = rubberCapacity;
    }

    public Long getMetalCapacity() {
        return metalCapacity;
    }

    public void setMetalCapacity(Long metalCapacity) {
        this.metalCapacity = metalCapacity;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "creationDate='" + creationDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                ", maxBallHealth=" + maxBallHealth +
                ", currentBallHealth=" + currentBallHealth +
                ", maxTeaterHealth=" + maxTeaterHealth +
                ", currentTeaterHealth=" + currentTeaterHealth +
                ", maxBearingHealth=" + maxBearingHealth +
                ", currentBearingHealth=" + currentBearingHealth +
                ", maxEnergy=" + maxEnergy +
                ", currentEnergy=" + currentEnergy +
                ", woodCapacity=" + woodCapacity +
                ", rubberCapacity=" + rubberCapacity +
                ", metalCapacity=" + metalCapacity +
                '}';
    }
}
