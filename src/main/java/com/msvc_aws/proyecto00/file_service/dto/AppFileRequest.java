package com.msvc_aws.proyecto00.file_service.dto;

import com.msvc_aws.proyecto00.file_service.Model.FileType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppFileRequest {


    private String id;

    private String name;
    private LocalDateTime lastUpdate;

    private LocalDateTime creationdate;

    private long filesize;

    private FileType filetype;

    private Integer userid;

    private String fileurl;

}
