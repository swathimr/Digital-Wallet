package main.scala.hello.handler

import main.scala.hello.resource._
import java.util.Calendar
import java.util.Date

class IdCardHandler {
  
  def populateIdcard(idcard:IdCard,userId:String) = {
     val today = Calendar.getInstance().getTime().toString()
     val random= (Math.round(Math.random() * 89999) + 10000).toString()
     idcard.cardId_=("C-"+random)
     idcard.card_name_=(idcard.card_name)
     idcard.card_number_=(idcard.card_number)
     idcard.expiration_date_=(idcard.expiration_date)
     HashMapHandler.cardMap+=(idcard.cardId->idcard)
     HashMapHandler.userCardMap+=(idcard.cardId->userId)
     idcard
    }
  
  def getAllIdCards(userId:String)={
  HashMapHandler.getIdCardsList.clear()
   for ((k,v) <- HashMapHandler.userCardMap)
   {
     if(v.contains(userId))
     {
       HashMapHandler.getIdCardsList.add(HashMapHandler.cardMap(k) )
       //arr += cardMap(k) 
     }
   }
   HashMapHandler.getIdCardsList
   }
  
}