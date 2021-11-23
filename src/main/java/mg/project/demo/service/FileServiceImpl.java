package mg.project.demo.service;

import lombok.extern.slf4j.Slf4j;
import mg.project.demo.sftp.SftpConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private SftpService sftpService;

    @Autowired
    private SftpConfig sftpConfig;

    @Autowired
    private ResourceLoader resourceLoader;

    public void upload() throws IOException {
        SftpSession session = sftpService.getSession();

        InputStream resourceAsStream = resourceLoader.getResource("classpath:mytextfile.txt").getInputStream();

        session.write(resourceAsStream, this.getDirectory() + "/mynewfile.txt");
        session.close();
    }

    public ByteArrayOutputStream download() throws IOException {
        SftpSession session = sftpService.getSession();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        session.read(this.getDirectory() + "/mynewfile.txt", outputStream);
        session.close();
        return outputStream;
    }

    private String getDirectory() {
        return "/" + sftpConfig.getDirectory();
    }

}
