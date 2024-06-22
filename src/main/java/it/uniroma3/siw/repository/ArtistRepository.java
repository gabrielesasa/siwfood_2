package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Movie;

public interface ArtistRepository extends CrudRepository<Artist,Long>{
	public List<Artist> findByYear(Integer year);
	public boolean existsByNameAndSurname(String name, String surname);	
}
