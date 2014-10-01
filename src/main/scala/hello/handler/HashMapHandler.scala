package main.scala.hello.handler

import scala.collection.mutable.Map
import collection.JavaConverters._

import main.scala.hello.resource._

object HashMapHandler {
val userMap = collection.mutable.HashMap[String,User]()
val cardMap = collection.mutable.HashMap[String,IdCard]()
val webLoginMap = collection.mutable.HashMap[String,WebLogin]()
val BankAccountMap = collection.mutable.HashMap[String,BankAccount]()
val userCardMap = collection.mutable.HashMap[String,String]()
val userLoginMap = collection.mutable.HashMap[String,String]()
val userBnkAcntMap = collection.mutable.HashMap[String,String]()

var getIdCardsList = new java.util.ArrayList[IdCard]()
var getWebLoginList = new java.util.ArrayList[WebLogin]()
var getBankAccountList =  new java.util.ArrayList[BankAccount]()

  }