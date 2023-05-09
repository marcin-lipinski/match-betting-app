package pl.marcinlipinski.matchbettingapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;

@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class User implements Serializable {
    @Id
    private long id;
    private double accountBalance;
}
