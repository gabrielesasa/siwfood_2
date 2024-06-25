package it.uniroma3.siw.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.IngredientePerRicetta;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.repository.CuocoRepository;
import it.uniroma3.siw.repository.RicettaRepository;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.RicettaService;

@Controller
public class RicettaController {
	@Autowired
	private RicettaRepository ricettaRepository;
	@Autowired
	private RicettaService ricettaService;
	@Autowired
	private IngredienteService ingredienteService;
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
		Ricetta ricetta=this.ricettaService.findById(id);
		List<IngredientePerRicetta> ingredienteRicetta=ricetta.getIngredienteRicetta();
		if(!ingredienteRicetta.isEmpty()) {
		IngredientePerRicetta ingre=ingredienteRicetta.get(0);									///serve query
		Ingrediente in=ingre.getIngrediente();
		Long idingrediente=in.getId();
		model.addAttribute("ingre", this.ingredienteService.findById(idingrediente));}
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
		model.addAttribute("cuochi", this.cuocoRepository.findAll());
		model.addAttribute("ricetta", this.ricettaRepository.findById(ricettaId).get());
		return "cuoco/cuocoDaAggiungere.html";
	}
	
	
	@GetMapping("cuoco/aggiungiCuocoRicetta/{ricettaid}")
	public String aggiungiCuocoRicetta(@PathVariable("ricettaid") Long id, Model model) {
		model.addAttribute("cuochi", this.cuocoRepository.findAll());
		model.addAttribute("ricetta", this.ricettaRepository.findById(id).get());
		return "cuoco/cuocoDaAggiungere.html";
	}
	@GetMapping("cuoco/aggiungiIngredienteRicetta")
	public String aggiungiRicetta(Model model) {
		model.addAttribute("ricette", this.ricettaRepository.findAll());
	    
	    return "cuoco/aggiungiIngredienteRicetta.html";
	}
	@GetMapping("/cuoco/AggiornaRicette")
	public String AggiornaRicette(Model model) {
		model.addAttribute("ricette",this.ricettaRepository.findAll());
	    return "cuoco/aggiornaRicette.html";
	}
	@GetMapping("/cuoco/cancellaRicetta")
	public String CancellaRicette(Model model) {
		model.addAttribute("ricette",this.ricettaRepository.findAll());
	    return "cuoco/cancellaRicetta.html";
	}
	@GetMapping("cuoco/formAggiornaRicetta/{ricettaid}")
	public String formAggiornaRicetta(@PathVariable("ricettaid") Long id, Model model) {
		model.addAttribute("cuochi", cuocoRepository.findAll());
		model.addAttribute("ricetta", ricettaRepository.findById(id).get());
		return "cuoco/formAggiornaRicetta.html";
	}
	@GetMapping("cuoco/CancellaRicetta/{ricettaid}")
	public String formCancellaRicetta(@PathVariable("ricettaid") Long id, Model model) {			//aggiungere metodo cascate
		Ricetta ricetta=this.ricettaRepository.findById(id).get();
		this.ricettaRepository.delete(ricetta);
		return "cuoco/cancellaRicetta.html";
	}
	@GetMapping("cuoco/aggiornaNome/{ricettaid}")
	public String formAggiornaNomeRicetta(@PathVariable("ricettaid") Long id, Model model) {
		model.addAttribute("ricetta", ricettaRepository.findById(id).get());
		return "cuoco/formAggiornaNome.html";
	}
	@PostMapping("cuoco/formAggiornaNome/{id}")
	public String formAggiornaNomeRicetta(@ModelAttribute("ricetta") Ricetta ricetta,@PathVariable("id") Long id, @RequestParam("nuovoNome") String nuovoNome, Model model) {
		ricetta.setNome(nuovoNome);
		this.ricettaRepository.save(ricetta);
		return "cuoco/formAggiornaNome.html";
	}
}
