package pl.marcinlipinski.matchbettingapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class League{
    @Id
    private Integer id;
    private String name;
    private String logo;
}
