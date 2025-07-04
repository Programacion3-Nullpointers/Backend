DROP PROCEDURE IF EXISTS PRODUCTO_INSERTAR;
DROP PROCEDURE IF EXISTS PRODUCTO_MODIFICAR;
DROP PROCEDURE IF EXISTS PRODUCTO_ELIMINAR;
DROP PROCEDURE IF EXISTS PRODUCTO_OBTENER;
DROP PROCEDURE IF EXISTS PRODUCTO_LISTAR;
DROP PROCEDURE IF EXISTS DESCUENTO_INSERTAR;
DROP PROCEDURE IF EXISTS DESCUENTO_MODIFICAR; 
DROP PROCEDURE IF EXISTS DESCUENTO_ELIMINAR; 
DROP PROCEDURE IF EXISTS DESCUENTO_LISTAR;
DROP PROCEDURE IF EXISTS CATEGORIA_INSERTAR;
DROP PROCEDURE IF EXISTS CATEGORIA_MODIFICAR;
DROP PROCEDURE IF EXISTS CATEGORIA_ELIMINAR;
DROP PROCEDURE IF EXISTS CATEGORIA_LISTAR;
DROP PROCEDURE IF EXISTS USUARIO_INSERTAR;
DROP PROCEDURE IF EXISTS USUARIO_MODIFICAR;
DROP PROCEDURE IF EXISTS USUARIO_ELIMINAR;
DROP PROCEDURE IF EXISTS USUARIO_LISTAR;
DROP PROCEDURE IF EXISTS ORDENVENTA_INSERTAR;
DROP PROCEDURE IF EXISTS ORDENVENTA_MODIFICAR;
DROP PROCEDURE IF EXISTS ORDENVENTA_ELIMINAR; 
DROP PROCEDURE IF EXISTS ORDENVENTA_LISTAR;
DROP PROCEDURE IF EXISTS DETALLE_INSERTAR;
DROP PROCEDURE IF EXISTS DETALLE_MODIFICAR;
DROP PROCEDURE IF EXISTS DETALLE_ELIMINAR; 
DROP PROCEDURE IF EXISTS DETALLE_LISTAR;
DROP PROCEDURE IF EXISTS ENTREGA_INSERTAR;
DROP PROCEDURE IF EXISTS ENTREGA_MODIFICAR;
DROP PROCEDURE IF EXISTS ENTREGA_ELIMINAR;
DROP PROCEDURE IF EXISTS ENTREGA_LISTAR;
DROP PROCEDURE IF EXISTS NOTIFICACION_INSERTAR;
DROP PROCEDURE IF EXISTS NOTIFICACION_MODIFICAR;
DROP PROCEDURE IF EXISTS NOTIFICACION_ELIMINAR; 
DROP PROCEDURE IF EXISTS NOTIFICACION_LISTAR;
DROP PROCEDURE IF EXISTS PRODUCTOCOTIZACION_INSERTAR;
DROP PROCEDURE IF EXISTS PRODUCTOCOTIZACION_MODIFICAR;
DROP PROCEDURE IF EXISTS PRODUCTOCOTIZACION_ELIMINAR;
DROP PROCEDURE IF EXISTS PRODUCTOCOTIZACION_LISTAR;
DROP PROCEDURE IF EXISTS PRODUCTOCOTIZACION_OBTENER;
DROP PROCEDURE IF EXISTS BOLETA_INSERTAR;
DROP PROCEDURE IF EXISTS BOLETA_ELIMINAR;
DROP PROCEDURE IF EXISTS BOLETA_LISTAR;
DROP PROCEDURE IF EXISTS BOLETA_OBTENER_POR_ID;
DROP PROCEDURE IF EXISTS BOLETA_ACTUALIZAR;
DROP PROCEDURE IF EXISTS sp_buscar_usuario_por_id;
DROP PROCEDURE IF EXISTS COMPROBANTE_PAGO_INSERTAR;
DROP PROCEDURE IF EXISTS COMPROBANTE_OBTENER;
DROP PROCEDURE IF EXISTS GetComprobantePagoById;
DROP PROCEDURE IF EXISTS COMPROBANTE_PAGO_ACTUALIZAR;
DROP PROCEDURE IF EXISTS FACTURA_INSERTAR;
DROP PROCEDURE IF EXISTS FACTURA_ACTUALIZAR;
DROP PROCEDURE IF EXISTS FACTURA_OBTENER_POR_ID;
DROP PROCEDURE IF EXISTS ORDEN_VENTA_LISTAR_POR_USUARIO;

