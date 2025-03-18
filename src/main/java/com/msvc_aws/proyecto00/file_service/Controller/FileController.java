package com.msvc_aws.proyecto00.file_service.Controller;

import com.msvc_aws.proyecto00.file_service.Service.FileService;
import com.msvc_aws.proyecto00.file_service.dto.AppFileRequest;
import com.msvc_aws.proyecto00.file_service.dto.AppFileResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@RestController
@RequestMapping("msvcaws/appfile")
public class FileController {

    FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping ("/{fileid}")
    public AppFileResponse getFileById(@PathVariable("fileid") String fileId)
    {
        return fileService.getAppFile(fileId);
    }


    @GetMapping
    public List<AppFileResponse> getFiles()
    {
        return fileService.getAppFiles();
    }

    @GetMapping ("/{userid}")
    public List<AppFileResponse> getFileByUserId(@PathVariable("userid") String userId)
    {
        return fileService.getAppFilesByUserId(userId);
    }


    @CircuitBreaker(name = "appfile", fallbackMethod = "fallbackMethod")
    @PostMapping
    public CompletableFuture<String> createFile (@RequestBody AppFileRequest appFileRequest)
    {
        fileService.createFile(appFileRequest);
        System.out.println(appFileRequest);
        return CompletableFuture.supplyAsync(()->"Archivo enviado");
    }

    @DeleteMapping("/{fileid}")
    public void deleteFile (@PathVariable ("fileid") String fileId)
    {
        fileService.deleteFile (fileId);
    }


    public CompletableFuture<String> fallbackMethod (AppFileRequest appFileRequest, RuntimeException runtimeException)
    {
        return CompletableFuture.supplyAsync(()->"El archivo no pudo ser enviado");
    }
}
