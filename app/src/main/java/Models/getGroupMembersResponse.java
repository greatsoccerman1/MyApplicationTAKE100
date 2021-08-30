package Models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class getGroupMembersResponse {
    private List<groupMember> groupMembers;
}
