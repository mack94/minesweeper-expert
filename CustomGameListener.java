package mines;

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

    private int minesCount;
    private int width;
    private int height;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        CustomGameFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        CustomGameFrame.getContentPane().add(new CustomGamePanel());
        CustomGameFrame.pack();
        CustomGameFrame.setLocationRelativeTo(null); // centers the frame
        CustomGameFrame.setResizable(false);
        CustomGameFrame.setVisible(true);
    }

    class CustomGamePanel extends JPanel
    {
        private JButton saveBtn, cancelBtn;
        private JLabel noOfMinesLabel, boardWidthLabel, boardHeightLabel;
        private JSlider noOfMines, boardWidth, boardHeight;

        public CustomGamePanel()
        {
            // Buttons
            saveBtn = new JButton("Save");
            cancelBtn = new JButton("Cancel");

            // Labels
            noOfMinesLabel = new JLabel("Number of Mines:");
            boardWidthLabel = new JLabel("Board Width:");
            boardHeightLabel = new JLabel("Board Height:");

            // Sliders
            noOfMines = new JSlider();
            boardWidth = new JSlider();
            boardHeight = new JSlider();

            //set slider properties
            noOfMines.setMaximum(500);
            noOfMines.setMinimum(10);
            noOfMines.setValue(20); // need to pass in an int
            noOfMines.setMajorTickSpacing(100);
            noOfMines.setMinorTickSpacing(10);
            //noOfMines.setSnapToTicks(true);
            noOfMines.setPaintLabels(true);
            noOfMines.setPaintTicks(true);

            boardWidth.setMaximum(100);
            boardWidth.setMinimum(10);
            boardWidth.setValue(20); // need to pass in an int
            boardWidth.setMajorTickSpacing(20);
            boardWidth.setMinorTickSpacing(5);
            //boardWidth.setSnapToTicks(true);
            boardWidth.setPaintLabels(true);
            boardWidth.setPaintTicks(true);

            boardHeight.setMaximum(60);
            boardHeight.setMinimum(10);
            boardHeight.setValue(20); // need to pass in an int
            boardHeight.setMajorTickSpacing(10);
            boardHeight.setMinorTickSpacing(5);
            //boardHeight.setSnapToTicks(true);
            boardHeight.setPaintLabels(true);
            boardHeight.setPaintTicks(true);

            // add to panel
            add(boardWidthLabel);
            add(boardWidth);
            add(boardHeightLabel);
            add(boardHeight);
            add(noOfMinesLabel);
            add(noOfMines);
            add(saveBtn);
            add(cancelBtn);

            setPreferredSize(new Dimension(330, 220));

            // initialising actions
            noOfMines.addChangeListener(new noOfMinesListener());
            boardWidth.addChangeListener(new boardWidthListener());
            boardHeight.addChangeListener(new boardHeightListener());

            cancelBtn.addActionListener(new FrameDispose());
            saveBtn.addActionListener(new createCustomGameListener());
        }
    }

    // Change Listener Classes
    class noOfMinesListener implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e)
        {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting())
            {
                minesCount = source.getValue();
                System.out.println(minesCount);
            }
        }
    }

    class boardWidthListener implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e)
        {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting())
            {
                width = source.getValue();
                System.out.println(width);
            }
        }
    }

    class boardHeightListener implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e)
        {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting())
            {
                height = source.getValue();
                System.out.println(height);
            }
        }
    }

    class FrameDispose implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            CustomGameFrame.dispose();
        }
    }
    class createCustomGameListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(minesCount>width*height){
                JOptionPane.showMessageDialog(null, "You selected more mines that can fit on the board!");
            }
            else{
            MineFrame.setNoOfMines(minesCount);
            MineFrame.setNoOfCols(width);
            MineFrame.setNoOfRows(height);
            MineFrame.startNewGame();
            CustomGameFrame.dispose();
            }
        }
    }
}
