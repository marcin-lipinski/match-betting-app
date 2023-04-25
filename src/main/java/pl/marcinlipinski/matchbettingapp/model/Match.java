package pl.marcinlipinski.matchbettingapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name="Event")
@Table(name="event")
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class Match implements Serializable {
    @Id
    private long id;
    private String status;
    private String homeTeam;
    private String homeTeamLogo;
    private String awayTeam;
    private String awayTeamLogo;
    private LocalDateTime startTime;
    private int homeTeamScore;
    private int awayTeamScore;
    private int winnerCode;
    private double homeTeamOdd;
    private double drawTeamOdd;
    private Double awayTeamOdd;
    private String leagueName;
    private String leagueLogo;
    private long betId;
}
