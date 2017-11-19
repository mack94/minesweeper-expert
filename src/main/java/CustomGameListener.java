import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CustomGameListener implements ActionListener
{
    private JFrame CustomGameFrame = new JFrame("Custom...");

    //Initialise variables and set them to the current ones on the field
    private int minesCount;
    private int width;
    private int height;

    public void actionPerformed(ActionEvent e)
    {
    	minesCount = MineFrame.getNoOfMines();
    	width = MineFrame.getNoOfCols();
    	height = MineFrame.getNoOfRows();
    	
        CustomGameFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        CustomGameFrame.setTitle("Custom Game");//Title of the frame
        CustomGameFrame.getContentPane().add(new CustomGamePanel());
        CustomGameFrame.setResizable(false);
        CustomGameFrame.pack();
        CustomGameFrame.setLocationRelativeTo(null); //Center the frame
        CustomGameFrame.setVisible(true);
    }

    class CustomGamePanel extends JPanel
    {
        private JButton saveBtn, cancelBtn;
        private JLabel noOfMinesLabel, boardWidthLabel, boardHeightLabel;
        private JSlider noOfMines, boardWidth, boardHeight;

        //Create the GUI
        public CustomGamePanel()
        {
            //Create Buttons
            saveBtn = new JButton("Save");
            cancelBtn = new JButton("Cancel");

            //Create Labels
            noOfMinesLabel = new JLabel("Number of Mines:");
            boardWidthLabel = new JLabel("Board Width:");
            boardHeightLabel = new JLabel("Board Height:");

            //Create Sliders
            noOfMines = new JSlider();
            boardWidth = new JSlider();
            boardHeight = new JSlider();

            //Set slider properties
            noOfMines.setMaximum(600);
            noOfMines.setMinimum(10);
            noOfMines.setFocusable(false);// fixes bug were a value can't be set while slider is in focus - tab keying is therefore disabled
            noOfMines.setValue(minesCount);
            noOfMines.setMajorTickSpacing(100);
            noOfMines.setMinorTickSpacing(10);
            noOfMines.setPaintLabels(true);
            noOfMines.setPaintTicks(true);

            boardWidth.setMaximum(100);
            boardWidth.setMinimum(10);
            boardWidth.setFocusable(false);// fixes bug were a value can't be set while slider is in focus - tab keying is therefore disabled
            boardWidth.setValue(width);
            boardWidth.setMajorTickSpacing(20);
            boardWidth.setMinorTickSpacing(5);
            boardWidth.setPaintLabels(true);
            boardWidth.setPaintTicks(true);

            boardHeight.setMaximum(60);
            boardHeight.setMinimum(10);
            boardHeight.setFocusable(false);// fixes bug were a value can't be set while slider is in focus - tab keying is therefore disabled
            boardHeight.setValue(height);
            boardHeight.setMajorTickSpacing(10);
            boardHeight.setMinorTickSpacing(5);
            boardHeight.setPaintLabels(true);
            boardHeight.setPaintTicks(true);

            //Add sliders, buttons and labels to panel
            add(boardWidthLabel);
            add(boardWidth);
            add(boardHeightLabel);
            add(boardHeight);
            add(noOfMinesLabel);
            add(noOfMines);
            add(saveBtn);
            add(cancelBtn);

            //Set size of the panel
            setPreferredSize(new Dimension(330, 220));

            //Associate actionListeners
            noOfMines.addChangeListener(new noOfMinesListener());
            boardWidth.addChangeListener(new boardWidthListener());
            boardHeight.addChangeListener(new boardHeightListener());

            cancelBtn.addActionListener(new FrameDispose());
            saveBtn.addActionListener(new createCustomGameListener());
        }
    }

    //Change Listeners
    class noOfMinesListener implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting())//check that the user has finished moving the slider
            {
                minesCount = source.getValue();//set the value from the slider to minesCount
            }
        }
    }

    class boardWidthListener implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting())//check that the user has finished moving the slider
            {
                width = source.getValue();//set the value from the slider to width
            }
        }
    }

    class boardHeightListener implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting())//check that the user has finished moving the slider
            {
                height = source.getValue();//set the value from the slider to height
            }
        }
    }

    //Action Listeners
    class FrameDispose implements ActionListener
    {
        
        //Cancel Button
        public void actionPerformed(ActionEvent e)
        {
            CustomGameFrame.dispose();//Dispose of the frame
        }
    }

    class createCustomGameListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (minesCount > width * height)
            {
                JOptionPane.showMessageDialog(null, "You selected more Mines that can fit on the Board!");//Show a message if the user has chosen more mines that can fit on the board
            }
            else
            {
            	Board.setDifficulty(3);
                MineFrame.setNoOfMines(minesCount);//Set the value of mineCount to the frame
                MineFrame.setNoOfCols(width);//Set the columns to the panel
                MineFrame.setNoOfRows(height);//Set the Rows to the panel
                MineFrame.calcDimentions(); //set frame width and height
                MineFrame.startNewGame();//call the newGame method
                CustomGameFrame.dispose();//call the dispose method to close the frame
            }
        }
    }
}