DELIMITER $
CREATE PROCEDURE PRODUCTO_INSERTAR(
    OUT _id_producto INT,
    IN _nombre VARCHAR(100),
    IN _descripcion VARCHAR(100),
    IN _stock INT,
    IN _precio DOUBLE,
    IN _imagen LONGBLOB,
    IN _activo TINYINT,
    IN _id_categoria INT
)
BEGIN
    INSERT INTO Producto(nombre, descripcion, stock, precio, Imagen, activo, idCategoria)
    VALUES (_nombre, _descripcion, _stock, _precio, _imagen, _activo, _id_categoria);
    SET _id_producto = LAST_INSERT_ID();
END $

CREATE PROCEDURE PRODUCTO_MODIFICAR(
    IN _id_producto INT,
    IN _nombre VARCHAR(100),
    IN _descripcion VARCHAR(100),
    IN _stock INT,
    IN _precio DOUBLE,
    IN _imagen LONGBLOB,
    IN _activo TINYINT,
    IN _id_categoria INT
)
BEGIN
    UPDATE Producto
    SET nombre = _nombre,
        descripcion = _descripcion,
        stock = _stock,
        precio = _precio,
        Imagen = _imagen,
        activo = _activo,
        idCategoria = _id_categoria
    WHERE idProducto = _id_producto;
END $

CREATE PROCEDURE PRODUCTO_ELIMINAR(
    IN _id_producto INT
)
BEGIN
    UPDATE Producto SET activo = 0 WHERE idProducto = _id_producto;
END $

CREATE PROCEDURE `PRODUCTO_OBTENER`(
    IN p_id_producto INT
)
BEGIN
    SELECT * FROM Producto
    WHERE idProducto = p_id_producto AND activo = 1;
END $

CREATE PROCEDURE PRODUCTO_LISTAR()
BEGIN
    SELECT * FROM Producto WHERE activo = 1;
END $

DELIMITER $$
CREATE PROCEDURE DESCUENTO_INSERTAR(
    OUT _id_descuento INT,
    IN _num_descuento INT,
    IN _activo TINYINT
)
BEGIN
    INSERT INTO Descuento( numDescuento, activo)
    VALUES ( _num_descuento, _activo);
    SET _id_descuento = LAST_INSERT_ID();
END $$
DELIMITER $
CREATE PROCEDURE DESCUENTO_MODIFICAR(
    IN _id_descuento INT,
    IN _num_descuento INT
)
BEGIN
    UPDATE Descuento
    SET numDescuento = _num_descuento
    WHERE idDescuento = _id_descuento;
END $
DELIMITER $
CREATE PROCEDURE DESCUENTO_ELIMINAR(
    IN _id_descuento INT
)
BEGIN
    UPDATE Descuento SET activo = 0 WHERE idDescuento = _id_descuento;
END $
DELIMITER $
CREATE PROCEDURE DESCUENTO_LISTAR()
BEGIN
    SELECT * FROM Descuento;
END $

DELIMITER $$
CREATE PROCEDURE CATEGORIA_INSERTAR(
    OUT _id_categoria INT,
    IN _descripcion VARCHAR(45),
    IN _nombre VARCHAR(45),
    IN _id_descuento INT
)
BEGIN
    INSERT INTO Categoria( descripcion, nombre, idDescuento)
    VALUES ( _descripcion, _nombre, _id_descuento);
    SET _id_categoria = LAST_INSERT_ID();
END $$
DELIMITER $
CREATE PROCEDURE CATEGORIA_MODIFICAR(
    IN _id_categoria INT,
    IN _descripcion VARCHAR(45),
    IN _nombre VARCHAR(45),
    IN _id_descuento INT
)
BEGIN
    UPDATE Categoria
    SET descripcion = _descripcion,
        nombre = _nombre,
        idDescuento = _id_descuento
    WHERE idCategoria = _id_categoria;
END $

DELIMITER $
CREATE PROCEDURE CATEGORIA_ELIMINAR(
    IN _id_categoria INT
)
BEGIN
    DELETE FROM Categoria WHERE idCategoria = _id_categoria;
