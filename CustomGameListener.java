package mines;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;

public class CustomGameListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{

		JFrame CustomGameFrame = new JFrame("Custom...");
		CustomGameFrame
				.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		CustomGameFrame.getContentPane().add(new CustomGamePanel());
		CustomGameFrame.pack();
		CustomGameFrame.setLocationRelativeTo(null); // centers the frame
		CustomGameFrame.setResizable(false);
		CustomGameFrame.setVisible(true);
	}

	class CustomGamePanel extends JPanel
	{
		private int maxMines = 50;
		private int minMines = 10;
		private int max = 200;
		private int min = 20;

		private int minesCount, width, height;
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
			noOfMines.setMaximum(maxMines);
			noOfMines.setMinimum(minMines);
			noOfMines.setValue(10); // need to pass in an int
			noOfMines.setMajorTickSpacing(10);
			noOfMines.setMinorTickSpacing(1);
			noOfMines.setSnapToTicks(true);
			noOfMines.setPaintLabels(true);
			noOfMines.setPaintTicks(true);

			boardWidth.setMaximum(max);
			boardWidth.setMinimum(min);
			boardWidth.setValue(20); // need to pass in an int
			boardWidth.setMajorTickSpacing(30);
			boardWidth.setMinorTickSpacing(5);
			boardWidth.setSnapToTicks(true);
			boardWidth.setPaintLabels(true);
			boardWidth.setPaintTicks(true);

			boardHeight.setMaximum(max);
			boardHeight.setMinimum(min);
			boardHeight.setValue(20); // need to pass in an int
			boardHeight.setMajorTickSpacing(30);
			boardHeight.setMinorTickSpacing(5);
			boardHeight.setSnapToTicks(true);
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

			// actions
			noOfMines.addChangeListener(null);
			boardWidth.addChangeListener(null);
			boardHeight.addChangeListener(null);
		}
	}
}
