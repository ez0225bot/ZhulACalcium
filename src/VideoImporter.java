import javax.swing.*;
import java.awt.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;




public class VideoImporter extends JFrame {

    private File selectedFile;
    private String selectedFileName;
    private JButton importButton;
    private JFrame frame;
    private boolean HasVideo;
    private JFileChooser filechooser;
    private FileNameExtensionFilter filter;


    public VideoImporter() {

        frame = new JFrame("File Importer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        importButton = new JButton("Import File");
        importButton.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(importButton);
        selectedFileName = "";

    importButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
             filechooser = new JFileChooser(System.getProperty("user.home") + "/Desktop/");
            filechooser.setDialogTitle("Select your AVI File");

             filter = new FileNameExtensionFilter("Video Files", "avi");
            filechooser.setFileFilter(filter);

            int result = filechooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = filechooser.getSelectedFile();

                if (selectedFile.getName().toLowerCase().endsWith("avi")) {
                    selectedFileName = selectedFile.getAbsolutePath();
                    HasVideo=true;
                    JOptionPane.showMessageDialog(frame, "File imported successfully:\n" + selectedFile.getAbsolutePath()+"\nAllow the software to convert video to greyscale.");
                    frame.dispose();
                } else {
                    HasVideo=false;
                    JOptionPane.showMessageDialog(frame, "Invalid file type. Please select an AVI file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                HasVideo=false;
                JOptionPane.showMessageDialog(frame, "No file selected.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    });

    frame.setLocationRelativeTo(null);
    frame.setVisible(true);}


    public boolean hasVideo()
    {
        return HasVideo;
    }

    public String getFilePath() {
        if (!HasVideo || selectedFileName.isEmpty()) {
            throw new IllegalStateException("No valid video file selected.");
        }
        return selectedFileName;
    }
}
