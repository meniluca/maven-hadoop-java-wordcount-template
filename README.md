## Project: maven-hadoop-java-wordcount-template
This is a Maven Hadoop Java project template. This boilerplate skeleton code contains one Driver, a Mapper and a Reducer ready to be modified with your code (they contain the classic wordcount example).

In [this article](https://nosqlnocry.wordpress.com/2015/03/13/hadoop-mapreduce-wordcount-example-in-java-introduction-to-hadoop-job/) you can find instructions about how to import the project in Eclipse and modify it, together with an explanation about Hadoop jobs.

### Customize your job
Modify the Mapper and Recuder inside the `src/main/java/com/example` folder and the maven file `pom.xml`. 

### Compile your project
To compile the project use the maven command

    mvn clean package

### Run your application
Inside your shell with Hadoop

    hadoop jar your-hadoop-application.jar arg0 arg1 ...
