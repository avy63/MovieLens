package functionalities;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import dbconnection.DBConnection;

import java.awt.Font;

import javax.swing.JList;

public class ShowData extends JFrame {

	private JPanel contentPane;
	private JLabel lblName;
	private JLabel lblWellcome;
	private int userId;
	JList list;
	public DBConnection dbConnection;
	Connection coneConnection = null;
	public WeightCalculation calculation;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowData frame = new ShowData();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public ShowData(int x) throws SQLException {
		this();
		this.userId = x;
		dbConnection = new DBConnection();

		lblName.setText("User id: " + userId);
		calculation = new WeightCalculation(userId);
		Map<Long, String> sortedmap = calculation.movieList();
		if (sortedmap != null && sortedmap.size() > 0) {
			int count = 0;
			DefaultListModel dlm = new DefaultListModel();
			for (Map.Entry<Long, String> entry : sortedmap.entrySet()) {

				dlm.addElement(entry.getKey() + " "+" "+entry.getValue());
				count++;
				if(count==10){
					break;
				}
			}
			list.setModel(dlm);
		}
	}

	public ShowData() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblWellcome = new JLabel("Wellcome");
		lblWellcome.setBounds(29, 11, 68, 14);
		contentPane.add(lblWellcome);

		lblName = new JLabel("New label");
		lblName.setBounds(107, 11, 288, 14);
		contentPane.add(lblName);

		JLabel lblTopRecommended = new JLabel("TOP 10 recommended item:");
		lblTopRecommended.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTopRecommended.setBounds(10, 29, 240, 14);
		contentPane.add(lblTopRecommended);

		list = new JList();
		list.setBounds(29, 54, 272, 173);
		contentPane.add(list);
	}
}
