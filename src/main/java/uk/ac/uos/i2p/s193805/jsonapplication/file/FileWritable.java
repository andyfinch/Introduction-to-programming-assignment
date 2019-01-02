package uk.ac.uos.i2p.s193805.jsonapplication.file;

/**
 * Interface to be used for FileWritable content requiring implementation of getFileName and getContent
 */
public interface FileWritable {

    /**
     * The required path and fileName where the report should be saved
     * @return the required FileName
     */
    String getFileName();

    /**
     * The required content to be shown on the report
     * @return content to be included
     */
    String getContent();
}
