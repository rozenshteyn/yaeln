package com.sciapps.yaeln.repository.search;

import com.sciapps.yaeln.domain.User;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;


/**
 * Spring Data Elasticsearch repository for the User entity.
 */
public interface UserSearchRepository extends ReactiveElasticsearchRepository<User, Long>, UserSearchRepositoryInternal {
}

interface UserSearchRepositoryInternal {
    Flux<User> search(String query);
}

class UserSearchRepositoryInternalImpl implements UserSearchRepositoryInternal {

    private final ReactiveElasticsearchTemplate reactiveElasticsearchTemplate;

    UserSearchRepositoryInternalImpl(ReactiveElasticsearchTemplate reactiveElasticsearchTemplate) {
        this.reactiveElasticsearchTemplate = reactiveElasticsearchTemplate;
    }

    @Override
    public Flux<User> search(String query) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        return reactiveElasticsearchTemplate.find(nativeSearchQuery, User.class);
    }
}
