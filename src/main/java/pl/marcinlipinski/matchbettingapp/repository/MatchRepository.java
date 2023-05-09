package pl.marcinlipinski.matchbettingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcinlipinski.matchbettingapp.model.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {

}