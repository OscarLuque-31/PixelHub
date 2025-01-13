package models;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "JuegosBiblioteca")
public class JuegosBiblioteca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idJuego;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    
    @Temporal(TemporalType.DATE)
    private Date fechaAñadido;
    
    private Integer rating;
    
    @Column(length = 120)
    private String comentario;
    
    private Boolean comprado;
    private Boolean jugado;
    private Boolean deseado;
    
    
    
	public Integer getIdJuego() {
		return idJuego;
	}
	public void setIdJuego(Integer idJuego) {
		this.idJuego = idJuego;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Date getFechaAñadido() {
		return fechaAñadido;
	}
	public void setFechaAñadido(Date fechaAñadido) {
		this.fechaAñadido = fechaAñadido;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Boolean getComprado() {
		return comprado;
	}
	public void setComprado(Boolean comprado) {
		this.comprado = comprado;
	}
	public Boolean getJugado() {
		return jugado;
	}
	public void setJugado(Boolean jugado) {
		this.jugado = jugado;
	}
	public Boolean getDeseado() {
		return deseado;
	}
	public void setDeseado(Boolean deseado) {
		this.deseado = deseado;
	}
    
}