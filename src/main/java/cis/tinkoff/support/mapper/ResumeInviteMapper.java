package cis.tinkoff.support.mapper;

import cis.tinkoff.controller.model.ResumeInviteDTO;
import cis.tinkoff.model.PositionRequest;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Objects;

@Singleton
public class ResumeInviteMapper {

    public ResumeInviteDTO toDto(PositionRequest positionRequest) {

        if (Objects.isNull(positionRequest)) {
            return null;
        }

        ResumeInviteDTO resumeInvite = new ResumeInviteDTO();

        if (positionRequest.getPosition() != null
                && positionRequest.getPosition().getProject() != null
                && positionRequest.getPosition().getProject().getMembers() != null) {
            resumeInvite.setMembersCount(positionRequest.getPosition().getProject().getMembers().size());
            positionRequest.getPosition().getProject().setMembers(null);
        } else {
            resumeInvite.setMembersCount(0);
        }

        resumeInvite.setResume(positionRequest.getResume())
                .setIsInvite(positionRequest.getIsInvite())
                .setStatus(positionRequest.getStatus())
                .setPosition(positionRequest.getPosition())
                .setCoverLetter(positionRequest.getCoverLetter());
        resumeInvite.setId(positionRequest.getId());
        resumeInvite.setIsDeleted(positionRequest.getIsDeleted());
        resumeInvite.setCreatedWhen(positionRequest.getCreatedWhen());

        return resumeInvite;
    }

    public List<ResumeInviteDTO> toDtos(List<PositionRequest> positionRequests) {
        return positionRequests
                .stream()
                .map(this::toDto)
                .toList();
    }
}
