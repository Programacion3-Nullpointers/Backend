package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.pagos.Boleta;
import com.jmq.inversiones.jmqpersistencia.dao.BoletaDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
 

public class BoletaDAOImpl extends BaseDAOImpl<Boleta> implements BoletaDAO{

    private final ComprobantePagoDAOImpl comprobanteDAO = new ComprobantePagoDAOImpl() {};
    
    @Override
    protected String getInsertQuery() {
        return "INSERT INTO Boleta(id,dni,nombre,fecha_emision) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE Boleta SET dni = ?, nombre = ?, fecha_emision = ? WHERE id = ?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM Boleta WHERE id = ?";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM Boleta INNER JOIN ComprobantePago USING(id) WHERE id = ?";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM Boleta INNER JOIN ComprobantePago USING(id)";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Boleta entity) throws SQLException {
        ps.setInt(1, entity.getId());
        ps.setString(2, entity.getDni());
        ps.setString(3, entity.getNombre());
        ps.setTimestamp(4, new Timestamp(entity.getFecha_emision().getTime()));
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Boleta entity) throws SQLException {
        ps.setString(1,entity.getDni());
        ps.setString(2,entity.getNombre());
        ps.setTimestamp(3,new Timestamp(entity.getFecha_emision().getTime()));
        ps.setInt(4, entity.getId());
    }

    @Override
    protected Boleta createFromResultSet(ResultSet rs) throws SQLException {
        Boleta b = new Boleta();
        
        b.setId(rs.getInt("idBoleta"));
        b.setDni(rs.getString("dni"));
        b.setNombre(rs.getString("nombre"));
        b.setFecha_emision(rs.getDate("fecha_emision"));
        b.setMonto_total(rs.getDouble("monto_total"));
        
        return b;
    }

    @Override
    protected void setId(Boleta entity, Integer id) {
        entity.setId(id);
    }
    
    @Override 
    public void agregar(Boleta f){
        comprobanteDAO.agregar(f);
        super.agregar(f);
    }
    
    @Override
    public void actualizar(Boleta f){
        comprobanteDAO.actualizar(f);
        super.actualizar(f);
    }
    
    
    public void eliminar(int id){
        super.eliminar(id);
        comprobanteDAO.eliminar(id);
    }
}
