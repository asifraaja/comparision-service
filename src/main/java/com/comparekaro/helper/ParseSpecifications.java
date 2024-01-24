package com.comparekaro.helper;

import com.comparekaro.models.Spec;
import com.comparekaro.models.metadata.GroupMetadata;
import com.comparekaro.models.metadata.SpecMetadata;
import com.comparekaro.models.metadata.SpecificationMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class ParseSpecifications {

    @Autowired
    private ObjectMapper objectMapper;

    public Map<String, Spec> parseSpecifications() throws IOException {
        var classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputsStreamReader = classloader.getResourceAsStream("specification-metadata.json");
        SpecificationMetadata metadata
                = objectMapper.readValue(inputsStreamReader, SpecificationMetadata.class);

        Map<String, Spec> specMap = new HashMap<>();
        for(GroupMetadata gm : metadata.getGroups()){
            specMap.putAll(mapSpec(gm));
        }

        return specMap;
    }

    public Map<String, Spec> parseFeatures() throws IOException {
        var classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputsStreamReader = classloader.getResourceAsStream("feature-metadata.json");
        SpecificationMetadata metadata
                = objectMapper.readValue(inputsStreamReader, SpecificationMetadata.class);

        Map<String, Spec> specMap = new HashMap<>();
        for(GroupMetadata gm : metadata.getGroups()){
            specMap.putAll(mapSpec(gm));
        }

        return specMap;
    }

    private Map<String, Spec> mapSpec(GroupMetadata group) {
        Map<String, Spec> specMap = new HashMap<>();
        for(SpecMetadata sm : group.getSpecifications()) {
            Spec spec = Spec.builder()
                    .group(group.getGroup())
                    .groupOrder(group.getGroupOrder())
                    .name(sm.getName())
                    .description(sm.getDescription())
                    .sortOrder(sm.getSortOrder()).build();
            specMap.put(sm.getName(), spec);
        }
        return specMap;
    }
}
