package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.dominio.pagos.ComprobantePago;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.pagos.Factura;
import com.jmq.inversiones.jmqpersistencia.dao.FacturaDAO;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;


public class FacturaDAOImpl extends BaseDAOImpl<Factura> implements FacturaDAO{

    private final ComprobantePagoDAOImpl comprobanteDAO = new ComprobantePagoDAOImpl() {};
        
    @Override
    protected String getInsertQuery() {
        return  " INSERT INTO Factura (id, RUC, razon_social, direccion, fecha_emision) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE Factura SET RUC=?, razon_social = ?, direccion = ?, fecha_emision = ? WHERE id = ?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM Factura WHERE id = ?";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM Factura INNER JOIN ComprobantePago USING(id) WHERE id = ?";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT *FROM Factura INNER JOIN ComprobantePago USING(id)";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Factura entity) throws SQLException {
        //ya fue insertado en comprobantePago antes de llegar aquí
        ps.setInt(1,entity.getId()); //clave heredada
        ps.setString(2,entity.getRUC());
        ps.setString(3,entity.getRazon_social());
        ps.setString(4,entity.getDireccion());
        ps.setTimestamp(5,new Timestamp(entity.getFecha_emision().getTime()));  
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Factura entity) throws SQLException {
        ps.setString(1,entity.getRUC());
        ps.setString(2,entity.getRazon_social());
        ps.setString(3,entity.getDireccion());
        ps.setTimestamp(4,new Timestamp(entity.getFecha_emision().getTime()));  
        ps.setInt(5, entity.getId());
    }

    @Override
    protected Factura createFromResultSet(ResultSet rs) throws SQLException {
        Factura f = new Factura();
        comprobanteDAO.fillFromResultSetBase(f, rs);
        //Datos Propios
        f.setRUC(rs.getString("RUC"));
        f.setRazon_social(rs.getString("razon_social"));
        f.setDireccion(rs.getString("direccion"));
        f.setFecha_emision(rs.getDate("fecha_emision"));
        return f;
    }

    @Override
    protected void setId(Factura entity, Integer id) {
        entity.setId(id);
    }
    
    @Override 
    public void agregar(Factura f){
        comprobanteDAO.agregar(f);
        super.agregar(f);
    }
    
    @Override
    public void actualizar(Factura f){
        comprobanteDAO.actualizar(f);
        super.actualizar(f);
    }
    
    
    public void eliminar(int id){
        super.eliminar(id);
        comprobanteDAO.eliminar(id);
    }
    
}
