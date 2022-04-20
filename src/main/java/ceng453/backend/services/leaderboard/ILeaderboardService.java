package ceng453.backend.services.leaderboard;

import ceng453.backend.models.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ILeaderboardService {
    ResponseEntity<BaseResponse> getLeaderboard(String startDate, String endDate);

    ResponseEntity<BaseResponse> addScore(String username, Integer score);
}
