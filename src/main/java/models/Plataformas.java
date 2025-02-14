package models;

import jakarta.persistence.*;

@Entity
@Table(name = "Plataformas")
public class Plataformas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_plataforma")
	private Integer idPlataforma;

	@ManyToOne
	@JoinColumn(name = "id_juego", nullable = false)
	private JuegosBiblioteca juego;

	@Column(nullable = false, length = 50)
	private String plataforma;

	// Getters y Setters
	public Integer getIdPlataforma() {
		return idPlataforma;
	}

	public void setIdPlataforma(Integer idPlataforma) {
		this.idPlataforma = idPlataforma;
	}

	public JuegosBiblioteca getJuego() {
		return juego;
	}

	public void setJuego(JuegosBiblioteca juego) {
		this.juego = juego;
	}

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}
}