package mines;

import java.awt.Color;
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
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class HighscoreListener implements MenuListener
{
    private JFrame HighscoreFrame = new JFrame("Highscore");

    @Override
    public void menuSelected(MenuEvent e)
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
            list.setEditable(false);
            list.setBackground(Color.decode("#eeeeee"));

            loadHighscoreFile();

            add(list);
            add(okBtn);
            setPreferredSize(new Dimension(330, 220));

        }

        public void loadHighscoreFile()
        {
          //this is for eclipse
            String path = System.getProperty("user.dir")+"/bin/mines/highscore.txt"; 
         // for JPL & co
            //String path = System.getProperty("user.dir")+ "/mines/highscore.txt"; 
            File file = new File(path);
            Scanner diskf = null;
            try
            {
                diskf = new Scanner(file);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            String fileText = "";

            while (diskf.hasNextLine())
            {
                String line = diskf.nextLine();
                fileText += line + "\n";
            }
            list.setText(fileText);
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

    @Override
    public void menuCanceled(MenuEvent e)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void menuDeselected(MenuEvent e)
    {
        // TODO Auto-generated method stub

    }
}