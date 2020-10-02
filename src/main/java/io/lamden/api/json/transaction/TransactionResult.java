
package io.lamden.api.json.transaction;

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
    "hash",
    "result",
    "stamps_used",
    "state",
    "status",
    "transaction"
})
public class TransactionResult {

    @JsonProperty("hash")
    private String hash;
    @JsonProperty("result")
    private String result;
    @JsonProperty("stamps_used")
    private Integer stampsUsed;
    @JsonProperty("state")
    private List<State> state = null;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("transaction")
    private Transaction transaction;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public TransactionResult() {
    }

    /**
     * 
     * @param result
     * @param stampsUsed
     * @param state
     * @param hash
     * @param transaction
     * @param status
     */
    public TransactionResult(String hash, String result, Integer stampsUsed, List<State> state, Integer status, Transaction transaction) {
        super();
        this.hash = hash;
        this.result = result;
        this.stampsUsed = stampsUsed;
        this.state = state;
        this.status = status;
        this.transaction = transaction;
    }

    @JsonProperty("hash")
    public String getHash() {
        return hash;
    }

    @JsonProperty("hash")
    public void setHash(String hash) {
        this.hash = hash;
    }

    @JsonProperty("result")
    public String getResult() {
        return result;
    }

    @JsonProperty("result")
    public void setResult(String result) {
        this.result = result;
    }

    @JsonProperty("stamps_used")
    public Integer getStampsUsed() {
        return stampsUsed;
    }

    @JsonProperty("stamps_used")
    public void setStampsUsed(Integer stampsUsed) {
        this.stampsUsed = stampsUsed;
    }

    @JsonProperty("state")
    public List<State> getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(List<State> state) {
        this.state = state;
    }

    @JsonProperty("status")
    public Integer getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonProperty("transaction")
    public Transaction getTransaction() {
        return transaction;
    }

    @JsonProperty("transaction")
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
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
