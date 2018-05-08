package com.aaamarkin.kingofthehill.objects;

public class User {

    private String login;
    private String creationDate;
    private String externalId;
    private Long id;

    public static final String LOGIN = "login";
    public static final String CREATION_DATE = "creationDate";
    public static final String EXTERNAL_ID = "externalId";

    public static final String ID = "id";


    private User(Builder builder) {
        this.login = builder.login;
        this.creationDate = builder.creationDate;
        this.externalId = builder.externalId;
        this.id = builder.id;
    }


    public static class Builder {
        private String login;
        private String creationDate;
        private String externalId;
        private Long id;

        public Builder login(String login) {
            this.login = login;
            return this;
        }

        public Builder creationDate(String creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder externalId(String externalId) {
            this.externalId = externalId;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
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


    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", externalId='" + externalId + '\'' +
                ", id=" + id +
                '}';
    }
}
