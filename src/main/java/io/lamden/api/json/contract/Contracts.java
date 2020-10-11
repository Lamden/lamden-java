package io.lamden.api.json.contract;

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
        "contracts"
})
public class Contracts {

    @JsonProperty("contracts")
    private List<String> contracts = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Contracts() {
    }

    /**
     *
     * @param contracts
     */
    public Contracts(List<String> contracts) {
        super();
        this.contracts = contracts;
    }

    @JsonProperty("contracts")
    public List<String> getContracts() {
        return contracts;
    }

    @JsonProperty("contracts")
    public void setContracts(List<String> contracts) {
        this.contracts = contracts;
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
