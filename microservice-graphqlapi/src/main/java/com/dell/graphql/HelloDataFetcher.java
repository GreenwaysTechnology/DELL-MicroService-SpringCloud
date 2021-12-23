package com.dell.graphql;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

//Which is service class eq to RestController
@DgsComponent
public class HelloDataFetcher {
    //api
    @DgsQuery(field = "hello")
    public String sayHello() {
        return "Hello GraphQl";
    }
}
