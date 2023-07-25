package cis.tinkoff.controller.model.custom;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude
public class SearchDTO {

    private List<?> content;
    private Integer pageCount;

    public static SearchDTO toDto(List<?> content, Integer pageCount) {
        if (content == null) {
            return null;
        }

        return new SearchDTO()
                .setContent(content)
                .setPageCount(pageCount);
    }
}
