package pl.marcinlipinski.matchbettingapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class League {
    @Id
    private Integer id;
    private String name;
    private String logo;
}
