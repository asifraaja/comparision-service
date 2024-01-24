package com.comparekaro.repository;

import com.comparekaro.repository.dao.SugesstionsMapper;
import com.comparekaro.repository.dao.SugesstionsMapperBuilder;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.file.Paths;

@Component
public class CassandraRepository {

    private CqlSession cqlSession;
    @Value("${cassandra.secure-connect-file}")
    private String secureConnectFile;
    @Value("${cassandra.client-id}")
    private String clientId;
    @Value("${cassandra.client-secret}")
    private String clientSecret;
    private SuggestionRepository suggestionRepository;

    public synchronized SuggestionRepository getSuggestionRepository(){
        if(suggestionRepository == null){
            SugesstionsMapper mapper = new SugesstionsMapperBuilder(this.cqlSession).build();
            this.suggestionRepository = mapper.suggestionsDao(cqlSession.getKeyspace().get());
        }
        return this.suggestionRepository;
    }


    @PostConstruct
    public void init(){
        this.cqlSession = CqlSession.builder()
                .withCloudSecureConnectBundle(Paths.get(secureConnectFile))
                .withAuthCredentials("ztvtGhieFIxejmyZSbZIWYCy","wZj-jBlj_HJ3GhMw7OO.jf4u8WGzZ088NZgkRp78g10Nj0XPjQAR_bSZ+EQsmNi+sn9CX_bQXL3hZXXz33bSgePyPAJQBt+pP9g52uvtBz+n0AD3EWm.zlSUiCaFplOX")
                .withKeyspace("compare_karo")
                .build();
    }

    @PreDestroy
    public void close(){
        if(cqlSession != null && !cqlSession.isClosed()){
            cqlSession.close();
        }
    }
}
