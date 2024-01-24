package com.comparekaro.config;

import com.comparekaro.helper.ParseSpecifications;
import com.comparekaro.models.Spec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SpecificationsConfiguration {

    @Bean("specMap")
    public Map<String, Spec> getSpecMap(ParseSpecifications parseSpecifications) throws IOException {
        return parseSpecifications.parseSpecifications();
    }

    @Bean("featureMap")
    public Map<String, Spec> getFeatureMap(ParseSpecifications parseSpecifications) throws IOException {
        return parseSpecifications.parseFeatures();
    }

    @Bean("groupOrderMap")
    public Map<String, Integer> getSpecGroupOrderMap(Map<String, Spec> specMap,
                                                     Map<String, Spec> featureMap){
         List<Spec> specList = new ArrayList<>();
         specList.addAll(specMap.values());
         specList.addAll(featureMap.values());

        Map<String, Integer> groupOrderMap = specList.stream()
                .collect(Collectors.toMap(Spec::getGroup, Spec::getGroupOrder, (k1, k2) -> k1));
        return groupOrderMap;
    }
}
