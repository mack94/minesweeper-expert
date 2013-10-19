package mines;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Load
{
	public Load()
	{
		JFrame mainframe = new JFrame ("main");
	    mainframe.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
	    mainframe.getContentPane().add(new MainPanel());
	    mainframe.pack();
	    mainframe.setVisible(true);
	}
	
}
class MainPanel extends JPanel
{
  int returnVal=0;
  
  private JButton button;
  private JTextArea label;
  private JFileChooser fileChooser;
  
  public MainPanel()
  {
    label = new JTextArea();
    label.setEditable(false);
    label.setPreferredSize(new Dimension (200,200));
    label.setBackground (Color.decode("#A7E8B0"));
    
    fileChooser = new JFileChooser();
    
    button = new JButton ("Load File");
    button.addActionListener (new buttonListener());
    
    add(button);
    add(label);
    
    setBackground (Color.decode("#A7E8B0"));
  }
  public class buttonListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e) {
    
      // create new Panel
      class FileChooserPanel extends JPanel
      {
        public FileChooserPanel()
        {
        }
      }
     FileChooserPanel fileChooserPanel = new FileChooserPanel ();
      
      
      //Handle open button action.
    if (e.getSource() == button) {
        int returnVal = fileChooser.showOpenDialog(fileChooserPanel);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            //This is where a real application would open the file.
            System.out.println("Opening: " + file.getName());
            try{
              Scanner diskf = new Scanner(file);
              String fileText="";
              while(diskf.hasNextLine())
              {
                String line = diskf.nextLine();
                fileText += line + "\n";
              }
              label.setText(fileText);
            }
            catch(FileNotFoundException ex) 
            {
              ex.printStackTrace();
            }
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }
    }
  }
}