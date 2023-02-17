# github-profiles

App separado entre back-end e front-end.

# github-profiles-back

Para realizar o start do backend, basta rodar o compando: docker-compose build, e após finalizar rodar o comando docker-compose up.

Neste processo ele irá inicar os contêineres do serviço back-end (que estará exposto na porta 8080) e do banco de dados (MongoDb que estará exposto na porta 27017).

# github-profiles-front

Para iniciar o front-end basta rodar os seguintes comandos (por padrão a aplicação irá ser iniciada na porta 5173):
- npm install
- npm run dev
