import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class FileLoader {
	
	private String fileContent = null;
	
	public void loadFile(File file)
	{
		String content = null;
		
		//Atver failu
		try
		{
			FileReader reader = new FileReader(file);
			
			char [] chars = new char[(int)file.length()];
			reader.read(chars);
			
			content = new String(chars);
			
			reader.close();
			
		}
		//Iznemumi
		catch (FileNotFoundException e)
		{
			System.out.println("Fails netika atrasts: "+e.getMessage());
		}
		catch (IOException e)
		{
			System.out.println("Faila ielades kluda: "+e.getMessage());
		}
		
		
		
		
		fileContent = content;
	}
	
	//Iegust faila saturu
	public String getFileContent()
	{
		return fileContent;
	}
}
