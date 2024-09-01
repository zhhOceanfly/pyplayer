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
	private static PopupMenu popupMenu;//trayIcon的弹出菜单
	public static TrayIcon trayIcon;//系统托盘的托盘图标
	private static SystemTray systemTray;//创建系统托盘
	private static MenuItem exit;//弹出菜单的项	
	private static MainFrame mainFrame=null;
	public static void main(String[] args) {
	
		mainFrame=new MainFrame();
		mainFrame=new MainFrame();
		mainFrame.showMusicList();
		mainFrame.setAlwaysOnTop(true);
		mainFrame.setVisible(true);
		systemTray=SystemTray.getSystemTray();//得到一个系统托盘
		Image	 image=Toolkit.getDefaultToolkit().getImage("picture.gif");
		trayIcon=new TrayIcon(image, "双击弹出程序，右击弹出菜单");
		trayIcon.setImageAutoSize(true);//使图片完全显示
	    try 
		{
			systemTray.add(trayIcon);//增加系统托盘图标
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