END $

DELIMITER $
CREATE PROCEDURE CATEGORIA_LISTAR()
BEGIN
    SELECT * FROM Categoria;
END $

DELIMITER $
CREATE PROCEDURE USUARIO_INSERTAR(
    OUT _id_usuario INT,
    IN _nombre_usuario VARCHAR(100),
    IN _contrasena VARCHAR(100),
    IN _activo TINYINT,
    IN _correo VARCHAR(100),
    IN _tipo_usuario ENUM('EMPRESA', 'CLIENTE','ADMIN'),
    IN _dni VARCHAR(45),
    IN _razon_social VARCHAR(45),
    IN _direccion VARCHAR(45),
    IN _ruc VARCHAR(11),
	IN _saldo DOUBLE
)
BEGIN
    INSERT INTO Usuario(nombreUsuario, contrasena, activo, correo, tipoUsuario, dni, razonsocial, direccion, RUC, saldo)
    VALUES (_nombre_usuario, _contrasena, _activo, _correo, _tipo_usuario, _dni, _razon_social, _direccion, _ruc, _saldo);
    SET _id_usuario = LAST_INSERT_ID();
END $
DELIMITER $
CREATE PROCEDURE USUARIO_MODIFICAR(
    IN _id_usuario INT,
    IN _nombre_usuario VARCHAR(100),
    IN _contrasena VARCHAR(100),
    IN _activo TINYINT,
    IN _correo VARCHAR(100),
    IN _tipo_usuario ENUM('EMPRESA', 'CLIENTE','ADMIN'),
    IN _dni VARCHAR(45),
    IN _razon_social VARCHAR(45),
    IN _direccion VARCHAR(45),
    IN _ruc VARCHAR(11),
	IN _saldo DOUBLE
)
BEGIN
    UPDATE Usuario
    SET nombreUsuario = _nombre_usuario,
        contrasena = _contrasena,
        activo = _activo,
        correo = _correo,
        tipoUsuario = _tipo_usuario,
        dni = _dni,
        razonsocial = _razon_social,
        direccion = _direccion,
        RUC = _ruc,
		saldo = _saldo
    WHERE idUsuario = _id_usuario;
END $

CREATE PROCEDURE USUARIO_ELIMINAR(
    IN _id_usuario INT
)
BEGIN
    UPDATE Usuario SET activo = 0 WHERE idUsuario = _id_usuario;
END $
 
CREATE PROCEDURE USUARIO_LISTAR()
BEGIN
    SELECT * FROM Usuario WHERE activo = 1;
END $

CREATE PROCEDURE ORDENVENTA_INSERTAR(
    OUT _id_ordenventa INT,
    IN _estado_compra ENUM('pendiente', 'pagado', 'enviado', 'entregado'),
    IN _fecha_orden DATE,
    IN _activo TINYINT,
    IN _id_usuario INT
)
BEGIN
    INSERT INTO OrdenVenta(estado_compra, fecha_orden, activo, idUsuario)
    VALUES (_estado_compra, _fecha_orden, _activo, _id_usuario);
    SET _id_ordenventa = LAST_INSERT_ID();
END $

CREATE PROCEDURE ORDENVENTA_MODIFICAR(
    IN _id_ordenventa INT,
    IN _estado_compra ENUM('pendiente', 'pagado', 'enviado', 'entregado'),
    IN _fecha_orden DATE,
    IN _activo TINYINT,
    IN _id_usuario INT
)
BEGIN
    UPDATE OrdenVenta
    SET estado_compra = _estado_compra,
        fecha_orden = _fecha_orden,
        activo = _activo,
        idUsuario = _id_usuario
    WHERE idOrdenVenta = _id_ordenventa;
END $

CREATE PROCEDURE ORDENVENTA_ELIMINAR(
    IN _id_ordenventa INT
)
BEGIN
    UPDATE OrdenVenta SET activo = 0 WHERE idOrdenVenta = _id_ordenventa;
END $

CREATE PROCEDURE ORDENVENTA_LISTAR()
BEGIN
    SELECT * FROM OrdenVenta;
END $

CREATE PROCEDURE DETALLE_INSERTAR(
    IN _id_orden INT,
    IN _id_producto INT,
    IN _cantidad INT,
    IN _precio_unitario DOUBLE
)
BEGIN
    INSERT INTO Detalle(id_orden, id_producto, cantidad, precio_unitario)
    VALUES (_id_orden, _id_producto, _cantidad, _precio_unitario);
