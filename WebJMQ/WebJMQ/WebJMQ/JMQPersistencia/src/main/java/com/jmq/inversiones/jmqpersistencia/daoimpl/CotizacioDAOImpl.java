package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.contizaciones.Cotizacion;
import com.jmq.inversiones.dominio.contizaciones.ProductoCotizacion;
import com.jmq.inversiones.jmqpersistencia.dao.CotizacionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CotizacioDAOImpl extends BaseDAOImpl<Cotizacion> implements CotizacionDAO{

    private final UsuarioDAOImpl usuario = new UsuarioDAOImpl();
    private final ProductoCotizacionDAOImpl productoCotizacionDAO = new ProductoCotizacionDAOImpl();
    
    @Override
    protected String getInsertQuery() {
        return "INSERT INTO Cotizacion(idUsuario, estadoCotización) VALUES (?,?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE Cotizacion SET estadoCotizacion = ? WHERE id= ?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM Cotizacion WHERE id=?";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM Cotizacion WHERE id= ?";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM Cotizacion";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Cotizacion entity) throws SQLException {
        ps.setInt(1, entity.getUsuario().getId());
        ps.setString(2, entity.getEstadoCotizacion());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Cotizacion entity) throws SQLException {
        
        ps.setString(1, entity.getEstadoCotizacion());
        ps.setInt(2, entity.getId());
    }

    @Override
    protected Cotizacion createFromResultSet(ResultSet rs) throws SQLException {
        Cotizacion coti = new Cotizacion();
        coti.setId(rs.getInt("idCotizacion"));
        coti.setUsuario(usuario.obtener(rs.getInt("idUsuario")));
        coti.setEstadoCotizacion(rs.getString("estadoCotizacion"));
        
        return coti;
    }

    @Override
    protected void setId(Cotizacion entity, Integer id) {
        entity.setId(id);
    }
    
      @Override
    public void agregar(Cotizacion cotizacion) {
        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(getInsertQuery(), Statement.RETURN_GENERATED_KEYS)) {
                setInsertParameters(ps, cotizacion);
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        setId(cotizacion, rs.getInt(1));
                    }
                }

                for (ProductoCotizacion pc : cotizacion.getProductos()) {
                    productoCotizacionDAO.agregar(pc);
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Error al agregar cotización", e);
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error de conexión al registrar cotización", e);
        }
    }


    @Override
    public void eliminar(Integer id) {
        Cotizacion coti = new Cotizacion();
        
        coti.setId(id);
        coti.setEstadoCotizacion("Cancelada");
        actualizar(coti);
    }

    @Override
    public void actualizarEstado(int id, String estado) {
        Cotizacion coti = new Cotizacion();
        coti.setId(id);
        coti.setEstadoCotizacion(estado);
        actualizar(coti);
    }
    
    
    
}
