 package com.example.myapplication
import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
class FSNI(context: Context) {
    //Abstraction to upload to firebase storage ,database,auth in common sequences.
    //Created by ronald colyar jr.
    //This abstraction will only work if you have database , auth , and storage setup.
    var errortracker = false
    val context = context

    fun ShowToast(message:String){
        Toast.makeText(context ,message, Toast.LENGTH_LONG).show()
    }

    fun databaseupload(databaseuserobject:Any, Url :String){
        val databasereference =FirebaseDatabase.getInstance().reference.child("/Users/${FirebaseAuth.currentUser!!.uid}")
        val Profilepicturereference = FirebaseDatabase.getInstance().reference.child("/Users/${FirebaseAuth.currentUser!!.uid}/ProfilePicture")
        val databaseinit = databasereference.setValue(databaseuserobject)
        databaseinit.addOnSuccessListener{
            Profilepicturereference.addOnSuccessListener{
                ShowToast("User Successfully Created!!")
            }
            Profilepicturereference.addOnFailureListener{
                errortracker=false
            }
        }
        databaseinit.addOnFailureListener{
            errortracker=false
        }
    }

    fun uploadImageAndDownloadUrl(email: String,profileimage:Uri,databaseuserobject:Any){
        val storageinformation = FirebaseStorage.getInstance().getReference().child("${email}/ProfilePicture")
        val imageuploadtask = storageinfo.putFile(profileimage!!)
        imageuploadtask.addOnSuccessListener{
            val urldownload = storageinformation.downloadUrl
            urldownload.addOnSuccessListener{
                databaseupload(databaseuserobject,it.toString())
            }
            urldownload.addOnFailureListener{
                errortracker= true
            }

        }
        imageuploadtask.addOnFailureListener {
            errortracker= true
        }
    }

    fun createuser(email: String, password: String,profileimage:Uri,databaseuserobject:Any){
        val createtask = FirebaseAuth.createUserWithEmailAndPassword(email,password)
        createtask.addOnSuccessListener{
            uploadImageAndDownloadUrl(email,profileimage,databaseuserobject)
        }
        createtask.addOnFailureListener{
            errortracker = true
        }

    }

    fun CreateUserAndUploadProfileImage(profileimage:Uri , databaseuserobject:Any ,email:String , password:String){
        info()
        createuser(email,password,profileimage,databaseuserobject)
        if (errortracker== true){
            ShowToast("Error Creating User!!!")
        }
    }

    fun info(){
        println("This System is Designed for the initial creation of a user.")
        println("This System does 3 different things:")
        println("1.Creates a user inside of firebase auth ")
        println("2.Uploads the user profile Image into firebase storage , using the users unique id number(provided by firebase auth).")
        println("3.Creates the user inside of firebase database  using the users unique id number(provided by firebase auth), with a Url reference to the image stored in storage for future references.")
        println("All 3 of these tasks are triggered by the function: CreateUserAndUploadProfileImage()")
    }
}