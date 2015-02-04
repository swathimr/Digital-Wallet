package main.scala.hello.handler
import main.scala.hello.resource._
import java.util.Calendar
import java.util.Date
import com.mongodb.casbah.commons.MongoDBObject
import org.bson.types.ObjectId
import org.springframework.web.client.RestTemplate
import org.springframework.http.converter.StringHttpMessageConverter
import org.json.JSONObject
import com.mongodb.DBObject
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus

class BankAccountHandler {
  
def populateBankAccount(bankAccount:BankAccount,userId:String) = {
  
  
    val template = new RestTemplate()
    template.getMessageConverters().add(new StringHttpMessageConverter());
    
     val responseEnt=template.getForObject("http://www.routingnumbers.info/api/data.json?rn="+bankAccount.routing_number,classOf[String])
     val test = new JSONObject(responseEnt)
     
     val random= (Math.round(Math.random() * 89999) + 10000).toString()
     bankAccount.ba_id_=("b-"+random)

     if(test.get("code")==200)
     {
       
       val bankAccntObj = MongoDBObject("ba_id"->bankAccount.ba_id,"account_name"->test.get("customer_name"),"routing_number"->bankAccount.routing_number,"account_number"->bankAccount.account_number)
       HashMapHandler.bankAccntCltn.insert(bankAccntObj)
       
       val userBankAcntObj=MongoDBObject("_id"->bankAccntObj.get("ba_id"),"userId"->userId)
       HashMapHandler.userBnkAcntCltn.insert(userBankAcntObj)
       
       val r = MongoDBObject("ba_id"->bankAccount.ba_id)
       val q = MongoDBObject("_id"->0)
       val bankAccountResponse = HashMapHandler.bankAccntCltn.findOne(r,q).get
     
      bankAccountResponse
      
     }
     else
     {
       new ResponseEntity(HttpStatus.BAD_REQUEST)
     }
    
     
    }

def getAllBankAccount(userId:String)={
  
    val dbObject1 = MongoDBObject("userId"->userId)
    val q = MongoDBObject.empty
    HashMapHandler.getBankAccountList.clear()
    val ignoreId = MongoDBObject("_id"->0)
    
   for (x <- HashMapHandler.userBnkAcntCltn.find(q, dbObject1)) 
    {
     val dbObject = MongoDBObject("ba_id" ->x.get("_id"))
     HashMapHandler.getBankAccountList.add(HashMapHandler.bankAccntCltn.findOne(dbObject,ignoreId).get)
    }
   HashMapHandler.getBankAccountList
   }

}