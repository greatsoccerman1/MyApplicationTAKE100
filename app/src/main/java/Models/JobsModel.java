package Models;

import androidx.lifecycle.ViewModel;

import java.io.Serializable;
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
public class JobsModel extends ViewModel implements Serializable {
    private String jobName;
    private String jobId;
    private int refreshRate;
    private String jobStatus;
    private BigDecimal jobPrice;
}
