package models;

import jakarta.persistence.*;

@Entity
@Table(name = "Capturas")
public class Capturas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_captura;
    
    @ManyToOne
    @JoinColumn(name = "id_nuevo_juego", nullable = false)
    private JuegoNuevo juegoNuevo;
    
    @Column(columnDefinition = "TINYBLOB")
    private byte[] captura;

    

	public Integer getId_captura() {
		return id_captura;
	}

	public void setId_captura(Integer id_captura) {
		this.id_captura = id_captura;
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