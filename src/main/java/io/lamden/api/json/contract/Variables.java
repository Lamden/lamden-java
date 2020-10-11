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
        "variables",
        "hashes"
})
public class Variables {

    @JsonProperty("variables")
    private List<String> variables = null;
    @JsonProperty("hashes")
    private List<String> hashes = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Variables() {
    }

    /**
     *
     * @param variables
     * @param hashes
     */
    public Variables(List<String> variables, List<String> hashes) {
        super();
        this.variables = variables;
        this.hashes = hashes;
    }

    @JsonProperty("variables")
    public List<String> getVariables() {
        return variables;
    }

    @JsonProperty("variables")
    public void setVariables(List<String> variables) {
        this.variables = variables;
    }

    @JsonProperty("hashes")
    public List<String> getHashes() {
        return hashes;
    }

    @JsonProperty("hashes")
    public void setHashes(List<String> hashes) {
        this.hashes = hashes;
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
