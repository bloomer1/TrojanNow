README

PLEASE read the following below as it not only gives updates but also rules/conventions we will
use throughout the course of this project

1. Integration Details

* Added Files: Account_Info.java, 
               MakeRequest_Util.java, 
               Weather_Service.java, 
               Db_Util.java, 
               Dispatcher.java, 
               Login_Util.java, 
               Post.java, 
               Sensor_Center.java, 
               Signup_Util.java, 
               User_Profile.java (all Rahul)
* Merged User_Posts.java (Richard) + UserPostService.java (Rahul) => User_Posts.java
* Merged User_Intel.java (Richard) + UserProfile.java (Rahul) => User_Info.java
* Merged Sensor.java (Richard) + SensorCenter (Rahul) => Sensor.java
* Replaced Fetcher.java (Richard) with Dispatcher.java (Rahul)
* Replaced Controller.java (Richard) with Db_Util.java (Rahul)
* Replaced User_Mobile.java (Richard) with Weather_Service.java (Rahul)
* Dropped Task.java (Richard) since we will communicate exclusively with Messages
* Dropped User_View.java (Richard) as we plan to use the emulator GUI to display the actual UI
* Dropped Server.java (Richard) since this will be encapsulated in the actual server we use
* Dropped User_Comments (Richard) for now - extra feature
* Dropped CharService.java and ChatUtil.java (Rahul) for now - extra feature
* Dropped User_Interface.java (Richard) since the Post.java (Rahul), Login_Util.java (Rahul), and Signup_Util.java (Rahul) will collectively accomplish what the UI is supposed to do

NOTE: I have added a few comments to some of the code you added. Just FYI

2. Naming of Connectors

Connectors will be named based on the two components which are attached to a single connector. The requesting component (the one who sends the initial message and receives a notification) name's initials will come first, followed by the type of connector (RPC, DATA, ADAPT, etc), and then followed by the notifying component (the one who receives the initial message and sends a notification) name's initials. So for instance, the Login_Util (LU) component is connected to the User Validation (UV) component by the RPC connector LU_RPC_UV

We will add the connectors on a need basis. That is, unless it is deemed that we need two components to communicate in order to get a task done, we will not make any connectors simply based off our previous designs. This way, we don't have redundant or useless connectors hanging around in our project; in contrast, each connector we add will have clearly defined purposes as to what kind of messages it will pass between two components. 

3. Next tasks

a. Figure out how to integrate the GUI widgets with Java listeners or handlers which can be invoked 
by the run() method in the components

b. Make a simple GUI for login, and make some basic calls to component methods

c. Start implementing connectors which will pass data to necessary components.
