INSERT INTO campis.product VALUES (752, 'Paquete de clavos', 'Caja de clavos simple.', 1, 1, 10.00, 'Ferrex', 80.00, 1, 3, 1000, 0);
INSERT INTO campis.product VALUES (111, 'Bolsa de grava', 'producto de prueba 2', 1, 1, 20.00, 'test2', 5000.00, 1, 1, 2000, 500);
INSERT INTO campis.product VALUES (739, 'Barril de aceite', 'Tierra de construccion', 11, 11, 3.00, 'Humus', 12.00, 2, 2, 4000, 0);
INSERT INTO campis.product VALUES (112, 'Bolsa de arena', 'producto de prueba 3', 1, 1, 30.00, 'test3', 20.00, 1, 1, 2000, 500);
INSERT INTO campis.product VALUES (144, 'Paquete de martillos', 'Martillos especiales para tumbar paredes.', 1, 1, 45.00, 'Comba Dura', 195100.00, 1, 2, 300, 0);
INSERT INTO campis.product VALUES (110, 'Bolsa de piedras', 'producto de prueba', 1, 1, 5.00, 'test1', 100.00, 1, 1, 20, 500);
INSERT INTO campis.supplier VALUES (1, 'Nombre', '12345678', 'Av Is there anybody in there?', 'hello@gmail.com', '987654321');
INSERT INTO campis.supplier VALUES (2, 'Cementos SOL', '18536793258', 'Av. Los Jazmines #185', 'cementossol@gmail.com', '666-9854');
INSERT INTO campis.supplier VALUES (0, 'Hallazgo', '0', 'xxx', 'xxx', '0');
INSERT INTO campis.supplier VALUES (3, 'Devolución', '0', 'xxx', 'xxx', '0');
INSERT INTO campis.batch VALUES (4190, 1, 0, '2017-11-25 13:39:34.143', '2017-11-30 00:00:00', 111, 1, true, NULL, '--', NULL, 0);
INSERT INTO campis.batch VALUES (4201, 50, 0, '2017-11-25 14:25:47.208', '2018-02-21 00:00:00', 739, 3, true, NULL, '--', NULL, 0);
INSERT INTO campis.batch VALUES (4203, 50, 0, '2017-11-25 14:25:47.208', '2018-02-21 00:00:00', 739, 3, true, NULL, '--', NULL, 0);
INSERT INTO campis.batch VALUES (4205, 50, 0, '2017-11-25 14:25:47.208', '2018-02-21 00:00:00', 739, 3, true, NULL, '--', NULL, 0);
INSERT INTO campis.batch VALUES (4207, 50, 0, '2017-11-25 14:29:20.62', '2018-08-18 00:00:00', 111, 3, true, NULL, '--', NULL, 0);
INSERT INTO campis.batch VALUES (4209, 40, 0, '2017-11-25 14:30:38.347', '2019-05-18 00:00:00', 112, 3, true, NULL, '--', NULL, 0);
INSERT INTO campis.batch VALUES (4211, 50, 0, '2017-11-25 14:31:37.998', '2019-06-29 00:00:00', 752, 3, true, NULL, '--', NULL, 0);
INSERT INTO campis.batch VALUES (4213, 50, 0, '2017-11-25 14:31:37.998', '2019-06-29 00:00:00', 752, 3, true, NULL, '--', NULL, 0);
INSERT INTO campis.batch VALUES (4215, 50, 0, '2017-11-25 14:31:37.998', '2019-06-29 00:00:00', 752, 3, true, NULL, '--', NULL, 0);
INSERT INTO campis.batch VALUES (4217, 12, 0, '2017-11-25 14:33:07.469', '2018-05-19 00:00:00', 144, 3, true, NULL, '--', NULL, 0);
INSERT INTO campis.batch VALUES (4219, 18, 0, '2017-11-25 14:34:33.431', '2018-03-23 00:00:00', 110, 3, true, NULL, '--', NULL, 0);
INSERT INTO campis.batch VALUES (4221, 18, 0, '2017-11-25 14:34:33.431', '2018-03-23 00:00:00', 110, 3, true, NULL, '--', NULL, 0);
INSERT INTO campis.batch VALUES (4223, 18, 0, '2017-11-25 14:34:33.431', '2018-03-23 00:00:00', 110, 3, true, NULL, '--', NULL, 0);
INSERT INTO campis.client VALUES (1, 'Benito Juarez', '48296679', '12354687952', true, 'Los naranjos 123', '555-3462', 'benito07@gmail.com', NULL);
INSERT INTO campis.client VALUES (284, 'dfasdfasdf', '--', '12341234', false, 'asdfasdfasdf', '1241234', 'asdfasdf', NULL);
INSERT INTO campis.client VALUES (285, 'pepito', '123123', '--', false, 'fsdfasdf', '12341234', 'sdfsdfsdf', NULL);
INSERT INTO campis.client VALUES (330, 'wertwert', '--', 'wertwert', false, 'sdfgsdfg', '123', 'dfgsdfg', NULL);
INSERT INTO campis.client VALUES (329, 'pepito', '123123', '--', false, 'qwerty', '45', 'qwerty', NULL);
INSERT INTO campis.client VALUES (331, 'serg', 'sdfgsdf', '--', false, 'fgsdfg', '345345', 'sdfgsdfg', NULL);
INSERT INTO campis.client VALUES (57, 'Pablo Castro', '12321', '1232122', false, '2121123', '333', 'test@test.com', NULL);
INSERT INTO campis.request_order VALUES (4192, '2017-11-25 13:40:47', '2017-11-30 00:00:00', 500.00, 727.21, 'EN PROGRESO', 1, 2, 25, NULL, 0.00, 111.10, 116.11);
INSERT INTO campis.delivery VALUES (46, 'La pen', '163-978', 4194);
INSERT INTO campis.dispatch_order VALUES (4194, 4192, 2, 'POR DESPACHAR', NULL, NULL);
INSERT INTO campis.dispatch_order_line VALUES (4195, 4194, 111, 1, false);
INSERT INTO campis.invoice VALUES (24, 4194, 0, 111.1, 95.0, 706.1);
INSERT INTO campis.invoice_line VALUES (38, 24, 111, 1, 500.0, 0.0, 1, 500.0);
INSERT INTO campis.movement VALUES (4191, '2017-11-25 13:39:51.59', 21, 1, 9, 0, 1, 4032, 4190);
INSERT INTO campis.movement VALUES (4196, '2017-11-25 13:43:39.299', 21, 1, 9, 3, 1, 4032, 4190);
INSERT INTO campis.movement VALUES (4202, '2017-11-25 14:26:29.597', 21, 50, 32, 0, 1, 4052, 4201);
INSERT INTO campis.movement VALUES (4204, '2017-11-25 14:26:29.597', 21, 50, 32, 0, 1, 4051, 4203);
INSERT INTO campis.movement VALUES (4206, '2017-11-25 14:26:29.597', 21, 50, 32, 0, 1, 4053, 4205);
INSERT INTO campis.movement VALUES (4208, '2017-11-25 14:29:36.761', 21, 50, 33, 0, 1, 4031, 4207);
INSERT INTO campis.movement VALUES (4210, '2017-11-25 14:30:49.033', 21, 40, 33, 0, 1, 4028, 4209);
INSERT INTO campis.movement VALUES (4212, '2017-11-25 14:31:55.008', 21, 50, 33, 0, 1, 4125, 4211);
INSERT INTO campis.movement VALUES (4214, '2017-11-25 14:31:55.008', 21, 50, 33, 0, 1, 4124, 4213);
INSERT INTO campis.movement VALUES (4216, '2017-11-25 14:31:55.008', 21, 50, 33, 0, 1, 4126, 4215);
INSERT INTO campis.movement VALUES (4218, '2017-11-25 14:33:14.898', 21, 12, 33, 0, 1, 4049, 4217);
INSERT INTO campis.movement VALUES (4220, '2017-11-25 14:34:56.573', 21, 18, 33, 0, 1, 4029, 4219);
INSERT INTO campis.movement VALUES (4222, '2017-11-25 14:34:56.573', 21, 18, 33, 0, 1, 4030, 4221);
INSERT INTO campis.movement VALUES (4224, '2017-11-25 14:34:56.573', 21, 18, 33, 0, 1, 4032, 4223);
INSERT INTO campis.refund VALUES (4197, 24, 'Por Ingresar', '2017-11-25 13:44:30.714235', NULL, NULL);
INSERT INTO campis.refund_line VALUES (4198, 4197, 1, 111);