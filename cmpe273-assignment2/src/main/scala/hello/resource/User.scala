package main.scala.hello.resource

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotNull

class User{
  
  /*Getters nd setters for userid */
  
    
 @JsonProperty("user_id")
     private var _userId :String = _ 
   
  def userId = _userId 
 
  def userId_= (userId:String) = _userId = userId
 
 /*Getters nd setters for userid */
  @NotNull
  @JsonProperty("email")
   private var _email:String =_ 
   
 // Getter 
 def email = _email
 
 // Setter 
 def email_= (email:String) = _email = email 
 
 
 /*Getters nd setters for userid */
 @NotNull
 @JsonProperty("password")
   private var _password:String =_ 
   
 // Getter 
 def password  = _password 
 
 // Setter 
 def password_= (password:String) = _password = password 
 
 @JsonProperty("name")
 private var _name:String =_ 
   
 // Getter 
 def name  = _name
 
 // Setter 
 def name_= (name:String):Unit = _name= name
 
@JsonProperty("created_at")
private var _created_at:String=_
//Getter
def created_at=_created_at

//setter
def created_at_=(created_at:String):Unit = _created_at=created_at

@JsonProperty("updated_at")
private var _updated_at:String=_

//Getter
def updated_at=_updated_at

//setter
def updated_at_=(updated_at:String):Unit = _updated_at=updated_at


}

