package com.aaamarkin.kingofthehill.objects;

import com.aaamarkin.kingofthehill.util.Tuple;
import com.aaamarkin.kingofthehill.util.UserSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class User implements UserDetails
{

    private String password;
    private String creationDate;
    private String updateDate;
    private String externalId;

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

    private Long id;

    private User(Builder builder) {
        this.password = builder.password;
        this.creationDate = builder.creationDate;
        this.updateDate = builder.updateDate;
        this.externalId = builder.externalId;
        this.xCoordinate = builder.xCoordinate;
        this.yCoordinate = builder.yCoordinate;
        this.maxBallHealth = builder.maxBallHealth;
        this.currentBallHealth = builder.currentBallHealth;
        this.maxTeaterHealth = builder.maxTeaterHealth;
        this.currentTeaterHealth = builder.currentTeaterHealth;
        this.maxBearingHealth = builder.maxBearingHealth;
        this.currentBearingHealth = builder.currentBearingHealth;
        this.maxEnergy = builder.maxEnergy;
        this.currentEnergy = builder.currentEnergy;
        this.woodCapacity = builder.woodCapacity;
        this.rubberCapacity = builder.rubberCapacity;
        this.metalCapacity = builder.metalCapacity;
        this.id = builder.id;
    }

    public void updateUserFields(UserInfo userInfo){
        this.creationDate = userInfo.getCreationDate();
        this.updateDate = userInfo.getUpdateDate();
        this.xCoordinate = userInfo.getxCoordinate();
        this.yCoordinate = userInfo.getyCoordinate();

        this.maxBallHealth = userInfo.getMaxBallHealth();
        this.currentBallHealth = userInfo.getCurrentBallHealth();
        this.maxTeaterHealth = userInfo.getMaxTeaterHealth();
        this.currentTeaterHealth = userInfo.getCurrentTeaterHealth();
        this.maxBearingHealth = userInfo.getMaxBearingHealth();
        this.currentBearingHealth = userInfo.getCurrentBearingHealth();
        this.maxEnergy = userInfo.getMaxEnergy();
        this.currentEnergy = userInfo.getCurrentEnergy();

        this.metalCapacity = userInfo.getMetalCapacity();
        this.woodCapacity = userInfo.getWoodCapacity();
        this.rubberCapacity = userInfo.getRubberCapacity();
    }

    public UserInfo getUserInfo(){

        return new UserInfo(this);

    }

    public static class Builder {
        private String password;
        private String creationDate;
        private String updateDate;
        private String externalId;
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

        private Long id;

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder creationDate(String creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder updateDate(String updateDate) {
            this.updateDate = updateDate;
            return this;
        }


        public Builder externalId(String externalId) {
            this.externalId = externalId;
            return this;
        }

        public Builder xCoordinate(Long xCoordinate) {
            this.xCoordinate = xCoordinate;
            return this;
        }

        public Builder yCoordinate(Long yCoordinate) {
            this.yCoordinate = yCoordinate;
            return this;
        }

        public Builder coordinates(Tuple<Long, Long> coordinate) {
            this.xCoordinate = coordinate._1;
            this.yCoordinate = coordinate._2;
            return this;
        }

        public Builder maxBallHealth(Long maxBallHealth) {
            this.maxBallHealth = maxBallHealth;
            return this;
        }

        public Builder currentBallHealth(Long currentBallHealth) {
            this.currentBallHealth = currentBallHealth;
            return this;
        }

        public Builder maxTeaterHealth(Long maxTeaterHealth) {
            this.maxTeaterHealth = maxTeaterHealth;
            return this;
        }

        public Builder currentTeaterHealth(Long currentTeaterHealth) {
            this.currentTeaterHealth = currentTeaterHealth;
            return this;
        }

        public Builder maxBearingHealth(Long maxBearingHealth) {
            this.maxBearingHealth = maxBearingHealth;
            return this;
        }

        public Builder currentBearingHealth(Long currentBearingHealth) {
            this.currentBearingHealth = currentBearingHealth;
            return this;
        }

        public Builder maxEnergy(Long maxEnergy) {
            this.maxEnergy = maxEnergy;
            return this;
        }

        public Builder currentEnergy(Long currentEnergy) {
            this.currentEnergy = currentEnergy;
            return this;
        }

        public Builder metalCapacity(Long metalCapacity) {
            this.metalCapacity = metalCapacity;
            return this;
        }

        public Builder rubberCapacity(Long rubberCapacity) {
            this.rubberCapacity = rubberCapacity;
            return this;
        }

        public Builder woodCapacity(Long woodCapacity) {
            this.woodCapacity = woodCapacity;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_ADMIN", "write");
    }

    @Override
    public String getUsername() {
        return externalId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return "User{" +
                "password='" + password + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", externalId='" + externalId + '\'' +
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
                ", id=" + id +
                '}';
    }
}
