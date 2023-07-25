package cis.tinkoff.controller.model.custom;

import cis.tinkoff.model.ProjectContact;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude
public class ContactDTO {

    private Long id;
    private String link;
    private String description;

    public static ContactDTO toContactDto(ProjectContact contact) {
        if (contact == null) {
            return null;
        }

        return new ContactDTO()
                .setId(contact.getId())
                .setLink(contact.getLink())
                .setDescription(contact.getDescription());
    }

    public static List<ContactDTO> toContactDto(List<ProjectContact> contacts) {
        if (contacts == null) {
            return null;
        }

        return contacts.stream()
                .map(ContactDTO::toContactDto)
                .toList();
    }
}
