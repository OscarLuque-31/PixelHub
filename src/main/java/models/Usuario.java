package models;

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
    
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    
    @Column(nullable = false, length = 255)
    private String password;
    
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Preferencias preferencias;
    
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

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Preferencias getPreferencias() {
		return preferencias;
	}

	public void setPreferencias(Preferencias preferencias) {
		this.preferencias = preferencias;
	}

	public List<JuegosBiblioteca> getJuegosBiblioteca() {
		return juegosBiblioteca;
	}

	public void setJuegosBiblioteca(List<JuegosBiblioteca> juegosBiblioteca) {
		this.juegosBiblioteca = juegosBiblioteca;
	}
    
}