package pl.marcinlipinski.matchbettingapp.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class Match {
    @Id
    private Integer id;
    private String status;
    private String homeTeam;
    private String homeTeamLogo;
    private String awayTeam;
    private String awayTeamLogo;
    private LocalDateTime startTime;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    private Integer winnerCode;
    private Double homeTeamOdd;
    private Double drawTeamOdd;
    private Double awayTeamOdd;
    private String leagueName;
    private String leagueLogo;


    public Match(String gcc) {
        super();
        homeTeam = gcc;
    }
}
