package com.comparekaro.helper;

import com.comparekaro.models.Similarity;
import com.comparekaro.models.domain.FullCarInfo;
import com.comparekaro.dto.SpecInfo;
import com.comparekaro.models.Spec;
import com.comparekaro.models.request.CompareCarRequest;
import com.comparekaro.models.response.BasicCarInfo;
import com.comparekaro.models.response.ComparisionDetails;
import com.comparekaro.models.response.Image;
import com.comparekaro.models.response.Metadata;
import com.comparekaro.models.response.PriceOverview;
import com.comparekaro.models.response.SpecDetailsResponse;
import com.comparekaro.models.response.Specification;
import com.comparekaro.models.response.suggestion.RespError;
import com.comparekaro.similarity.ISimilarityFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Component
public class ComparisonDetailsResponseCreator {
    public static final String EMPTY_STRING = "";
    public static final String COMPARISON_BETWEEN_ = "Comparison between ";

    @Autowired
    @Qualifier("specMap")
    private Map<String, Spec> specMap;

    @Autowired
    @Qualifier("featureMap")
    private Map<String, Spec> featureMap;

    @Autowired
    @Qualifier("groupOrderMap")
    private Map<String, Integer> groupOrderMap;

    @Autowired
    private ISimilarityFinder similarityFinder;

    public ComparisionDetails generator(Map<String, FullCarInfo> carMap,
                                        List<String> cars,
                                        CompareCarRequest request) {

        ComparisionDetails comparisionDetails = ComparisionDetails.builder()
                .cars(cars)
                .images(setImages(carMap, cars))
                .basicCarInfoList(setCarInfos(carMap, cars))
                .featureList(setFeatures(carMap, cars))
                .specificationList(setSpecs(carMap, cars))
                .maxPriceOverviewList(setMaxPrice(carMap, cars))
                .minPriceOverviewList(setMinPrice(carMap, cars))
                .metadata(setMetadata(carMap, cars))
                .build();
        setSimilarityAndDifference(comparisionDetails, request);
        return comparisionDetails;
    }

    private void setSimilarityAndDifference(ComparisionDetails comparisionDetails,
                                            CompareCarRequest request) {
        List<Specification> specs = getAllSpecifications(comparisionDetails);

        Map<String, Specification> similarSpecs = new HashMap<>();
        Map<String, Specification> differentSpecs = new HashMap<>();
        boolean hideSimilarity = request.isHideSimilar();
        for(Specification spec : specs){
            Similarity similarity = similarityFinder.findSimilar(spec);
            if(!hideSimilarity && similarity.equals(Similarity.ALL_SAME))
                similarSpecs.put(spec.getName(), new Specification(spec));
            else if(similarity.equals(Similarity.ALL_DIFFERENT) || similarity.equals(Similarity.FEW_SAME))
                differentSpecs.put(spec.getName(), new Specification(spec));
        }
        comparisionDetails.setSimilarity(groupAllSpecs(similarSpecs, featureMap, specMap));
        comparisionDetails.setDifferences(groupAllSpecs(differentSpecs, featureMap, specMap));

    }

    private static List<Specification> getAllSpecifications(ComparisionDetails comparisionDetails) {
        List<Specification> specs = new ArrayList<>();
        specs.addAll(
                comparisionDetails.getSpecificationList().stream()
               .flatMap(sdr -> sdr.getItems().stream())
               .collect(Collectors.toList()));
        specs.addAll(
                comparisionDetails.getFeatureList().stream()
                        .flatMap(sdr -> sdr.getItems().stream())
                        .collect(Collectors.toList()));
        return specs;
    }

    private List<SpecDetailsResponse> groupAllSpecs(Map<String, Specification> specs,
                                                    Map<String, Spec> featureMap,
                                                    Map<String, Spec> specMap) {
        Map<String, List<Specification>> groupMap = groupBySpec(specs, specMap);
        groupMap.putAll(groupBySpec(specs, featureMap));

        List<SpecDetailsResponse> sdrList = new ArrayList<>();
        for(Map.Entry<String, List<Specification>> entry : groupMap.entrySet()){
            String group = entry.getKey();
            SpecDetailsResponse sdr = SpecDetailsResponse.builder()
                    .group(group)
                    .groupOrder(groupOrderMap.get(group))
                    .items(entry.getValue())
                    .build();
            sdr.getItems().sort(Comparator.comparingInt(Specification::getSortedOrder));
            sdrList.add(sdr);
        }
        sdrList.sort(Comparator.comparingInt(SpecDetailsResponse::getGroupOrder));
        return sdrList;
    }

    private Map<String, List<Specification>> groupBySpec(Map<String, Specification> specs,
                                                         Map<String, Spec> specMap) {
        Map<String, List<Specification>> groupMap = new HashMap<>();
        for(Map.Entry<String, Spec> entry : specMap.entrySet()){
            String specName = entry.getKey();
            Spec specDetail = entry.getValue();
            if(specs.containsKey(specName)){
                List<Specification> list = groupMap.getOrDefault(specDetail.getGroup(), new ArrayList<>());
                list.add(specs.get(specName));
                groupMap.put(specDetail.getGroup(), list);
            }
        }
        return groupMap;
    }

