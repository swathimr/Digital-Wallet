package main.scala.hello.handler
import main.scala.hello.resource._
import java.util.Calendar
import java.util.Date

class BankAccountHandler {
  
def populateBankAccount(bankAccount:BankAccount,userId:String) = {
  
     val mapName = HashMapHandler.BankAccountMap
     val random= (Math.round(Math.random() * 89999) + 10000).toString()
     bankAccount.ba_id_=("b-"+random)
     bankAccount.account_name_=(bankAccount.account_name)
     bankAccount.routing_number_=(bankAccount.routing_number)
     bankAccount.account_number_=(bankAccount.account_number)
     mapName+=(bankAccount.ba_id->bankAccount)
     HashMapHandler.userBnkAcntMap+=(bankAccount.ba_id->userId)
     bankAccount
    }

def getAllBankAccount(userId:String)={
    val localListName = HashMapHandler.getBankAccountList
    localListName.clear()
   for ((k,v) <- HashMapHandler.userBnkAcntMap)
   {
     if(v.contains(userId))
     {
       localListName.add(HashMapHandler.BankAccountMap(k) )
     }
   }
   localListName
   }

}