Instruções para rodar a aplicação:\
A aplicação possui 2 servidores (autenticação (oaut2) e recursos) e será necessário rodar um servidor para o front-end, o banco de dados usado foi o postgres;

1-Clonar o repositório e importar os projetos do backend no spring boot tool suit 4 (backend/AuthServer e backend/BlogServer);\
2-Criar dois bancos de dados no postgres com nomes "blog_database" e "auth_database";\
3-Configurar o App properties dos dois projetos com o url de conexão do postgres, o usuario e a senha;\
      database.connect.uri\
      database.username\
      database.password    \
4-As aplicações devem rodar nas portas (do contrario o front-end não encontrará os servidores): \
      AuthServer: 8080\
      BlogServer: 8081\
5-A aplicação BlogServer necessita salvar arquivos, portante é necessário uma pasta dedicada no fileSystem.\
  Passar o caminho da pasta no application.properties do BlogServer\
    EX: file.system.base.path=D:/ProjetosPessoais/NewBlogFileSystem/ \

6-Rodar as duas aplicações, nesse momento as aplicações devem subir sem erro. \
    
7-Para rodar o front-end deve-se ter instalado o npm (gerenciador de pacotes) \
8-Rodar o comando "npm install http-server -g" para installar o servidor que irá servir o front-end \
9-Abrir um cmd dentro da pasta dist/new-blog do front-end (frontend/new-blog/dist/new-blog) \
10-Rodar o comando "http-server -c -1 -p 4200 --proxy http://localhost:4200?" \
    O front-end deve rodar na porta 4200; \
    
11-Ao abrir localhost:4200 o fluxo de oauth deve ser iniciado, podendo assim criar uma conta de usuario e realizar o login. \
    Ao realizar o login a pagina inicial do blog deve aparecer. \
    
ERROS CONHECIDOS: \
  O servidor de autenticação está mantendo a sessão realizar o login. Isso é um problema com a biblioteca de oauth do spring boot, \
        onde ela não expira a sessão ao redirecionar de volta o usuário. Existem algumas soluções, mas não tive tempo de implementar.
        Isso causa o problema onde ao realizar o logout a aplicação é relogada automaticamente. \
        Um "workaround" é limpar manualmente o cookie JSESSIONID. \
        
  Apesar das apis para criação de album de fotos, exclusão, inserção de photos (PhotoAlbumController) e exclusão estarem prontas. \
  As funcionalidades não estão implementadas no front-end. Sendo possivel apenas visualizar o album de fotos "Fotos da linha do tempo" pra onde vão todas as fotos enviadas junto aos posts. \
    
