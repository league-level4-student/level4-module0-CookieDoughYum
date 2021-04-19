package _02_Pixel_Art;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//import _05_Serialization.SaveData;

public class GridInputPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTextField windowWidthField;
	private JTextField windowHeightField;
	private JTextField rowsField;
	private JTextField colsField;
	private JButton submitButton;
	private JButton loadImage;
	
			
	PixelArtMaker pam;
	
	private static final String DATA_FILE = "src/_02_Pixel_Art/SaveState.dat";
	
	public GridInputPanel(PixelArtMaker pam) {
		this.pam = pam;
		
		windowWidthField = new JTextField(5);
		windowHeightField = new JTextField(5);
		rowsField = new JTextField(5);
		colsField = new JTextField(5);
		submitButton = new JButton("Submit");
		loadImage=new JButton("Load Image");
		
		add(new JLabel("screen width:"));
		add(windowWidthField);
		add(new JLabel("\tscreen height:"));
		add(windowHeightField);
		add(new JLabel("\ttotal rows:"));
		add(rowsField);
		add(new JLabel("\ttotal columns:"));
		add(colsField);
		add(submitButton);
		add(loadImage);
		submitButton.addActionListener((e)->submit());
		loadImage.addActionListener((e)->pam.loaded());
		
	}
	
	private void submit() {
		boolean valid = false;
		int w = -1;
		int h = -1;
		int r = -1;
		int c = -1;
		try {
			w = Integer.parseInt(windowWidthField.getText());
			h = Integer.parseInt(windowHeightField.getText());
			r = Integer.parseInt(rowsField.getText());
			c = Integer.parseInt(colsField.getText());
			
			if(w <= 0 || h <= 0 || r <= 0 || c <= 0) {
				invalidateInput();
			}else {
				valid = true;
			}
		}catch(NumberFormatException e) {
			invalidateInput();
		}
		
		if(valid) {
			pam.submitGridData(w, h, r, c);
		}
	}
	public void test() {
		GridPanel g=PixelArtMaker.gp;
		save(g);
		
//display loadedData on the grid
	}
	public static void save(GridPanel data) {
		try (FileOutputStream fos = new FileOutputStream(new File(DATA_FILE)); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(data);
			//System.out.println(DATA_FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static GridPanel load() {
		try (FileInputStream fis = new FileInputStream(new File(DATA_FILE)); ObjectInputStream ois = new ObjectInputStream(fis)) {
			System.out.println(DATA_FILE);
			return (GridPanel) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			// This can occur if the object we read from the file is not
			// an instance of any recognized class
			e.printStackTrace();
			return null;
		}
	}
	
	private void invalidateInput() {
		JOptionPane.showMessageDialog(null, "Be sure all fields are complete with positive numbers.", "ERROR", 0);
	}
	
	
}
