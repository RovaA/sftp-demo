package mg.project.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mg.project.demo.service.FileService;
import mg.project.demo.sftp.UploadMessageGateway;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

    @Autowired
    private FileService fileService;

    private final UploadMessageGateway gateway;

    @GetMapping("api/upload/{content}")
    public String uploadFile(@PathVariable String content, @RequestParam("extra") String extra) throws IOException {
        String resultContent = String.format("Hello... We have uploaded... %s \n with some extra stuff: %s", content, extra);

        File file = new File("tmp/mytmpfile.txt");
        if (file.exists())
            file.delete();

        FileUtils.write(file, resultContent, "UTF-8");
        gateway.uploadFile(file);

        return resultContent;
    }

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
