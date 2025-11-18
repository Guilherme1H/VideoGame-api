🎮 API de Gerenciamento de Jogos
E aí! Essa é uma API RESTful que criei para gerenciar jogos, desenvolvedoras, publishers e plataformas. Ela foi desenvolvida com Quarkus para a disciplina de Web Services e o foco foi construir uma API robusta e moderna, implementando conceitos avançados como: segurança (API Key e Rate Limiting), idempotência para evitar requisições duplicadas e HATEOAS para uma navegação inteligente.

🚀 O que usei pra construir?
Quarkus: Meu framework Java preferido! Leve, rápido e perfeito para APIs de alta performance.
Java 17: A linguagem por trás de tudo, em sua versão moderna.
Panache: Ajuda demais a lidar com o banco de dados de um jeito simples e produtivo.
Hibernate Validator: Pra garantir que os dados que chegam na API estão sempre certinhos.
H2 Database: Um banco de dados em memória, rapidinho para rodar localmente e testar.
Princípios REST Avançados:
HATEOAS: Links inteligentes que te mostram o que fazer em seguida na API.
Segurança: Filtros para autenticação via X-API-Key e controle de Rate Limiting.
Idempotência: Proteção contra requisições duplicadas usando Idempotency-Key.
Filtros JAX-RS: Implementação de logging e ETag para otimização de cache.
Guava: Biblioteca do Google utilizada para o cache eficiente do Rate Limiting e da Idempotência.
�� O que essa API faz?
Essa API te permite gerenciar o ecossistema de um catálogo de jogos:

🎮 Jogos:
Criar, ver, atualizar e apagar jogos.
Listar todos os jogos com filtros (por gênero) e ordenação (por título ou avaliação).
Ver os jogos de uma desenvolvedora específica.
👩‍💻 Desenvolvedoras:
Criar, ver, atualizar e apagar.
Associar a uma Publisher no momento da criação/atualização.
🏢 Publishers:
Criar, ver, atualizar e apagar.
Funciona como o "guarda-chuva" que agrupa várias desenvolvedoras (relação One-to-Many).
�� Para testar
Pra você ver tudo funcionando e até fazer umas chamadas de teste, use o Swagger UI! Ele já está configurado com toda a documentação, exemplos e os campos para autenticação.

Rodando no seu PC: 
localhost
🛠️ Colocando pra Rodar
O que você precisa ter: Java 17+ e Maven 3.8+.

Pegar o código:

bash
Copiar

git clone https://github.com/Guilherme1H/VideoGame-api.git
cd VideoGame-api
Rodar no modo "desenvolvimento" (com atualizações automáticas):

bash
Copiar

./mvnw quarkus:dev
A API estará em http://localhost:8080 e qualquer mudança no código já aparece na hora!

Pra gerar um arquivo final (tipo pra produção):

bash
Copiar

./mvnw clean package
java -jar target/quarkus-app/quarkus-run.jar
🧠 As Peças Chave do Sistema (Entidades)
Jogo: Onde ficam todas as informações dos jogos.
JogoDetalhes: Entidade separada para guardar a descrição e avaliação, demonstrando um relacionamento One-to-One com Jogo.
Desenvolvedora: Quem faz os jogos.
Publisher: Quem publica os jogos. Uma Publisher pode ter várias Desenvolvedoras, demonstrando um relacionamento One-to-Many.
Plataforma: Onde os jogos rodam. A relação entre Jogo e Plataforma/Desenvolvedora é Many-to-Many.
�� Licença
Relaxa! Esse projeto é de uso livre para fins acadêmicos.
