package mg.project.demo.sftp;

import lombok.extern.slf4j.Slf4j;
import mg.project.demo.service.FileServiceImpl;
import mg.project.demo.service.SftpService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.sftp.filters.SftpSimplePatternFileListFilter;
import org.springframework.integration.sftp.gateway.SftpOutboundGateway;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizer;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizingMessageSource;
import org.springframework.integration.sftp.outbound.SftpMessageHandler;
import org.springframework.messaging.MessageHandler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Configuration
public class SftpSyncConfiguration {

    @Autowired
    private SftpConfig sftpConfig;

    @Autowired
    private SftpService sftpService;

    @Bean
    public SftpInboundFileSynchronizer synchronizer() {
        SftpInboundFileSynchronizer sftpInboundFileSynchronizer = new SftpInboundFileSynchronizer(sftpService.getFactory());
        sftpInboundFileSynchronizer.setDeleteRemoteFiles(true);
        sftpInboundFileSynchronizer.setRemoteDirectory(sftpConfig.getDirectory());
        sftpInboundFileSynchronizer.setFilter(new SftpSimplePatternFileListFilter("*.txt"));
        return sftpInboundFileSynchronizer;
    }

    @Bean
    @InboundChannelAdapter(channel = "filesuploaded", poller = @Poller(fixedDelay = "10000"))
    public MessageSource<File> sftpMessageSource() {
        SftpInboundFileSynchronizingMessageSource source = new SftpInboundFileSynchronizingMessageSource(synchronizer());
        source.setLocalDirectory(new File("tmp/incoming"));
        source.setAutoCreateLocalDirectory(true);
        source.setMaxFetchSize(1);
        return source;
    }

    @ServiceActivator(inputChannel = "filesuploaded")
    public void handleIncomingFile(File file) throws IOException {
        log.info("File {}", file.getName());
        String content = FileUtils.readFileToString(file, "UTF-8");
        log.info("Content: {}", content);
        if (file.exists())
            file.delete();
    }

    @Bean
    @ServiceActivator(inputChannel = "filestreat")
    public MessageHandler uploadHandler() {
        SftpMessageHandler messageHandler = new SftpMessageHandler(sftpService.getFactory());
        messageHandler.setRemoteDirectoryExpression(new LiteralExpression(sftpConfig.getDirectory()));
        messageHandler.setFileNameGenerator(message -> String.format("mytextfile_%s.txt", LocalDateTime.now()));
        return messageHandler;
    }

    @Bean
    @ServiceActivator(inputChannel = "filestreat")
    public MessageHandler uploadMessageHandler() {
        return new SftpOutboundGateway(sftpService.getFactory(), "ls", sftpConfig.getDirectory());
    }

}
