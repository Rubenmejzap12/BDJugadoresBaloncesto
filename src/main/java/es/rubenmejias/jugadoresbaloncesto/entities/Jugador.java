
package es.rubenmejias.jugadoresbaloncesto.entities;

import es.rubenmejias.jugadoresbaloncesto.entities.Equipo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Ruben Mejias
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "JUGADOR")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Jugador.findAll", query = "SELECT j FROM Jugador j"),
    @javax.persistence.NamedQuery(name = "Jugador.findByIdJugad", query = "SELECT j FROM Jugador j WHERE j.idJugad = :idJugad"),
    @javax.persistence.NamedQuery(name = "Jugador.findByNombre", query = "SELECT j FROM Jugador j WHERE j.nombre = :nombre"),
    @javax.persistence.NamedQuery(name = "Jugador.findByApellidos", query = "SELECT j FROM Jugador j WHERE j.apellidos = :apellidos"),
    @javax.persistence.NamedQuery(name = "Jugador.findByTelefono", query = "SELECT j FROM Jugador j WHERE j.telefono = :telefono"),
    @javax.persistence.NamedQuery(name = "Jugador.findByEmail", query = "SELECT j FROM Jugador j WHERE j.email = :email"),
    @javax.persistence.NamedQuery(name = "Jugador.findByFechaNacimiento", query = "SELECT j FROM Jugador j WHERE j.fechaNacimiento = :fechaNacimiento"),
    @javax.persistence.NamedQuery(name = "Jugador.findByNumHijos", query = "SELECT j FROM Jugador j WHERE j.numHijos = :numHijos"),
    @javax.persistence.NamedQuery(name = "Jugador.findByPosicion", query = "SELECT j FROM Jugador j WHERE j.posicion = :posicion"),
    @javax.persistence.NamedQuery(name = "Jugador.findBySalario", query = "SELECT j FROM Jugador j WHERE j.salario = :salario"),
    @javax.persistence.NamedQuery(name = "Jugador.findByActivo", query = "SELECT j FROM Jugador j WHERE j.activo = :activo"),
    @javax.persistence.NamedQuery(name = "Jugador.findByFoto", query = "SELECT j FROM Jugador j WHERE j.foto = :foto")})
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "ID_JUGAD")
    private Integer idJugad;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "NOMBRE")
    private String nombre;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "APELLIDOS")
    private String apellidos;
    @javax.persistence.Column(name = "TELEFONO")
    private String telefono;
    @javax.persistence.Column(name = "EMAIL")
    private String email;
    @javax.persistence.Column(name = "FECHA_NACIMIENTO")
    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaNacimiento;
    @javax.persistence.Column(name = "NUM_HIJOS")
    private Short numHijos;
    @javax.persistence.Column(name = "POSICION")
    private Character posicion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @javax.persistence.Column(name = "SALARIO")
    private BigDecimal salario;
    @javax.persistence.Column(name = "ACTIVO")
    private Boolean activo;
    @javax.persistence.Column(name = "FOTO")
    private String foto;
    @javax.persistence.JoinColumn(name = "EQUIPO", referencedColumnName = "ID_EQUIP")
    @javax.persistence.ManyToOne
    private Equipo equipo;

    public Jugador() {
    }

    public Jugador(Integer idJugad) {
        this.idJugad = idJugad;
    }

    public Jugador(Integer idJugad, String nombre, String apellidos) {
        this.idJugad = idJugad;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public Integer getIdJugad() {
        return idJugad;
    }

    public void setIdJugad(Integer idJugad) {
        this.idJugad = idJugad;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public Short getNumHijos() {
        return numHijos;
    }

    public void setNumHijos(Short numHijos) {
        this.numHijos = numHijos;
    }

    public Character getPosicion() {
        return posicion;
    }

    public void setPosicion(Character posicion) {
        this.posicion = posicion;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idJugad != null ? idJugad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jugador)) {
            return false;
        }
        Jugador other = (Jugador) object;
        if ((this.idJugad == null && other.idJugad != null) || (this.idJugad != null && !this.idJugad.equals(other.idJugad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.rubenmejias.jugadoresbaloncesto.entities.Jugador[ idJugad=" + idJugad + " ]";
    }
    
}
