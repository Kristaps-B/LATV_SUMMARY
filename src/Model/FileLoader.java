package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class FileLoader {

	private String fileContent = null;

	@SuppressWarnings("resource")
	public void loadFile(File file) throws Exception {
		String content = "";
		String str = "";

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));

			if (in.read() != 65279) {
				throw new UnsupportedEncodingException("Nav UTF-8 fails!");
			}

			while ((str = in.readLine()) != null) {
				content += str + "\n";
			}

			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Fails netika atrasts: " + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new Exception("TXT fails nav UTF-8 formatâ!");
		} catch (IOException e) {
			System.out.println("IO iznemums! " + e.getMessage());
		}

		fileContent = content;
	}

	// Iegust faila saturu
	public String getFileContent() {
		return fileContent;
	}
}
