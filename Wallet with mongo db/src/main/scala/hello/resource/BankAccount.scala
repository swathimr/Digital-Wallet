package main.scala.hello.resource
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotNull

class BankAccount {

   @JsonProperty("ba_id")
   private var _ba_id :String = _
   
   def ba_id = _ba_id 
 
  def ba_id_= (ba_id:String) = _ba_id = ba_id 
  
  @JsonProperty("account_name")
 // @NotEmpty
   private var _account_name:String = _
   
   def account_name = _account_name 
 
  def account_name_= (account_name:String) = _account_name = account_name 
  
  @NotNull
  @JsonProperty("routing_number")
   private var _routing_number :String = _
   
   def routing_number = _routing_number 
 
  def routing_number_= (routing_number:String) = _routing_number = routing_number 
  
  @NotNull
  @JsonProperty("account_number")
   private var _account_number :String = _
   
   def account_number = _account_number 
 
  def account_number_= (account_number:String) = _account_number = account_number 
  
}