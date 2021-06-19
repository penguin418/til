package example.boilerplate.springmybatis.entity;

import lombok.*;

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.id = 0;
        this.username = username;
        this.password = password;
    }
}
