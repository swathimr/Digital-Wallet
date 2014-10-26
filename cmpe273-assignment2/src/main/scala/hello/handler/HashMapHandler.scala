package main.scala.hello.handler

import scala.collection.mutable.Map
import collection.JavaConverters._
import main.scala.hello.resource._
import com.mongodb.casbah.MongoClient
import com.mongodb.DBObject
import com.mongodb.casbah.MongoURI
import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.MongoCollectionBase
import com.mongodb.casbah.MongoClientURI

object HashMapHandler {
val userMap = collection.mutable.HashMap[String,User]()
val cardMap = collection.mutable.HashMap[String,IdCard]()
val webLoginMap = collection.mutable.HashMap[String,WebLogin]()
val BankAccountMap = collection.mutable.HashMap[String,BankAccount]()
val userCardMap = collection.mutable.HashMap[String,String]()
val userLoginMap = collection.mutable.HashMap[String,String]()
val userBnkAcntMap = collection.mutable.HashMap[String,String]()

var getIdCardsList = new java.util.ArrayList[DBObject]()
var getWebLoginList = new java.util.ArrayList[DBObject]()
var getBankAccountList =  new java.util.ArrayList[DBObject]()

val uri = MongoClientURI("mongodb://cmpe273:cmpe273@ds045089.mongolab.com:45089/wallet")
val mongoClient = MongoClient(uri)
val dbName = mongoClient("wallet")
dbName.authenticate("cmpe273","cmpe273")

 //val mongoClient = MongoClient("localhost", 27017)
    // val dbName = mongoClient("test")
     val usrCollctn= dbName("User")
     val idCardCltn = dbName("IdCard")
     val usrCardCltn = dbName("UserCardMap")
     val webLoginCltn = dbName("WebLogin")
     val usrLoginCltn = dbName("UserWebLoginMap")
     val bankAccntCltn = dbName("BankAccount")
     val userBnkAcntCltn = dbName("UserAccountMap")

  }