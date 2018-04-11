package View;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import Tool.All_revisions;
import Tool.CreateFolder;
import Tool.Java_Bat;
import Tool.MoveFile;
import Tool.SimplifyInfo;
import Tool.brevision;
import Tool.calculateAuthors;
import Tool.calculate_codesmells;
import Tool.compare_excel;
import Tool.match_String;
import Tool.pick_up_refactors;
import Tool.random_selected;
import Util.PathUtil;

//���������棬�����ռ�·����Ϣ
//����ÿ��
public class MainView {
	JFrame frame;
	JPanel function_panel;
	JPanel path_panel;
	JButton[] jButton;
	Container container;
	JLabel path;
	JTextField path_area;
	JLabel projectname;
	JTextField pro_name;
	JLabel webpath;
	JTextField webpath_area;

	public static String root;
	public static String name;
	public static String web;

	public void initdata() {
		root = path_area.getText();
		name = pro_name.getText();
		web = webpath_area.getText();
		PathUtil pu = new PathUtil();
		pu.Path_Root = root;
		pu.filename = name;
		pu.Http = web;
	}

	// ����
	MainView() {
		frame = new JFrame();

		function_panel = new JPanel();
		path_panel = new JPanel();
		function_panel.setLayout(new GridLayout(3, 3, 5, 5));
		path_panel.setLayout(new GridLayout(2, 2, 5, 5));

		jButton = new JButton[9];
		for (int i = 0; i < 9; i++) {
			jButton[i] = new JButton(String.valueOf(i + 1));
		}
		jButton[0].setText("��ѡSAR");
		jButton[1].setText("ͳ������");
		jButton[2].setText("���ǰ�汾�������а汾");
		jButton[3].setText("��SARѡ��");
		jButton[4].setText("���ɽű�");
		jButton[5].setText("�ƶ��ļ�");
		jButton[6].setText("�Ա�Excel");
		jButton[7].setText("���������ζ");
		jButton[8].setText("��������");

		for (int i = 0; i < 9; i++) {
			function_panel.add(jButton[i]);
		}

		path = new JLabel("��Ŀ¼(����:H:)");
		path_area = new JTextField(10);
		projectname = new JLabel("��Ŀ����");
		pro_name = new JTextField(10);
		webpath = new JLabel("��ַ��");
		webpath_area = new JTextField(10);

		JPanel p = new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		p.add(path);
		p.add(path_area);
		p.add(projectname);
		p.add(pro_name);
		p.add(webpath);
		p.add(webpath_area);
		path_panel.add(p);

		// JPanel total = new JPanel();
		// total.add(path_panel);
		// total.add(function_panel);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(path_panel, BorderLayout.NORTH);
		frame.getContentPane().add(function_panel, BorderLayout.CENTER);
		// frame.add(function_panel);

		frame.setTitle("PMDletʵ�����");
		frame.pack();
		frame.setSize(600, 600);
		frame.setLocation(200, 200);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jButton[0].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				initdata();
				CreateFolder cf = new CreateFolder();
				cf.PrepareDirectory();
				// 1////////
				pick_up_refactors r = new pick_up_refactors();
				r.startPick();
				r = null;
				path_area.setEditable(false);
				pro_name.setEditable(false);
				webpath_area.setEditable(false);

			}
		});

		jButton[1].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				calculateAuthors ca = new calculateAuthors();
				ca.startCal();
				ca = null;

			}
		});
		jButton[2].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				All_revisions ar = new All_revisions();
				ar.fetch_revisions();
				ar = null;
				//
				// // 4///////
				brevision b = new brevision();
				b.startBreVision();
				b = null;

			}
		});
		jButton[3].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				random_selected rs = new random_selected();
				rs.startRandom();
				rs = null;

			}
		});
		jButton[4].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Java_Bat jb = new Java_Bat();
				System.out.println("SAR����");
				jb.start_Bat("SAR");
				System.out.println("nonSAR����");
				jb.start_Bat("nonSAR");// nonSAR
				jb = null;

			}
		});
		jButton[5].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				MoveFile mf = new MoveFile();
				mf.startMove("SAR");
				mf.startMove("nonSAR");

			}
		});
		jButton[6].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				compare_excel e = new compare_excel();
				MoveFile mf = new MoveFile();
				mf.readVersions("SAR");
				e.startCompare("SAR");
				mf.readVersions("nonSAR");
				e.startCompare("nonSAR");
				e = null;
				//
				// // 8////
				match_String ms = new match_String();
				ms.startMatch();
				ms = null;

			}
		});
		jButton[7].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				calculate_codesmells ccs = new calculate_codesmells();
				ccs.Start("SAR");
				ccs.Start("nonSAR");// nonSAR
				ccs = null;

			}
		});
		jButton[8].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				SimplifyInfo sfi = new SimplifyInfo();
				sfi.startSimplifyInfo();
				sfi = null;

			}
		});

	}

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		// ����Ҫ��������̣߳���Ȼ�޷��ﵽ����Ч��������ԭ����Ҳû�
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
							"org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Substance Graphite failed to initialize");
				}
				try {
					new MainView();
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});

		// ������
		// ����ȷ�������ļ����Ѿ�����
		// ���Ŀ¼�ṹ������
		// 0//////////
		// CreateFolder cf = new CreateFolder();
		// cf.PrepareDirectory();

		// 1////////
		// pick_up_refactors r = new pick_up_refactors();
		// r.startPick();
		// r = null;

		// 2////////
		// calculateAuthors ca = new calculateAuthors();
		// ca.startCal();
		// ca = null;
		//
		// // 3///////
		// All_revisions ar = new All_revisions();
		// ar.fetch_revisions();
		// ar = null;
		//
		// // 4///////
		// brevision b = new brevision();
		// b.startBreVision();
		// b = null;
		//
		// // 5//////
		// random_selected rs = new random_selected();
		// rs.startRandom();
		// rs = null;
		//
		// // 6/////
		// Java_Bat jb = new Java_Bat();
		// System.out.println("SAR����");
		// jb.start_Bat("SAR");
		// System.out.println("nonSAR����");
		// jb.start_Bat("nonSAR");// nonSAR
		// jb = null;

		// 6.5//////
		// MoveFile mf = new MoveFile();
		// mf.startMove("SAR");
		// mf.startMove("nonSAR");

		// // 7/////
		// compare_excel e = new compare_excel();
		// MoveFile mf = new MoveFile();
		// mf.readVersions("SAR");
		// e.startCompare("SAR");
		// mf.readVersions("nonSAR");
		// e.startCompare("nonSAR");
		// e = null;
		//
		// // 8////
		// match_String ms = new match_String();
		// MoveFile mf = new MoveFile();
		// mf.readVersions("SAR");
		// ms.startMatch();
		// ms = null;
		//
		// // 9////
		// calculate_codesmells ccs = new calculate_codesmells();
		// ccs.Start("SAR");
		// ccs.Start("nonSAR");// nonSAR
		// ccs = null;
		//
		// // 10/////
		// addFunctions af = new addFunctions();
		// af.startAddFunction();
		// af = null;
		//
		// // 11/////
		// SimplifyInfo sfi = new SimplifyInfo();
		// sfi.startSimplifyInfo();
		// sfi = null;
	}
}
