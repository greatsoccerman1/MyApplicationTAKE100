package Models;

import lombok.*;

@Getter
@Setter
@Builder
@ToString @AllArgsConstructor @NoArgsConstructor
public class GetGroupMemberInfoRequest {
    private String infoForPersonId;
    private String startEarningDate;
    private String endEarningDate;
}
