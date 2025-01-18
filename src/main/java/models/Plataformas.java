package models;

import jakarta.persistence.*;

@Entity
@Table(name = "Plataformas")
public class Plataformas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_plataforma;
    
    @ManyToOne
    @JoinColumn(name = "id_nuevo_juego", nullable = false)
    private JuegoNuevo juegoNuevo;
    
    @Column(nullable = false, length = 50)
    private String plataforma;

    
	public Integer getId_plataforma() {
		return id_plataforma;
	}

	public void setId_plataforma(Integer id_plataforma) {
		this.id_plataforma = id_plataforma;
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