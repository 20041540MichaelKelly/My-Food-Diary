Introduction <br>
The app that I have created is based on a log for food, you can create your own log entries to
monitor intake, you can edit or delete the food item in the list.
When you create a food item you can add title, you can add a description, location as to where the
food was consumed or purchased and also a image that can be chosen
from your images or a new feature I implemented the ability to take a picture and use that image.
All these will be displayed in the list, this list will be populated
by the user logged in or a switch can be turned on that allows users to only view other peoples
entries because at the end of the day that is its purpose to gain ideas
and knowledge on food. Also there is additional links in the navigation that will bring the user to
vast array of food items from all over the world. The other link is
the map fragment that will display all locations added with food details.

The 3rd party applications that I have implemented in my project are <br>
1.firebase <br>
2. (Spoonacular) <br>
3. Google sign in <br>
4. Camera <br>
5. Google Maps<br>

Firebase<br>
I have implemented a real time dB from firebase that uses authentication for the user to create
user or to loggin. Then the Id from this is being used in the authentication process is stord in a
separate dB called user-food, there is also a food dB that is just a map of all the details stored
for the food item object. So the user-db is used the most as it is to check the credentials of the
user and retrieve the correct data. This is done by referencing the database and having
liveFireBaseUser that retrieves the userid that can be compared and then validated against the data
stored.

Google signIn<br>
This is achieved my implementing this into the grade.build and then creating a onClickListener that
starts up the list of available Google accounts on the phone if there are any. This is convenient as
you're account will be auto generated from existing credentials associated with your Google account.

Spoonaculiar (Free Api)<br>
This is a free api to a certain extent depending on how many calls are.made a day. that I am.using
with the app so the user can retrieve food items from all over the world, this will give the user
the option of filtering through the data to find what they are looking for but also to add this to
their own food log as a food they might.like to try. This then will be stored in the firebase food
dB associated with the user logged in. It will.display images and title and description and cals.
basically everything that our dB stores.

The camera<br>
I decided to implement this feature for the user to be able to capture there own images of food or
whatever as it would be inconvenient to have to open another app to take a photo of food, why not
allow the user to do it in this app also. The image then will be stored just like the image that can
be chosen from existing images.

Google Maps<br>
This is used twice in the project, as it is used to choose the location of where the food was ate or
 where you can purchase the food. This will be stored in the food object that will be stored in the
 firebase dB, there will be a link in the navigation menu where the user can then navigate the map
 fragment where they can view all the place marks they have added or all the place marks everyone
 entered read only though.

 UX/UI Design<br>
 The user experience and design I went with was a simple design and intuitive for the user with self
 explanatory navigation, if app is downloaded on the phone first time it will be prompted to sign up
 by manual or Google which is in line with other modern applications, ease of sign up and login. I
 tried to base the whole.project on ease of use. The less work the user has to do to accomplish what
  they need the better,  the food list will load first when signed in as it is nice to review before
  adding anything extra. It is created with a recycler view that is fed from the firebase dB. Then
  the user can add a foodItem, now this is where I copied amazon's way to be honest. I stead of a
  scroll view to scroll up and see buttons, I took the scroll view out as They obviously know what
  works. There nothing fancy about the map activity that adds the location of food. It is just the
  activity from first project but is re wrote so a fragment can receive the parcelble of location.
  You have the option of capturing an image or choose an image which is convienet, it is created by
  using fragments as this is more modular.


Fragments<br>
The first version of this project was created using activities as the framework but in the second part being this project I have created It on Fragments. Fragments are a convenient way of having multiple activities being controlled from the 1 activity. It makes the application more modular. The process in which these were implemented was by following the labs but then turning to my already implemented activities and then converting them freestyle. The Fragments that I use are for MyFoodDiary page, MyFoodList page, Map page, QR Scanner and Food Ideas page.

Nav Bar<br>
The Nav Bar contains multiple options for the user and it has its own style in the xml layout but also its own menu layout. This in turn is called from the mothership being the Home Activity. All fragments are referenced in the main_navigation menu layout and from here they reference their own individual xml layout files. So no matter where you are you can bring up the navigation drawer from any of the fragments mentioned above.

My Food List<br>
This is the landing page for the app after the splash screen, This will display a list of food items that can be edited or deleted and/or the list can be added to. On this page you can navigate to anywhere else in the application, you have quick access to My Food Diary from the Floating action Button. From the Nav Drawer you have access to the MyFoodDiary page, MyFoodList page, Map page, QR Scanner and world foods page. Also there is the option to sign out on the nav drawer. My food List Can have items manually created and added to it or the user can filter through world foods and add a food Item from that page.

