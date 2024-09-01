package com.hao.player;
/**
 * @author haopeng
 * @time:2011-6-26
 * @name:Myplayer-version1.0
 * @QQ:785035177
 */
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class StartPlayer implements ActionListener {
	private static PopupMenu popupMenu;//trayIcon�ĵ����˵�
	public static TrayIcon trayIcon;//ϵͳ���̵�����ͼ��
	private static SystemTray systemTray;//����ϵͳ����
	private static MenuItem exit;//�����˵�����	
	private static MainFrame mainFrame=null;
	public static void main(String[] args) {
	
		mainFrame=new MainFrame();
		mainFrame=new MainFrame();
		mainFrame.showMusicList();
		mainFrame.setAlwaysOnTop(true);
		mainFrame.setVisible(true);
		systemTray=SystemTray.getSystemTray();//�õ�һ��ϵͳ����
		Image	 image=Toolkit.getDefaultToolkit().getImage("picture.gif");
		trayIcon=new TrayIcon(image, "˫�����������һ������˵�");
		trayIcon.setImageAutoSize(true);//ʹͼƬ��ȫ��ʾ
	    try 
		{
			systemTray.add(trayIcon);//����ϵͳ����ͼ��
		} catch (AWTException e) 
		{
			e.printStackTrace();
		}
		 popupMenu=new PopupMenu();
		 trayIcon.setPopupMenu(popupMenu);
		 exit = new MenuItem("Exit", null);		
		 popupMenu.add(exit);
		 trayIcon.addActionListener(new StartPlayer());
		 exit.addActionListener(new StartPlayer());
		 
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getSource()==exit)
			{
				systemTray.remove(trayIcon);
				System.exit(0);
			}else if(e.getSource()==trayIcon)
			{
				
				if(mainFrame==null)
				{
					mainFrame=new MainFrame();
					mainFrame.showMusicList();
					mainFrame.setAlwaysOnTop(true);
					mainFrame.setVisible(true);
				}else
				{	
					mainFrame.setAlwaysOnTop(true);
					mainFrame.setVisible(true);
			    }
				
			}
			
		}

	}


