package models;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "JuegoNuevo")
public class JuegoNuevo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idNuevoJuego;
    
    private Integer rating;
    
    @Column(length = 120)
    private String comentario;
    
    @Column(nullable = false, length = 60)
    private String titulo;
    
    @Column(length = 200)
    private String descripcion;
    
    private Boolean comprado;
    private Boolean jugado;
    private Boolean deseado;
    
    @Lob
    private byte[] imagen;
    
    @OneToMany(mappedBy = "juegoNuevo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Capturas> capturas;
    
    @OneToMany(mappedBy = "juegoNuevo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plataformas> plataformas;

    
    
	public Integer getIdNuevoJuego() {
		return idNuevoJuego;
	}

	public void setIdNuevoJuego(Integer idNuevoJuego) {
		this.idNuevoJuego = idNuevoJuego;
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public List<Capturas> getCapturas() {
		return capturas;
	}

	public void setCapturas(List<Capturas> capturas) {
		this.capturas = capturas;
	}

	public List<Plataformas> getPlataformas() {
		return plataformas;
	}

	public void setPlataformas(List<Plataformas> plataformas) {
		this.plataformas = plataformas;
	}
}