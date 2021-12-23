package com.dell.graphql;

import com.dell.graphql.models.Show;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

import java.util.List;

@DgsComponent
public class ShowDataFetcher {

    @DgsQuery(field = "shows")
    public List<Show> shows() {
        return List.of(new Show("The Mummy", 1999), new Show("The Matrix", 2000));
    }
}
