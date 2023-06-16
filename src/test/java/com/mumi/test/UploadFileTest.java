package com.mumi.test;

import com.mumi.reggie.utils.AliOSSUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class UploadFileTest {
    @Test
    public void test1(){
        String fileName = "afdsafga.jpg";
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(suffix);
    }

    @Test
    public void downTest() throws IOException {
        AliOSSUtils aliOSSUtils = new AliOSSUtils();

        InputStream inputStream = aliOSSUtils.download("0a596c86-8fe3-4f53-b21a-9cc54a40b0c1.png");
        inputStream.close();
    }
}
