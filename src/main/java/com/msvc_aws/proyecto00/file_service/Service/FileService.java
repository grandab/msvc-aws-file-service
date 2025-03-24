package com.msvc_aws.proyecto00.file_service.Service;

import com.msvc_aws.proyecto00.file_service.Model.AppFile;
import com.msvc_aws.proyecto00.file_service.Respository.FIleRepository;
import com.msvc_aws.proyecto00.file_service.dto.AppFileRequest;
import com.msvc_aws.proyecto00.file_service.dto.AppFileResponse;
import com.msvc_aws.proyecto00.file_service.dto.UserResponse;
import com.msvc_aws.proyecto00.file_service.event.TransactionPlacedEvent;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.List;

@Service
public class FileService {

    private WebClient.Builder wwbCliBuilder;
    private final FIleRepository fileRepository;
    private final Tracer tracer;
    private final KafkaTemplate<String, TransactionPlacedEvent> kafkaTemplate;


    @Autowired
    public FileService(WebClient.Builder wwbCliBuilder, FIleRepository fileRepository,
                       Tracer tracer, KafkaTemplate<String, TransactionPlacedEvent> kafkaTemplate) {
        this.wwbCliBuilder = wwbCliBuilder;
        this.fileRepository = fileRepository;
        this.tracer = tracer;
        this. kafkaTemplate = kafkaTemplate;
    }




    @SneakyThrows
    public String createFile (AppFileRequest appFileRequest)
    {
        AppFile appFile = new AppFile();
        appFile = AppFile.builder()
                //.id(appFileRequest.getId())
                .userid(appFileRequest.getUserid())
                .name(appFileRequest.getName())

                .filetype(appFileRequest.getFiletype())
                .filesize(appFileRequest.getFilesize())
                .fileurl(appFileRequest.getFileurl())
                .build();

        Span appFileServiceLookup = tracer.nextSpan().name("appFileServiceLookup");
        try (Tracer.SpanInScope isLookup = tracer.withSpan(appFileServiceLookup.start()))
        {
            appFileServiceLookup.tag("call", "appfile-service");
            UserResponse userResponse = wwbCliBuilder.build().get()
                    .uri("http://user-service:8087/msvcaws/users/{userId}", appFileRequest.getUserid())
                    .retrieve()
                    .bodyToMono(UserResponse.class)
                    .block();
            return checkUser(userResponse, appFile);
        }
        finally {
            appFileServiceLookup.end();
        }


    }
    //Metodo axuliar
    private String checkUser (UserResponse userResponse, AppFile appFile)
    {
        if (userResponse!= null)
        {
            fileRepository.save(appFile);
            String forKafka = appFile.getUserid()+" and date "+ appFile.getLastUpdate() ;
            //TransactionPlacedEvent event = new TransactionPlacedEvent(forKafka);
           // kafkaTemplate.send("notificationTopic", event);
            return " Datos guardados";
        }
        else
        {
            throw new IllegalArgumentException("El Usuario no existe");
        }
    }

    public AppFileResponse getAppFile (String id)
    {
        AppFile appFile = fileRepository.getReferenceById(id);
        return mapToFileResponse(appFile);
    }

    public List<AppFileResponse> getAppFilesByUserId(String id)
    {
        Integer userId = Integer.valueOf(id);

        List<AppFile> listAppFiles = fileRepository.findAllByUserid(userId);
        return  listAppFiles.stream().map(this::mapToFileResponse).toList();
    }

    public List<AppFileResponse> getAppFiles()
    {
        List<AppFile> listAppFiles = fileRepository.findAll();
        return listAppFiles.stream().map(this::mapToFileResponse).toList();
    }
    private AppFileResponse mapToFileResponse (AppFile appFile)
    {

        return AppFileResponse.builder()
                .id(appFile.getId())
                .userid(appFile.getUserid())
                .name(appFile.getName())
                .filetype(appFile.getFiletype())
                .filesize(appFile.getFilesize())
                .fileurl(appFile.getFileurl())
                .creationdate(appFile.getCreationdate())
                .lastUpdate(appFile.getLastUpdate())
                .build();
    }

    public void deleteFile (String id)
    {
        fileRepository.deleteById(id);
    }


}
