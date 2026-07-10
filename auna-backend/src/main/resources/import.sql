-- ==== Usuarios ====
INSERT INTO usuarios (id_usuario, numero_celular, num_documento, apellido, correo_electronico, nombre, contrasena, tipo_documento, tipo_usuario) VALUES (1,'999999999','77777777','Admin','admin@correo.com','Admin','$2a$10$XWcKMwUDe3mLwkRIH6YnWO2mgUQPSFZodzxO5zIxtZv7EJbsIni9m','DNI','ADMINISTRADOR');
INSERT INTO usuarios (id_usuario, numero_celular, num_documento, apellido, correo_electronico, nombre, contrasena, tipo_documento, tipo_usuario) VALUES (2,'888888888','66666666','Paciente','paciente@correo.com','Paciente','$2a$10$hb9sjqd4uiD9TP7u7U37c.PCMHKfRu.LPsX2wcfUBBvxxJJuukbE2','DNI','PACIENTE');

-- ==== Administradores ====
INSERT INTO administradores (id_administrador, id_usuario) VALUES (1,1);

-- ==== Pacientes ====
INSERT INTO pacientes (id_paciente, id_usuario) VALUES (1,2);

-- ==== Especialidades ====
INSERT INTO especialidades (id_especialidad, descripcion, nombre_especialidad) VALUES (1,'Especialidad médica enfocada en el corazón y sistema circulatorio.','Cardiología'),(2,'Especialidad dedicada al diagnóstico y tratamiento de enfermedades de la piel.','Dermatología'),(3,'Se encarga del sistema nervioso y sus trastornos.','Neurología'),(4,'Especialidad médica centrada en la salud de los niños.','Pediatría'),(5,'Se enfoca en el sistema reproductor femenino.','Ginecología'),(6,'Especialidad que trata las condiciones del sistema musculoesquelético.','Ortopedia'),(7,'Diagnóstico y tratamiento de enfermedades oculares.','Oftalmología'),(8,'Se ocupa de los trastornos mentales y emocionales.','Psiquiatría'),(9,'Diagnóstico y tratamiento del cáncer.','Oncología'),(10,'Estudio de las hormonas y las glándulas endocrinas.','Endocrinología');

-- ==== Sedes ====
INSERT INTO sedes (id_sedes, nombre_distrito, nombre_sede) VALUES (1,'Miraflores','Clínica Delgado Auna'),(2,'San Isidro','Clínica Auna Guardia Civil'),(3,'Arequipa','Clínica Auna Vallesur'),(4,'Piura','Auna Piura'),(5,'Callao','Auna Bellavista'),(6,'Chiclayo','Auna Chiclayo'),(7,'Trujillo','Auna Trujillo');

-- ==== Detalle Sede ====
INSERT INTO detalle_sede (id_detalle_sede, id_especialidad, id_sedes) VALUES (1,1,1),(2,2,1),(3,3,1),(4,4,1),(5,5,1),(6,6,1),(7,4,2),(8,5,2),(9,1,2),(10,2,2),(11,10,2),(12,6,3),(13,7,3),(14,1,4),(15,8,4),(16,9,5),(17,2,5),(18,3,6),(19,10,6),(20,4,7),(21,5,7);

-- ==== Médicos ====
INSERT INTO medicos (id_especialidad, id_medico, nombre_medico) VALUES (1,1,'Dr. Juan Pérez'),(2,2,'Dra. María Torres'),(3,3,'Dr. Luis Gutiérrez'),(4,4,'Dra. Ana Morales'),(5,5,'Dr. Pedro Rivas'),(6,6,'Dra. Carla Jiménez'),(7,7,'Dr. Esteban Soto'),(8,8,'Dra. Verónica Salas'),(9,9,'Dr. Jorge Mendoza'),(10,10,'Dra. Isabel Chávez'),(1,11,'Dr. Ricardo Paredes'),(2,12,'Dra. Natalia León'),(3,13,'Dr. Hugo Fernández'),(4,14,'Dra. Sofía Campos'),(5,15,'Dr. Fernando Díaz'),(6,16,'Dra. Daniela Rojas'),(7,17,'Dr. Andrés Navarro'),(8,18,'Dra. Teresa Blanco'),(9,19,'Dr. Víctor Quispe'),(10,20,'Dra. Lorena Palacios');

-- ==== Jornada Médicos ====
INSERT INTO jornada_medicos (id_jornada_medicos, id_medico, dia_semana) VALUES (1,1,'LUNES'),(2,1,'MARTES'),(3,1,'MIERCOLES'),(4,1,'JUEVES'),(5,1,'VIERNES'),(6,2,'MARTES'),(7,2,'JUEVES'),(8,3,'LUNES'),(9,3,'MIERCOLES'),(10,3,'VIERNES'),(11,4,'MARTES'),(12,4,'MIERCOLES'),(13,4,'JUEVES'),(14,4,'VIERNES'),(15,4,'SABADO'),(16,5,'LUNES'),(17,5,'MARTES'),(18,5,'MIERCOLES'),(19,5,'JUEVES'),(20,6,'MIERCOLES'),(21,6,'JUEVES'),(22,6,'VIERNES'),(23,6,'SABADO'),(24,6,'DOMINGO'),(25,7,'LUNES'),(26,7,'MARTES'),(27,7,'MIERCOLES'),(28,7,'JUEVES'),(29,7,'VIERNES'),(30,8,'MARTES'),(31,8,'VIERNES'),(32,9,'LUNES'),(33,9,'MIERCOLES'),(34,9,'SABADO'),(35,10,'JUEVES'),(36,10,'DOMINGO'),(37,11,'LUNES'),(38,11,'MARTES'),(39,11,'MIERCOLES'),(40,11,'JUEVES'),(41,11,'VIERNES'),(42,12,'MARTES'),(43,12,'JUEVES'),(44,13,'LUNES'),(45,13,'MIERCOLES'),(46,13,'VIERNES'),(47,14,'MARTES'),(48,14,'MIERCOLES'),(49,14,'JUEVES'),(50,14,'VIERNES'),(51,14,'SABADO'),(52,15,'LUNES'),(53,15,'MARTES'),(54,15,'MIERCOLES'),(55,15,'JUEVES'),(56,16,'MIERCOLES'),(57,16,'JUEVES'),(58,16,'VIERNES'),(59,16,'SABADO'),(60,16,'DOMINGO'),(61,17,'LUNES'),(62,17,'MARTES'),(63,17,'MIERCOLES'),(64,17,'JUEVES'),(65,17,'VIERNES'),(66,18,'MARTES'),(67,18,'VIERNES'),(68,19,'LUNES'),(69,19,'MIERCOLES'),(70,19,'SABADO'),(71,20,'JUEVES'),(72,20,'DOMINGO');
