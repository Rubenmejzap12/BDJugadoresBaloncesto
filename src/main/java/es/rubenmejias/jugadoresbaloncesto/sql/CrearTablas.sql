CREATE TABLE EQUIPO (
    ID_EQUIP INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    CODIGO CHAR(4),
    NOMBRE VARCHAR(20) NOT NULL,
    CONSTRAINT ID_EQUIPO_PK PRIMARY KEY (ID_EQUIP)
);

CREATE TABLE JUGADOR (
    ID_JUGAD INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY, -- Id autonumérico
    NOMBRE VARCHAR(20) NOT NULL,
    APELLIDOS VARCHAR(40) NOT NULL,
    TELEFONO VARCHAR(15),
    EMAIL VARCHAR(30),
    EQUIPO INTEGER,
    FECHA_NACIMIENTO DATE,
    NUM_HIJOS SMALLINT,
    POSICION CHAR(1),
    SALARIO DECIMAL(7,2),
    ACTIVO BOOLEAN,
    FOTO VARCHAR(30),
    CONSTRAINT ID_JUGADOR_PK PRIMARY KEY (ID_JUGAD),
    CONSTRAINT EQUIP_JUGADOR_FK FOREIGN KEY (EQUIPO) REFERENCES EQUIPO (ID_EQUIP)
);