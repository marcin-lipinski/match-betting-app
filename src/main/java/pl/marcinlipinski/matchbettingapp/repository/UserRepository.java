package pl.marcinlipinski.matchbettingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcinlipinski.matchbettingapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
