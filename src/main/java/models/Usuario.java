package models;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "Usuario")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 30)
	private String username;

	@Column(nullable = false, length = 30)
	private String nombre;

	@Column(nullable = false, length = 50)
	private String apellidos;

	@Column(nullable = false, unique = true, length = 100)
	private String email;

	@Column(name = "fecha_nacimiento", columnDefinition = "Date")
	private Date fecha_nacimiento;

	@Column(nullable = false, length = 255)
	private String password;

	@Column(name = "fecha_creacion", columnDefinition = "Date")
	private LocalDate fecha_creacion;


	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<JuegosBiblioteca> juegosBiblioteca;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public LocalDate getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(LocalDate fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public List<JuegosBiblioteca> getJuegosBiblioteca() {
		return juegosBiblioteca;
	}

	public void setJuegosBiblioteca(List<JuegosBiblioteca> juegosBiblioteca) {
		this.juegosBiblioteca = juegosBiblioteca;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", nombre=" + nombre + ", apellidos=" + apellidos
				+ ", email=" + email + ", fecha_nacimiento=" + fecha_nacimiento + ", password=" + password
				+ ", fecha_creacion=" + fecha_creacion + ", juegosBiblioteca="
				+ juegosBiblioteca + "]";
	}
	
	

}