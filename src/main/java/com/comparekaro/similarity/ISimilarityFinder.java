package com.comparekaro.similarity;

import com.comparekaro.models.Similarity;
import com.comparekaro.models.Spec;
import com.comparekaro.models.response.Specification;

public interface ISimilarityFinder {
    Similarity findSimilar(Specification spec);
}
