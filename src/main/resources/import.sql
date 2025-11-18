ALTER SEQUENCE publisher_seq RESTART WITH 1;
ALTER SEQUENCE desenvolvedora_seq RESTART WITH 1;
ALTER SEQUENCE plataforma_seq RESTART WITH 1;
ALTER SEQUENCE jogodetalhes_seq RESTART WITH 1;
ALTER SEQUENCE jogo_seq RESTART WITH 1;

INSERT INTO Publisher(id, nome, anoFundacao) VALUES (nextval('publisher_seq'), 'Electronic Arts', 1982);
INSERT INTO Publisher(id, nome, anoFundacao) VALUES (nextval('publisher_seq'), 'Sony Interactive Entertainment', 1993);
INSERT INTO Publisher(id, nome, anoFundacao) VALUES (nextval('publisher_seq'), 'Bandai Namco', 2006);

INSERT INTO Desenvolvedora(id, nome, paisOrigem, anoFundacao) VALUES (nextval('desenvolvedora_seq'), 'CD Projekt Red', 'Polônia', 2002);
INSERT INTO Desenvolvedora(id, nome, paisOrigem, anoFundacao) VALUES (nextval('desenvolvedora_seq'), 'FromSoftware', 'Japão', 1986);
INSERT INTO Plataforma(id, nome, fabricante, anoLancamento) VALUES (nextval('plataforma_seq'), 'PC', 'Vários', 1981);
INSERT INTO Plataforma(id, nome, fabricante, anoLancamento) VALUES (nextval('plataforma_seq'), 'PlayStation 5', 'Sony', 2020);

INSERT INTO JogoDetalhes(id, descricao, avaliacaoCritica) VALUES (nextval('jogodetalhes_seq'), 'Um caçador de monstros em um vasto mundo de fantasia.', 93.0);
INSERT INTO JogoDetalhes(id, descricao, avaliacaoCritica) VALUES (nextval('jogodetalhes_seq'), 'Explore as Terras Intermédias em busca de se tornar o Lorde Prístino.', 96.0);

INSERT INTO Jogo(id, titulo, genero, dataLancamento, detalhes_id) VALUES (nextval('jogo_seq'), 'The Witcher 3: Wild Hunt', 'RPG', '2015-05-19', 1);
INSERT INTO Jogo(id, titulo, genero, dataLancamento, detalhes_id) VALUES (nextval('jogo_seq'), 'Elden Ring', 'SOULS_LIKE', '2022-02-25', 2);

INSERT INTO Jogo_Plataforma(Jogo_id, plataformas_id) VALUES (1, 1);
INSERT INTO Jogo_Plataforma(Jogo_id, plataformas_id) VALUES (1, 2);
INSERT INTO Jogo_Plataforma(Jogo_id, plataformas_id) VALUES (2, 1);
INSERT INTO Jogo_Plataforma(Jogo_id, plataformas_id) VALUES (2, 2);

INSERT INTO Jogo_Desenvolvedora(Jogo_id, desenvolvedoras_id) VALUES (1, 1);
INSERT INTO Jogo_Desenvolvedora(Jogo_id, desenvolvedoras_id) VALUES (2, 2);
