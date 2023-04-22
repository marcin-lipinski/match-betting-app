package pl.marcinlipinski.matchbettingapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Bet {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private int[] matchesList;
}
