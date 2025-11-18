ALTER SEQUENCE desenvolvedora_seq RESTART WITH 1;
ALTER SEQUENCE plataforma_seq RESTART WITH 1;
ALTER SEQUENCE jogo_seq RESTART WITH 1;

INSERT INTO Desenvolvedora(id, nome, paisOrigem, anoFundacao) VALUES (nextval('desenvolvedora_seq'), 'CD Projekt Red', 'Polônia', 2002);
INSERT INTO Desenvolvedora(id, nome, paisOrigem, anoFundacao) VALUES (nextval('desenvolvedora_seq'), 'Rockstar Games', 'EUA', 1998);
INSERT INTO Desenvolvedora(id, nome, paisOrigem, anoFundacao) VALUES (nextval('desenvolvedora_seq'), 'FromSoftware', 'Japão', 1986);
INSERT INTO Desenvolvedora(id, nome, paisOrigem, anoFundacao) VALUES (nextval('desenvolvedora_seq'), 'Naughty Dog', 'EUA', 1984);
INSERT INTO Desenvolvedora(id, nome, paisOrigem, anoFundacao) VALUES (nextval('desenvolvedora_seq'), 'Santa Monica Studio', 'EUA', 1999);
INSERT INTO Desenvolvedora(id, nome, paisOrigem, anoFundacao) VALUES (nextval('desenvolvedora_seq'), 'EA Sports', 'Canadá', 1991);
INSERT INTO Desenvolvedora(id, nome, paisOrigem, anoFundacao) VALUES (nextval('desenvolvedora_seq'), 'Polyphony Digital', 'Japão', 1998);
INSERT INTO Desenvolvedora(id, nome, paisOrigem, anoFundacao) VALUES (nextval('desenvolvedora_seq'), 'Infinity Ward', 'EUA', 2002);

INSERT INTO Plataforma(id, nome, fabricante, anoLancamento) VALUES (nextval('plataforma_seq'), 'PC', 'Vários', 1981);
INSERT INTO Plataforma(id, nome, fabricante, anoLancamento) VALUES (nextval('plataforma_seq'), 'PlayStation 5', 'Sony', 2020);
INSERT INTO Plataforma(id, nome, fabricante, anoLancamento) VALUES (nextval('plataforma_seq'), 'Xbox Series X/S', 'Microsoft', 2020);
INSERT INTO Plataforma(id, nome, fabricante, anoLancamento) VALUES (nextval('plataforma_seq'), 'Nintendo Switch', 'Nintendo', 2017);

INSERT INTO Jogo(id, titulo, genero, dataLancamento, descricao, avaliacaoCritica) VALUES (nextval('jogo_seq'), 'The Witcher 3: Wild Hunt', 'RPG', '2015-05-19', 'Um caçador de monstros em um vasto mundo de fantasia.', 93.0);
INSERT INTO Jogo(id, titulo, genero, dataLancamento, descricao, avaliacaoCritica) VALUES (nextval('jogo_seq'), 'Cyberpunk 2077', 'RPG', '2020-12-10', 'Um RPG de ação em Night City, uma megalópole obcecada por poder.', 86.0);
INSERT INTO Jogo(id, titulo, genero, dataLancamento, descricao, avaliacaoCritica) VALUES (nextval('jogo_seq'), 'Persona 5 Royal', 'RPG', '2019-10-31', 'Ladrões fantasmas adolescentes que mudam os corações dos corruptos.', 95.0);
INSERT INTO Jogo(id, titulo, genero, dataLancamento, descricao, avaliacaoCritica) VALUES (nextval('jogo_seq'), 'Red Dead Redemption 2', 'ACAO_AVENTURA', '2018-10-26', 'A história de Arthur Morgan no fim da era do Velho Oeste.', 97.0);
INSERT INTO Jogo(id, titulo, genero, dataLancamento, descricao, avaliacaoCritica) VALUES (nextval('jogo_seq'), 'God of War Ragnarök', 'ACAO_AVENTURA', '2022-11-09', 'Kratos e Atreus enfrentam o apocalipse nórdico.', 94.0);
INSERT INTO Jogo(id, titulo, genero, dataLancamento, descricao, avaliacaoCritica) VALUES (nextval('jogo_seq'), 'The Last of Us Part I', 'ACAO_AVENTURA', '2022-09-02', 'Uma jornada brutal por uma América pós-pandêmica.', 88.0);
INSERT INTO Jogo(id, titulo, genero, dataLancamento, descricao, avaliacaoCritica) VALUES (nextval('jogo_seq'), 'Elden Ring', 'SOULS_LIKE', '2022-02-25', 'Explore as Terras Intermédias em busca de se tornar o Lorde Prístino.', 96.0);
INSERT INTO Jogo(id, titulo, genero, dataLancamento, descricao, avaliacaoCritica) VALUES (nextval('jogo_seq'), 'Dark Souls III', 'SOULS_LIKE', '2016-03-24', 'Em busca dos Senhores das Cinzas no reino de Lothric.', 89.0);
INSERT INTO Jogo(id, titulo, genero, dataLancamento, descricao, avaliacaoCritica) VALUES (nextval('jogo_seq'), 'Bloodborne', 'SOULS_LIKE', '2015-03-24', 'Um caçador em uma cidade gótica infestada por uma doença endêmica.', 92.0);
INSERT INTO Jogo(id, titulo, genero, dataLancamento, descricao, avaliacaoCritica) VALUES (nextval('jogo_seq'), 'FIFA 23', 'ESPORTES', '2022-09-30', 'O mais recente simulador de futebol da EA.', 76.0);
INSERT INTO Jogo(id, titulo, genero, dataLancamento, descricao, avaliacaoCritica) VALUES (nextval('jogo_seq'), 'NBA 2K23', 'ESPORTES', '2022-09-09', 'Simulador de basquete com modos de jogo variados.', 78.0);
INSERT INTO Jogo(id, titulo, genero, dataLancamento, descricao, avaliacaoCritica) VALUES (nextval('jogo_seq'), 'F1 22', 'CORRIDA', '2022-07-01', 'O jogo oficial da temporada de 2022 da Fórmula 1.', 79.0);