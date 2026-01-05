package io.wulfcodes.serdes.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.wulfcodes.serdes.util.serdes.EmailSerializer;
import io.wulfcodes.serdes.util.serdes.UsernameDeserializer;

// 1. Define Order of fields in JSON output
@JsonPropertyOrder({ "order_id", "status", "placed_at", "total_amount", "payment", "shipping" })
// 2. Ignore specific fields if they appear in JSON but we don't have fields for them
@JsonIgnoreProperties({ "internal_code", "legacy_id" })
// 3. specific null handling rules
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {

    // 4. Property Renaming: JSON "order_id" <-> Java "id"
    // @JsonAlias allows reading "id", "orderId", or "order_id" from input
    @JsonProperty("order_id")
    @JsonAlias({"id", "orderId"}) 
    public String id;

    // 5. Date Formatting: Custom pattern
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("placed_at")
    public Date placedAt;

    // 6. Enums: Map JSON string "confirmed" to Enum constant
    public OrderStatus status;

    // 7. Raw Value: Inject raw JSON string directly (rare but powerful)
    // If this string was "{\"a\":1}", it prints as an object, not a string
    @JsonRawValue
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public String rawMetadata; 

    @JsonProperty("total_amount")
    public double amount;

    // 8. Security: This field is completely ignored (Read & Write)
    @JsonIgnore
    public String internalComments;

    // 9. Polymorphism: Handles different subclasses based on "type" property
    @JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, 
        include = JsonTypeInfo.As.PROPERTY, 
        property = "type"
    )
    @JsonSubTypes({
        @JsonSubTypes.Type(value = CreditCardPayment.class, name = "credit_card"),
        @JsonSubTypes.Type(value = PayPalPayment.class, name = "paypal")
    })
    public PaymentMethod payment;

    // 10. Unwrapping: Flattens the "shipping" object.
    // Java: order.shipping.street
    // JSON: "ship_street" (due to prefix)
    @JsonUnwrapped(prefix = "ship_") 
    public Address shipping;


    // 11. Custom Serialization and Deserialization
    @JsonDeserialize(using = UsernameDeserializer.class)
    public String username;

    @JsonSerialize(using = EmailSerializer.class)
    public String email;

    // 12. Dynamic Data: Capture unknown fields (like "campaign_id") into this map
    private Map<String, Object> otherProperties = new HashMap<>();


    @JsonAnySetter
    public void add(String key, Object value) {
        otherProperties.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getOtherProperties() {
        return otherProperties;
    }
}




