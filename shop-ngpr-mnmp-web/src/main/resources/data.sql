-- data.sql


CREATE TABLE IF NOT EXISTS categorias (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(2000),
    image VARCHAR(500)
    );

INSERT INTO categorias (name, description, image) VALUES
                                                      ('Flores Naturales', 'Flores frescas de temporada', 'flornatural.png'),    -- ID 1
                                                      ('Flores Temporada', 'Flores de temporada', NULL),                         -- ID 2
                                                      ('Ramos de flores', 'Reune flores en un solo ramo', 'ramoflores.png'),      -- ID 3
                                                      ('Arreglo floral', 'Para eventos, centros de mesa, etc', 'arreglofloral.jpg'), -- ID 4
                                                      ('Flores Artificiales', 'Crochet, plastico...', 'florartificial.png'),      -- ID 5
                                                      ('Semillas', 'Planta tu propia flor', 'semillas.png'),                      -- ID 6
                                                      ('Categoría Vacía', 'Sin productos para el ejercicio', NULL);               -- ID 7

-- marcas para la relacion
CREATE TABLE IF NOT EXISTS marcas (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      name VARCHAR(100) NOT NULL UNIQUE
    );

INSERT INTO marcas (name) VALUES
                              ('FloraMaster'),
                              ('BioSemillas'),
                              ('DecoArt'),
                              ('Marca Sin Uso');

CREATE TABLE IF NOT EXISTS product (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       product_code VARCHAR(13) NOT NULL,
    name VARCHAR(200) NOT NULL,
    description VARCHAR(4000) NOT NULL,
    image VARCHAR(500),
    price DOUBLE NOT NULL,
    discount INT NOT NULL,
    brand_id BIGINT NOT NULL,
    FOREIGN KEY (brand_id) REFERENCES marcas(id)
    );

--20 productos:
INSERT INTO product (product_code, name, description, image, price, discount, brand_id) VALUES
                                                                                            ('8412345678905', 'Rosa Individual Roja', 'Una rosa roja de tallo largo...', 'rosa-roja.jpg', 4.50, 0, 1),
                                                                                            ('7613030500123', 'Pack 10 Tulipanes Variados', 'Colorido conjunto de diez tulipanes...', null, 15.00, 10, 1),
                                                                                            ('4006381333931', 'Lirio Blanco de Calidad', 'Exquisito lirio blanco...', 'lirio.jpg', 6.20, 0, 1),
                                                                                            ('5012345678900', 'Crisantemo de Otoño', 'Planta de crisantemo en maceta...', null, 8.50, 0, 1),
                                                                                            ('3012345678901', 'Pensamiento Multicolor Verano', 'Pequeñas pero resistentes flores...', 'pensamiento.jpg', 2.95, 0, 1),
                                                                                            ('8423456789012', 'Girasol de Agosto', 'Girasol de corte de gran tamaño...', null, 3.80, 5, 1),
                                                                                            ('2012345678902', 'Ramo Silvestre de Campo', 'Una composición desenfadada...', 'ramo-silvestre.jpg', 25.00, 15, 1),
                                                                                            ('1012345678903', 'Ramo de Novia Preservado', 'Elegante ramo diseñado con flores preservadas...', 'ramo-novia.jpg', 65.00, 0, 3),
                                                                                            ('9012345678904', 'Ramo Explosión de Color', 'Impresionante mezcla de flores variadas...', null, 32.50, 20, 1),
                                                                                            ('8012345678905', 'Centro de Mesa Gala', 'Sofisticado arreglo bajo...', 'centro-gala.jpg', 48.00, 0, 3),
                                                                                            ('7012345678906', 'Cesta de Flores Amistad', 'Cesta de mimbre artesanal...', null, 35.90, 10, 3),
                                                                                            ('6012345678907', 'Corona de Flores Memorial', 'Arreglo circular de gran tamaño...', 'corona.jpg', 85.00, 0, 3),
                                                                                            ('5012345678908', 'Orquídea de Seda Realista', 'Réplica de orquídea fabricada en seda...', null, 42.00, 0, 3),
                                                                                            ('4012345678909', 'Ramo de Flores de Crochet', 'Artesanía pura hecha a mano...', 'crochet.jpg', 28.00, 0, 3),
                                                                                            ('3012345678900', 'Panel de Pared Floral Artificial', 'Panel decorativo de 50x50 cm...', null, 19.99, 5, 3),
                                                                                            ('2012345678901', 'Cactus de Plástico Decorativo', 'Set de tres pequeños cactus...', 'cactus-fake.jpg', 12.50, 0, 3),
                                                                                            ('1012345678902', 'Semillas de Lavanda Profumata', 'Sobre con 500 semillas...', null, 3.50, 0, 2),
                                                                                            ('9012345678903', 'Kit Huerto Urbano Tomates', 'Completo kit que incluye semillas...', 'kit-tomate.jpg', 18.75, 15, 2),
                                                                                            ('8012345678904', 'Semillas de Flores Silvestres', 'Mezcla de semillas de flores...', null, 5.90, 0, 2),
                                                                                            ('7012345678905', 'Semillas de Bonsai Arce Rojo', 'Paquete de semillas seleccionadas...', 'semillas-arce.jpg', 7.40, 0, 2),
                                                                                            ('0000000000000', 'Flor Misteriosa', 'Producto sin categoría.', null, 0.00, 0, 1);

