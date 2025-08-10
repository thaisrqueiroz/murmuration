INSERT INTO category (id, name) VALUES (1, 'Food');
INSERT INTO category (id, name) VALUES (2, 'Loan');
INSERT INTO category (id, name) VALUES (3, 'Pet Care');
INSERT INTO category (id, name) VALUES (4, 'Language Classes');
INSERT INTO category (id, name) VALUES (5, 'Sport Classes');
INSERT INTO category (id, name) VALUES (6, 'Handmade Products');

INSERT INTO user (username, name, email, password, role, location, balance)
VALUES ('admin', 'Admin User', 'admin@email.con', '$2a$12$eW5Z1b3k7f8z4j1Q9F5hOe0Y6d3m5J8Zy7K9l5cX1k2u7G4H5D3iG', 'ADMIN', 'Valencia', 100);
VALUES ('rubens_garcia', 'Rubens Garcia', 'elrubens@email.com', '$2a$12$Gu29FejMW6R7uQAgm40.1eclyi2s7GMdTbiPljIvzbZUnzKsObupS', 'USER', 'Xirivella', 120);
VALUES ('selma_gonzalez', 'Selma Gonzalez', 'selma@email.com', '$2a$12$vxnsj/7LYi5rmMURE5syHO8yZY1pvYP/7vwdLdhqOAhPRdokjVjOC', 'USER', 'Valencia', 80);
VALUES ('anna_magaro', 'Anna Carolina Magaro', 'annacarolina@email.com', '$2a$12$qjuS/I.Dbq7897PGbk.5nuG7G9kory4UKWe07m47S.kajwrKincr.', 'USER', 'Valencia', 30);
VALUES ('lorena_pumarino', 'Lorena Pumariño', 'lorena@email.com', '$2a$12$EYX.uDNasVKxnpBD3PZaWurfINF8Q9f/a7/CbqqT1LIy1SCK6xZoC', 'USER', 'Paterna', 75);
VALUES ('carlos_valls', 'Carlos Valls', 'carlos@email.com', '$2a$12$xu5VM5uAqtEDgl8KSsDebOfej8in3NVZBpHg9xYpHYASlQLjuldee', 'USER', 'Patacona', 67);
VALUES ('belen_valiente', 'Belén Valiente', 'belen@email.com', '$2a$12$g4pVyfH21b4JEWk2e795R.UenYAYD7SBG9nNw9IA3imh.q7qP8VSG', 'USER', 'Alboraya', 48);
VALUES ('stefano_ventrudo', 'Stefano Ventrudo', 'stefano@email.com', '$2a$12$qO8Zl2d0mf7eqaZmXFoRcOuUHOUoPQTJoX7x1uJEwsnObB31i2lSq', 'USER', 'Torrent', 23);
VALUES ('delia_molina', 'Delia Molina Cordero', 'delia@email.com', '$2a$12$LQi08VgGh4jlDBrIappD7eO0adStCd9fxpZ9t33drjsfac32TfpQS', 'USER', 'Aldaya', 51);

INSERT INTO offer (title, description, category_id, price, owner_id, location) VALUES
('Homemade vegan cheese', 'Fermented almond cheese with herbs', 1, 10, 2, 'Valencia'),
('Yoga class in the park', '1-hour Hatha Yoga session', 5, 15, 4, 'Paterna'),
('Plant babysitting', 'I’ll take care of your plants while you travel', 3, 5, 6, 'Alboraya'),
('Sourdough bread', 'Fresh homemade rye bread', 1, 7, 1, 'Xirivella'),
('Lend a drill', 'Electric drill for short-term use', 2, 12, 3, 'Valencia'),
('French conversation exchange', '30-minute call to practice speaking', 4, 8, 5, 'La Eliana'),
('Vegan cupcakes', 'Box of 6 cupcakes - gluten-free', 1, 9, 7, 'Torrent');

INSERT INTO transaction (transaction_title, amount, transaction_date, sender_user_id, receiver_user_id) VALUES
('Bought sourdough bread from Rubens', 7, '2025-08-01', 3, 1),
('French practice with Carlos', 8, '2025-08-02', 6, 5),
('Yoga class by Lorena', 15, '2025-08-03', 2, 4),
('Homemade cheese from Selma', 10, '2025-08-04', 5, 2),
('Vegan cupcakes by Stefano', 9, '2025-08-05', 8, 7);