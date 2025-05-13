import java.io.*;


public class JavaToPythonVideoPlayer {
    public void JavaToPythonVideo(String videoFilePath) {
        try {
            String pythonScriptPath = "/Users/elenazhu/PycharmProjects/PythonProject/VideoPlayer.py";  // path to python script
            ProcessBuilder pb = new ProcessBuilder(
                    "/Users/elenazhu/myenv/bin/python",  // bc my code is run in a virtual environment
                    pythonScriptPath,
                    videoFilePath
            );


            // PYTHONPATH to my virtual environment's directory
            pb.environment().put("PYTHONPATH", "/Users/elenazhu/myenv/lib/python3.13/site-packages");


            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

