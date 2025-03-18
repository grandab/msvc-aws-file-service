package com.msvc_aws.proyecto00.file_service.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appfiles")
public class AppFile {

    @Id
    @Column (name =  "id" )
    @GeneratedValue (strategy = GenerationType.UUID)
    private String id;

    @Column (name = "name" )
    private String name;

    @CreationTimestamp
    @Column (name="creationdate")
    private LocalDateTime creationdate;

    @UpdateTimestamp
    @Column (name="lastUpdate")
    private LocalDateTime lastUpdate;


    @Column (name = "filesize" )
    private long filesize;

    @Column (name = "filetype")
    @Enumerated
    private FileType filetype;

    @Column (name = "userid" )
    private Integer userid;

    @Column (name = "fileurl")
    private String fileurl;
}
