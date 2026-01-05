package io.wulfcodes.serdes.model;

import com.fasterxml.jackson.annotation.JsonProperty;

class CreditCardPayment extends PaymentMethod {
    @JsonProperty("card_number")
    public String cardNumber;
    
    // Write Only: We can read CVV from JSON, but never write it back to JSON (Security)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public int cvv;
}