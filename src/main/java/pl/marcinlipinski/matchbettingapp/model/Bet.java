package pl.marcinlipinski.matchbettingapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Bet{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime endDate;
    private LocalDateTime betDate;
    private double inputValue;
    private double oddValue;
    private double possibleWinValue;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "bets")
    Set<Match> matches = new HashSet<>();
}
