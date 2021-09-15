#WebVision csc207 Project

##Installation To import the project open intelliJ IDE and select file>open locate the phase 2 folder and select the pom.xml file, click on open>open as project>delete project and reimport. Give the IDE a couple of minutes while all dependencies are being imported. Once it opens you will realize the project opened successfully if there are no import errors and the project is set to run the APPLICATION class.

Right click on pom.xml and hover over Maven. Make sure it's added as a Maven project. On the right bar of your IDE, click Maven. Under Lifecycle, double click install. Then, under Plugins|spring-boot, double click spring-boot:run to run the project.

Run the application and wait for Java spring to open and start the project. If there are no errors you should be notified on the console that the live server has been initialized properly and the application is now running on http://localhost:8080. If you have another service running on port 8080 refer to the troubleshooting section.

If there are any errors regarding the Java language, check the language level, then try rebuilding the project and running. There may be an error with the method readAllBytes() in Documents class since that is for Java 9. There should be a commented line telling you to comment out that line and uncomment the line below to have it working for Java 8.

##Using the Application To access all the applications when using the coordinator, go to view applications, do not try to access them from the job posting itself. Note: we don't have the coordinator or interviewer company they are associated with when they log in, and so the value for their company is null (which is why accessing from job posting wont work). What I mean by this is that we had forgotten to set the value for the users company when logging in and so it ends up being null. If there were a few lines of code put in the setCurrentUser, then this would have been fixed (we did not notice this before presentation because it looked like it was working). Also, make sure to create/register as a coordinator, then as an interviewer, then as an applicant before doing anything else.

##Troubleshooting If the above installation fails try any of the following

    Reimport the project just like mentioned above
    Right click on the pom.xml file and go maven>Generate Sources and Update folders
    If you have a service running on port 8080 navigate to src>main>resources>application.properties and change the line that says 'spring.port=8080'.

