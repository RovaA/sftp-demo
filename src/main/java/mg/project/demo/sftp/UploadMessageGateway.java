package mg.project.demo.sftp;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.io.File;

@MessagingGateway
public interface UploadMessageGateway {

    @Gateway(requestChannel = "filestreat")
    void uploadFile(File file);

}
