package pl.marcinlipinski.matchbettingapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name="event")
@Table(name="event")
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class Match {
    @Id
    private Long id;
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "bet_event",
            joinColumns = { @JoinColumn(name = "bet_id") },
            inverseJoinColumns = { @JoinColumn(name = "event_id") }
    )
    private Set<Bet> bets = new HashSet<>();

    public void addBet(Bet bet) {
        this.bets.add(bet);
        bet.getMatches().add(this);
    }
}
