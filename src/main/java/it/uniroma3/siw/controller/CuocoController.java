package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.repository.CuocoRepository;
import it.uniroma3.siw.service.CuocoService;

@Controller
public class CuocoController {
	@Autowired
	private CuocoRepository cuocoRepository;
	@Autowired
	private CuocoService cuocoService;
	@GetMapping("/paginacuochi")
	public String getCuoco(Model model) {		
		model.addAttribute("cuochi", this.cuocoService.findAll());
		return "paginacuochi.html";
		}
	@GetMapping("/menu")
	public String getmenu() {		
		return "menu.html";
		}
	@GetMapping("/cuoco/{id}")
	public String getRicetta(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cuoco", this.cuocoService.findById(id));
		return "cuoco.html";
	}
}
