package io.lamden.api.json.nonce;

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
        "nonce",
        "processor",
        "sender"
})
public class Nonce {

    @JsonProperty("nonce")
    private Integer nonce;
    @JsonProperty("processor")
    private String processor;
    @JsonProperty("sender")
    private String sender;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Nonce() {
    }

    /**
     *
     * @param sender
     * @param nonce
     * @param processor
     */
    public Nonce(Integer nonce, String processor, String sender) {
        super();
        this.nonce = nonce;
        this.processor = processor;
        this.sender = sender;
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}