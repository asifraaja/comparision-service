package com.comparekaro.similarity;

import com.comparekaro.models.Similarity;
import com.comparekaro.models.Spec;
import com.comparekaro.models.response.Specification;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SimpleSimilarityFinder implements ISimilarityFinder{
    @Override
    /*
     * If all values are empty then return 0
     * If all values are non-empty & similar then return +1
     * If any one value is different then return -1
     * */
    public Similarity findSimilar(Specification spec) {
        List<String> values = spec.getValues();
        Map<String, Integer> freqMap = new HashMap<>();
        for(String str : values){
            if(str == null || str.trim().isEmpty()) continue;
            freqMap.put(str, freqMap.getOrDefault(str, 0) + 1);
        }
        if (freqMap.isEmpty()) return Similarity.ALL_EMPTY;
        if(freqMap.size() == 1) return Similarity.ALL_SAME;
        if(freqMap.size() == values.size()) return Similarity.ALL_DIFFERENT;
        return Similarity.FEW_SAME;
    }

}
