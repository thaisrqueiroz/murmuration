DELETE FROM transactions;
DELETE FROM offers;
DELETE FROM users;
DELETE FROM categories;
ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE offers AUTO_INCREMENT = 1;
ALTER TABLE categories AUTO_INCREMENT = 1;

INSERT INTO categories (id, name) VALUES (1, 'Food');
INSERT INTO categories (id, name) VALUES (2, 'Loan');
INSERT INTO categories (id, name) VALUES (3, 'Pet Care');
INSERT INTO categories (id, name) VALUES (4, 'Classes');
INSERT INTO categories (id, name) VALUES (5, 'Exchange');
INSERT INTO categories (id, name) VALUES (6, 'Handmade Products');
INSERT INTO categories (id, name) VALUES (7, 'Electronics');

INSERT INTO users (username, name, email, password, role, location, balance) VALUES ('admin', 'Admin User', 'admin@email.com', '$2a$12$fu5U8ffoS7fBBs/uJ88qRebYqduvYKEgsElAkVCHpUmPRD3ZHsoyq', 'ADMIN', 'Valencia', 100);
INSERT INTO users (username, name, email, password, role, location, balance) VALUES ('rubens_garcia', 'Rubens Garcia', 'elrubens@email.com', '$2a$12$qAPOHQ9Phe039yKYGFWIUevX6k9N/JEe9uJmujJGfZDYpYmHxGZKu', 'USER', 'Xirivella', 77);
INSERT INTO users (username, name, email, password, role, location, balance) VALUES ('selma_gonzalez', 'Selma Gonzalez', 'selma@email.com', '$2a$12$a65DjbrxIJPZtxYRCmk6OuBh88Rm5ntX6m2dW3yCdiy/O7JRTiuMK', 'USER', 'Valencia', 80);
INSERT INTO users (username, name, email, password, role, location, balance) VALUES ('anna_magaro', 'Anna Carolina Magaro', 'annacarolina@email.com', '$2a$12$aaooSvmGbWIHYgcpuybdI.D6xuQuTF5CHirQdkcbNPRFlK9SzYgHS', 'USER', 'Valencia', 30);
INSERT INTO users (username, name, email, password, role, location, balance) VALUES ('lorena_pumarino', 'Lorena Pumariño', 'lorena@email.com', '$2a$12$6ElMOBQHtqJAXADugqvwT.6c5H0ZixxXjFL3B8lf2Xf2sysBX0bG.', 'USER', 'Paterna', 75);
INSERT INTO users (username, name, email, password, role, location, balance) VALUES ('carlos_valls', 'Carlos Valls', 'carlos@email.com', '$2a$12$4NHOia2IQp6WwHqGbPljieeySywW9bgi3adNBIg1QErlesqaCpR0S', 'USER', 'Patacona', 67);
INSERT INTO users (username, name, email, password, role, location, balance) VALUES ('belen_valiente', 'Belén Valiente', 'belen@email.com', '$2a$12$bFRWpULA01OZac6rNmD5FOxf9zi9LzGbDzXouwJWBQE.w5EqQk1z.', 'USER', 'Alboraya', 48);
INSERT INTO users (username, name, email, password, role, location, balance) VALUES ('stefano_ventrudo', 'Stefano Ventrudo', 'stefano@email.com', '$2a$12$hHVZLB4r.h/mq3MW0tSJuecCs3pFHwxUQ4gmNvAIoTdX6i94C6hw.', 'USER', 'Torrent', 23);
INSERT INTO users (username, name, email, password, role, location, balance) VALUES ('delia_molina', 'Delia Molina Cordero', 'delia@email.com', '$2a$12$N6x6XifNYNPmJ8hnsO3iQui4Me0EGDQRgHZUH5AuFN3prH8LLJaAa', 'USER', 'Aldaya', 51);

INSERT INTO offers (title, description, category_id, price, owner_id, location, is_unique) VALUES ('Homemade vegan cheese', 'Fermented almond cheese with herbs', 1, 10, 2, 'Valencia', false);
INSERT INTO offers (title, description, category_id, price, owner_id, location, is_unique) VALUES ('Yoga class in the park', '1-hour Hatha Yoga session', 4, 15, 4, 'Paterna', false);
INSERT INTO offers (title, description, category_id, price, owner_id, location, is_unique) VALUES ('Sourdough bread', 'Fresh homemade rye bread', 1, 7, 1, 'Xirivella', false);
INSERT INTO offers (title, description, category_id, price, owner_id, location, is_unique) VALUES ('Mountain bike', 'Full-suspension mountain bike for trail rides', 5, 80, 2, 'Valencia', true);
INSERT INTO offers (title, description, category_id, price, owner_id, location, is_unique) VALUES ('Pet sitter', 'I''ll take care of your dogs or cats while you travel, for one hour', 3, 5, 6, 'Alboraya', false);
INSERT INTO offers (title, description, category_id, price, owner_id, location, is_unique) VALUES ('Lend a drill', 'Electric drill for short-term use', 2, 12, 3, 'Valencia', false);
INSERT INTO offers (title, description, category_id, price, owner_id, location, is_unique) VALUES ('French conversation exchange', '30-minute call to practice speaking', 4, 8, 5, 'La Eliana', false);
INSERT INTO offers (title, description, category_id, price, owner_id, location, is_unique) VALUES ('Vegan cupcakes', 'Box of 6 cupcakes - gluten-free', 1, 9, 7, 'Torrent', false);
INSERT INTO offers (title, description, category_id, price, owner_id, location, is_unique) VALUES ('Knitted scarf', 'Handmade wool scarf', 6, 20, 8, 'Aldaya', true);

INSERT INTO transactions (offer_id, receiver_user_id, customer_user_id, transaction_date) VALUES (5, 6, 4, '2025-08-23 10:00:00');
INSERT INTO transactions (offer_id, receiver_user_id, customer_user_id, transaction_date) VALUES (6, 3, 6, '2025-08-24 14:30:00');
INSERT INTO transactions (offer_id, receiver_user_id, customer_user_id, transaction_date) VALUES (7, 5, 8, '2025-08-25 09:15:00');
INSERT INTO transactions (offer_id, receiver_user_id, customer_user_id, transaction_date) VALUES (9, 8, 3, '2025-08-26 16:45:00');
INSERT INTO transactions (offer_id, receiver_user_id, customer_user_id, transaction_date) VALUES (1, 2, 5, '2025-08-27 11:20:00');
INSERT INTO transactions (offer_id, receiver_user_id, customer_user_id, transaction_date) VALUES (3, 1, 7, '2025-08-28 13:50:00');