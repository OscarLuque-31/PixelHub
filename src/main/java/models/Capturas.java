package models;

import jakarta.persistence.*;

@Entity
@Table(name = "Capturas")
public class Capturas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_captura")
    private Integer idCaptura;

    @ManyToOne
    @JoinColumn(name = "id_juego", nullable = false)
    private JuegosBiblioteca juego;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] captura;

    // Getters y Setters
    public Integer getIdCaptura() {
        return idCaptura;
    }

    public void setIdCaptura(Integer idCaptura) {
        this.idCaptura = idCaptura;
    }

    public JuegosBiblioteca getJuego() {
        return juego;
    }

    public void setJuego(JuegosBiblioteca juego) {
        this.juego = juego;
    }

    public byte[] getCaptura() {
        return captura;
    }

    public void setCaptura(byte[] captura) {
        this.captura = captura;
    }
}