# github-profiles

App separado entre back-end e front-end.

# github-profiles-back

Para realizar o start do backend, basta rodar o compando: docker-compose build, e após finalizar rodar o comando docker-compose up.

Neste processo ele irá inicar os contêineres,sendo o do serviço back-end (que estará exposto na porta 8080) e o do banco de dados (MongoDb que estará exposto na porta 27017).

# github-profiles-front

Para iniciar o front-end basta rodar os seguintes comandos (por padrão a aplicação irá ser iniciada na porta 5173):
- `npm install` 
- `npm run dev`

# Considerações sobre o serviço desenvolvido

Dado o desafio passado, entendi que de acordo com as necessidades, o serviço teria uma característica de trabalhar mais com operações de I/O do que com o processamento de dados, o que considerei sendo melhor para atender este requisito o serviço utilizar uma perspectiva reativa.

A escolha pelo banco de dados MongoDB consistiu em verificar que não teria a necessidade de separar os relacionamentos entre as informações, uma vez que elas dependem de um mesmo dado em comum.

A estrutura do serviço ficou divido em camadas, e sua escolha se deu por seu uma padrão comum em aplicações de back-end.
