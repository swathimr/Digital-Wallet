package main.scala.hello.resource

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotNull

class IdCard {
  
  @JsonProperty("card_id")
   private var _cardId :String = _
   
   def cardId = _cardId 
 
  def cardId_= (cardId:String) = _cardId = cardId 
   
   @NotNull
   @JsonProperty("card_name")
   private var _card_name:String =_
   
   def card_name = _card_name
 
  def card_name_= (userId:String) = _card_name = card_name 
   
  @NotNull
   @JsonProperty("card_number")
   private var _card_number:String=_
   
   def card_number = _card_number 
 
  def card_number_= (card_number:String) = _card_number = card_number 
   
   @JsonProperty("expiration_date")
   private var _expiration_date:String=_
   
   def expiration_date = _expiration_date
 
  def expiration_date_= (userId:String) = _expiration_date = expiration_date 
}