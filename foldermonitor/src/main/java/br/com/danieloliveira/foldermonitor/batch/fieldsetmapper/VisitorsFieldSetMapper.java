package br.com.danieloliveira.foldermonitor.batch.fieldsetmapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;
import br.com.danieloliveira.foldermonitor.core.dto.VisitorsDto;

public class VisitorsFieldSetMapper implements FieldSetMapper<VisitorsDto> {
    @SuppressWarnings("null")
    @Override
    public VisitorsDto mapFieldSet(final FieldSet fieldSet) throws BindException {
        return VisitorsDto.builder()
                .visitorId(fieldSet.readLong("visitorId"))
                .firstName(fieldSet.readString("firstName"))
                .lastName(fieldSet.readString("lastName"))
                .emailAddress(fieldSet.readString("emailAddress"))
                .phoneNumber(fieldSet.readString("phoneNumber"))
                .address(fieldSet.readString("address"))
                .visitDate(fieldSet.readString("visitDate"))
                .build();
    }
}
