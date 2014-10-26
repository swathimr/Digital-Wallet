package main.scala.hello.handler
import main.scala.hello.resource._
import java.util.Calendar
import java.util.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.TimeZone

class UserHandler {
  
  def populateUser(user:User) = {
     val today = Calendar.getInstance().getTime()
     val dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
     val formattedDate:String =dateFormat.format(today)
    val created_at = new java.sql.Timestamp(System.currentTimeMillis()).toString();
     val random= (Math.round(Math.random() * 89999) + 10000).toString()
     if(user!= null) {
       user.email_=(user.email)
       user.userId_=("u-"+random)
       user.password_=(user.password)
       user.name_=(user.name)
       user.created_at_=(formattedDate)
       user.updated_at_=(formattedDate)
     } 
     HashMapHandler.userMap+=(user.userId->user)
     user
    }

  
  def updtUser(userId:String,user:User) = {
      val today = Calendar.getInstance().getTime()
      val dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
     val formattedDate:String =dateFormat.format(today)
     
   if(HashMapHandler.userMap.contains(userId))
    {
     val oldUser:User= HashMapHandler.userMap(userId)
     oldUser.email_=(user.email)
     oldUser.password_=(user.password)
     oldUser.name_=(user.name)
     oldUser.updated_at_=(formattedDate)
     HashMapHandler.userMap+=(user.userId->oldUser)
    }
  }

}