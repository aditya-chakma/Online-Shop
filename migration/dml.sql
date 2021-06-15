-- user
INSERT INTO user(name, email, mobile_no, role, password, address, image_link) VALUES('aditya', 'aditya.chakma@therapservices.net', '+8801763397014', 'ADMIN', '0000', 'indira road', '/aditya.chakma/home/images');
INSERT INTO user(name, email, mobile_no, role, password, address, image_link) VALUES('imran', 'al.imran@therapservices.net', '+8801911054847', 'ADMIN', '0000', 'dohs', '/al.imran/home/images');

INSERT INTO user(name, email, mobile_no, role, password, address, image_link) VALUES('sadia', 'sadia.afroz@therapservices.net', '+8801987654321', 'CUSTOMER', '0000', 'dhaka', '/sadia.afroz/home/images');
-- the following should not INSERT due to constraint
INSERT INTO user(name, email, mobile_no, role, password, address, image_link) VALUES('sadia', 'sadia.afroz@therapservices.net', '+8801987654321', 'CUSTOMER', '0000', 'dhaka', '/sadia.afroz/home/images');
INSERT INTO user(name, email, mobile_no, role, password, address, image_link) VALUES('rana', 'rana.masud@therapservices.net', '+8801234567890', 'CUSTOMER', '0000', 'dhaka', '/rana.masud/home/images');
INSERT INTO user(name, email, mobile_no, role, password, address, image_link) VALUES('dipon', 'tanjim.munir@therapservices.net', '+8802345123789', 'CUSTOMER', '0000', 'dhaka', '/tanjim.munir/home/images');

-- complaint
INSERT INTO complaint(title, user_id, status) VALUES('price too high', 3, 'OPEN');

-- complaint_reply
INSERT INTO complaint_reply(complaint_id, message) VALUES(1, 'price of hyperx cloud alpha is too high. It\'s cheaper in neighbor shops');
INSERT INTO complaint_reply(complaint_id, message) VALUES(1, 'lol. go to neibhour shop then.');
INSERT INTO complaint_reply(complaint_id, message) VALUES(1, 'Jabona. Taka ferot chai.');

-- category
INSERT INTO category(name) VALUES('accessories');
INSERT INTO category(name) VALUES('food');
INSERT INTO category(name) VALUES('electronics');

-- product
INSERT INTO product(category_id, name, quantity, price, status, details) VALUES(1, 'hyperx cloud', 5, 9900, 'IN_STOCK', 'Shei ekta headphone');
INSERT INTO product(category_id, name, quantity, price, status, details) VALUES(1, 'logitech g102', 5, 2100, 'IN_STOCK', 'Shei ekta mouse');
INSERT INTO product(category_id, name, quantity, price, status, details) VALUES(2, 'pran delight toast', 100, 50, 'IN_STOCK', 'Shei ekta toast');
INSERT INTO product(category_id, name, quantity, price, status, details) VALUES(3, 'blower', 5, 700, 'IN_STOCK', 'Shei ekta blower');

-- product_image
INSERT INTO product_image(product_id, image_link) VALUES(1, '/home/product/image/1_1.png');
INSERT INTO product_image(product_id, image_link) VALUES(1, '/home/product/image/1_2.png');
INSERT INTO product_image(product_id, image_link) VALUES(1, '/home/product/image/1_3.png');

INSERT INTO product_image(product_id, image_link) VALUES(2, '/home/product/image/2_1.png');
INSERT INTO product_image(product_id, image_link) VALUES(3, '/home/product/image/2_1.png');
INSERT INTO product_image(product_id, image_link) VALUES(4, '/home/product/image/3_1.png');

-- rating
INSERT INTO rating(rating, user_id, product_id) VALUES(5, 3, 1);
INSERT INTO rating(rating, user_id, product_id) VALUES(3, 5, 2);

-- cart_item
INSERT INTO cart_item(user_id, product_id, quantity) VALUES(3, 1, 1);
INSERT INTO cart_item(user_id, product_id, quantity) VALUES(5, 2, 2);

-- order
INSERT INTO `order`(user_id, sub_total, status) VALUES(3, 9000, 'DELIVERED');
INSERT INTO `order`(user_id, sub_total, status) VALUES(5, 150, 'DELIVERED');
INSERT INTO `order`(user_id, sub_total, status) VALUES(6, 4200, 'CONFIRMED');
-- order_product
INSERT INTO order_product(product_id, order_id, quantity) VALUES(1, 1, 1);
INSERT INTO order_product(product_id, order_id, quantity) VALUES(3, 2, 3);
INSERT INTO order_product(product_id, order_id, quantity) VALUES(2, 3, 2);

