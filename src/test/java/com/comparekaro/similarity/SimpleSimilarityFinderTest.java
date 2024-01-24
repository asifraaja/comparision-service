package com.comparekaro.similarity;

import com.comparekaro.models.Similarity;
import com.comparekaro.models.response.Specification;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SimpleSimilarityFinderTest {
    SimpleSimilarityFinder classToTest = new SimpleSimilarityFinder();

    @Test
    public void testAllEmpty(){
        Specification spec = Specification.builder()
                .values(List.of("", "", "")).build();
        Similarity similarity = classToTest.findSimilar(spec);
        assertEquals(Similarity.ALL_EMPTY, similarity);
    }

    @Test
    public void testAllSame(){
        Specification spec = Specification.builder()
                .values(List.of("a", "a", "a")).build();
        Similarity similarity = classToTest.findSimilar(spec);
        assertEquals(Similarity.ALL_SAME, similarity);
    }

    @Test
    public void testFewSame(){
        Specification spec = Specification.builder()
                .values(List.of("a", "b", "a")).build();
        Similarity similarity = classToTest.findSimilar(spec);
        assertEquals(Similarity.FEW_SAME, similarity);
    }


    @Test
    public void testAllDifferent(){
        Specification spec = Specification.builder()
                .values(List.of("a", "b", "c")).build();
        Similarity similarity = classToTest.findSimilar(spec);
        assertEquals(Similarity.ALL_DIFFERENT, similarity);
    }
}
