package com.zhujunji.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Optional;

/**
 * 文件工具类
 */
@Slf4j
public class FileUtil {

    /**
     * 删除文件
     * @param file  文件或者文件夹路径
     * @return boolean
     */
    public static boolean delete(String file){
        return delete(new File(file));
    }

    /**
     * 删除文件
     * @param file  文件或者文件夹
     * @return boolean
     */
    public static boolean delete(File file){
        if(file != null && file.exists()){
            if(file.isDirectory()){
                File[] childFiles = file.listFiles();
                if(childFiles != null){
                    for (File childFile : childFiles) {
                        // 文件夹递归删除, 文件直接删除
                        boolean success = childFile.isDirectory() ? delete(childFile) : childFile.delete();
                        if(!success){
                            log.error("file delete failed: {}",childFile.getAbsolutePath());
                            return false;
                        }
                    }
                }
            }
            return file.delete();
        }
        return false;
    }

    public static Optional<String> getSuffix(String fileName){
        String suffix = fileName == null ? null : fileName.substring(fileName.lastIndexOf(".") + 1,fileName.length());
        return Optional.ofNullable(suffix);
    }

    public static void main(String[] args) {
        System.out.println(getSuffix(null));
        System.out.println(getSuffix("123.png"));
        System.out.println(getSuffix("123.png."));
        System.out.println(getSuffix(" .xx  .txt"));
    }
}
