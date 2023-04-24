package pl.marcinlipinski.matchbettingapp.repositor;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcinlipinski.matchbettingapp.model.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {

}