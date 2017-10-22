package functionalities;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import dbconnection.DBConnection;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SignUP extends JFrame {

	private JPanel contentPane;
	private JTextField textFielduserID;
	private JLabel lblNewLabel;
	private JTextField textFieldGender;
	private JLabel lblNewLabel_1;
	private JTextField textFieldAge;
	private JLabel lblOccupation;
	private JTextField textFieldOccupation;
	private JLabel lblZipCode;
	private JTextField textFieldZipcode;
	private JButton btnSignup;
	private DBConnection dbconnection;
	private Connection connection=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUP frame = new SignUP();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SignUP() {
		dbconnection=new  DBConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSignUp = new JLabel("Sign UP");
		lblSignUp.setBounds(170, 11, 46, 14);
		contentPane.add(lblSignUp);
		
		JLabel lblUserId = new JLabel("User ID");
		lblUserId.setBounds(30, 34, 46, 14);
		contentPane.add(lblUserId);
		
		textFielduserID = new JTextField();
		textFielduserID.setBounds(141, 32, 196, 17);
		contentPane.add(textFielduserID);
		textFielduserID.setColumns(10);
		
		lblNewLabel = new JLabel("Gender");
		lblNewLabel.setBounds(30, 60, 79, 14);
		contentPane.add(lblNewLabel);
		
		textFieldGender = new JTextField();
		textFieldGender.setColumns(10);
		textFieldGender.setBounds(141, 60, 196, 17);
		contentPane.add(textFieldGender);
		
		lblNewLabel_1 = new JLabel("Age");
		lblNewLabel_1.setBounds(30, 85, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		textFieldAge = new JTextField();
		textFieldAge.setColumns(10);
		textFieldAge.setBounds(141, 83, 196, 17);
		contentPane.add(textFieldAge);
		
		lblOccupation = new JLabel("Occupation");
		lblOccupation.setBounds(30, 110, 90, 14);
		contentPane.add(lblOccupation);
		
		textFieldOccupation = new JTextField();
		textFieldOccupation.setColumns(10);
		textFieldOccupation.setBounds(141, 106, 196, 17);
		contentPane.add(textFieldOccupation);
		
		lblZipCode = new JLabel("Zip Code");
		lblZipCode.setBounds(30, 135, 90, 14);
		contentPane.add(lblZipCode);
		
		textFieldZipcode = new JTextField();
		textFieldZipcode.setColumns(10);
		textFieldZipcode.setBounds(141, 129, 196, 17);
		contentPane.add(textFieldZipcode);
		
		btnSignup = new JButton("Sign UP");
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection=dbconnection.sqliteConncetion();
				try{
					String query="Insert into users (userId,gender,age,occupation,zipcode) values(?,?,?,?,?)";
					PreparedStatement preparedStatement=connection.prepareStatement(query);
					preparedStatement.setString(1,textFielduserID.getText());
					preparedStatement.setString(2,textFieldGender.getText());
					preparedStatement.setString(3,textFieldAge.getText());
					preparedStatement.setString(4,textFieldOccupation.getText());
					preparedStatement.setString(5,textFieldZipcode.getText());
					preparedStatement.execute();
					JOptionPane.showMessageDialog(null,"Data Saved Successfully");
					preparedStatement.close();
				}catch(Exception eq){
					JOptionPane.showInternalMessageDialog(null, eq);
				}
			}
		});
		btnSignup.setBounds(131, 191, 156, 23);
		contentPane.add(btnSignup);
	}
}
