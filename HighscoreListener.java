package mines;

import java.awt.BorderLayout;
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
import javax.swing.border.LineBorder;
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
        private JTextArea name, score;

        //private JLabel heading;

        public HighscoreFrame()
        {
            setLayout(new BorderLayout());

            name = new JTextArea("Name \n \n");
            name.setEditable(false);
            name.setBackground(Color.decode("#eeeeee"));
            name.setBorder(new LineBorder(Color.decode("#eeeeee"), 10));

            okBtn = new JButton("OK");
            okBtn.addActionListener(new FrameDispose());

            score = new JTextArea("Score \n \n");
            score.setEditable(false);
            score.setBackground(Color.decode("#eeeeee"));
            score.setBorder(new LineBorder(Color.decode("#eeeeee"), 10));

            loadHighscoreFile();

            add(name, BorderLayout.WEST);
            add(score, BorderLayout.CENTER);
            add(okBtn, BorderLayout.SOUTH);
            setPreferredSize(new Dimension(330, 220));

        }

        public void loadHighscoreFile()
        {
            // this is for Eclipse
            String path = System.getProperty("user.dir") + "/bin/mines/highscore.txt";
            // for JPL & co
            //String path = System.getProperty("user.dir") + "/mines/highscore.txt"; 
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

            String nameValue = name.getText();
            String scoreValue = score.getText();
            int index = 0;

            while (diskf.hasNextLine())
            {
                String line = diskf.nextLine();
                index = line.indexOf(':');
                nameValue += line.substring(0, index) + "\n";
                scoreValue += line.substring(index + 1, line.length() - 1) + "\n";
            }

            name.setText(nameValue);
            score.setText(scoreValue);
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
