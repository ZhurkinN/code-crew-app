package cis.tinkoff.controller.model;

import cis.tinkoff.model.ProjectContact;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude
public class ContactDTO {
    private Long id;
    private String link;
    private String description;

    public static ContactDTO toContactDto(ProjectContact contact) {
        if (contact == null) {
            return null;
        }

        ContactDTO contactDTO = ContactDTO.builder()
                .id(contact.getId())
                .link(contact.getLink())
                .description(contact.getDescription())
                .build();

        return contactDTO;
    }

    public static List<ContactDTO> toContactDto(List<ProjectContact> contacts) {
        if (contacts == null) {
            return null;
        }

        List<ContactDTO> contactDTOList = contacts.stream()
                .map(ContactDTO::toContactDto)
                .toList();

        return contactDTOList;
    }
}
