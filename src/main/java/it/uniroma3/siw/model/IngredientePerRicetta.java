package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class IngredientePerRicetta {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long quantita;
	@ManyToOne											//da verificare !!!!!!!
	private Ingrediente ingrediente;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getQuantita() {
		return quantita;
	}
	public void setQuantita(Long quantita) {
		this.quantita = quantita;
	}
	public Ingrediente getIngrediente() {
		return ingrediente;
	}
	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IngredientePerRicetta other = (IngredientePerRicetta) obj;
		return Objects.equals(id, other.id);
	}
	
}
