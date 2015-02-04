package main.scala.hello

import org.springframework.web.bind.annotation.{RequestMapping, RestController}
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RequestBody
import main.scala.hello.resource.User
import main.scala.hello.resource.WebLogin
import main.scala.hello.handler._
import org.springframework.http.{ResponseEntity, HttpStatus}
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.http.HttpHeaders
import java.util.Calendar
import java.util.Date
import scala.collection.mutable.Map
import collection.JavaConverters._
import main.scala.hello.resource.IdCard
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.client.ResponseExtractor
import main.scala.hello.resource.BankAccount
import org.springframework.web.filter.ShallowEtagHeaderFilter
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.http.HttpRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.http.HttpEntity
import javax.validation.Valid
import java.text.SimpleDateFormat


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
  
  
  /* Method to get the users based on userid
   * Also implemented conditional get 
   * Will retrieve the user only if the values have been updated
  */
  @RequestMapping(value=Array("/api/v1/users/{userId}"),method = Array(RequestMethod.GET))
     def getUser(@PathVariable userId:String,req:WebRequest) = {
        
   if(HashMapHandler.userMap.contains(userId))
    {
     val test:User=HashMapHandler.userMap(userId)
    val etagHashed = "\"" + 1 + "-" + test.updated_at.hashCode() + "\"";
    val tests=req.getHeader("If-None-Match")
    val header = new HttpHeaders()
    header.set("ETag",etagHashed)
    
        
    if(tests!=null && tests.equals(etagHashed))
     {
       new ResponseEntity(HttpStatus.NOT_MODIFIED)
     }
     else
     {
       new ResponseEntity(HashMapHandler.userMap(userId),header,HttpStatus.OK)
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
     user
    }
  
  @RequestMapping(value=Array("/api/v1/users/{userId}"),method = Array(RequestMethod.PUT))
  @ResponseStatus(value = HttpStatus.CREATED)
  def updateUser(@PathVariable userId:String,@Valid @RequestBody user:User)= {
    
    val userHndlr = new UserHandler()
    userHndlr.updtUser(userId,user)
    HashMapHandler.userMap(userId)
    }
  
  @RequestMapping(value=Array("/api/v1/users/{userId}/idcards"),method = Array(RequestMethod.POST))
  @ResponseStatus(value = HttpStatus.CREATED)
     def createIdCard(@Valid @RequestBody idcard:IdCard,@PathVariable userId:String)= {
      if(HashMapHandler.userMap.contains(userId))
     {
     val idCardHndlr = new IdCardHandler()
     idCardHndlr.populateIdcard(idcard, userId)
     idcard
     }
     else
     {
       "Your user account has nt been created yet"
     }
    }
  
  @RequestMapping(value=Array("/api/v1/users/{userId}/idcards"),method = Array(RequestMethod.GET))
  @ResponseStatus(value=HttpStatus.OK)
     def getIdCard(@PathVariable userId:String) = {
   if(HashMapHandler.userMap.contains(userId))
    {
      val idCardHndlr = new IdCardHandler()
     idCardHndlr.getAllIdCards(userId)
     HashMapHandler.getIdCardsList
     }
   else
   {
     "You are not a user"
   }
   }
  
  
  @RequestMapping(value=Array("/api/v1/users/{userId}/idcards/{cardId}"),method = Array(RequestMethod.DELETE))
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
     def deleteIdCard(@PathVariable userId:String,@PathVariable cardId:String) = {
    
   if(HashMapHandler.cardMap.contains(cardId))
    {
      HashMapHandler.cardMap.remove(cardId)
      HashMapHandler.userCardMap.remove(cardId)
      new ResponseEntity(HttpStatus.NO_CONTENT)
    }
   else
   {
     "You are not a user"
   }
   }
  
  @RequestMapping(value=Array("/api/v1/users/{userId}/weblogins"),method = Array(RequestMethod.POST))
  @ResponseStatus(value = HttpStatus.CREATED)
     def createWebLogin(@Valid @RequestBody webLogin:WebLogin,@PathVariable userId:String)= {
     if(HashMapHandler.userMap.contains(userId))
     {
     val webLoginHndlr = new WebLoginHandler()
     webLoginHndlr.populateWebLogin(webLogin, userId)
     webLogin
     }
     else
     {
       "Your user account has nt been created yet"
     }
    }
  
  
  @RequestMapping(value=Array("/api/v1/users/{userId}/weblogins"),method = Array(RequestMethod.GET))
  @ResponseStatus(value=HttpStatus.OK)
     def getWebLogin(@PathVariable userId:String) = {
    
   if(HashMapHandler.userMap.contains(userId))
    {
      val webLoginHndlr = new WebLoginHandler()
     webLoginHndlr.getAllWebLogin(userId)
     HashMapHandler.getWebLoginList
     }
   else
   {
     "You are not a user"
   }
   }
  
  @RequestMapping(value=Array("/api/v1/users/{userId}/weblogins/{login_id}"),method = Array(RequestMethod.DELETE))
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
     def deleteWebLogin(@PathVariable userId:String,@PathVariable login_id:String) = {
    
   if(HashMapHandler.webLoginMap.contains(login_id))
    {
      HashMapHandler.webLoginMap.remove(login_id)
      HashMapHandler.userLoginMap.remove(login_id)
      new ResponseEntity(HttpStatus.NO_CONTENT)
    }
   else
   {
     "You are not a user"
   }
   }
  
   @RequestMapping(value=Array("/api/v1/users/{userId}/bankaccounts"),method = Array(RequestMethod.POST))
    @ResponseStatus(value = HttpStatus.CREATED)
     def createBankAccount(@Valid @RequestBody bankAccount:BankAccount,@PathVariable userId:String)= {
     if(HashMapHandler.userMap.contains(userId))
     {
     val bnkAcntHndlr = new BankAccountHandler()
     bnkAcntHndlr.populateBankAccount(bankAccount, userId)
     bankAccount
     }
     else
     {
       "Your user account has nt been created yet"
     }
    }
   
   
   @RequestMapping(value=Array("/api/v1/users/{userId}/bankaccounts"),method = Array(RequestMethod.GET))
   @ResponseStatus(value = HttpStatus.OK)
     def getBankAccount(@PathVariable userId:String) = {
    
   if(HashMapHandler.userMap.contains(userId))
    {
      val bnkAcntHndlr = new BankAccountHandler()
     bnkAcntHndlr.getAllBankAccount(userId)
     HashMapHandler.getBankAccountList
     }
   else
   {
     "You are not a user"
   }
   }
  
  @RequestMapping(value=Array("/api/v1/users/{userId}/bankaccounts/{ba_id}"),method = Array(RequestMethod.DELETE))
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
     def deleteBankAccount(@PathVariable userId:String,@PathVariable ba_id:String) = {
    
   if(HashMapHandler.BankAccountMap.contains(ba_id))
    {
      HashMapHandler.BankAccountMap.remove(ba_id)
      HashMapHandler.userBnkAcntMap.remove(ba_id)
      new ResponseEntity(HttpStatus.NO_CONTENT)
    }
   else
   {
     "You are not a user"
   }
   }
 }