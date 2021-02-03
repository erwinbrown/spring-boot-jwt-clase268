/* Populate tables */
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('Nito', 'Cortizo', 'presidente@gmail.com', '2018-07-18', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('Antoniio', 'Noriega', 'general@gmail.com', '2020-05-22', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('Andres', 'Guzman', 'profesor@bolsadeideas.com', '2019-08-01', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('John', 'Doe', 'john.doe@gmail.com', '2017-08-02', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2019-08-03', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('Jane', 'Doe', 'jane.doe@gmail.com', '2017-08-04', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2017-08-05', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('Erich', 'Gamma', 'erich.gamma@gmail.com', '2018-08-06', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('Richard', 'Helm', 'richard.helm@gmail.com', '2017-08-07', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2018-08-08', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('John', 'Vlissides', 'john.vlissides@gmail.com', '2019-08-09', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('James', 'Gosling', 'james.gosling@gmail.com', '2017-08-010', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('Bruce', 'Lee', 'bruce.lee@gmail.com', '2018-08-11', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('Johnny', 'Doe', 'johnny.doe@gmail.com', '2019-08-12', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('John', 'Roe', 'john.roe@gmail.com', '2017-08-13', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('Jane', 'Roe', 'jane.roe@gmail.com', '2018-08-14', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('Richard', 'Doe', 'richard.doe@gmail.com', '2017-08-15', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('Janie', 'Doe', 'janie.doe@gmail.com', '2018-08-16', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('Phillip', 'Webb', 'phillip.webb@gmail.com', '2017-08-17', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('Stephane', 'Nicoll', 'stephane.nicoll@gmail.com', '2019-08-18', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('Sam', 'Brannen', 'sam.brannen@gmail.com', '2017-08-19', ''); 
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('Juergen', 'Hoeller', 'juergen.Hoeller@gmail.com', '2017-08-20', ''); 
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('Janie', 'Roe', 'janie.roe@gmail.com', '2017-08-21', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('John', 'Smith', 'john.smith@gmail.com', '2018-08-22', '');
INSERT INTO clientes (nombre, apellido, email, fecha_creacion, foto) VALUES('Joe', 'Bloggs', 'joe.bloggs@gmail.com', '2017-08-23', '');


/* Populate tabla productos */
INSERT INTO productos (nombre, precio, create_at) VALUES('Panasonic Pantalla LCD', 158.85, current_date());
INSERT INTO productos (nombre, precio, create_at) VALUES('Sony Camara digital DSC-W320B', 240.90, current_date());
INSERT INTO productos (nombre, precio, create_at) VALUES('Apple iPod shuffle', 325.75, current_date());
INSERT INTO productos (nombre, precio, create_at) VALUES('Sony Notebook Z110', 379.45, current_date());
INSERT INTO productos (nombre, precio, create_at) VALUES('Hewlett Packard Multifuncional F2280', 69.95, current_date());
INSERT INTO productos (nombre, precio, create_at) VALUES('Bianchi Bicicleta Aro 26', 99.95, current_date());
INSERT INTO productos (nombre, precio, create_at) VALUES('Mica Comoda 5 Cajones', 129.90, current_date());


INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura a Nombre del Cliente 1', 'Nota referente al cliente 1', 1, current_date());
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(2, 1, 4);
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(1, 1, 5);
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(1, 1, 7);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura a Nombre Cliente 1', 'Alguna nota referente al cliente 1 ', 1, current_date());
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(3, 2, 6);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura a Nombre Cliente 2', 'Alguna nota referente al cliente 2', 2, current_date());
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(1, 3, 2);
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(2, 3, 3);
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(2, 3, 5);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura a Nombre del cliente 3', 'Alguna nota referente al cliente 3', 3, current_date());
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(3, 4, 4);
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(2, 4, 2);

