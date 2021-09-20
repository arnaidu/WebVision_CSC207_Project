# WebVision csc207 Project 
-- If you have any questions regarding the project, you can inquire by contacting me at "anil.r.naidu@gmail.com".
## RUNNING APPLICATION

The files will need to opened up in an IDE as I am currently unable to properly package it into a .jar file.
There are some issues, probably with how this IDE reads file locations, and I do not have the time to 
figure this out as I want to move past this older project. The IDE used for this project was IntelliJ. 
There may be a need to right click on pom.xml in order to add it as a maven project. When everything is set up
then you can run the Application.java within the IDE. Running this from a terminal won't work.

Note: The path to where the java files are is `WebvisionWebapp/src/main/java/com/csc/spring`. This is where the implementation is
and the Application.java file to run if you want. Again, do not run in a terminal, as there is no .jar file as yet. I might
come back to this to fix it later, but I am more focused on job applications right now.

Run the application and wait for Java spring to open
and start the project. If there are no errors you should be notified on the console that
the live server has been initialized properly and the application is now running 
on http://localhost:8080. If you have another service running on port 8080 refer to the
troubleshooting section at the bottom of this read me. 

If there are any errors regarding the Java language, check the language level, 
then try rebuilding the project and running. There may be an error with the method readAllBytes() in Documents
class since that is for Java 9. There should be a commented line telling you to comment out that line and uncomment the 
line below to have it working for Java 8. Generally speaking, we used Java 8, and there may be a need to go to all parts
of the IDE to change the bytecode to 8, and the SDK to 1.8. Again, this wouldn't be a problem if I put everything into
a working .jar file, but I can't at the moment. I may try to do this in the future, but probably not, since this project is
not important anymore. 

## Using the Application
Make sure to create/register as a coordinator, then as an interviewer, then as an applicant before doing anything else.
This will allow things to work sufficiently well. However, there are some NULL pointer exceptions which need to be fixed.
There are at least a couple areas where we are probably missing a line of code to properly insert data into the repo, and so
NULL values are returned when accessed. This may require models to be added to the code. There are also some UI issues (things
happening more than once or things not dissapearing when they should). There are only a few UI issues, with most of it working
fine, however, I personally did not work on the parts that contain these issues. It would be possible for me to fix all of the above
issues, however, this would require that I relearn some Vaadin to get used to it. I do not feel like going through a 2 year old project
and learning Vaadin, which I will never use in my future. I am currently working with React and other things that are nicer to use. 


## Troubleshooting
If the above installation fails try any of the following
* Reimport the project
* If you have a service running on port 8080 navigate to src>main>resources>application.properties
and change the line that says 'spring.port=8080' to some other
