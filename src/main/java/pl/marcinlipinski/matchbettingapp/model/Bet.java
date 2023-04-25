package pl.marcinlipinski.matchbettingapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Bet {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private int[] matchesList;
    private LocalDateTime endDate;
    private double betValue;
    private double potentialWinValue;
}
