
package es.rubenmejias.jugadoresbaloncesto.entities;

import es.rubenmejias.jugadoresbaloncesto.entities.Jugador;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Ruben Mejias
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "EQUIPO")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Equipo.findAll", query = "SELECT e FROM Equipo e"),
    @javax.persistence.NamedQuery(name = "Equipo.findByIdEquip", query = "SELECT e FROM Equipo e WHERE e.idEquip = :idEquip"),
    @javax.persistence.NamedQuery(name = "Equipo.findByCodigo", query = "SELECT e FROM Equipo e WHERE e.codigo = :codigo"),
    @javax.persistence.NamedQuery(name = "Equipo.findByNombre", query = "SELECT e FROM Equipo e WHERE e.nombre = :nombre")})
public class Equipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "ID_EQUIP")
    private Integer idEquip;
    @javax.persistence.Column(name = "CODIGO")
    private String codigo;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "NOMBRE")
    private String nombre;
    @javax.persistence.OneToMany(mappedBy = "equipo")
    private Collection<Jugador> jugadorCollection;

    public Equipo() {
    }

    public Equipo(Integer idEquip) {
        this.idEquip = idEquip;
    }

    public Equipo(Integer idEquip, String nombre) {
        this.idEquip = idEquip;
        this.nombre = nombre;
    }

    public Integer getIdEquip() {
        return idEquip;
    }

    public void setIdEquip(Integer idEquip) {
        this.idEquip = idEquip;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Collection<Jugador> getJugadorCollection() {
        return jugadorCollection;
    }

    public void setJugadorCollection(Collection<Jugador> jugadorCollection) {
        this.jugadorCollection = jugadorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEquip != null ? idEquip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equipo)) {
            return false;
        }
        Equipo other = (Equipo) object;
        if ((this.idEquip == null && other.idEquip != null) || (this.idEquip != null && !this.idEquip.equals(other.idEquip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.rubenmejias.jugadoresbaloncesto.entities.Equipo[ idEquip=" + idEquip + " ]";
    }
    
}
