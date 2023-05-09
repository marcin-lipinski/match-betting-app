package pl.marcinlipinski.matchbettingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcinlipinski.matchbettingapp.model.League;

public interface LeagueRepository  extends JpaRepository<League, Long> {

}