package com.dell.graphql;

import com.dell.graphql.models.Hero;
import com.dell.graphql.models.Movie;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;

@DgsComponent
public class MovieDataFetcher {

    @DgsData(parentType = "Query",field = "movie")
    public Movie showMovie() {
        return new Movie("1", "Mission Impossible", new Hero("1", "Tom", "New York"));
    }
}
