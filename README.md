## <center><h1>GreenDao Module for Android Studio</h1></center>

This project aims to provide a reusable instrument for easy and fast implementation of [GreenDao](http://greendao-orm.com/) database in every Android project. It consists of Java module - GreenDao database universal generator which is ready to be put in any project. By editing single line of code, point a path to your project where ready to use database files will be created. Usage of module is presented on exemplary project that is also included in the package. 

## Quick Setup
<h3>1. Download module</h3>
Pull the package with exemplary project and generator from GitHub.

<h3>2. Add module to project</h3>
Place MyDaoGenerator anywhere you like in your project tree.

<h3>3. Connect generator with project</h3> 
By editing ONLY `outputDir` parameter in <i>gradle.build</i> file point the direction where database files should be created:

    project(':MyDaoGenerator') {
        apply plugin: 'application'
        apply plugin: 'java'

        mainClassName = "pl.surecase.eu.MyDaoGenerator"
        // edit output direction
        outputDir = "../DaoExample/src/main/java-gen"

        dependencies {
            compile fileTree(dir: 'libs', include: ['*.jar'])
            compile('de.greenrobot:DaoGenerator:1.3.0')
        }

        task createDocs {
            def docs = file(outputDir)
            docs.mkdirs()
        }

        run {
            args outputDir
        }
    }

Module provide DaoGenerator library on its own via Maven software tool. This library is necessary for creating GreenDao database files. Directory entered by user will be created if it doesn't exist and sent do java code as console argument.

<h3>4. Create database</h3>
MyDaoGenerator contains only one java class. It is a place where you can create your database accordingly to your needs. Example presented below is a creation of single object named <i> Box </i> that contains fields such as ID, Name, Slots and Description. You can learn how to modify this file by using [GreenDao documentation](http://greendao-orm.com/documentation/).

    public class MyDaoGenerator {

        public static void main(String args[]) throws Exception {
            Schema schema = new Schema(3, "greendao");
            Entity box = schema.addEntity("Box");
            box.addIdProperty();
            box.addStringProperty("name");
            box.addIntProperty("slots");
            box.addStringProperty("description");
            new DaoGenerator().generateAll(schema, args[0]);
        }
    } 

<h3>5. Run module </h3>
After pointing output path of your database files and creating your database code, you can run the module to generate files. Module isn't a part of your project. It is only a tool that you use to generate files - it isn't compiled during build of your project. 

- To run MyDaoGenerator go to Gradle task section in Android Studio. 
- Pick MyDaoGenerator from the Gradle project tree.
- Chose <b>run</b> task.

![gradleRun](https://github.com/SureCase/GreenDaoForAndroidStudio/blob/master/Screenshots/gradleRun.png?raw=true)

- Module has created files accordingly to content of MyDaoGenerator.class and in directory specified in `outputDir` parameter.

<h3>6. Make project aware of GreenDao files</h3>
To use GreenDao objects, project needs GreenDao library to be included.

- It is possible to get it by [downloading *.JAR file](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22de.greenrobot%22%20AND%20a%3A%22greendao%22).

or:

- Add Maven code to <i>gradle.build</i> file.

        dependencies {
             compile 'de.greenrobot:greendao:1.3.7'
        }