-- TABLA INTERMEDIARIA (MUCHOS A MUCHOS)
CREATE TABLE IF NOT EXISTS product_categories (
                                                  product_id BIGINT NOT NULL,
                                                  category_id BIGINT NOT NULL,
                                                  PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (category_id) REFERENCES categorias(id)
    );

INSERT INTO product_categories (product_id, category_id) VALUES
                                                             (1,1), (3,1), (7,1),
                                                             (2,1), (2,2),
                                                             (4,2), (5,2), (6,2),
                                                             (7,3), (9,3),
                                                             (8,3), (8,5),
                                                             (14,3), (14,5),
                                                             (10,4), (11,4), (12,4), (7,4),
                                                             (13,5), (15,5), (16,5),
                                                             (17,6), (18,6), (19,6), (20,6);

CREATE TABLE IF NOT EXISTS usuarios (
                                        user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    birth_date DATE,
    phone_number VARCHAR(20),
    date_time_registration TIMESTAMP NOT NULL
    );

DELETE FROM usuarios WHERE email = 'admin@tienda.com';

-- primer usuario
INSERT INTO usuarios (user_id, full_name, email, password, date_time_registration)
VALUES (1, 'Administrador', 'admin@tienda.com', '$2a$12$Vw7nVFvr0T3NaDK6i37m8.xNBaqWZHVggcrQHynHcfVeG5eTOpHi.', CURRENT_TIMESTAMP());
--ROLES
CREATE TABLE IF NOT EXISTS roles (
                                     id VARCHAR(20) PRIMARY KEY,  -- Cambiado de 6 a 20
    descripcion VARCHAR(100) NOT NULL
    );

INSERT INTO roles (id, descripcion) VALUES ('ADMIN', 'Administrador');
INSERT INTO roles (id, descripcion) VALUES ('USER', 'Usuario normal');

--user: usuario tienda
INSERT INTO usuarios (user_id, full_name, email, password, date_time_registration)
VALUES (2, 'Usuario Normal', 'user@tienda.com', '$2a$12$GAMT3qMxPXMtHmnl5.Apx.kfec3NOqA1kEPYIgHSbpmVIcpO0UJBG', CURRENT_TIMESTAMP());
--ahora el usuario admin va a tener dos roles, y el usuario solo 1 que es user
CREATE TABLE IF NOT EXISTS usuarios_roles (
                                              usuario_id BIGINT NOT NULL,
                                              rol_id VARCHAR(20) NOT NULL,  -- Cambia de 6 a 20
    PRIMARY KEY (usuario_id, rol_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(user_id),
    FOREIGN KEY (rol_id) REFERENCES roles(id)
    );

INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (1, 'ADMIN');
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (1, 'USER');

INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (2, 'USER');

--registro de eventos
CREATE TABLE IF NOT EXISTS security_events (
                                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                               username VARCHAR(255) NOT NULL,
    fecha_hora TIMESTAMP NOT NULL,
    event_type VARCHAR(50) NOT NULL
    );

-- Esto le dice a la tabla que el siguiente ID debe ser el 3
ALTER TABLE usuarios ALTER COLUMN user_id RESTART WITH 3;