    private Metadata setMetadata(Map<String, FullCarInfo> carMap, List<String> cars) {
        List<String> names = cars.stream().map(car -> carMap.get(car).getName()).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        sb.append(COMPARISON_BETWEEN_);
        StringJoiner sj = new StringJoiner(" vs ");
        names.forEach(sj::add);
        sb.append(sj);
        return Metadata.builder()
                .comparisionText(sb.toString()).build();
    }

    private List<PriceOverview> setMinPrice(Map<String, FullCarInfo> carMap, List<String> cars) {
        return cars.stream()
                .map(car -> {
                    FullCarInfo carInfo = carMap.get(car);
                    return carInfo.getMinPriceOverview();
                })
                .collect(Collectors.toList());
    }

    private List<PriceOverview> setMaxPrice(Map<String, FullCarInfo> carMap, List<String> cars) {
        return cars.stream()
                .map(car -> {
                    FullCarInfo carInfo = carMap.get(car);
                    return carInfo.getMaxPriceOverview();
                })
                .collect(Collectors.toList());
    }

    private List<SpecDetailsResponse> setSpecs(Map<String, FullCarInfo> carMap, List<String> cars) {
        List<FullCarInfo> carInfos = cars.stream()
                .map(carMap::get)
                .collect(Collectors.toList());
        List<Map<String, SpecInfo>> list = new ArrayList<>();
        for(FullCarInfo ci : carInfos){
            Map<String, SpecInfo> map = ci.getSpecifications().stream().collect(Collectors.toMap(k -> k.getName(), v -> v));
            list.add(map);
        }
        return getSpecDetailsResponses(list, specMap);
    }

    private List<SpecDetailsResponse> setFeatures(Map<String, FullCarInfo> carMap, List<String> cars) {
        List<FullCarInfo> carInfos = cars.stream()
                .map(carMap::get)
                .collect(Collectors.toList());
        List<Map<String, SpecInfo>> list = new ArrayList<>();
        for(FullCarInfo ci : carInfos){
            Map<String, SpecInfo> map = ci.getFeatures().stream().collect(Collectors.toMap(k -> k.getName(), v -> v));
            list.add(map);
        }
        return getSpecDetailsResponses(list, featureMap);
    }

    private List<SpecDetailsResponse> getSpecDetailsResponses(List<Map<String, SpecInfo>> list,
                                                              Map<String, Spec> specificationsMap) {
        Map<String, List<Specification>> map = new HashMap<>();
        for(Map.Entry<String, Spec> entry : specificationsMap.entrySet()){
            String group = entry.getKey();
            Spec value = entry.getValue();
            List<Specification> defaultSpecs = map.getOrDefault(value.getGroup(), new ArrayList<>());
            defaultSpecs.add(compareSpecs(group, value, list));
            map.put(value.getGroup(), defaultSpecs);
        }

        List<SpecDetailsResponse> sdrList = new ArrayList<>();
        for(Map.Entry<String, List<Specification>> entry : map.entrySet()){
            String group = entry.getKey();
            SpecDetailsResponse sdr = SpecDetailsResponse.builder()
                    .group(group)
                    .groupOrder(groupOrderMap.get(group))
                    .items(entry.getValue())
                    .build();
            sdr.getItems().sort(Comparator.comparingInt(Specification::getSortedOrder));
            sdrList.add(sdr);
        }
        sdrList.sort(Comparator.comparingInt(SpecDetailsResponse::getGroupOrder));
        return sdrList;
    }

    private Specification compareSpecs(String key, Spec value, List<Map<String, SpecInfo>> list) {
        List<String> values = new ArrayList<>();
        for(Map<String, SpecInfo> spec : list){
            if(spec.containsKey(key)) {
                values.add(spec.get(key).getValue());
            }else {
                values.add(EMPTY_STRING);
            }
        }
        return Specification.builder()
                .name(value.getName())
                .description(value.getDescription())
                .sortedOrder(value.getSortOrder())
                .values(values)
                .build();
    }


    private List<BasicCarInfo> setCarInfos(Map<String, FullCarInfo> carMap, List<String> cars) {
        return cars.stream()
                .map(carMap::get)
                .map(FullCarInfo::toBasicCarInfo)
                .collect(Collectors.toList());
    }

    private List<Image> setImages(Map<String, FullCarInfo> carMap, List<String> cars) {
        return cars.stream()
                .map(car -> carMap.get(car).getImages())
                .collect(Collectors.toList());
    }

    public ComparisionDetails generateErrorResponse() {
        return ComparisionDetails.builder()
                .errors(List.of(new RespError("CAR INFO NOT FOUND", "Unable to compare car as car info not found")))
                .build();
    }
}
