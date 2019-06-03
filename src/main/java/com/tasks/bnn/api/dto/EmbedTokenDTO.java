package com.tasks.bnn.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmbedTokenDTO {
    @JsonProperty("tokenId")
    private String id;

    @JsonProperty("token")
    private String value;

    @JsonProperty("expiration")
    private String expiration;
}
