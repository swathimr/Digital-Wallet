package main.scala.hello.handler
import main.scala.hello.resource._
import java.util.Calendar
import java.util.Date
import org.bson.types.ObjectId
import com.mongodb.casbah.commons.MongoDBObject

class WebLoginHandler {
 
    def populateWebLogin(webLogin:WebLogin,userId:String) = {
      
     val random= (Math.round(Math.random() * 89999) + 10000).toString()
     webLogin.login_id_=("l-"+random)
     val webLoginObj = MongoDBObject("login_id"->webLogin.login_id,"url"->webLogin.url,"login"->webLogin.login,"password"->webLogin.password) 
     HashMapHandler.webLoginCltn.insert(webLoginObj)
     
     
     val userLoginObj = MongoDBObject("_id"->webLoginObj.get("login_id"),"userId"->userId)
     HashMapHandler.usrLoginCltn.insert(userLoginObj)
     
     val r = MongoDBObject("login_id"->webLogin.login_id)
     val q = MongoDBObject("_id"->0)
     val webLoginResponse = HashMapHandler.webLoginCltn.findOne(r,q).get
          
     webLoginResponse
    }

  def getAllWebLogin(userId:String)={
    
    val dbObject1 = MongoDBObject("userId"->userId)
    val q = MongoDBObject.empty
    
    HashMapHandler.getWebLoginList.clear()
    val ignoreId = MongoDBObject("_id"->0)
   for (x <- HashMapHandler.usrLoginCltn.find(q, dbObject1)) 
    {
     val dbObject = MongoDBObject("login_id" ->x.get("_id"))
     HashMapHandler.getWebLoginList.add(HashMapHandler.webLoginCltn.findOne(dbObject,ignoreId).get)
    }
   HashMapHandler.getWebLoginList
   }
  
}