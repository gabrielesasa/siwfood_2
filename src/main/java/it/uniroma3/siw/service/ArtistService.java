package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.repository.ArtistRepository;

@Service
public class ArtistService {
	@Autowired
	private ArtistRepository artistRepository;
	public Artist findById(Long id) {
		return artistRepository.findById(id).get();
	}
	public Iterable<Artist>findAll(){
		return artistRepository.findAll();
	}
	public void save(Artist artist) {
		artistRepository.save(artist);
		
	}
}
