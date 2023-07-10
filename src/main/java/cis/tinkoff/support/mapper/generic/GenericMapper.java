package cis.tinkoff.support.mapper.generic;

import cis.tinkoff.controller.model.generic.GenericDTO;
import cis.tinkoff.model.generic.GenericModel;

import java.time.ZoneOffset;
import java.util.List;

public abstract class GenericMapper<M extends GenericModel, D extends GenericDTO> {

    public abstract D toDto(M model);

    public List<D> toDtos(List<M> models) {
        return models.stream()
                .map(this::toDto)
                .toList();
    }

    protected void setGenericFields(M model,
                                    D dto) {
        dto.setId(model.getId());
        dto.setIsDeleted(model.getIsDeleted());
        dto.setCreatedWhen(model.getCreatedWhen().toEpochSecond(ZoneOffset.UTC));
    }
}