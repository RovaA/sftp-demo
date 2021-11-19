package mg.project.demo.service;

import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;

public interface SftpService {

    DefaultSftpSessionFactory getFactory();

    SftpSession getSession();

}
