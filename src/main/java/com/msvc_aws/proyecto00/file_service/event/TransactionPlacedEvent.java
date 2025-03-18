package com.msvc_aws.proyecto00.file_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionPlacedEvent {
    String fileIdDate;
}
