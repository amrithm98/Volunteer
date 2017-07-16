# Volunteers
Event Management Application for IEEE madC 2017. Backend done in node.js . Uses firebase for authentication,storage,real-time database,notifications,app indexing,testing,crash detection,remote config,ui and analytics

Volunteers
V-4-Volunteers is an android application that helps volunteers across IEEE groups to actively organize and manage events. 
The application will have a user-friendly interface where the event organizer of a student branch or community can add an event. 
Once an event is added, several subsections will be generated for each event which includes various areas of interest when a particular event is conducted. Usually , when an event is conducted , there will be teams for registration, food, venue, sessions,sponsorship etc and there will be one or more persons who would take the overall leadership role. 
The leader can add the volunteers to individual groups.
The android application have its backend done in Node.js.
Once the leader adds the volunteers to the groups , he can view the tasks assigned to each individual thereby getting instantaneous details of people working in a particular team and to get a control over the activities happening in that particular team
Notifications will be generated whenever needed so as to remind the volunteers. The entire process is transparent and each volunteer can see what happens in the administration process. 
Entire details will be stored in a server database. 
The application will have a forum architecture and has  group chat features for the users.
The application is connected to firebase which is a real-time database. 
Authentication is done using firebase which ensures security using tokens.
Leader can send notifications to the users as and when required. 
The Forum has an online interface as well. The same forum which is visible in the application is available online. So to get the updates, it’s not necessary that one person has the app.
If a user subscribes to the notifications in the  online chat application , he will get notifications even the browser is closed. 
Online Chat URL is : https://v-4-volunteers.firebaseapp.com

Working
A User installs the app and he will be taken to the login Screen
At the login Screen , the user clicks the G+ button and logs in with his Google Account. 
After Login, the user will be taken to the register page where the user gives his mobile number and college name. If the college name is not in the auto-complete list, user can add the college from the same page and then register his account.
When the user logs in after registering, he will be taken to the events page. If the user has events, it will be displayed, Otherwise he will be taken to the new Event Page. 
In the new event page, user can add an event along with the details. 
In the main Activity of the application , there is a navigation drawing listing few options.
On Clicking the Feed option, the user can get the news-feed of an event.
On Clicking the Events Page, the user can get the list of the events he is involved in.
On Clicking the Forum Page, User will the taken to the group chat page. 
On Clicking the Manage Team Page, the user will be taken to a page which contains a list of teams for an event which are the Accomodation,Food and Venue,Publicity,Sessions,Sponsorship and Registration teams, 
On Clicking any of the team, user will be given a list of volunteers who are in the particular team of the respective event.  
User can view the task assigned to each volunteer and mark them as complete.
On Clicking the Fab Button on the main Page, user can Add an event. Post a news feed, and Add a volunteer to a team. 
Also there is an Option to send notification to all other users. 
So these are the overall features, We are still working on it to add more features. 
Here is the github link of the app : https://github.com/amrithm98/Volunteer
Here is the github link of the backend Done in Node.js:  https://github.com/amrithm98/Volunteers-Backend
Here is the link of the online chat :  https://v-4-volunteers.firebaseapp.com
Youtube Link of The Video :https://www.youtube.com/watch?v=3b7hEDdxk2A



