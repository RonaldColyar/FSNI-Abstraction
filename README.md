# FSNI-Abstraction

A tool to assist android developers with the creation of a "common user" across Firebase Database , Firebase Authentication and Firebase Storage.

# Usage:

- By calling one function :`CreateUserAndUploadProfileImage(profileimage:Uri , databaseuserobject:Any ,email:String , password:String)` FSNI will:
   1. Create a user of your choice(any passed in object) in Firebase Database.
   2. Create the user in Firebase Authentication.
   3. Store the user's profile picture `profileimage:Uri` under unique path (relative to the user's information) in Firebase Storage.
   
# What are the paths that the users are stored under?
- For Firebase Storage(storing profile image) :  
     Actual Code Reference: `/Users/${FirebaseAuth.currentUser!!.uid}/ProfilePicture`
- For Firebase Database:
     Actual Code Reference: `/Users/${FirebaseAuth.currentUser!!.uid}`
     
# Please Note:
  You would need to configure Firebase [Storage](https://firebase.google.com/docs/storage/android/start), 
  [Authentication](https://firebase.google.com/docs/auth/android/start) and [Database](https://firebase.google.com/docs/database/android/start).
  
  
   
