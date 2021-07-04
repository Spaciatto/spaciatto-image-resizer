package br.com.spaciatto.imageresizer.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileWalker {

    private List<File> files = new ArrayList<>();

    public List<File> walk(String path ) {

        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return files;

        for (File f : list) {
            if (f.isDirectory()) {
                walk(f.getAbsolutePath());
            }
            else {
                files.add(f);
            }
        }

        return files;

    }

}