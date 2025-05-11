DROP PROCEDURE IF EXISTS PRODUCTO_INSERTAR 
DROP PROCEDURE IF EXISTS PRODUCTO_MODIFICAR
DROP PROCEDURE IF EXISTS PRODUCTO_ELIMINAR 
DROP PROCEDURE IF EXISTS PRODUCTO_LISTAR 
DROP PROCEDURE IF EXISTS DESCUENTO_INSERTAR 
DROP PROCEDURE IF EXISTS DESCUENTO_MODIFICAR 
DROP PROCEDURE IF EXISTS DESCUENTO_ELIMINAR 
DROP PROCEDURE IF EXISTS DESCUENTO_LISTAR
DROP PROCEDURE IF EXISTS CATEGORIA_INSERTAR
DROP PROCEDURE IF EXISTS CATEGORIA_MODIFICAR
DROP PROCEDURE IF EXISTS CATEGORIA_ELIMINAR
DROP PROCEDURE IF EXISTS CATEGORIA_LISTAR 
DROP PROCEDURE IF EXISTS USUARIO_INSERTAR
DROP PROCEDURE IF EXISTS USUARIO_MODIFICAR
DROP PROCEDURE IF EXISTS USUARIO_ELIMINAR 
DROP PROCEDURE IF EXISTS USUARIO_LISTAR
DROP PROCEDURE IF EXISTS ORDENVENTA_INSERTAR
DROP PROCEDURE IF EXISTS ORDENVENTA_MODIFICAR
DROP PROCEDURE IF EXISTS ORDENVENTA_ELIMINAR 
DROP PROCEDURE IF EXISTS ORDENVENTA_LISTAR
DROP PROCEDURE IF EXISTS DETALLE_INSERTAR
DROP PROCEDURE IF EXISTS DETALLE_MODIFICAR
DROP PROCEDURE IF EXISTS DETALLE_ELIMINAR 
DROP PROCEDURE IF EXISTS DETALLE_LISTAR
DROP PROCEDURE IF EXISTS ENTREGA_INSERTAR
DROP PROCEDURE IF EXISTS ENTREGA_MODIFICAR
DROP PROCEDURE IF EXISTS ENTREGA_ELIMINAR
DROP PROCEDURE IF EXISTS ENTREGA_LISTAR
DROP PROCEDURE IF EXISTS NOTIFICACION_INSERTAR
DROP PROCEDURE IF EXISTS NOTIFICACION_MODIFICAR
DROP PROCEDURE IF EXISTS NOTIFICACION_ELIMINAR 
DROP PROCEDURE IF EXISTS NOTIFICACION_LISTAR


