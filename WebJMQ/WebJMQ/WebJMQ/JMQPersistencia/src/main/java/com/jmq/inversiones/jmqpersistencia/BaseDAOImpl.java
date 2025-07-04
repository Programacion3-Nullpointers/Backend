package com.jmq.inversiones.jmqpersistencia;
import com.jmq.inversiones.dbmanager.DBManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAOImpl<T> implements BaseDAO<T> {
    
    protected abstract String getInsertQuery();
    protected abstract String getUpdateQuery();
    protected abstract String getDeleteQuery();
    protected abstract String getSelectByIdQuery();
    protected abstract String getSelectAllQuery();
    
    protected abstract void setInsertParameters(PreparedStatement ps, T entity) throws SQLException;
    protected abstract void setUpdateParameters(PreparedStatement ps, T entity) throws SQLException;
    protected abstract T createFromResultSet(ResultSet rs) throws SQLException;
    protected abstract void setId(T entity, Integer id);
    
    @Override
    public void agregar(T entity) {
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(getInsertQuery())) {

            setInsertParameters(cs, entity);
            cs.execute();

            int idGenerado = cs.getInt(1);
            if (idGenerado <= 0) {
                throw new SQLException("No se generó un ID válido al insertar. El procedimiento pudo haber fallado.");
            }
            setId(entity, idGenerado);

        } catch (SQLException e) {
            throw new RuntimeException("Error al agregar entidad", e);
        }
    }

    @Override
    public T obtener(Integer id) {
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(getSelectByIdQuery())) {
            
            //setUpdateParameters(cs, );
            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return createFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener entidad", e);
        }
        return null;
    }

    @Override
    public List<T> listarTodos() {
        List<T> entities = new ArrayList<>();
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(getSelectAllQuery());
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                entities.add(createFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar entidades", e);
        }
        return entities;
    }

    @Override
    public void actualizar(T entity) {
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement ps = conn.prepareCall(getUpdateQuery())) {
            
            setUpdateParameters(ps, entity);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar entidad", e);
        }
    }

    @Override
    public void eliminar(Integer id) {
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(getDeleteQuery())) {
            
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar entidad", e);
        }
    }
}