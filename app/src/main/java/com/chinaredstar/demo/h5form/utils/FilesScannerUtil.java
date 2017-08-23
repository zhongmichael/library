package com.chinaredstar.demo.h5form.utils;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by hairui.xiang on 2017/8/22.
 */

public class FilesScannerUtil {
    public boolean isExist(){
        File dir = new File("");
        dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return false;
            }
        });
        return false;
    }
}
