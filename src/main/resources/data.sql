INSERT INTO categories (id, name) VALUES (1, 'Food');
INSERT INTO categories (id, name) VALUES (2, 'Loan');
INSERT INTO categories (id, name) VALUES (3, 'Pet Care');
INSERT INTO categories (id, name) VALUES (4, 'Language Classes');
INSERT INTO categories (id, name) VALUES (5, 'Sport Classes');
INSERT INTO categories (id, name) VALUES (6, 'Handmade Products');

INSERT INTO users (username, name, email, password, role, location, balance) VALUES ('admin', 'Admin User', 'admin@email.con', '$2a$12$fu5U8ffoS7fBBs/uJ88qRebYqduvYKEgsElAkVCHpUmPRD3ZHsoyq', 'ADMIN', 'Valencia', 100);
INSERT INTO users (username, name, email, password, role, location, balance) VALUES ('rubens_garcia', 'Rubens Garcia', 'elrubens@email.com', '$2a$12$qAPOHQ9Phe039yKYGFWIUevX6k9N/JEe9uJmujJGfZDYpYmHxGZKu', 'USER', 'Xirivella', 5);
INSERT INTO users (username, name, email, password, role, location, balance) VALUES ('selma_gonzalez', 'Selma Gonzalez', 'selma@email.com', '$2a$12$a65DjbrxIJPZtxYRCmk6OuBh88Rm5ntX6m2dW3yCdiy/O7JRTiuMK', 'USER', 'Valencia', 80);
INSERT INTO users (username, name, email, password, role, location, balance) VALUES ('anna_magaro', 'Anna Carolina Magaro', 'annacarolina@email.com', '$2a$12$aaooSvmGbWIHYgcpuybdI.D6xuQuTF5CHirQdkcbNPRFlK9SzYgHS', 'USER', 'Valencia', 30);
INSERT INTO users (username, name, email, password, role, location, balance) VALUES ('lorena_pumarino', 'Lorena Pumariño', 'lorena@email.com', '$2a$12$6ElMOBQHtqJAXADugqvwT.6c5H0ZixxXjFL3B8lf2Xf2sysBX0bG.', 'USER', 'Paterna', 75);
INSERT INTO users (username, name, email, password, role, location, balance) VALUES ('carlos_valls', 'Carlos Valls', 'carlos@email.com', '$2a$12$4NHOia2IQp6WwHqGbPljieeySywW9bgi3adNBIg1QErlesqaCpR0S', 'USER', 'Patacona', 67);
INSERT INTO users (username, name, email, password, role, location, balance) VALUES ('belen_valiente', 'Belén Valiente', 'belen@email.com', '$2a$12$bFRWpULA01OZac6rNmD5FOxf9zi9LzGbDzXouwJWBQE.w5EqQk1z.', 'USER', 'Alboraya', 48);
INSERT INTO users (username, name, email, password, role, location, balance) VALUES ('stefano_ventrudo', 'Stefano Ventrudo', 'stefano@email.com', '$2a$12$hHVZLB4r.h/mq3MW0tSJuecCs3pFHwxUQ4gmNvAIoTdX6i94C6hw.', 'USER', 'Torrent', 23);
INSERT INTO users (username, name, email, password, role, location, balance) VALUES ('delia_molina', 'Delia Molina Cordero', 'delia@email.com', '$2a$12$N6x6XifNYNPmJ8hnsO3iQui4Me0EGDQRgHZUH5AuFN3prH8LLJaAa', 'USER', 'Aldaya', 51);

INSERT INTO offers (title, description, category_id, price, owner_id, location) VALUES ('Homemade vegan cheese', 'Fermented almond cheese with herbs', 1, 10, 2, 'Valencia');
INSERT INTO offers (title, description, category_id, price, owner_id, location) VALUES ('Yoga class in the park', '1-hour Hatha Yoga session', 5, 15, 4, 'Paterna');
INSERT INTO offers (title, description, category_id, price, owner_id, location) VALUES ('Pet sitter', 'I''ll take care of your dogs or cats while you travel, for one hour', 3, 5, 6, 'Alboraya');
INSERT INTO offers (title, description, category_id, price, owner_id, location) VALUES ('Sourdough bread', 'Fresh homemade rye bread', 1, 7, 1, 'Xirivella');
INSERT INTO offers (title, description, category_id, price, owner_id, location) VALUES ('Lend a drill', 'Electric drill for short-term use', 2, 12, 3, 'Valencia');
INSERT INTO offers (title, description, category_id, price, owner_id, location) VALUES ('French conversation exchange', '30-minute call to practice speaking', 4, 8, 5, 'La Eliana');
INSERT INTO offers (title, description, category_id, price, owner_id, location) VALUES ('Vegan cupcakes', 'Box of 6 cupcakes - gluten-free', 1, 9, 7, 'Torrent');

INSERT INTO transactions (offer_id, receiver_user_id, customer_user_id, transaction_date) VALUES (5, 3, 4, '2025-08-23 10:00:00');
INSERT INTO transactions (offer_id, receiver_user_id, customer_user_id, transaction_date) VALUES (6, 5, 6, '2025-08-24 14:30:00');
INSERT INTO transactions (offer_id, receiver_user_id, customer_user_id, transaction_date) VALUES (7, 7, 8, '2025-08-25 09:15:00');