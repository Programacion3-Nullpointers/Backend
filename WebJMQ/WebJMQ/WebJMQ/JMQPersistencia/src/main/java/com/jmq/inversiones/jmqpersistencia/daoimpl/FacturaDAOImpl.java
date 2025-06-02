package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.dominio.pagos.ComprobantePago;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.pagos.Factura;
import com.jmq.inversiones.jmqpersistencia.dao.FacturaDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;


public class FacturaDAOImpl extends BaseDAOImpl<Factura> implements FacturaDAO{

    private final ComprobantePagoDAOImpl comprobanteDAO = new ComprobantePagoDAOImpl() {};
        
    @Override
    protected String getInsertQuery() {
        return "{CALL FACTURA_INSERTAR(?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getUpdateQuery() {
         return "{CALL FACTURA_ACTUALIZAR(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM Factura WHERE idFactura = ?";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "{CALL FACTURA_OBTENER_POR_ID(?)}";
    }

    @Override
    protected String getSelectAllQuery() {
         return "SELECT * FROM Factura INNER JOIN ComprobantePago ON Factura.idFactura = ComprobantePago.idComprobantePago";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Factura entity) throws SQLException {
        //ya fue insertado en comprobantePago antes de llegar aquí
        CallableStatement cs = (CallableStatement) ps;
        cs.setInt(1,entity.getId()); //clave heredada
        cs.setString(2,entity.getRUC());
        cs.setString(3,entity.getRazon_social());
        cs.setString(4,entity.getDireccion());
        cs.setTimestamp(5,new Timestamp(entity.getFecha_emision().getTime()));  
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Factura entity) throws SQLException {
        CallableStatement cs = (CallableStatement) ps;
        cs.setInt(1, entity.getId());
        cs.setString(2,entity.getRUC());
        cs.setString(3,entity.getRazon_social());
        cs.setString(4,entity.getDireccion());
        cs.setTimestamp(5,new Timestamp(entity.getFecha_emision().getTime()));  
        cs.setInt(6, entity.getOrden().getId());
        cs.setString(7, entity.getMetodoPago().name());
        cs.setTimestamp(8, new Timestamp(entity.getFecha_pago().getTime()));
        cs.setDouble(9, entity.getMonto_total());
    }

    @Override
    protected Factura createFromResultSet(ResultSet rs) throws SQLException {
        Factura f = new Factura();
        comprobanteDAO.fillFromResultSetBase(f, rs);
        //Datos Propios
        f.setRUC(rs.getString("RUC"));
        f.setRazon_social(rs.getString("razon_social"));
        f.setDireccion(rs.getString("direccion_fiscal"));
        f.setFecha_emision(rs.getDate("fecha_emision"));
        
        return f;
    }

    @Override
    protected void setId(Factura entity, Integer id) {
        entity.setId(id);
    }

    @Override
    public void agregar(Factura factura) {
        // Paso 1: Insertar en ComprobantePago (padre)
        comprobanteDAO.agregar(factura); // Este debe establecer el ID en la entidad

        // Paso 2: Insertar en Factura (hija)
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(getInsertQuery())) {

            setInsertParameters(cs, factura);
            cs.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Error al agregar Factura", e);
        }
    }
    

//    @Override 
//    public void agregar(Factura f){
//        comprobanteDAO.agregar(f);
//        super.agregar(f);
//    }
    
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
