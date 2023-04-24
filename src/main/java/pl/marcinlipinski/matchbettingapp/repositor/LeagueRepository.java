package pl.marcinlipinski.matchbettingapp.repositor;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcinlipinski.matchbettingapp.model.League;
import pl.marcinlipinski.matchbettingapp.model.Match;

public interface LeagueRepository  extends JpaRepository<League, Long> {

}