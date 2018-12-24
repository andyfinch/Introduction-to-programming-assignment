package uk.ac.uos.i2p.s193805.jsonapplication.file;

import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task.AddTask;
import uk.ac.uos.i2p.s193805.jsonapplication.taskhandling.task.Task;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWriter {

    FileWritable fileWritable;

    public FileWriter(FileWritable fileWritable) {
        this.fileWritable = fileWritable;
    }

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
