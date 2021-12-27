Introduction
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

The 3rd party applications that I have implemented in my project are
1.firebase
2. (Spoonacular)
3. Google sign in
4. Camera
5. Google Maps

Firebase
I have implemented a real time dB from firebase that uses authentication for the user to create
user or to loggin. Then the Id from this is being used in the authentication process is stord in a
separate dB called user-food, there is also a food dB that is just a map of all the details stored
for the food item object. So the user-db is used the most as it is to check the credentials of the
user and retrieve the correct data. This is done by referencing the database and having
liveFireBaseUser that retrieves the userid that can be compared and then validated against the data
stored.

Google signIn
This is achieved my implementing this into the grade.build and then creating a onClickListener that
starts up the list of available Google accounts on the phone if there are any. This is convenient as
you're account will be auto generated from existing credentials associated with your Google account.

Spoonaculiar (Free Api)
This is a free api to a certain extent depending on how many calls are.made a day. that I am.using
with the app so the user can retrieve food items from all over the world, this will give the user
the option of filtering through the data to find what they are looking for but also to add this to
their own food log as a food they might.like to try. This then will be stored in the firebase food
dB associated with the user logged in. It will.display images and title and description and cals.
basically everything that our dB stores.

The camera
I decided to implement this feature for the user to be able to capture there own images of food or
whatever as it would be inconvenient to have to open another app to take a photo of food, why not
allow the user to do it in this app also. The image then will be stored just like the image that can
be chosen from existing images.

Google Maps
This is used twice in the project, as it is used to choose the location of where the food was ate or
 where you can purchase the food. This will be stored in the food object that will be stored in the
 firebase dB, there will be a link in the navigation menu where the user can then navigate the map
 fragment where they can view all the place marks they have added or all the place marks everyone
 entered read only though.

 UX/UI Design
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