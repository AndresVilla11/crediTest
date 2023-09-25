INSERT INTO product_type(code, type) VALUES('101010', 'Tarjeta de debito');
INSERT INTO product_type(code, type) VALUES('101020', 'Tarjeta de credito');
INSERT INTO product_type(code, type) VALUES('101030', 'Credito libre');

INSERT INTO status_transaction(status) VALUES('aprobado');
INSERT INTO status_transaction(status) VALUES('negado');
INSERT INTO status_transaction(status) VALUES('anulada');

INSERT INTO status(status) VALUES('inactivo');
INSERT INTO status(status) VALUES('activo');
INSERT INTO status(status) VALUES('bloqueado');