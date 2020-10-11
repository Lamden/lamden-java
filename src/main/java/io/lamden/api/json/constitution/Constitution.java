package io.lamden.api.json.constitution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "masternodes",
        "delegates"
})
public class Constitution {

    @JsonProperty("masternodes")
    private List<String> masternodes = null;
    @JsonProperty("delegates")
    private List<String> delegates = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Constitution() {
    }

    /**
     *
     * @param masternodes
     * @param delegates
     */
    public Constitution(List<String> masternodes, List<String> delegates) {
        super();
        this.masternodes = masternodes;
        this.delegates = delegates;
    }

    @JsonProperty("masternodes")
    public List<String> getMasternodes() {
        return masternodes;
    }

    @JsonProperty("masternodes")
    public void setMasternodes(List<String> masternodes) {
        this.masternodes = masternodes;
    }

    @JsonProperty("delegates")
    public List<String> getDelegates() {
        return delegates;
    }

    @JsonProperty("delegates")
    public void setDelegates(List<String> delegates) {
        this.delegates = delegates;
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