package org.acme.util.adapter.rest;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

@Getter
@Setter
public class Range {

    @QueryParam(Links.OFFSET)
    @DefaultValue("0")
    @Min(value = 0, message = "parameter 'offset' must be at least {value}")
    int offset;

    @QueryParam(Links.LIMIT)
    @DefaultValue("20")
    @Min(value = 1, message = "parameter 'limit' must be at least {value}")
    @Max(value = 100, message = "parameter 'limit' must be at most {value}")
    int limit;
}
