package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Props {
    //@JsonProperty("url")
    private String url;
    @JsonProperty("is_production")
    private Boolean isProduction;
    //@JsonProperty("threads")
    private Integer threads;
}
