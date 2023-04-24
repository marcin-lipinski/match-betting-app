package pl.marcinlipinski.matchbettingapp.repositor;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcinlipinski.matchbettingapp.model.Bet;

public interface BetRepository extends JpaRepository<Bet, Long> {

}
