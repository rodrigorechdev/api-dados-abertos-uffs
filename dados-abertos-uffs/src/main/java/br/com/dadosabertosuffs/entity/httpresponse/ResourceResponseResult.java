package br.com.dadosabertosuffs.entity.httpresponse;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.com.dadosabertosuffs.entity.dto.ResourceWithRelationshipResponse;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class ResourceResponseResult {

    private boolean include_total;
    private String resource_id;
    private List<ResourceResponseResultField> fields;
    private String records_format;
    private JsonArray records;
    private JsonObject _links;
    private JsonObject filters;
    private Integer total;

    /**Campo não recebido da API da UFFS, criado pela API atual. */
    private List<ResourceWithRelationshipResponse> recordsWithRelationships;

}
