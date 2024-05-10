package br.com.danieloliveira.foldermonitor.core.dto;

import lombok.Builder;

@Builder
public record VisitorsDto(
    Long visitorId,
    String firstName,
    String lastName,
    String emailAddress,
    String phoneNumber,
    String address,
    String visitDate) {
}
