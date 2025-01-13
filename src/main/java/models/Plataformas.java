package models;

import jakarta.persistence.*;

@Entity
@Table(name = "Plataformas")
public class Plataformas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPlataforma;
    
    @ManyToOne
    @JoinColumn(name = "id_nuevo_juego", nullable = false)
    private JuegoNuevo juegoNuevo;
    
    @Column(nullable = false, length = 50)
    private String plataforma;

    
    
	public Integer getIdPlataforma() {
		return idPlataforma;
	}

	public void setIdPlataforma(Integer idPlataforma) {
		this.idPlataforma = idPlataforma;
	}

	public JuegoNuevo getJuegoNuevo() {
		return juegoNuevo;
	}

	public void setJuegoNuevo(JuegoNuevo juegoNuevo) {
		this.juegoNuevo = juegoNuevo;
	}

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}
    
}