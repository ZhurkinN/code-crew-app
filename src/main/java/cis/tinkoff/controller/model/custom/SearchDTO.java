package cis.tinkoff.controller.model.custom;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(chain = true) // вот это можно вынести отовсюду в ломбок.конфиг
@JsonInclude
public class SearchDTO {
    private List<?> content;
    private Integer pageCount;

    public static SearchDTO toDto(List<?> content, Integer pageCount) { // почему методы-мапперы в дто? напиши маппер
        // отдельно, а лучше генирируй
        if (content == null) {
            return null;
        }

        return SearchDTO.builder()
                .content(content)
                .pageCount(pageCount)
                .build();
    }
}
