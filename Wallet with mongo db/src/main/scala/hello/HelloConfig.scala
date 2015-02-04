package main.scala.hello

import org.springframework.web.bind.annotation.{RequestMapping, RestController}
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import main.scala.hello.resource._
import main.scala.hello.handler._
import org.springframework.http.{ResponseEntity, HttpStatus}
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.http.HttpHeaders
import java.util.Calendar
import java.util.Date
import scala.collection.mutable.Map
import collection.JavaConverters._
import org.springframework.web.client.ResponseExtractor
import main.scala.hello.resource.BankAccount
import org.springframework.web.filter.ShallowEtagHeaderFilter
import org.springframework.web.bind.annotation._
import org.springframework.http.HttpRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.http.HttpEntity
import javax.validation.Valid
import java.text.SimpleDateFormat
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.MongoCollection
import com.mongodb.DBObject
import com.mongodb.casbah.commons.MongoDBObject
import org.bson.types.ObjectId
import org.springframework.http.MediaType
import java.util.Arrays
import org.springframework.web.client.RestTemplate
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.HttpMethod



/**
 * This config class will trigger Spring @annotation scanning and auto configure Spring context.
 *
 * @author swathi.mr
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@RestController
@EnableAutoConfiguration
class HelloConfig {
  
  /*sample to print helloworld*/
	@RequestMapping(value=Array("/"))
     def test():String = {
	 "HelloWorld"
    }
  
   
  @RequestMapping(value=Array("/api/v1/users/{userId}"),method = Array(RequestMethod.GET))
     def getUser(@PathVariable userId:String,req:WebRequest) = {
        
     val dbObject = MongoDBObject("user_id" ->userId)
     val checkIfUserExists = HashMapHandler.usrCollctn.findOne(dbObject).get
     val q = MongoDBObject("_id"->0)
     
     /*println("printing with dbobject updated time::"+dbObject.get("_id"))
     println("iam printing to c wats stored in db object"+dbObject)
     println("yeah in a get of user"+HashMapHandler.usrCollctn.findOne(dbObject).get)*/
   
  if(checkIfUserExists!=null)
      {
        val test=HashMapHandler.usrCollctn.findOne(dbObject).get("updated_at")
        val etagHashed = "\"" + 1 + "-" + test.hashCode() + "\"";
        val tests=req.getHeader("If-None-Match")
        val header = new HttpHeaders()
        header.set("ETag",etagHashed)
        
      if(tests!=null && tests.equals(etagHashed))
       {
         new ResponseEntity(HttpStatus.NOT_MODIFIED)
       }
       else
         { 
           new ResponseEntity(HashMapHandler.usrCollctn.findOne(dbObject,q).get,header,HttpStatus.OK)
         }
    }
   else
     {
       "You are not a user"
     }
   }
  
   @RequestMapping(value=Array("/api/v1/users"),method = Array(RequestMethod.POST))
   @ResponseStatus(value = HttpStatus.CREATED)
     def createUser(@Valid @RequestBody user:User) = {
     
     val userHndlr = new UserHandler()
     userHndlr.populateUser(user)

    }
  
  @RequestMapping(value=Array("/api/v1/users/{userId}"),method = Array(RequestMethod.PUT))
  @ResponseStatus(value = HttpStatus.CREATED)
  def updateUser(@PathVariable userId:String,@Valid @RequestBody user:User)= {
    
    val dbObject = MongoDBObject("user_id"->userId)
    val checkIfUserExists = HashMapHandler.usrCollctn.findOne(dbObject).get
    if(checkIfUserExists!=null)
    {
    val userHndlr = new UserHandler()
    userHndlr.updtUser(userId,user)
    }
    else
    {
      "Your user account hasnot been created yet"
    }
    }
  
  @RequestMapping(value=Array("/api/v1/users/{userId}/idcards"),method = Array(RequestMethod.POST))
  @ResponseStatus(value = HttpStatus.CREATED)
     def createIdCard(@Valid @RequestBody idcard:IdCard,@PathVariable userId:String)= {
    
    
    val dbObject = MongoDBObject("user_id" ->userId)
    val checkIfUserExists = HashMapHandler.usrCollctn.findOne(dbObject).get
    if(checkIfUserExists!=null)
    {
    val idCardHndlr = new IdCardHandler()
     idCardHndlr.populateIdcard(idcard,userId)
    }
     else
     {
       "Your user account has nt been created yet"
     }
    }
  
  @RequestMapping(value=Array("/api/v1/users/{userId}/idcards"),method = Array(RequestMethod.GET))
  @ResponseStatus(value=HttpStatus.OK)
     def getIdCard(@PathVariable userId:String) = {
      
   val dbObject = MongoDBObject("user_id"->userId)
   val checkIfUserExists = HashMapHandler.usrCollctn.findOne(dbObject).get
    if(checkIfUserExists!=null && checkIfUserExists!="None"){
      
     val idCardHndlr = new IdCardHandler()
     idCardHndlr.getAllIdCards(userId) 
   }
    else
   {
     //"You are not a user"
      HttpStatus.NOT_FOUND
   }  
   }
  
  
  @RequestMapping(value=Array("/api/v1/users/{userId}/idcards/{cardId}"),method = Array(RequestMethod.DELETE))
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
     def deleteIdCard(@PathVariable userId:String,@PathVariable cardId:String) = {
    
    val removeUserCardMap= MongoDBObject("_id" ->cardId)
    val removeCardMap = MongoDBObject("card_id"->cardId)
     val checkIfCardExists = HashMapHandler.idCardCltn.findOne(removeCardMap).get
    if(checkIfCardExists!=null)
    {
     HashMapHandler.usrCardCltn.remove(removeUserCardMap)
     HashMapHandler.idCardCltn.remove(removeCardMap)
     new ResponseEntity(HttpStatus.NO_CONTENT)
    }
   else {
      "You are not a user"
    }
   }
  
  @RequestMapping(value=Array("/api/v1/users/{userId}/weblogins"),method = Array(RequestMethod.POST))
  @ResponseStatus(value = HttpStatus.CREATED)
     def createWebLogin(@Valid @RequestBody webLogin:WebLogin,@PathVariable userId:String)= {
    
    val dbObject = MongoDBObject("user_id" ->userId)
     val checkIfUserExists = HashMapHandler.usrCollctn.findOne(dbObject).get
     if(checkIfUserExists!=null)
     {
     val webLoginHndlr = new WebLoginHandler()
     webLoginHndlr.populateWebLogin(webLogin, userId)
     }
     else
     {
       "Your user account has nt been created yet"
     }
    }
  
  
  @RequestMapping(value=Array("/api/v1/users/{userId}/weblogins"),method = Array(RequestMethod.GET))
  @ResponseStatus(value=HttpStatus.OK)
     def getWebLogin(@PathVariable userId:String) = {
    
    val dbObject1 = MongoDBObject("user_id"->userId)
   val checkIfUserExists = HashMapHandler.usrCollctn.findOne(dbObject1).get
   if(checkIfUserExists!=null)
    {
      val webLoginHndlr = new WebLoginHandler()
     webLoginHndlr.getAllWebLogin(userId)
     }
   else
   {
     "You are not a user"
   }
   }
  
  @RequestMapping(value=Array("/api/v1/users/{userId}/weblogins/{login_id}"),method = Array(RequestMethod.DELETE))
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
     def deleteWebLogin(@PathVariable userId:String,@PathVariable login_id:String) = {
    
    val removeWebLogin = MongoDBObject("login_id"->login_id)
    val removeWebUserMap = MongoDBObject("_id"->login_id)
    val checkIfCardExists = HashMapHandler.webLoginCltn.findOne(removeWebLogin).get
    
    if(checkIfCardExists!=null)
    {
     HashMapHandler.usrLoginCltn.remove(removeWebUserMap)
     HashMapHandler.webLoginCltn.remove(removeWebLogin)
     new ResponseEntity(HttpStatus.NO_CONTENT)
    }
   else {
      "You are not a user"
    }
   }
  
   @RequestMapping(value=Array("/api/v1/users/{userId}/bankaccounts"),method = Array(RequestMethod.POST))
    @ResponseStatus(value = HttpStatus.CREATED)
     def createBankAccount(@Valid @RequestBody bankAccount:BankAccount,@PathVariable userId:String)= {
     
    val dbObject = MongoDBObject("user_id" ->userId)
    val checkIfUserExists = HashMapHandler.usrCollctn.findOne(dbObject).get

     if(checkIfUserExists!=null)
     {
     val bnkAcntHndlr = new BankAccountHandler()
     bnkAcntHndlr.populateBankAccount(bankAccount, userId)
     }
     else
     {
       "Your user account has nt been created yet"
     }
    }
   
   
   @RequestMapping(value=Array("/api/v1/users/{userId}/bankaccounts"),method = Array(RequestMethod.GET))
   @ResponseStatus(value = HttpStatus.OK)
     def getBankAccount(@PathVariable userId:String) = {
     
     val dbObject1 = MongoDBObject("user_id"->userId)
    val checkifUserExists = HashMapHandler.usrCollctn.findOne(dbObject1).get
     
   if(checkifUserExists!=null)
    {
      val bnkAcntHndlr = new BankAccountHandler()
     bnkAcntHndlr.getAllBankAccount(userId)    
     }
   else
   {
     "You are not a user"
   }
     }
  
  @RequestMapping(value=Array("/api/v1/users/{userId}/bankaccounts/{ba_id}"),method = Array(RequestMethod.DELETE))
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
     def deleteBankAccount(@PathVariable userId:String,@PathVariable ba_id:String) = {
    
    val dbObject = MongoDBObject("ba_id"->ba_id)
    val usrBankMap = MongoDBObject("_id"->ba_id)
    val checkifBankAcntExits =HashMapHandler.bankAccntCltn.findOne(dbObject).get
    
   if(checkifBankAcntExits!=null)
    {
      HashMapHandler.bankAccntCltn.remove(dbObject)
      HashMapHandler.userBnkAcntCltn.remove(usrBankMap)
      new ResponseEntity(HttpStatus.NO_CONTENT)
    }
   else
   {
     "You are not a user"
   }
   }
  
 }