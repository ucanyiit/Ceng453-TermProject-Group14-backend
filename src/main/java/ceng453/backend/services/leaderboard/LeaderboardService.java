package ceng453.backend.services.leaderboard;

import ceng453.backend.models.DTOs.leaderboard.LeaderboardScoreDTO;
import ceng453.backend.models.database.Score;
import ceng453.backend.models.database.User;
import ceng453.backend.models.responses.BaseResponse;
import ceng453.backend.models.responses.leaderboard.ScoresResponse;
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
import java.util.stream.Collectors;

@Service
public class LeaderboardService implements ILeaderboardService {


    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<ScoresResponse> getLeaderboard(String startDate, String endDate) {
        if (startDate == null || endDate == null) {
            return new ScoresResponse(false, "Start and end date are required", null).prepareResponse(HttpStatus.BAD_REQUEST);
        }

        try {
            Date formattedStartDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
            Date formattedEndDate = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);

            List<Score> scores = scoreRepository.findAllByTimestampBetween(formattedStartDate, formattedEndDate);

            scores.sort((o1, o2) -> o2.getScore().compareTo(o1.getScore()));
            scores = scores.stream().limit(20).collect(Collectors.toList());

            List<LeaderboardScoreDTO> scoreDTOS = scores.stream().map(score -> new LeaderboardScoreDTO(score.getUser().getUsername(), score.getScore(), score.getTimestamp().toString())).collect(java.util.stream.Collectors.toList());

            return new ScoresResponse(true, "", scoreDTOS).prepareResponse(HttpStatus.OK);
        } catch (ParseException e) {
            return new ScoresResponse(false, "Start and end date should be in the dd/MM/yyyy format", null).prepareResponse(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<BaseResponse> addScore(String username, Double score) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return new BaseResponse(false, "User not found").prepareResponse(HttpStatus.NOT_FOUND);
        }

        Score newScore = new Score(user, score);

        scoreRepository.save(newScore);

        return new BaseResponse(true, "Score is saved").prepareResponse(HttpStatus.OK);
    }
}
