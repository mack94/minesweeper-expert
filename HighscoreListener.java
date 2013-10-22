package mines;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class HighscoreListener implements ActionListener
{
    private JFrame HighscoreFrame = new JFrame("Highscore");

    @Override
    public void actionPerformed(ActionEvent e)
    {
        HighscoreFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        HighscoreFrame.getContentPane().add(new HighscoreFrame());
        HighscoreFrame.pack();
        HighscoreFrame.setLocationRelativeTo(null); // centers the frame
        HighscoreFrame.setResizable(false);
        HighscoreFrame.setVisible(true);
    }

    class HighscoreFrame extends JPanel
    {
        private JButton okBtn;
        private JTextArea list;

        public HighscoreFrame()
        {
            okBtn = new JButton("OK");
            okBtn.addActionListener(new FrameDispose());

            list = new JTextArea("");

            loadHighscoreFile();
            
            add(list);
            add(okBtn);
            setPreferredSize(new Dimension(330, 220));

        }

        public void loadHighscoreFile()
        {
            File file = new File("highscore.txt");
            // open the file.
            System.out.println("Opening: " + file.getName());
            try
            {
                Scanner diskf = new Scanner(file);
                String fileText = "";
                
                while(diskf.hasNextLine())
                {
                  String line = diskf.nextLine();
                  fileText += line + "\n";
                }
                list.setText(fileText);
                
            }

            catch (FileNotFoundException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    class FrameDispose implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            HighscoreFrame.dispose();
        }
    }
}