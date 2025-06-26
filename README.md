# Frontline Framework 

![Frontline](/assets/images/frontline.png  "Frontline framework")

[**Gatling FrontLine**](https://gatling.io/gatling-frontline/?utm_term=%2Bgatling&utm_campaign=Brand&utm_source=adwords&utm_medium=ppc&hsa_acc=6274855159&hsa_cam=10875823399&hsa_grp=110701435327&hsa_ad=457802585195&hsa_src=g&hsa_tgt=kwd-300292310068&hsa_kw=%2Bgatling&hsa_mt=b&hsa_net=adwords&hsa_ver=3&gclid=CjwKCAiA8Jf-BRB-EiwAWDtEGsNbfKxnTEOqrw1J5uubPxS0PmL8oR8apvRDtspT6bAzhBJVV1ZXDRoCWZoQAvD_BwE) (Gatling's Enterprise version) helps you manage your load testing strategy at the scale of your entire organization. Run your Gatling's simulations and get access to advanced features: new metrics (TCP connections, bandwidth usage, DNS resolutions, injectors' monitoring, trends), live reporting and clustering (for large-scale load testing campaigns).

### Gatling & IntelliJ IDEA Setup

Our load testing project use java and Maven. Install them follow these steps:

### Java
**Step 1**: Download [Java](https://www.oracle.com/java/technologies/javase-downloads.html#JDK11)\
**Step 2**: Install JDK and set JAVA_HOME in System Variables.\
**Step 3**: Make sure your Java is properly installed and configured. Use java -v to check it out.\
![Java](/assets/images/Java_version.png)

### Maven
**Step 1**: Download [Maven](http://maven.apache.org/).\
**Step 2**: Configure maven using this [guide](https://maven.apache.org/install.html).
* **Step 2.1**: Make sure your Maven is properly installed and configured. Use mvn -v to check it out.

![maven](/assets/images/maven_version.png "Maven version")
#### Intellij IDEA
This is the IDE we'll use for our load project:\
**Step 1**: Download [IntelliJ](https://www.jetbrains.com/es-es/idea/download/#section=windows) Community version and install it following the installer instructions\
**Step 2**: Install [Scala](https://docs.scala-lang.org/) plugin to start working with [Gatling](https://gatling.io/)\
![scala](/assets/images/Scala_plugin.png "Scala Plugin")

### Configuring repository & cloning Load testing Project

Our load testing code lives in GitHub. We need to configure our IDE properly to then clone the code in a local repository, please follow the next steps:

**Step 1**: Create a Git account using this [guide](http://tlvconfluence01:8090/display/IN/InContact+GitHub+Access)\
**Step 2**: Get a personal Access Token to provide access to the load testing repo from IntelliJ using this [guide](https://docs.github.com/en/free-pro-team@latest/github/authenticating-to-github/authorizing-a-personal-access-token-for-use-with-saml-single-sign-on)\
**Step 3**: Make sure [Git](https://git-scm.com/) is installed in your local machine\
**Step 4**: Open IntelliJ IDEA and configure Git / GitHub following the next steps:
* **Step 4.1**: Once IntelliJ is opened, go to **File** > **Settings** > **Version control** > **Git**
* **Step 4.2**: Make sure the Path to Git executable field points to the git.exe file and click on Test button to check is properly installed.

![connect](/assets/images/Connect_git.png "Connect to Git")

* **Step 4.3**: Now go to GitHub and introduce your username and password (which would be personal access token from step 2). Finally click on Test button to check the connection.

![token](/assets/images/Personal_token.png "Personal Token")
* **Step 4.5**: Click on ok button to save config changes\
**Step 5**: Go to VCS > Checkout from Version Control > GitHub and introduce the following values
       Repository: https://github.com/inContact/qa-load-tests
       Parent  Directory: Path where your code will be cloned.

![Clone](/assets/images/Clone_repository.png "Clone repository")

Click on Clone button. Once is completed you'll see a windows popup message saying the project has been checked out. Click on Yes button to open it.

![version](/assets/images/version_Control.png "Versioning Control")

       You should be able to see the project now (You may need to open the project explorer window going to View > Tools Windows > Project)

### Building Gatling project
To build and get the dependencies, use the following steps:

**Step 1**: Open the Load testing project\
**Step 2**: Right click in the project and go to Maven > Reimport.  This will download maven & gatling dependencies\
**Step 3**: In the main menu Go to Build > Build project (or **Ctrl + F9**). The project should be built sucessfully

### How to run

#### Locally

After build the project a list of available Gatling simulations will appear:

![version](/assets/images/simulations.png "Versioning Control")

Select a simulation by typing a number that match with the target simulation.


#### Configure Variables by MVN

* **cluster** -> SC1, SC10, SC11 ,HC4 ,DO66, DO98, ICPune, SO33, SO32, TO31.
* **executionTime** -> Set the duration of Agent working with one contact (loop).
* **rampIBPhone** -> Set Agents number that will work with Inbound Calls.
* **rampOBPhone** ->  Set Agents number that will work with Outbound Calls.
* **rampWorkItem** ->  Set Agents number that will work with WorkItems.
* **rampChat** ->  Set Agents number that will work with Chats.
* **skillDuration** -> Set time the contact will last working with an Agent.
* **rampUpPeriod** -> Set the time to reach the expected amout of Agents.  

To customize the defaults configuration from Frontline select one cluster from above and replace in below mvn commands.

##### RUN InboundPhone  
> mvn gatling:test -Dgatling.simulationClass=frontline.simulations.InContact.CXoneProfile.InboundPhone -Dcluster=**SC1** -DrampUpPeriod=**10** -DrampIBPhone=**100** -DexecutionTime=**60** -DskillDuration=**15**

##### RUN OutboundPhone  
> mvn gatling:test -Dgatling.simulationClass=frontline.simulations.InContact.CXoneProfile.OutboundPhone -D**cluster**=**SC1** -DrampUpPeriod=**10** -DrampOBPhone=**100** -DexecutionTime=**60** 

##### RUN WorkItem  
> mvn gatling:test -Dgatling.simulationClass=frontline.simulations.InContact.CXoneProfile.WorkItem -Dcluster=**SC1** -DrampWorkItem=**100** -DexecutionTime=**60** 

##### RUN Chat  
> mvn gatling:test -Dgatling.simulationClass=frontline.simulations.InContact.CXoneProfile.Chat -Dcluster=**SC1** -DrampChat=**100** -DexecutionTime=**60** 
