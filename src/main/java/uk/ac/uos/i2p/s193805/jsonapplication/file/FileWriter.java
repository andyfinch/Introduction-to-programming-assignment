package uk.ac.uos.i2p.s193805.jsonapplication.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *  Takes a FileWritable object and saves content to file
 */
public class FileWriter {

    private FileWritable fileWritable;

    public FileWriter(FileWritable fileWritable) {
        this.fileWritable = fileWritable;
    }

    /**
     * Uses data from the FileWritable object to save file with required content
     * @throws IOException
     */
    public void writeToFile() throws IOException {

        //Get the file reference
        Path path = Paths.get(fileWritable.getFileName());

        //Use try-with-resource to get auto-closeable writer instance
        try (BufferedWriter writer = Files.newBufferedWriter(path))
        {
            writer.write(fileWritable.getContent());
        }
    }
}
