package com.rguzman.techstore.domain.model;


public class User {
    private String name;
    private String lastName;
    private String job;
    private String email;
    private String github;
    private String token;
    private long expires;

    public User() {
    }

    public User(String name, String lastName, String job, String email, String github, String token, long expires) {
        this.name = name;
        this.lastName = lastName;
        this.job = job;
        this.email = email;
        this.github = github;
        this.token = token;
        this.expires = expires;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }
}
