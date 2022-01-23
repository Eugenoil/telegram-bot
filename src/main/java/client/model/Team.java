package client.model;

import lombok.Data;

import java.util.List;

@Data
public class Team {
    private String teamName;
    private List<User> users;
}
