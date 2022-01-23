package client.model;

import client.roles.Roles;
import lombok.Data;

@Data
public class User {
    private Long name;
    private Roles rolesState;
}
