package pl.marcinlipinski.matchbettingapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Bet implements Serializable {
    @Id
    private long id;
    private LocalDateTime endDate;
    private LocalDateTime betDate;
    private double inputValue;
    private double oddValue;
    private double possibleWinValue;

}
