package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.repository.CuocoRepository;
import it.uniroma3.siw.repository.RicettaRepository;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.RicettaService;

@Controller
public class RicettaController {
	@Autowired
	private RicettaRepository ricettaRepository;
	@Autowired
	private RicettaService ricettaService;
	@Autowired
	private CuocoService cuocoService;
	@Autowired
	private CuocoRepository cuocoRepository;
	@GetMapping("/generico/paginaricette")
	public String getRicette(Model model) {		
		model.addAttribute("ricette", this.ricettaService.findAll());
		return "/generico/paginaricette.html";
		}
	@GetMapping("/generico/ricetta/{id}")
	public String getRicetta(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ricetta", this.ricettaService.findById(id));
		return "/generico/ricetta.html";
	}
	@PostMapping("cuoco/nuovaRicetta")
	public String nuovaRicetta(@ModelAttribute("ricetta") Ricetta ricetta, Model model) {
		this.ricettaRepository.save(ricetta);
		return "/cuoco/indexCuoco.html";
	}
	@GetMapping("/cuoco/aggiungiRicetta")
	public String formnuovaRicetta(Model model) {
		model.addAttribute("ricetta", new Ricetta());
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.isAuthenticated()) {
	        for (GrantedAuthority auth : authentication.getAuthorities()) {
	            System.out.println("Current user role: " + auth.getAuthority());
	        }
	    }
	    return "/cuoco/aggiungiRicetta.html";
	}
	@GetMapping("/cuoco/setCuocoRicetta/{cuocoId}/{ricettaId}")
	public String setCuocoRicetta(@PathVariable("cuocoId") Long cuocoId, @PathVariable("ricettaId") Long ricettaId, Model model) {
		
		Cuoco cuoco = this.cuocoRepository.findById(cuocoId).get();
		Ricetta ricetta = this.ricettaRepository.findById(ricettaId).get();
		ricetta.setCuoco(cuoco);
		this.ricettaRepository.save(ricetta);
		model.addAttribute("cuochi", cuocoRepository.findAll());
		model.addAttribute("ricetta", ricettaRepository.findById(ricettaId).get());
		return "cuoco/cuocoDaAggiungere.html";
	}
	
	
	@GetMapping("cuoco/aggiungiCuocoRicetta/{ricettaid}")
	public String aggiungiCuocoRicetta(@PathVariable("ricettaid") Long id, Model model) {
		model.addAttribute("cuochi", cuocoRepository.findAll());
		model.addAttribute("ricetta", ricettaRepository.findById(id).get());
		return "cuoco/cuocoDaAggiungere.html";
	}
}