DELIMITER $
CREATE PROCEDURE PRODUCTO_INSERTAR(
    OUT _id_producto INT,
    IN _nombre VARCHAR(100),
    IN _descripcion VARCHAR(100),
    IN _stock INT,
    IN _precio DOUBLE,
    IN _imagen VARCHAR(255),
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
    IN _imagen VARCHAR(255),
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

CREATE PROCEDURE PRODUCTO_LISTAR()
BEGIN
    SELECT * FROM Producto WHERE activo = 1;
END $

CREATE PROCEDURE DESCUENTO_INSERTAR(
    OUT _id_descuento INT,
    IN _num_descuento INT
)
BEGIN
    INSERT INTO Descuento(idDescuento, numDescuento)
    VALUES (_id_descuento, _num_descuento);
    SET _id_descuento = LAST_INSERT_ID();
END $

CREATE PROCEDURE DESCUENTO_MODIFICAR(
    IN _id_descuento INT,
    IN _num_descuento INT
)
BEGIN
    UPDATE Descuento
    SET numDescuento = _num_descuento
    WHERE idDescuento = _id_descuento;
END $

CREATE PROCEDURE DESCUENTO_ELIMINAR(
    IN _id_descuento INT
)
BEGIN
    DELETE FROM Descuento WHERE idDescuento = _id_descuento;
END $

CREATE PROCEDURE DESCUENTO_LISTAR()
BEGIN
    SELECT * FROM Descuento;
END $

CREATE PROCEDURE CATEGORIA_INSERTAR(
    OUT _id_categoria INT,
    IN _descripcion VARCHAR(45),
    IN _nombre VARCHAR(45),
    IN _id_descuento INT
)
BEGIN
    INSERT INTO Categoria(idCategoria, descripcion, nombre, idDescuento)
    VALUES (_id_categoria, _descripcion, _nombre, _id_descuento);
    SET _id_categoria = LAST_INSERT_ID();
END $

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

CREATE PROCEDURE CATEGORIA_ELIMINAR(
    IN _id_categoria INT
)
BEGIN
    DELETE FROM Categoria WHERE idCategoria = _id_categoria;
END $

CREATE PROCEDURE CATEGORIA_LISTAR()
BEGIN
    SELECT * FROM Categoria;
END $

CREATE PROCEDURE USUARIO_INSERTAR(
    OUT _id_usuario INT,
    IN _nombre_usuario VARCHAR(45),
    IN _contrasena VARCHAR(45),
    IN _activo INT,
    IN _correo VARCHAR(45),
    IN _tipo_usuario ENUM('EMPRESA', 'CLIENTE'),
    IN _razon_social VARCHAR(45),
    IN _direccion VARCHAR(45),
    IN _ruc VARCHAR(45)
)
BEGIN
    INSERT INTO Usuario(nombreUsuario, contrasena, activo, correo, tipoUsuario, razonsocial, direccion, RUC)
    VALUES (_nombre_usuario, _contrasena, _activo, _correo, _tipo_usuario, _razon_social, _direccion, _ruc);
    SET _id_usuario = LAST_INSERT_ID();
END $

CREATE PROCEDURE USUARIO_MODIFICAR(
    IN _id_usuario INT,
    IN _nombre_usuario VARCHAR(45),
    IN _contrasena VARCHAR(45),
    IN _activo INT,
    IN _correo VARCHAR(45),
    IN _tipo_usuario ENUM('EMPRESA', 'CLIENTE'),
    IN _razon_social VARCHAR(45),
    IN _direccion VARCHAR(45),
    IN _ruc VARCHAR(45)
)
BEGIN
    UPDATE Usuario
    SET nombreUsuario = _nombre_usuario,
        contrasena = _contrasena,
        activo = _activo,
        correo = _correo,
        tipoUsuario = _tipo_usuario,
        razonsocial = _razon_social,
        direccion = _direccion,
        RUC = _ruc
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
    IN _id_usuario INT
)
BEGIN
    INSERT INTO OrdenVenta(estado_compra, fecha_orden, idUsuario)
    VALUES (_estado_compra, _fecha_orden, _id_usuario);
    SET _id_ordenventa = LAST_INSERT_ID();
END $

CREATE PROCEDURE ORDENVENTA_MODIFICAR(
    IN _id_ordenventa INT,
    IN _estado_compra ENUM('pendiente', 'pagado', 'enviado', 'entregado'),
    IN _fecha_orden DATE,
    IN _id_usuario INT
)
BEGIN
    UPDATE OrdenVenta
    SET estado_compra = _estado_compra,
        fecha_orden = _fecha_orden,
        idUsuario = _id_usuario
    WHERE idOrdenVenta = _id_ordenventa;
END $

CREATE PROCEDURE ORDENVENTA_ELIMINAR(
    IN _id_ordenventa INT
)
BEGIN
    DELETE FROM OrdenVenta WHERE idOrdenVenta = _id_ordenventa;
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

CREATE PROCEDURE NOTIFICACION_INSERTAR(
    OUT _id_notificacion INT,
    IN _titulo VARCHAR(100),
    IN _mensaje VARCHAR(255),
    IN _fecha_hora DATETIME,
    IN _estado ENUM('ENVIADO', 'RECIBIDO'),
    IN _id_usuario INT
)
BEGIN
    INSERT INTO Notificacion(titulo, mensaje, fecha_hora, estado, idUsuario)
    VALUES (_titulo, _mensaje, _fecha_hora, _estado, _id_usuario);
    SET _id_notificacion = LAST_INSERT_ID();
END $

CREATE PROCEDURE NOTIFICACION_MODIFICAR(
    IN _id_notificacion INT,
    IN _titulo VARCHAR(100),
    IN _mensaje VARCHAR(255),
    IN _fecha_hora DATETIME,
    IN _estado ENUM('ENVIADO', 'RECIBIDO'),
    IN _id_usuario INT
)
BEGIN
    UPDATE Notificacion
    SET titulo = _titulo,
        mensaje = _mensaje,
        fecha_hora = _fecha_hora,
        estado = _estado,
        idUsuario = _id_usuario
    WHERE idNotificacion = _id_notificacion;
END $

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