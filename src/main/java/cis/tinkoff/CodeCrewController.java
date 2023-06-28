package cis.tinkoff;

import io.micronaut.http.annotation.*;

@Controller("/code-crew")
public class CodeCrewController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}