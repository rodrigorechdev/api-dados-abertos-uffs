package br.com.dadosabertosuffs.mapper;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import br.com.dadosabertosuffs.entity.dto.ResourceRelacionado;

public class Mapper {
    
    public static JsonArray listResourceRelacionadoToJsonArray(List<ResourceRelacionado> relacionamentosConteudo) {
        return new Gson().toJsonTree(relacionamentosConteudo, new TypeToken<List<ResourceRelacionado>>() {}.getType()).getAsJsonArray();
    }
}
