package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.pagos.Descuento;
import com.jmq.inversiones.jmqpersistencia.dao.DescuentoDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


public class DescuentoDAOImpl extends BaseDAOImpl<Descuento> implements DescuentoDAO{

    
    @Override
    protected String getInsertQuery() {
        return "{CALL DESCUENTO_INSERTAR(?, ?, ?)}";
    }

    @Override
    protected String getUpdateQuery() {
        return "{CALL DESCUENTO_MODIFICAR( ?, ?)}";
    }

    @Override
    protected String getDeleteQuery() {
        return "{CALL DESCUENTO_ELIMINAR( ?)}";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM Descuento d WHERE d.idDescuento=?";
    }

    @Override
    protected String getSelectAllQuery() {
        return "{CALL DESCUENTO_LISTAR()}";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Descuento entity) throws SQLException {
        CallableStatement cs = (CallableStatement) ps;
        cs.registerOutParameter(1, Types.INTEGER);
        cs.setDouble(2, entity.getNumDescuento());
        cs.setBoolean(3, entity.isActivo());
        cs.execute();
        //setId(entity, cs.getInt(1));
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Descuento entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected Descuento createFromResultSet(ResultSet rs) throws SQLException {
        Descuento des = new Descuento();
        des.setId(rs.getInt("idDescuento"));
        des.setNumDescuento(rs.getInt("numDescuento"));
        return des;
    }

    @Override
    protected void setId(Descuento entity, Integer id) {
        entity.setId(id);// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
//    @Override
//    public void agregar(Descuento desc) {
//        // Sobrescribimos el método para usar el SP
//        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
//            conn.setAutoCommit(false);
//            try {
//                // Registrar el descuento
//                try (CallableStatement cs = conn.prepareCall(getInsertQuery())) {
//                    
//                    cs.registerOutParameter(1, Types.INTEGER);
//                    cs.setDouble(2, desc.getPorcentaje());
//                    cs.setBoolean(3, true);
//                    cs.execute();
//                    setId(desc, cs.getInt(1));
//                }
//                
//                
//                conn.commit();
//            } catch (SQLException e) {
//                conn.rollback();
//                throw e;
//            } finally {
//                conn.setAutoCommit(true);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Error al registrar descuento", e);
//        }
//    }
    
    @Override
    public void actualizar(Descuento desc) {
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(getUpdateQuery())) {
            
            cs.setInt(1, desc.getId());
            cs.setDouble(2, desc.getNumDescuento());
            cs.execute();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar descuento", e);
        }
    }
    @Override
    public void eliminar(Integer id) {
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(getDeleteQuery())) {
            
            cs.setInt(1,id);
            //cs.setDouble(2, desc.getPorcentaje());
            cs.execute();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar descuento", e);
        }
    }
    
//    @Override
//    public List<Descuento> listarTodos() {
//        List<Descuento> descuentos = new ArrayList<>();
//        try (Connection conn = DBManager.getInstance().obtenerConexion();
//             CallableStatement cs = conn.prepareCall(getSelectAllQuery());
//             ResultSet rs = cs.executeQuery()) {
//            
//            while (rs.next()) {
//                descuentos.add(createFromResultSet(rs));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Error al listar descuentos", e);
//        }
//        return descuentos;
//    }
    
}
