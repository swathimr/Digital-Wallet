package main.scala.hello.handler
import main.scala.hello.resource._
import java.util.Calendar
import java.util.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.TimeZone
import com.mongodb.casbah.MongoClient
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.DBObject
import com.mongodb.casbah.commons._
import org.bson.types.ObjectId
import com.mongodb.casbah.commons.Imports.DBObject

class UserHandler {
  
  def populateUser(user:User) = {
    
     val today = Calendar.getInstance().getTime()
     val dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
     val formattedDate:String =dateFormat.format(today)
     val random= (Math.round(Math.random() * 89999) + 10000).toString()
     user.userId_=("u-"+random)
      
      val userObj=MongoDBObject("user_id"->(user.userId),"email"->(user.email),"password"->(user.password),"name"->(user.name),"created_at"->(formattedDate),"updated_at"->(formattedDate))
      HashMapHandler.usrCollctn.save(userObj) 
      val q = MongoDBObject("_id"->0)
      val r = MongoDBObject("user_id"->user.userId)
      println("in here2")
      val printUser = HashMapHandler.usrCollctn.findOne(r,q).get
     //for(doc <- printUser) println( doc ) 
     printUser
    }

  
  def updtUser(userId:String,user:User) = {
    
     val today = Calendar.getInstance().getTime()
     val dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
     val formattedDate:String =dateFormat.format(today)
     
     val dbObject = MongoDBObject("user_id"->userId)
     val createdOn = HashMapHandler.usrCollctn.findOne(dbObject)
   
     if(dbObject!=null)
     {
       val userUpdt= HashMapHandler.usrCollctn.update(dbObject,MongoDBObject("user_id"->userId,"email"->(user.email),"password"->(user.password),"name"->(user.name),"created_at"->createdOn.get.get("created_at"),"updated_at"->(formattedDate)))
     }
     val r = MongoDBObject("_id"->0)
     val updateUser=HashMapHandler.usrCollctn.findOne(dbObject,r).get
     updateUser
  }

}