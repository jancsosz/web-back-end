package carregistry.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDTO {

    @ApiModelProperty(position = 0)
    private String name;

    @ApiModelProperty(position = 1)
    private String ownerName;

    @ApiModelProperty(position = 2)
    private String type;

    @ApiModelProperty(position = 3)
    private String numPlate;

    @ApiModelProperty(position = 4)
    private Integer weight;
}
