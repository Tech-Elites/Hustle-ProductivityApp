package com.example.productivity;

public class UserDetailClass {
    String name, email;
    int points;

    public UserDetailClass(String name, String email, int points) {
        this.name = name;
        this.email = email;
        this.points = points;
    }

    public UserDetailClass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }


}
