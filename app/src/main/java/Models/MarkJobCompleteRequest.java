package Models;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MarkJobCompleteRequest {
    private String jobId;
    private String groupId;
    private int refreshRate;
    private BigDecimal price;
    private String personId;
    private String jobStatus;
}
