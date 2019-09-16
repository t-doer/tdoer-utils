/*
 * Copyright 2017-2019 T-Doer (tdoer.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tdoer.utils.zip;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * The class is a utility tool for zip and download a file. For example:
 *
 * <pre>
 *     OutputStream out = response.getOutputStream();
 *     DownloadZipper dz = new DownloadZipper(out, "GBK);
 *
 *     // Example I: zip a local file
 *     dz.zipEntry("a.txt", new File("a.txt");
 *
 *     // Example II: zip a remote file
 *     InputStream is = null;
 *     try{
 *        URL url = new URL("http://www.bybon.com/static/a.jpg")
 *        URLConnection conn = url.openConnection();
 *        is = conn.openStream();
 *        dz.zipEntry("static/a.jpg", is);
 *     }finally{
 *         if(is != null){
 *             is.close();
 *         }
 *     }
 *
 *     // Example III: A generating excel file
 *     dz.startEntry("data.xls")
 *     HSSFWorkbook wb = bizz.exportOrder(dto);
 *     // ...
 *     wb.write(dz.getZipOutputStream());
 *     dz.closeEntry()
 *
 *     // Example IV:
 *     dz.startEntry("b.txt");
 *     dz.writeEntry(new StringBufferInputStream("Hello world");
 *     dz.closeEntry();
 *
 *     // At last, finish zipping
 *     dz.finishZip()
 *
 *
 * </pre>
 *
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */
public class DownloadZipper {
    private ZipOutputStream zipOut;

    public DownloadZipper(OutputStream outputStream, String encoding){
        zipOut = new ZipOutputStream(outputStream);
        zipOut.setEncoding(encoding);
    }

    /**
     * Start an entry of given name
     *
     * @param entryName
     * @throws IOException
     */
    public void startEntry(String entryName) throws IOException{
        ZipEntry entry = new ZipEntry(entryName);
        zipOut.putNextEntry(entry);
    }

    /**
     * Write entry data from the input stream
     *
     * @param inputStream
     * @throws IOException
     */
    public void writeEntry(InputStream inputStream) throws IOException{
        byte data[] = new byte[1024];
        BufferedInputStream bis = new BufferedInputStream(inputStream, data.length);
        int count;
        while ((count = bis.read(data, 0, data.length)) != -1) {
            zipOut.write(data, 0, count);
        }

        bis.close();
        inputStream.close();
    }

    /**
     * Close an entry
     *
     * @throws IOException
     */
    public void closeEntry() throws  IOException{
        zipOut.closeEntry();
        zipOut.flush();
    }

    /**
     * Get the Zip Output Stream
     *
     * @return
     */
    public ZipOutputStream getZipOut() {
        return zipOut;
    }


    /**
     * Zip an entry, that's, it's same with the codes below:
     *
     * <pre>
     *     startEntry(entryName);
     *     writeEntry(inputStream);
     *     closeEntry();
     * </pre>
     * @param entryName
     * @param inputStream
     * @throws IOException
     */
    public void  zipEntry(String entryName, InputStream inputStream) throws IOException {
        ZipEntry entry = new ZipEntry(entryName);
        zipOut.putNextEntry(entry);

        byte data[] = new byte[1024];
        BufferedInputStream bis = new BufferedInputStream(inputStream, data.length);
        int count;
        while ((count = bis.read(data, 0, data.length)) != -1) {
            zipOut.write(data, 0, count);
        }

        bis.close();
        inputStream.close();
        zipOut.closeEntry();
        zipOut.flush();
    }

    public void finishZip() throws IOException{
        zipOut.close();
    }

}
