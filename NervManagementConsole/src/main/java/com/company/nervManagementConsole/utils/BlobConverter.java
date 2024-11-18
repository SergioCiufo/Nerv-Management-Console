package com.company.nervManagementConsole.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;

public class BlobConverter {
	public static Blob generateBlob(String filePath, Connection connection) throws Exception{
		InputStream is = new FileInputStream(filePath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte [] buffer = new byte[1024];
		int read = -1;
		while ( (read = is.read(buffer, 0, buffer.length)) != -1)  {
			baos.write(buffer, 0, read);
		}
		baos.flush();
		byte [] data = baos.toByteArray();
        Blob result = connection.createBlob();  // Crea un Blob direttamente dalla connessione
        try (OutputStream os = result.setBinaryStream(1)) {
            os.write(data);  // Scrivi i byte nel Blob
        }
		baos.close();
		is.close();
		return result;
	}
	
	public static void saveFile(Blob blob,String filePath) throws Exception {
		InputStream is = blob.getBinaryStream();
    	FileOutputStream fos = new FileOutputStream(filePath);
		byte [] buffer = new byte[1024];
		int read = -1;
		while ( (read = is.read(buffer, 0, buffer.length)) != -1)  {
			fos.write(buffer, 0, read);
		}
		fos.flush();
		fos.close();
	}
}

