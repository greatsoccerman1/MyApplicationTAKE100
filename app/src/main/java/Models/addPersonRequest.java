package Models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class addPersonRequest {
    private String firstName;
    private String lastName;
    private String groupId;
    private String userName;
    private String role;
    private String groupName;
}
