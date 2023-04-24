package pl.marcinlipinski.matchbettingapp.service;

import org.springframework.stereotype.Service;
import pl.marcinlipinski.matchbettingapp.repositor.BetRepository;

@Service
public class BetService {
    private final BetRepository betRepository;

    public BetService(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    public void deleteAll(){
        betRepository.deleteAll();
    }
}
