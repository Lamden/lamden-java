
package io.lamden.api.json.transaction;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "contract",
    "function",
    "kwargs",
    "nonce",
    "processor",
    "sender",
    "stamps_supplied"
})
public class Payload {

    @JsonProperty("contract")
    private String contract;
    @JsonProperty("function")
    private String function;
    @JsonProperty("kwargs")
    private Map<String, Object> kwargs;
    @JsonProperty("nonce")
    private Integer nonce;
    @JsonProperty("processor")
    private String processor;
    @JsonProperty("sender")
    private String sender;
    @JsonProperty("stamps_supplied")
    private Integer stampsSupplied;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Payload() {
    }

    /**
     * 
     * @param sender
     * @param contract
     * @param function
     * @param kwargs
     * @param stampsSupplied
     * @param nonce
     * @param processor
     */
    public Payload(String contract, String function, Map<String, Object> kwargs, Integer nonce, String processor, String sender, Integer stampsSupplied) {
        super();
        this.contract = contract;
        this.function = function;
        this.kwargs = kwargs;
        this.nonce = nonce;
        this.processor = processor;
        this.sender = sender;
        this.stampsSupplied = stampsSupplied;
    }

    @JsonProperty("contract")
    public String getContract() {
        return contract;
    }

    @JsonProperty("contract")
    public void setContract(String contract) {
        this.contract = contract;
    }

    @JsonProperty("function")
    public String getFunction() {
        return function;
    }

    @JsonProperty("function")
    public void setFunction(String function) {
        this.function = function;
    }

    @JsonProperty("kwargs")
    public Map<String, Object> getKwargs() {
        return kwargs;
    }

    @JsonProperty("kwargs")
    public void setKwargs(Map<String, Object> kwargs) {
        this.kwargs = kwargs;
    }

    @JsonProperty("nonce")
    public Integer getNonce() {
        return nonce;
    }

    @JsonProperty("nonce")
    public void setNonce(Integer nonce) {
        this.nonce = nonce;
    }

    @JsonProperty("processor")
    public String getProcessor() {
        return processor;
    }

    @JsonProperty("processor")
    public void setProcessor(String processor) {
        this.processor = processor;
    }

    @JsonProperty("sender")
    public String getSender() {
        return sender;
    }

    @JsonProperty("sender")
    public void setSender(String sender) {
        this.sender = sender;
    }

    @JsonProperty("stamps_supplied")
    public Integer getStampsSupplied() {
        return stampsSupplied;
    }

    @JsonProperty("stamps_supplied")
    public void setStampsSupplied(Integer stampsSupplied) {
        this.stampsSupplied = stampsSupplied;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
