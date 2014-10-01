package main.scala.hello.resource

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotNull

class WebLogin {
  
   @JsonProperty("login_id")
   private var _login_id :String = _
   
   def login_id = _login_id 
 
  def login_id_= (login_id:String) = _login_id = login_id 
  
  @NotNull
  @JsonProperty("url")
   private var _url :String = _
   
   def url = _url 
 
  def url_= (url:String) = _url = url 
  
  @NotNull
  @JsonProperty("login")
  //@NotEmpty
   private var _login :String = _
   
   def login = _login 
 
  def login_= (login:String) = _login = login 
  
  @NotNull
  @JsonProperty("password")
  //@NotEmpty
   private var _password :String = _
   
   def password = _password 
 
  def password_= (password:String) = _password = password 
   

}