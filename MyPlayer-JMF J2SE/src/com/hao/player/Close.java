package com.hao.player;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Close extends JDialog implements ActionListener{

	/**
	 * Launch the application.
	 */
	private JButton bt_mini,bt_exit;
	public static void main(String[] args) {
		try {
			Close dialog = new Close();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Close() {
		setTitle("\u63D0\u793A");
		setBounds(100, 100, 450, 88);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				bt_mini = new JButton("\u6700\u5C0F\u5316\u5230\u6258\u76D8");
				bt_mini.setActionCommand("OK");
				buttonPane.add(bt_mini);
				getRootPane().setDefaultButton(bt_mini);
			}
			{
				bt_exit = new JButton("\u9000\u51FA\u7A0B\u5E8F");
				bt_exit.setActionCommand("Cancel");
				buttonPane.add(bt_exit);
			}
			bt_exit.addActionListener(this);
			bt_mini.addActionListener(this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
      if(e.getSource()==bt_mini)
      {
    	  MainFrame.frame.setVisible(false);
      }else if(e.getSource()==bt_exit)
      {
    	  System.exit(0);
      }
		
	}

}