END $

CREATE PROCEDURE DETALLE_MODIFICAR(
    IN _id_orden INT,
    IN _id_producto INT,
    IN _cantidad INT,
    IN _precio_unitario DOUBLE
)
BEGIN
    UPDATE Detalle
    SET cantidad = _cantidad,
        precio_unitario = _precio_unitario
    WHERE id_orden = _id_orden AND id_producto = _id_producto;
END $

CREATE PROCEDURE DETALLE_ELIMINAR(
    IN _id_orden INT,
    IN _id_producto INT
)
BEGIN
    DELETE FROM Detalle WHERE id_orden = _id_orden AND id_producto = _id_producto;
END $

CREATE PROCEDURE DETALLE_LISTAR()
BEGIN
    SELECT * FROM Detalle;
END $

CREATE PROCEDURE ENTREGA_INSERTAR(
    OUT _id_entrega INT,
    IN _id_orden INT,
    IN _fecha_entrega DATETIME,
    IN _direccion VARCHAR(120),
    IN _dni INT,
    IN _tipo_entrega ENUM('DELIVERY', 'RECOJO')
)
BEGIN
    INSERT INTO Entrega(id_orden, fecha_entrega, direccion, dni, TipoEntrega)
    VALUES (_id_orden, _fecha_entrega, _direccion, _dni, _tipo_entrega);
    SET _id_entrega = LAST_INSERT_ID();
END $

CREATE PROCEDURE ENTREGA_MODIFICAR(
    IN _id_entrega INT,
    IN _id_orden INT,
    IN _fecha_entrega DATETIME,
    IN _direccion VARCHAR(120),
    IN _dni INT,
    IN _tipo_entrega ENUM('DELIVERY', 'RECOJO')
)
BEGIN
    UPDATE Entrega
    SET id_orden = _id_orden,
        fecha_entrega = _fecha_entrega,
        direccion = _direccion,
        dni = _dni,
        TipoEntrega = _tipo_entrega
    WHERE idEntrega = _id_entrega;
END $

CREATE PROCEDURE ENTREGA_ELIMINAR(
    IN _id_entrega INT
)
BEGIN
    DELETE FROM Entrega WHERE idEntrega = _id_entrega;
END $

CREATE PROCEDURE ENTREGA_LISTAR()
BEGIN
    SELECT * FROM Entrega;
END $

CREATE PROCEDURE PRODUCTOCOTIZACION_INSERTAR(
	OUT _id_productocotizacion INT,
    IN _descripcion VARCHAR(45),
    IN _cantidad INT,
    IN _precioCotizado DOUBLE,
    IN _idCotizacion INT
)
BEGIN
	INSERT INTO productoCotizado (descripcion,cantidad, precioCotizado,
    idCotizacion) VALUES ( _descripcion, _cantidad, 
    _precioCotizado, _idCotizacion );
    SET _id_productocotizacion = LAST_INSERT_ID();
END $

CREATE PROCEDURE PRODUCTOCOTIZACION_MODIFICAR(
	IN _descripcion VARCHAR(45),
    IN _cantidad INT,
    IN _precioCotizado DOUBLE,
    IN _idCotizacion INT,
    IN _id_productocotizacion INT
)
BEGIN
	UPDATE productoCotizado
    SET descripcion = _descripcion,
    cantidad = _cantidad,
    precioCotizado = _precioCotizado,
    idCotizacion = _idCotizacion
    WHERE idproductoCotizado = _id_productocotizacion;
END $

CREATE PROCEDURE PRODUCTOCOTIZACION_ELIMINAR(
	IN _id_productocotizacion INT
)
BEGIN
	DELETE FROM productoCotizado
    WHERE idproductoCotizado = _id_productocotizacion;
END $

CREATE PROCEDURE PRODUCTOCOTIZACION_LISTAR()
BEGIN
	SELECT * FROM productoCotizado;
END $

CREATE PROCEDURE PRODUCTOCOTIZACION_OBTENER(
	IN _id_productocotizacion INT
)
BEGIN
	SELECT * FROM productoCotizado 
    WHERE idproductoCotizado = _id_productocotizacion;
END $

