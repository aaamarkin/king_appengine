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

    private Long id;

    private User(Builder builder) {
        this.password = builder.password;
        this.creationDate = builder.creationDate;
        this.updateDate = builder.updateDate;
        this.externalId = builder.externalId;
        this.xCoordinate = builder.xCoordinate;
        this.yCoordinate = builder.yCoordinate;
        this.id = builder.id;
    }

    public void updateUserFields(UserInfo userInfo){
        this.creationDate = userInfo.getCreationDate();
        this.updateDate = userInfo.getUpdateDate();
        this.xCoordinate = userInfo.getxCoordinate();
        this.yCoordinate = userInfo.getyCoordinate();
    }

    public UserInfo getUserInfo(){

        return new UserInfo(this.creationDate, this.updateDate, this.xCoordinate, this.yCoordinate);

    }

    public static class Builder {
        private String password;
        private String creationDate;
        private String updateDate;
        private String externalId;
        private Long xCoordinate;
        private Long yCoordinate;
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


    @Override
    public String toString() {
        return "User{" +
                "password='" + password + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", externalId='" + externalId + '\'' +
                ", xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                ", id=" + id +
                '}';
    }
}
