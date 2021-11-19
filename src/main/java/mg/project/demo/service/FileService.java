package mg.project.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface FileService {

    void upload() throws IOException;

    ByteArrayOutputStream download() throws IOException;
}
