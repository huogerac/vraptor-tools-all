VRaptor Tools
===================

Ferramenta de linha de comando para facilitar a criação
das entidades (models) e controllers em projetos Java Web
que utilizam VRaptor.

**Nota**: Este projeto é um complemento para quem gosta
  de maven. Uma alternativa a este projeto é http://caelum.github.com/vraptor/docs/vraptor-scaffold-en/ 

Instalacao
--------------------
A maneira mais simples é baixar o código, e via maven (http://maven.apache.org/) fazer o install:
    
    git clone https://github.com/huogerac/vraptor-tools-all.git
    cd vraptor-tools-all
    mvn clean install

Em seguida, é necessário adicionar o JAR do VRaptor-tools ao seu projeto.
Dentro de plugins do pom.xml, adicionar:

    <build>
        ...
        <plugins>
            ...
            <plugin>
                <groupId>br.com.caelum.vraptor</groupId>
                <artifactId>vraptor-maven-plugin</artifactId>
                <version>0.0.2</version>
                <configuration>
                    <package_root>br.com.meuprojeto</package_root>
                    <persistenceAPI>hibernate</persistenceAPI>
                </configuration>
            </plugin>
        </plugins>
    </build>

**Nota**: Para facilitar a criação de projetos web VRaptor + maven
  existe o archetype https://github.com/huogerac/vraptor-hibernate-archetype


Utilização
--------------------
Com o JAR do VRaptor-tools configurado no pom.xml do seu projeto web, dentro da
pasta do projeto pode-se utilizar os comandos do VRaptor-tools.

Criar um model

    mvn vraptor:create-model -Dmodel=Issue -Drepository=Issues -Dfields=status,description,type:int

Criar um controller

    mvn vraptor:create-controller -Dmodel=Issue -Drepository=Issues -Dfields=status,description,type:int -Dmethod=crud

É isto, o projeto esta bem no começo, tem muito trabalho a ser feito ainda.
Ideias e críticas são bem vindas (via pull request ou huogerac at gmail . com)
