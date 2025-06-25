package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.dominio.pagos.Descuento;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.jmqpersistencia.dao.DescuentoDAO;

import java.sql.*;

public class DescuentoDAOImpl extends BaseDAOImpl<Descuento> implements DescuentoDAO {

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO Descuento (porcentaje, activo) VALUES (?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE Descuento SET porcentaje = ?, activo = ? WHERE id = ?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM Descuento WHERE id = ?";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM Descuento WHERE id = ?";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM Descuento";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Descuento entity) throws SQLException {
        ps.setDouble(1, entity.getNumDescuento());
        ps.setBoolean(2, entity.isActivo());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Descuento entity) throws SQLException {
        ps.setDouble(1, entity.getNumDescuento());
        ps.setBoolean(2, entity.isActivo());
        ps.setInt(3, entity.getId());
    }

    @Override
    protected Descuento createFromResultSet(ResultSet rs) throws SQLException {
        Descuento d = new Descuento();
        d.setId(rs.getInt("id"));
        d.setNumDescuento(rs.getDouble("porcentaje"));
        d.setActivo(rs.getBoolean("activo"));
        return d;
    }

    @Override
    protected void setId(Descuento entity, Integer id) {
        entity.setId(id);
    }
}
