package Models;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter @Builder @ToString @AllArgsConstructor @NoArgsConstructor
public class GetGroupMemberInfoResponse {

    private BigDecimal totalIncome;
}
