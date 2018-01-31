package user.repository;

import java.util.Random;

public class User {
    private Integer id;
    private String name;

    public User() {
        id = new Random().nextInt(Integer.MAX_VALUE);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
