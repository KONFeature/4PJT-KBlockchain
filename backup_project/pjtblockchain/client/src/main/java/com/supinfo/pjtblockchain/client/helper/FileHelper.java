package com.supinfo.pjtblockchain.client.helper;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHelper {

    /**
     * Generic method helping us to write or append to a file and create it an it's parent if they doesn't exist
     * @param file
     * @param toWrite
     * @param append
     * @return
     * @throws IOException
     */
    public static File writeToFile(File file, String toWrite, boolean append) throws IOException {
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if(!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file, append);
        fileWriter.append(append ? toWrite + StringUtils.CR + StringUtils.LF : toWrite);
        fileWriter.close();
        return file;
    }

}
