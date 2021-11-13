package com.dd.campsites.web.rest;

import com.dd.campsites.service.campsitesindia.fileupload.message.ResponseMessage;
import com.dd.campsites.service.campsitesindia.fileupload.model.FileInfo;
import com.dd.campsites.service.campsitesindia.fileupload.service.FilesStorageService;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

/**
 * REST controller for managing {@link FilesResource}.
 */
@RestController
@RequestMapping("/api")
public class FilesResource {

    private final Logger log = LoggerFactory.getLogger(FilesResource.class);

    private static final String ENTITY_NAME = "fileInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FilesStorageService storageService;

    public FilesResource(FilesStorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * {@code POST  /files} : upload files.
     *
     * @param files to be uploaded.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new features, or with status {@code 400 (Bad Request)} if the features has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFiles(
        @RequestParam("files") MultipartFile[] files,
        @RequestParam("listingName") String listingName,
        @RequestParam("location") String location
    ) throws URISyntaxException {
        log.debug("REST request to upload number of files : {}", files.length);
        String message = "";
        try {
            List<String> fileNames = new ArrayList<>();

            Arrays
                .asList(files)
                .stream()
                .forEach(
                    file -> {
                        storageService.save(listingName, location, file);
                        fileNames.add(file.getOriginalFilename());
                    }
                );

            message = "Uploaded the files successfully: " + fileNames;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Fail to upload files!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    /**
     * {@code GET  /files} : get all the files.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of features in body.
     */
    @GetMapping("/upload/getAllFiles")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService
            .loadAll()
            .map(
                path -> {
                    String filename = path.getFileName().toString();
                    String url = MvcUriComponentsBuilder
                        .fromMethodName(FilesResource.class, "getFile", path.getFileName().toString())
                        .build()
                        .toString();

                    return new FileInfo(filename, url);
                }
            )
            .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    /**
     * {@code GET  /files} : get specific file.
     *@param filename to fetch.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of features in body.
     */
    @GetMapping("/upload/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
            .body(file);
    }
}
