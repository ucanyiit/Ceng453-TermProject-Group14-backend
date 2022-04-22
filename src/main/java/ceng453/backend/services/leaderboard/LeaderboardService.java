package ceng453.backend.services.leaderboard;

import ceng453.backend.models.ResponseModels.BaseResponse;
import ceng453.backend.models.ResponseModels.Leaderboard.ScoresResponse;
import ceng453.backend.models.Score;
import ceng453.backend.models.User;
import ceng453.backend.models.leaderboardDTOs.LeaderboardScoreDTO;
import ceng453.backend.models.leaderboardDTOs.ScoreDTO;
import ceng453.backend.repositories.ScoreRepository;
import ceng453.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class LeaderboardService implements ILeaderboardService {


    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<BaseResponse> getLeaderboard(String startDate, String endDate) {
        if (startDate == null || endDate == null) {
            return new BaseResponse(false, "Start and end date are required").prepareResponse(HttpStatus.BAD_REQUEST);
        }

        try {
            Date formattedStartDate =new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
            Date formattedEndDate =new SimpleDateFormat("dd/MM/yyyy").parse(endDate);

            List<Score> scores = scoreRepository.findAllByTimestampBetween(formattedStartDate, formattedEndDate);

            List<LeaderboardScoreDTO> scoreDTOS = scores.stream().map(score -> new LeaderboardScoreDTO(score.getUser().getUsername(), score.getScore(), score.getTimestamp().toString())).collect(java.util.stream.Collectors.toList());

            return new ScoresResponse(true, "", scoreDTOS).prepareResponse(HttpStatus.OK);
        } catch (ParseException e) {
            return new BaseResponse(false, "Start and end date should be in the dd/MM/yyyy format").prepareResponse(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<BaseResponse> addScore(String username, Integer score) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return new BaseResponse(false, "User not found").prepareResponse(HttpStatus.NOT_FOUND);
        }

        Score newScore = new Score(user, score);

        scoreRepository.save(newScore);

        return new BaseResponse(true, "Score is saved").prepareResponse(HttpStatus.OK);
    }
}
