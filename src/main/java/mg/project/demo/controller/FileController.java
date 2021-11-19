package mg.project.demo.controller;

import lombok.extern.slf4j.Slf4j;
import mg.project.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("api/file/upload")
    public void upload() {
        try {
            fileService.upload();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    @GetMapping("api/file/download")
    public String download() {
        try {
            return new String(fileService.download().toByteArray());
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return "";
    }

}
