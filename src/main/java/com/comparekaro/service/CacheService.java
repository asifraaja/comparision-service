package com.comparekaro.service;

import com.comparekaro.errors.CarsNotFoundException;
import com.comparekaro.mappers.SuggestionDtoToModelMapper;
import com.comparekaro.models.domain.FullCarInfo;
import com.comparekaro.dto.SuggestionDto;
import com.comparekaro.errors.CarNotFoundException;
import com.comparekaro.errors.SuggestionNotFoundException;
import com.comparekaro.models.domain.SuggestionModel;
import com.comparekaro.repository.CassandraRepository;
import com.comparekaro.repository.FullCarInfoRepository;
import com.comparekaro.repository.SuggestionRepository;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CacheService {
    public static final FullCarInfo EMPTY_FULL_CAR_INFO = FullCarInfo.builder().build();
    public static final SuggestionDto EMPTY_SUGGESTIONS = new SuggestionDto();
    @Autowired private CassandraRepository cassandraRepository;
    @Autowired private FullCarInfoRepository fullCarInfoRepository;
    private LoadingCache<String, SuggestionDto> suggestionsCache;
    private LoadingCache<String, FullCarInfo> carInfoCache;

    public SuggestionDto getSuggestions(String carId){
        try {
            return suggestionsCache.get(carId);
        } catch (UncheckedExecutionException e){
            throw new SuggestionNotFoundException("Car : " + carId + " is not found");
        }catch (ExecutionException e) {
            log.error("Execution Exception while fetching suggestions : {}",e.getMessage() );
            throw new RuntimeException(e);
        }
    }

    public FullCarInfo getCarInfo(String carId){
        try {
            return carInfoCache.get(carId);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (UncheckedExecutionException e){
            throw new CarNotFoundException(e.getMessage());
        }
    }

    @PostConstruct
    public void initialize(){
        suggestionsCache = initialzeSuggestionCache();
        carInfoCache = initialzeCarInfoCache();
    }

    private LoadingCache<String, FullCarInfo> initialzeCarInfoCache(){
        return CacheBuilder.newBuilder()
                .maximumSize(10)
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .recordStats()
                .build(new CacheLoader<String, FullCarInfo>() {
                    @Override
                    public FullCarInfo load(String key) throws Exception {
                        return fetchCarInfo(key);
                    }
                });
    }

    private LoadingCache<String, SuggestionDto> initialzeSuggestionCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(10)
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .recordStats()
                .build(new CacheLoader<String, SuggestionDto>() {
                    @Override
                    public SuggestionDto load(String key) throws Exception {
                        try{
                            SuggestionDto suggestion = fetchSuggestions(key);
                            if(suggestion == null || suggestion.getId() == null)
                                throw new SuggestionNotFoundException("Suggestion not found for " + key);
                            return suggestion;
                        }catch (Exception e){
                            log.error("Exception when fetching car suggestions : {}", e.getMessage());
                            throw new SuggestionNotFoundException("Suggestion not found for " + key);
                        }
                    }
                });
    }

    private SuggestionDto fetchSuggestions(String key){
        SuggestionRepository suggestionRepository = cassandraRepository.getSuggestionRepository();
        SuggestionDto suggestion = suggestionRepository.findByCarId(key);
        return suggestion;
    }

    private FullCarInfo fetchCarInfo(String carId){
        try{
            return fullCarInfoRepository.findByCarId(carId);
        }catch (NoSuchElementException nse){
            log.error("Unable to fetch car information from repository");
            throw new CarNotFoundException("Car : " + carId + " is not found");
        }catch (Exception e){
            log.error("Exception while fetching full car info : {}",e.getMessage());
        }
        return FullCarInfo.builder().build();
    }

}
