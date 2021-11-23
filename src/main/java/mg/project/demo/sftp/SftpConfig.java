package mg.project.demo.sftp;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class SftpConfig {

    public final String DIRECTORY = "/upload";

    @Value("${sftp.host:127.0.0.1}")
    private String host;

    @Value("${sftp.port:22}")
    private String port;

    @Value("${sftp.user}")
    private String user;

    @Value("${sftp.password}")
    private String password;

    public String getDirectory() {
        return user + DIRECTORY;
    }

}
