package models;

import jakarta.persistence.*;

@Entity
@Table(name = "Capturas")
public class Capturas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCaptura;
    
    @ManyToOne
    @JoinColumn(name = "id_nuevo_juego", nullable = false)
    private JuegoNuevo juegoNuevo;
    
    @Lob
    private byte[] captura;

    
    
	public Integer getIdCaptura() {
		return idCaptura;
	}

	public void setIdCaptura(Integer idCaptura) {
		this.idCaptura = idCaptura;
	}

	public JuegoNuevo getJuegoNuevo() {
		return juegoNuevo;
	}

	public void setJuegoNuevo(JuegoNuevo juegoNuevo) {
		this.juegoNuevo = juegoNuevo;
	}

	public byte[] getCaptura() {
		return captura;
	}

	public void setCaptura(byte[] captura) {
		this.captura = captura;
	}
    
}