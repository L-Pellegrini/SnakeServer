package it.uniroma3.spring.snake.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.spring.snake.model.Match;
import it.uniroma3.spring.snake.model.User;

public interface MatchRepository extends CrudRepository<Match, Long>{
	
	public List<Match> findTop10ByOrderByPositionAsc();
	
	public Match findTopByPlayerOrderByPositionAsc(User player);
}
