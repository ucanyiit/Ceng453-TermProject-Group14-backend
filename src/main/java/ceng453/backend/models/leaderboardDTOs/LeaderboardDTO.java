package ceng453.backend.models.leaderboardDTOs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class LeaderboardDTO {
    @ApiModelProperty(value = "The start date for the leaderboard.", example = "10/10/2019")
    private String startDate;

    @ApiModelProperty(value = "The end date for the leaderboard.", example = "10/10/2020")
    private String endDate;
}
