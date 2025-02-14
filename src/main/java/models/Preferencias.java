package models;

import jakarta.persistence.*;

@Entity
@Table(name = "Preferencias")
public class Preferencias {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_preferencia;

	@Column(name = "id_usuario")
	private Integer id_usuario;

	@Column(name = "tipo_preferencia")
	private int tipo;

	@Column(length = 20)
	private String preferencia;

	public Integer getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getPreferencia() {
		return preferencia;
	}

	public void setPreferencia(String preferencia) {
		this.preferencia = preferencia;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

}