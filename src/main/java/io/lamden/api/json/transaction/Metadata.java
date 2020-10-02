
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
    "signature",
    "timestamp"
})
public class Metadata {

    @JsonProperty("signature")
    private String signature;
    @JsonProperty("timestamp")
    private Integer timestamp;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Metadata() {
    }

    /**
     * 
     * @param signature
     * @param timestamp
     */
    public Metadata(String signature, Integer timestamp) {
        super();
        this.signature = signature;
        this.timestamp = timestamp;
    }

    @JsonProperty("signature")
    public String getSignature() {
        return signature;
    }

    @JsonProperty("signature")
    public void setSignature(String signature) {
        this.signature = signature;
    }

    @JsonProperty("timestamp")
    public Integer getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
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
