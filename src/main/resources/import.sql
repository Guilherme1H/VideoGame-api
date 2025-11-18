ALTER SEQUENCE publisher_seq RESTART WITH 1;
ALTER SEQUENCE desenvolvedora_seq RESTART WITH 1;
ALTER SEQUENCE plataforma_seq RESTART WITH 1;
ALTER SEQUENCE jogodetalhes_seq RESTART WITH 1;
ALTER SEQUENCE jogo_seq RESTART WITH 1;

INSERT INTO Publisher(id, nome, anoFundacao) VALUES (nextval('publisher_seq'), 'CDPR', 2002);
INSERT INTO Plataforma(id, nome, fabricante, anoLancamento) VALUES (nextval('plataforma_seq'), 'PC', 'Various', 1981);
INSERT INTO JogoDetalhes(id, descricao, avaliacaoCritica) VALUES (nextval('jogodetalhes_seq'), 'Um RPG de mundo aberto.', 93.0);

INSERT INTO Desenvolvedora(id, nome, paisOrigem, anoFundacao, publisher_id) VALUES (nextval('desenvolvedora_seq'), 'CD Projekt Red', 'Polônia', 2002, 1);
INSERT INTO Jogo(id, titulo, genero, dataLancamento, detalhes_id) VALUES (nextval('jogo_seq'), 'The Witcher 3', 'RPG', '2015-05-19', 1);

INSERT INTO Jogo_Desenvolvedora(Jogo_id, desenvolvedoras_id) VALUES (1, 1);
INSERT INTO Jogo_Plataforma(Jogo_id, plataformas_id) VALUES (1, 1);