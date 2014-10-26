package main.scala.hello.handler
import main.scala.hello.resource._
import java.util.Calendar
import java.util.Date

class WebLoginHandler {
    val userloginmapName =HashMapHandler.userLoginMap
    def populateWebLogin(webLogin:WebLogin,userId:String) = {
    val mapName = HashMapHandler.webLoginMap
     val random= (Math.round(Math.random() * 89999) + 10000).toString()
     webLogin.login_id_=("l-"+random)
     webLogin.url_=(webLogin.url)
     webLogin.login_=(webLogin.login)
     webLogin.password_=(webLogin.password)
     mapName+=(webLogin.login_id->webLogin)
     userloginmapName+=(webLogin.login_id->userId)
     webLogin
    }

  def getAllWebLogin(userId:String)={
    val localListName = HashMapHandler.getWebLoginList
    localListName.clear()
   for ((k,v) <- userloginmapName)
   {
     if(v.contains(userId))
     {
       localListName.add(HashMapHandler.webLoginMap(k) )
       //arr += cardMap(k) 
     }
   }
   localListName
   }
  
}