My Food Diary<br>
On this page the user can create a food Item, they enter title and description through edit texts. Simple enough and then users have the choice of selecting an existing picture in the gallery or a new feature that I included is to take their own picture of choice and this then will be stored in firebase DB. Then the user can add location through the Map Activity. This indeed is the activity, I didn't do too much additional work on this page as I wanted to implement other features as to updating stuff that worked well. So on this map Activity, your location is automatically tracked on the map but the user can also change the location if they wish, if they remember a location of importance to the foodItem i.e a shop that it was purchased from. So then add the food and it is stored in the Firebase Real Time DB.

Food Ideas<br>
This page is using the Spoonacular API. It allows a certain amount of requests a day on the free plan. I found this api had so many options that were of use for this project. I went with Retrofit for my own application, there was some tweaking on my part though to implement this with spoonacular. I had to research in how to implement headers through Retrofit, I applied it in the client the api key and header name, So then when I'm creating api calls I didn't have to apply these over every query to the api. I have a filter option on this page, but I had asked a few people in my circle what they think, a filter with many options or just the die hard stuff of use like what food ideas under a certain amount of calories and how many options you want returned from the api.

Testing<br>
A way That I tested my app and got user feedback was to hand my phone around to my family and friends and really use it and test it for me and to give me their feedback. Any feedback I got that was constructive that would make this app function or be more intuitive or user friendly then I implemented it. A clear example of this would be implemented the new feature of taking a photo, This allows the user to take a photo through the app and logging without having to go to camera take a picture and then go back to my food diary and then choose the image, the effort and time consumption of this process,

QR Scanner<br>
This feature I wanted to create something a little different that can be of use, especially in these covid vaccination certs times. It was actually from this that I came up with this idea. I have four QR examples I created that the user can test that are attached to the README.md. So the user chooses the Scanner from the nav Drawer and then the user scans a QR Code that feeds back the information it contains into a user friendly format for the user. Once it has been loaded then the user can add it to their food list. This is a nifty little feature that I really enjoyed implementing, the implementation in the gradle was the ZXing library.

![image](https://user-images.githubusercontent.com/50295964/147794163-1ae55476-c840-4d16-b56d-78ab84268088.png)
<br><br>
![image](https://user-images.githubusercontent.com/50295964/147794191-2ff01a68-3838-4c3c-836b-4282fd25ba07.png)
<br><br>
![image](https://user-images.githubusercontent.com/50295964/147794221-d28e51b8-2d4f-4f6a-b85b-a9605ce4ac0a.png)

Flow of project<br>
A splash screen shows and The user creates or sign up and then is brought automatically after credentials are checked to the my food list. The user then can click the FAB button and be brought automatically to the my food diary creation of the food object. Other options include hitting the Nav bar and navigating anywhere like the food idea fragment, where the user can set the max amount of calories they want to find and how many items they want returned, here they can click on the food item and it will be added to their own private my food list. Then they can go back to their list and edit or delete. The user can click on maps and see their locations where the food was logged, and toggle the switch to see everyones. This feature also exists in my food list. Another cool feature is the barcode/QR code scanner that allows the user to scan codes and add to their food list. It is all built around the food list. You can also sign out from the navbar that will bring you back to the create/login page.

MVVM<br>
I used this model in the framework as I thought it would be beneficial to the purpose of being able to click on the individual food item and pass the TIMESTAMP (because firebase uses a String as its id that starts with like -MS121hdj) The time stamp is a long that can be used as ID as it is unique. This is definitely best practice when dealing in real time with firebase db. I created multiple viewModels with different purposes. The most used viewModel is MyFoodListViewModel, I re-used this method to gather the list of food items as to be DRY(don't repeat yourself) I would reference this in what ever fragment I am using and loop through the observable list to withdraw the items or specific item required. every fragment I am using and loop through the observable list to withdraw the items or specific item required. I would create fun methods then that would communicate ith with the FirebaseManager(where it directly collected data requested) <- only returning Unit form, which is the equivalent of void in java so it returns nothing just does its computation.

References <br>
reader.tutors.dev. (n.d.). Tutors. [online] Available at: https://reader.tutors.dev/#/course/wit-hdip-comp-sci-2020-mobile-app-dev.netlify.app [Accessed 30 Dec. 2021].

Google Developers. (n.d.). Scan Barcodes with ML Kit on Android. [online] Available at: https://developers.google.com/ml-kit/vision/barcode-scanning/android [Accessed 30 Dec. 2021].

GitHub. (n.d.). location-samples/BasicLocationKotlin at main · android/location-samples. [online] Available at: https://github.com/android/location-samples/tree/main/BasicLocationKotlin [Accessed 30 Dec. 2021].

www.programcreek.com. (n.d.). Java Code Examples for com.google.zxing.client.android.Intents. [online] Available at: https://www.programcreek.com/java-api-examples/?api=com.google.zxing.client.android.Intents [Accessed 30 Dec. 2021].

Android Developers. (n.d.). Kotlin and Android. [online] Available at: https://developer.android.com/kotlin?hl=en [Accessed 30 Dec. 2021].