DROP PROCEDURE IF EXISTS NOTIFICACION_INSERTAR
DELIMITER $$
CREATE PROCEDURE NOTIFICACION_INSERTAR(
    OUT _id_notificacion INT,
    IN _titulo VARCHAR(100),
    IN _mensaje VARCHAR(255),
    IN _fecha_envio DATETIME,
    IN _estado VARCHAR(45),
    IN _asunto VARCHAR(45)
)
BEGIN
    INSERT INTO Notificacion(tipo, mensaje, fecha_envio, estado, asunto)
    VALUES (_titulo, _mensaje, _fecha_envio, _estado, _asunto);
    SET _id_notificacion = LAST_INSERT_ID();
END $$

DROP PROCEDURE IF EXISTS NOTIFICACION_MODIFICAR

DELIMITER $$
CREATE PROCEDURE NOTIFICACION_MODIFICAR(
    IN _id_notificacion INT,
    IN _titulo VARCHAR(100),
    IN _mensaje VARCHAR(255),
    IN _fecha_envio DATETIME,
    IN _estado VARCHAR(45),
    IN _asunto VARCHAR(45)
)
BEGIN
    UPDATE Notificacion
    SET tipo= _titulo,
        mensaje = _mensaje,
        fecha_envio = _fecha_envio,
        estado = _estado,
        asunto = _asunto
    WHERE idNotificacion = _id_notificacion;
END $$

CREATE PROCEDURE NOTIFICACION_ELIMINAR(
    IN _id_notificacion INT
)
BEGIN
    DELETE FROM Notificacion WHERE idNotificacion = _id_notificacion;
END $

CREATE PROCEDURE NOTIFICACION_LISTAR()
BEGIN
    SELECT * FROM Notificacion;
END $


CREATE PROCEDURE BOLETA_ELIMINAR(
	IN _id_boleta INT
)
BEGIN
	DELETE FROM Boleta WHERE idBoleta = _id_boleta;
END $
DELIMITER $$
CREATE PROCEDURE BOLETA_LISTAR()
BEGIN
	SELECT * FROM Boleta;
END $$
DELIMITER $$



CREATE PROCEDURE BOLETA_INSERTAR (
    IN _id_boleta INT,
    IN _dni VARCHAR(15),
    IN _nombre VARCHAR(100),
    IN _fecha_emision DATE
)
BEGIN
    INSERT INTO Boleta (idBoleta, dni, nombre, fecha_emision)
    VALUES (_id_boleta, _dni, _nombre, _fecha_emision);
END$$

DELIMITER ;
DELIMITER $$

CREATE DEFINER=`admin`@`%` PROCEDURE `COMPROBANTE_PAGO_INSERTAR` (
    OUT p_id INT,
    IN p_id_orden INT,
    IN p_metodo_pago VARCHAR(50),
    IN p_fecha_pago DATE,
    IN p_monto_total DECIMAL(10,2)
)
BEGIN
    INSERT INTO ComprobantePago(id_orden, metodo_pago, fecha_pago, monto_total)
    VALUES (p_id_orden, p_metodo_pago, p_fecha_pago, p_monto_total);

    SET p_id = LAST_INSERT_ID();
END$$
DELIMITER $$

CREATE PROCEDURE BOLETA_OBTENER_POR_ID(IN p_idBoleta INT)
BEGIN
    SELECT * 
    FROM Boleta 
    INNER JOIN ComprobantePago 
        ON Boleta.idBoleta = ComprobantePago.idComprobantePago
    WHERE Boleta.idBoleta = p_idBoleta;
END$$

DELIMITER ;
DELIMITER $$

CREATE PROCEDURE BOLETA_ACTUALIZAR(
    IN p_id INT,
    IN p_dni VARCHAR(15),
    IN p_nombre VARCHAR(100),
    IN p_fecha_emision DATE,
    IN p_id_orden INT,
    IN p_metodo_pago VARCHAR(50),
    IN p_fecha_pago DATE,
    IN p_monto_total DECIMAL(10,2)
)
BEGIN
    -- Actualizar ComprobantePago (padre)
    UPDATE ComprobantePago
    SET id_orden = p_id_orden,
        metodo_pago = p_metodo_pago,
        fecha_pago = p_fecha_pago,
        monto_total = p_monto_total
    WHERE idComprobantePago = p_id;

    -- Actualizar Boleta (hija)
    UPDATE Boleta
    SET dni = p_dni,
        nombre = p_nombre,
        fecha_emision = p_fecha_emision
    WHERE idBoleta = p_id;
