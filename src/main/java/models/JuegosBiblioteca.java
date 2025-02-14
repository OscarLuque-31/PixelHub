package models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "JuegosBiblioteca")
public class JuegosBiblioteca {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_juego")
	private Integer idJuego;

	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;

	@Column(name = "fecha_añadido")
	private Date fechaAñadido;

	@Column(name = "url_imagen", nullable = true)
	private String url_imagen;

	@Column(nullable = false)
	private Integer rating;

	@Column(length = 120)
	private String comentario;

	@Column
	private String titulo;

	@Column(columnDefinition = "TEXT")
	private String descripcion;

	@Column(nullable = false)
	private Boolean comprado;

	@Column(nullable = false)
	private Boolean jugado;

	@Column(nullable = false)
	private Boolean deseado;

	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] imagen;

	@OneToMany(mappedBy = "juego", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Capturas> capturas;

	@OneToMany(mappedBy = "juego", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Plataformas> plataformas;

	// Getters y Setters
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

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

	public String getUrlImagen() {
		return url_imagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.url_imagen = urlImagen;
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