package mg.project.demo.service;

import mg.project.demo.sftp.SftpConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class SftpServiceImpl implements SftpService {

    @Autowired
    private SftpConfig sftpConfig;

    private DefaultSftpSessionFactory factory;

    @PostConstruct
    public void init() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(22);
        factory.setAllowUnknownKeys(true);
        factory.setUser("sftp_user");
        factory.setPassword("eloquant");
        this.factory = factory;
    }

    @Override
    public DefaultSftpSessionFactory getFactory() {
        return factory;
    }

    @Override
    public SftpSession getSession() {
        return factory.getSession();
    }
}
