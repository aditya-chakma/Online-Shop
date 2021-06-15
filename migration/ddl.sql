CREATE TABLE user(
id INT AUTO_INCREMENT NOT NULL,
name VARCHAR(50) NOT NULL,
email VARCHAR(50) UNIQUE NOT NULL,
mobile_no VARCHAR(20) NOT NULL,
role VARCHAR(15) NOT NULL,
password VARCHAR(50) NOT NULL,
address VARCHAR(200) NOT NULL,
image_link VARCHAR(100) NOT NULL,
created_at TIMESTAMP DEFAULT NOW(),
updated_at TIMESTAMP DEFAULT NOW(),
PRIMARY KEY(id)
);

CREATE TABLE complaint(
id INT AUTO_INCREMENT NOT NULL,
title VARCHAR(100) NOT NULL,
user_id INT NOT NULL,
created_at TIMESTAMP DEFAULT NOW(),
status VARCHAR(15) NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(user_id) REFERENCES user(id)
);

CREATE TABLE complaint_reply(
id INT AUTO_INCREMENT NOT NULL,
complaint_id INT NOT NULL,
message VARCHAR(500) NOT NULL,
created_at TIMESTAMP DEFAULT NOW(),
PRIMARY KEY(id),
FOREIGN KEY(complaint_id) REFERENCES complaint(id)
);

CREATE TABLE category(
id INT AUTO_INCREMENT NOT NULL,
name VARCHAR(50) NOT NULL,
PRIMARY KEY(id)
);

CREATE TABLE product(
id INT AUTO_INCREMENT NOT NULL,
category_id INT NOT NULL,
name VARCHAR(50) NOT NULL,
quantity INT NOT NULL,
price DOUBLE NOT NULL,
status VARCHAR(15) NOT NULL,
details VARCHAR(500),
created_at TIMESTAMP DEFAULT NOW(),
updated_at TIMESTAMP DEFAULT NOW(),
PRIMARY KEY(id),
FOREIGN KEY(category_id) REFERENCES category(id)
);

CREATE TABLE product_image (
id INT AUTO_INCREMENT NOT NULL,
product_id INT NOT NULL,
image_link VARCHAR(100) NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(product_id) REFERENCES product(id)
);

CREATE TABLE rating(
id INT AUTO_INCREMENT NOT NULL,
rating INT NOT NULL,
user_id INT NOT NULL,
product_id INT NOT NULL,
created_at TIMESTAMP DEFAULT NOW(),
PRIMARY KEY(id),
FOREIGN KEY(user_id) REFERENCES user(id),
FOREIGN KEY(product_id) REFERENCES product(id)
);

CREATE TABLE cart_item(
id INT AUTO_INCREMENT NOT NULL,
user_id INT NOT NULL,
product_id INT NOT NULL,
quantity INT NOT NULL,
created_at TIMESTAMP DEFAULT NOW(),
updated_at TIMESTAMP DEFAULT NOW(),
PRIMARY KEY(id),
FOREIGN KEY(user_id) REFERENCES user(id),
FOREIGN KEY(product_id) REFERENCES product(id)
);

CREATE TABLE `order`(
id INT AUTO_INCREMENT NOT NULL,
user_id INT NOT NULL,
sub_total DOUBLE NOT NULL,
status VARCHAR(15) NOT NULL,
created_at TIMESTAMP DEFAULT NOW(),
updated_at TIMESTAMP DEFAULT NOW(),
PRIMARY KEY(id),
FOREIGN KEY(user_id) REFERENCES user(id)
);

CREATE TABLE order_product(
id INT AUTO_INCREMENT NOT NULL,
product_id INT NOT NULL,
order_id INT NOT NULL,
quantity INT NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(product_id) REFERENCES product(id),
FOREIGN KEY(order_id) REFERENCES `order`(id)
);


-- DROP TABLE rating;
-- DROP TABLE cart_item;
-- DROP TABLE complaint_reply;
-- DROP TABLE order_product;
-- DROP TABLE `order`;
-- DROP TABLE complaint;
-- DROP TABLE user;
-- DROP TABLE product_image;
-- DROP TABLE product;
-- DROP TABLE category;

