package cis.tinkoff.controller;

import cis.tinkoff.service.DictionaryService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Dictionaries", description = "All actions with dictionary-type models.")
@Controller("/api/v1/dictionaries")
@Secured(SecurityRule.IS_ANONYMOUS)
@RequiredArgsConstructor
public class DictionaryController {

    private final DictionaryService dictionaryService;

}
