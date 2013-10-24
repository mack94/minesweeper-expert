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
import javax.swing.JOptionPane;
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
        private JTextArea name, score, diff;

        public HighscoreFrame()
        {
            setLayout(new BorderLayout());

            name = new JTextArea("Name \n \n");
            name.setEditable(false);
            name.setBackground(Color.decode("#eeeeee"));
            name.setBorder(new LineBorder(Color.decode("#eeeeee"), 10));

            diff = new JTextArea("Difficulity \n \n");
            diff.setEditable(false);
            diff.setBackground(Color.decode("#eeeeee"));
            diff.setBorder(new LineBorder(Color.decode("#eeeeee"), 10));

            okBtn = new JButton("OK");
            okBtn.addActionListener(new FrameDispose());

            score = new JTextArea("Score \n \n");
            score.setEditable(false);
            score.setBackground(Color.decode("#eeeeee"));
            score.setBorder(new LineBorder(Color.decode("#eeeeee"), 10));

            loadHighscoreFile();

            add(diff, BorderLayout.CENTER);
            add(name, BorderLayout.WEST);
            add(score, BorderLayout.EAST);
            add(okBtn, BorderLayout.SOUTH);
            setPreferredSize(new Dimension(330, 220));

        }

        public void loadHighscoreFile()
        {
            String path = "mines/highscore.txt";;
            File file = new File(path);
            Scanner diskf = null;
            try
            {
                diskf = new Scanner(file);
            }
            catch (FileNotFoundException e)
            {
                JOptionPane.showMessageDialog(null, "The highscore.txt file could not be found!");
                e.printStackTrace();
            }

            String nameValue = name.getText();
            String scoreValue = score.getText();
            String difficulyValue = diff.getText();
            int index = 0;
            int index2 = 0;

            while (diskf.hasNextLine())
            {
                String line = diskf.nextLine();

                try
                {
                    index = line.indexOf(':');
                    index2 = line.indexOf(':', index + 1);
                    nameValue += line.substring(0, index) + "\n";
                    difficulyValue += line.substring(index + 1, index2) + "\n";
                    scoreValue += line.substring(index2 + 1, line.length()) + "\n";
                }
                catch (IndexOutOfBoundsException e)
                {
                    JOptionPane.showMessageDialog(null, "The highscore.txt file is currupt");
                    e.printStackTrace();
                }
            }

            name.setText(nameValue);
            diff.setText(difficulyValue);
            score.setText(scoreValue);

            diskf.reset();
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
