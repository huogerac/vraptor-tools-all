package br.com.caelum.vraptor.filegen;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileSystem {

	public static boolean copyfile(File source, File target) throws FileGeneratorException {

		try {

			InputStream in = new FileInputStream(source);
			OutputStream out = new FileOutputStream(target);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();

			return true;

		} catch (IOException e) {
			throw new FileGeneratorException(e.getMessage());
		}
	}



	public synchronized static boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}





	public static File read(String filename) throws FileGeneratorException {
		File file = new File(filename);
		
		if (!file.exists()) {
			throw new FileGeneratorException("Arquivo nao encontrado: " + filename);
		}
		if (!file.canRead()) {
			throw new FileGeneratorException("Erro ao ler arquivo: " + filename);
		}		

		return file;
	}



	public static boolean delete(String filename) {
		File file = new File(filename);
		
		if (!file.exists()) {
			return false;
		}
		
		if (file.isFile()) {
			return file.delete();
		}
		
		return FileSystem.deleteDirectory(file);
	}



	public static File createFolder(String filename) throws FileGeneratorException {

		File file = null;
		
		String d = "";
		String[] subdir = filename.split("/");
		for (int i = 0; i < subdir.length; i++) {
			d += subdir[i] + "/";
			file = createDirectory(d);
		}

		return file;
	}
	
	private static File createDirectory(String directory) throws FileGeneratorException {
		File file = new File(directory);
		
		
		if (file.exists() && file.canWrite()) {
			return file;
		}
		
		boolean create = file.mkdir();
		if (!create) {
			//throw new FileGemException("Error creating: " + filename);
		}

		return file;
	}



	public static File write(String filename) throws FileGeneratorException {
		
		File file = new File(filename);
		if (!file.canWrite()) {
			throw new FileGeneratorException("Error creating: " + filename);
		}
		return file;
	}
	
	public static void writeToFile(File outputfile, byte[] content) throws Exception {
		ByteArrayInputStream is = new ByteArrayInputStream(content);
		FileOutputStream out;
		
		try {
			
			out = new FileOutputStream(outputfile);
			byte buf[]=new byte[1024];
			int len;

			while ((len=is.read(buf))>0)
				out.write(buf,0,len);
			
			out.close();
			is.close();
			
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
	}


	public static File writeNewFile(String filename) throws FileGeneratorException {
		
		File file = new File(filename);
		try {
			
			file.createNewFile();
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileGeneratorException("Error creating new file: " + filename);
		}
		return file;
	}	

	
	public static String readContent(File file) {
		
		String source = "";
        try {
        	
        	InputStream is = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while (is.available() > 0) {
                baos.write(is.read());
            }
            
            source = baos.toString();
            is.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
		return source;
		
	}	

	
	
}
