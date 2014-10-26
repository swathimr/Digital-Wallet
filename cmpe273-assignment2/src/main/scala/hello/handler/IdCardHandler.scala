package main.scala.hello.handler

import main.scala.hello.resource._
import java.util.Calendar
import java.util.Date
import com.mongodb.casbah.commons.MongoDBObject
import org.bson.types.ObjectId

class IdCardHandler {
  
  def populateIdcard(idcard:IdCard,userId:String) = {
     val random= (Math.round(Math.random() * 89999) + 10000).toString()
     
     idcard.cardId_=("c-"+random)
     val IdCardObj=MongoDBObject("card_id"->idcard.cardId,"card_name"->idcard.card_name,"card_number"->idcard.card_number,"expiration_date"->idcard.expiration_date)
     HashMapHandler.idCardCltn.insert(IdCardObj)
         
     val userCardObj=MongoDBObject("_id"->IdCardObj.get("card_id"),"userId"->userId)
     HashMapHandler.usrCardCltn.insert(userCardObj)
     println("Mapped user values are:::"+ HashMapHandler.usrCardCltn.findOne(userCardObj).get.toString())
     
     val r = MongoDBObject("card_id"->idcard.cardId)
     val q = MongoDBObject("_id"->0)
     val cardResponse = HashMapHandler.idCardCltn.findOne(r,q).get
     
     cardResponse
    }
  
  def getAllIdCards(userId:String)={
    
    val dbObject1 = MongoDBObject("userId"->userId)
    val q = MongoDBObject.empty
 
    HashMapHandler.getIdCardsList.clear()
    val ignoreId = MongoDBObject("_id"->0)
    val x = null
    for (x <- HashMapHandler.usrCardCltn.find(q, dbObject1)) 
    {
     val dbObject = MongoDBObject("card_id" ->x.get("_id"))
     HashMapHandler.getIdCardsList.add(HashMapHandler.idCardCltn.findOne(dbObject,ignoreId).get)
    }
    
    HashMapHandler.getIdCardsList
    }

}