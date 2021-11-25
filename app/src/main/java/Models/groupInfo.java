package Models;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class groupInfo implements Serializable {
    private String groupName;
    private String groupCode;
    private String groupOwnerId;
    private String role;
}
