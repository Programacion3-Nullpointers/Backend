package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.pagos.Boleta;
import com.jmq.inversiones.dominio.pagos.MetodoPago;
import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import com.jmq.inversiones.jmqpersistencia.dao.BoletaDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
 

public class BoletaDAOImpl extends BaseDAOImpl<Boleta> implements BoletaDAO{

    private final ComprobantePagoDAOImpl comprobanteDAO = new ComprobantePagoDAOImpl() {};
    
    @Override
    protected String getInsertQuery() {
        return "{CALL BOLETA_INSERTAR(?, ?, ?, ?)}";
    }

    @Override
    protected String getUpdateQuery() {
        return "{CALL BOLETA_ACTUALIZAR(?, ?, ?, ?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM Boleta WHERE idBoleta = ?";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "{CALL BOLETA_OBTENER_POR_ID(?)}";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM Boleta INNER JOIN ComprobantePago ON Boleta.idBoleta = ComprobantePago.idComprobantePago";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Boleta entity) throws SQLException {
        CallableStatement cs = (CallableStatement) ps;
        cs.setInt(1, entity.getId());
        cs.setString(2, entity.getDni());
        cs.setString(3, entity.getNombre());
        cs.setTimestamp(4, new Timestamp(entity.getFecha_emision().getTime()));
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Boleta entity) throws SQLException {
        CallableStatement cs = (CallableStatement) ps;
        cs.setInt(1, entity.getId());
        cs.setString(2, entity.getDni());
        cs.setString(3, entity.getNombre());
        cs.setTimestamp(4, new Timestamp(entity.getFecha_emision().getTime()));
        cs.setInt(5, entity.getOrden().getId());
        cs.setString(6, entity.getMetodoPago().name());
        cs.setTimestamp(7, new Timestamp(entity.getFecha_pago().getTime()));
        cs.setDouble(8, entity.getMonto_total());
    }

    @Override
    protected Boleta createFromResultSet(ResultSet rs) throws SQLException {
        Boleta b = new Boleta();
        
        b.setId(rs.getInt("idBoleta"));
        b.setDni(rs.getString("dni"));
        b.setNombre(rs.getString("nombre"));
        b.setFecha_emision(rs.getDate("fecha_emision"));
        int id_orden = rs.getInt("id_orden");
        OrdenVenta orden = new OrdenVenta();
        orden.setId(id_orden);
        b.setOrden(orden);
        b.setMetodoPago(MetodoPago.valueOf(rs.getString("metodo_pago")));
        b.setFecha_pago(rs.getTimestamp("fecha_pago"));
        b.setMonto_total(rs.getDouble("monto_total"));
        
        return b;
    }

    @Override
    protected void setId(Boleta entity, Integer id) {
        entity.setId(id);
    }
    
    @Override 
    public void agregar(Boleta boleta) {
        // Primero insertas en ComprobantePago
        comprobanteDAO.agregar(boleta);

        // Luego insertas en Boleta
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(getInsertQuery())) {

            cs.setInt(1, boleta.getId()); // ID ya seteado por ComprobantePago
            cs.setString(2, boleta.getDni());
            cs.setString(3, boleta.getNombre());
            cs.setTimestamp(4, new Timestamp(boleta.getFecha_emision().getTime()));
            cs.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar Boleta", e);
        }
    }

//    @Override
//    public void agregar(Boleta boleta){
//        comprobanteDAO.agregar(boleta);
//        super.agregar(boleta);
//    }
    @Override
    public void actualizar(Boleta f){
        comprobanteDAO.actualizar(f);
        super.actualizar(f);
    }
    
    
    public void eliminar(int id){
        super.eliminar(id);
        comprobanteDAO.eliminar(id);
    }
    
//    @Override
//    public void eliminarHeredado(int id){
//        try (Connection conn = DBManager.getInstance().obtenerConexion();
//             PreparedStatement ps = conn.prepareStatement(getDeleteQuery())) {
//            
//            ps.setInt(1, id);
//            ps.executeUpdate();
//            comprobanteDAO.eliminar(id);
//        } catch (SQLException e) {
//            throw new RuntimeException("Error al eliminar entidad", e);
//        }
//    }
}
