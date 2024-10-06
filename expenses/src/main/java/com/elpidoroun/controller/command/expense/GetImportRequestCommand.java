package com.elpidoroun.controller.command.expense;

import com.elpidoroun.controller.command.AbstractRequest;
import com.elpidoroun.controller.command.Command;
import com.elpidoroun.generated.dto.ImportRequestDto;
import com.elpidoroun.mappers.ImportRequestStatusMapper;
import com.elpidoroun.model.ImportRequest;
import com.elpidoroun.repository.ImportRequestRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.elpidoroun.controller.command.Operations.GET_IMPORT_REQUEST;
import static java.util.Objects.isNull;

@Component
@AllArgsConstructor
public class GetImportRequestCommand  implements Command<GetImportRequestCommand.Request, ImportRequestDto> {

    @NonNull ImportRequestRepository importRequestRepository;

    @Override
    public ImportRequestDto execute(GetImportRequestCommand.Request request) {
        var importRequest = importRequestRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("ImportRequest with ID: " + request.getId() + " not found"));

        return buildResponse(importRequest);
    }

    private ImportRequestDto buildResponse(ImportRequest importRequest) {
        ImportRequestDto responseDto = new ImportRequestDto();
        responseDto.setId(importRequest.getId());
        responseDto.setTotalNumberOfRows(importRequest.getTotalNumberOfRows());
        responseDto.setNumberOfSuccessImports(importRequest.getTotalNumberOfSuccessRows());
        responseDto.setNumberOfFailedImports(importRequest.getNumberOfFailedRows());
        responseDto.setStatus(ImportRequestStatusMapper.toDto(importRequest.calculateStatus()));

        return responseDto;
    }

    public static GetImportRequestCommand.Request request(Long id){
        return new GetImportRequestCommand.Request(id);
    }

    @Override
    public boolean isRequestIncomplete(GetImportRequestCommand.Request request) {
        return isNull(request) || isNull(request.getId());
    }

    @Override
    public String missingParams(GetImportRequestCommand.Request request) {
        return Stream.of(isNull(request)
                        || isNull(request.getId()) ? "importRequest id is missing" : null
                )
                .filter(Objects::nonNull)
                .collect(Collectors.joining(",'"));
    }

    @Override
    public String getOperation() { return GET_IMPORT_REQUEST.getValue(); }

    protected static class Request extends AbstractRequest {
        private final Long id;
        protected Request(Long id){
            this.id = id;
        }
        public Long getId(){ return id; }

    }
}