END$$

DELIMITER ;
DELIMITER $$

CREATE DEFINER=`admin`@`%` PROCEDURE `COMPROBANTE_OBTENER`(IN comprobanteId INT)
BEGIN
    SELECT * 
    FROM ComprobantePago 
    WHERE idComprobantePago = comprobanteId;
END $$

CREATE PROCEDURE GetComprobantePagoById(IN comprobanteId INT)
BEGIN
    SELECT * 
    FROM ComprobantePago 
    WHERE idComprobantePago = comprobanteId;
END $$
DELIMITER ;
DELIMITER $$

CREATE PROCEDURE COMPROBANTE_PAGO_ACTUALIZAR(
    IN p_id_orden INT,
    IN p_metodo_pago VARCHAR(50),
    IN p_fecha_pago DATETIME,
    IN p_monto_total DECIMAL(10,2),
    IN p_id_comprobante INT
)
BEGIN
    UPDATE ComprobantePago 
    SET 
        id_orden = p_id_orden,
        metodo_pago = p_metodo_pago,
        fecha_pago = p_fecha_pago,
        monto_total = p_monto_total
    WHERE idComprobantePago = p_id_comprobante;
END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE FACTURA_INSERTAR (
    IN p_idFactura INT,
    IN p_ruc VARCHAR(11),
    IN p_razon_social VARCHAR(100),
    IN p_direccion_fiscal VARCHAR(200),
    IN p_fecha_emision DATETIME
)
BEGIN
    INSERT INTO Factura (idFactura, RUC, razon_social, direccion_fiscal, fecha_emision)
    VALUES (p_idFactura, p_ruc, p_razon_social, p_direccion_fiscal, p_fecha_emision);
END $$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE FACTURA_ACTUALIZAR (
    IN p_idFactura INT,
    IN p_ruc VARCHAR(11),
    IN p_razon_social VARCHAR(100),
    IN p_direccion_fiscal VARCHAR(200),
    IN p_fecha_emision DATETIME,
    IN p_id_orden INT,
    IN p_metodo_pago VARCHAR(50),
    IN p_fecha_pago DATETIME,
    IN p_monto_total DECIMAL(10,2)
)
BEGIN
    -- Primero actualiza ComprobantePago (padre)
    UPDATE ComprobantePago
    SET id_orden = p_id_orden,
        metodo_pago = p_metodo_pago,
        fecha_pago = p_fecha_pago,
        monto_total = p_monto_total
    WHERE idComprobantePago = p_idFactura;

    -- Luego actualiza Factura (hija)
    UPDATE Factura
    SET RUC = p_ruc,
        razon_social = p_razon_social,
        direccion_fiscal = p_direccion_fiscal,
        fecha_emision = p_fecha_emision
    WHERE idFactura = p_idFactura;
END $$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE FACTURA_OBTENER_POR_ID (
    IN p_idFactura INT
)
BEGIN
    SELECT * 
    FROM Factura
    INNER JOIN ComprobantePago 
        ON Factura.idFactura = ComprobantePago.idComprobantePago
    WHERE Factura.idFactura = p_idFactura;
END $$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE ORDEN_VENTA_LISTAR_POR_USUARIO (
    IN p_idUsuario INT
)
BEGIN
    SELECT *
    FROM OrdenVenta
    WHERE idUsuario = p_idUsuario;
END$$

DELIMITER ;
DELIMITER $$
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_buscar_usuario_por_id`(
    IN p_idUsuario INT
)
BEGIN
    SELECT 
        idUsuario,
        nombreUsuario,
        contrasena,
        activo,
        correo,
        tipoUsuario,
        dni,
        razonsocial,
        direccion,
        RUC,
		saldo
    FROM Usuario
    WHERE idUsuario = p_idUsuario;
END $$

CREATE PROCEDURE DETALLE_LISTAR_POR_ORDEN (
    IN p_id_orden INT
)
BEGIN
    SELECT 
        id_orden,
        id_producto,
        cantidad,
        precio_unitario
    FROM Detalle
    WHERE id_orden = p_id_orden;
END $$

DELIMITER ;
DELIMITER ;