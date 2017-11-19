import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

public class HighscoreListener implements ActionListener
{
    private JFrame HighscoreFrame = new JFrame("Highscore");

    public void actionPerformed(ActionEvent e)
    {
        HighscoreFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        HighscoreFrame.getContentPane().add(new HighscoreFrame());
        HighscoreFrame.setResizable(false);
        HighscoreFrame.pack();
        HighscoreFrame.setLocationRelativeTo(null); //Centers the frame
        HighscoreFrame.setVisible(true);
    }

    class HighscoreFrame extends JPanel
    {
        //Initialise GUI
        private JButton okBtn;
        private JTextArea name, score, diff;

        public HighscoreFrame()
        {

            //Create Name column
            name = new JTextArea("Name \n \n");
            name.setEditable(false);
            name.setBackground(Color.decode("#eeeeee"));
            name.setBorder(new LineBorder(Color.decode("#eeeeee"), 10));

            //Create difficulty column
            diff = new JTextArea("Difficulity \n \n");
            diff.setEditable(false);
            diff.setBackground(Color.decode("#eeeeee"));
            diff.setBorder(new LineBorder(Color.decode("#eeeeee"), 10));

            //Create Time column
            score = new JTextArea("Time \n \n");
            score.setEditable(false);
            score.setBackground(Color.decode("#eeeeee"));
            score.setBorder(new LineBorder(Color.decode("#eeeeee"), 10));

            //Create OK Button
            okBtn = new JButton("OK");
            okBtn.addActionListener(new FrameDispose());

            loadHighscoreFile();//call the method to load the highscore.txt file;

            //Add objects to board
            add(name);
            add(diff);
            add(score);
            add(okBtn);
            setPreferredSize(new Dimension(330, 600));

        }

        //Method to load the highscore.txt and add the text to the respective textArea
        public void loadHighscoreFile()
        {
            String path = "highscore.txt";;
            File file = new File(path);
            try
            {
                file.createNewFile();
                Scanner diskf = new Scanner(file);
                
                //Get the initial values from the TextAreas
                String nameValue = name.getText();
                String scoreValue = score.getText();
                String difficulyValue = diff.getText();

                //Initialise 2 index values
                int index = 0;
                int index2 = 0;
                int lineNo = 1;

                while (diskf.hasNextLine())
                {
                    String line = diskf.nextLine();

                    try
                    {
                        index = line.indexOf(':');//Search through the line for ":" and return the index
                        index2 = line.indexOf(':', index + 1);//Search through the line for the 2nd ":" and return the index
                        nameValue += line.substring(0, index) + "\n";//Grab the before first ":" and add it to nameValue
                        difficulyValue += line.substring(index + 1, index2) + "\n";//Grab the string between the ":"s and add it to difficulyValue
                        scoreValue += line.substring(index2 + 1, line.length()) + "\n";//Grab the String after the 2nd ":" and add it to scoreValue
                    }
                    catch (IndexOutOfBoundsException e)//Exception handeling
                    {
                        JOptionPane.showMessageDialog(null, "line " + lineNo + " in highscore.txt is currupt");
                    }

                    lineNo++;
                }

                name.setText(nameValue);//Set to the new values
                diff.setText(difficulyValue);//Set to the new values
                score.setText(scoreValue);//Set to the new values

                //close scanner
                diskf.close();

            }
            catch (FileNotFoundException e)//Exception handling
            {
                JOptionPane.showMessageDialog(null, "The '" + file.getAbsolutePath() + "' file could not be found!");
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(null, "The '" + file.getAbsolutePath() + "' file could not be Created!");
            }
        }
    }

    class FrameDispose implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            HighscoreFrame.dispose();//Dispose of frame
        }
    }
}
