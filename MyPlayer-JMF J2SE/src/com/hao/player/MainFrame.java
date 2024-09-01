package com.hao.player;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.URL;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.GainControl;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.Time;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JSlider;
public class MainFrame extends JFrame implements ActionListener,ControllerListener,ChangeListener,WindowListener{

	private JPanel contentPane;
	private JComboBox comboBox;
    private JButton bt_play,bt_pause,bt_end,bt_next,bt_last,bt_select;
    private Player player=null;
    private Time time=null;;
    private int musicIndex=0;
    private String musicsUrl="music/";
    private JLabel label_2;
    private JLabel lab_number;
    private JSlider slider,slider_progress;
    private JLabel lab_music_name;
    private JLabel label_4;
    public static MainFrame frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MainFrame();
					frame.showMusicList();
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
	public MainFrame() {
		setTitle("MyPlayer-version1.0");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(this);
		setResizable(false);
		setBounds(100, 100, 601, 224);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		comboBox = new JComboBox();
		
		bt_last = new JButton("\u4E0A\u4E00\u9996");
		
		bt_next = new JButton("\u4E0B\u4E00\u9996");
		
		 bt_pause = new JButton("\u6682\u505C");
		
		bt_play = new JButton("\u64AD\u653E");
		
		 bt_end = new JButton("\u505C\u6B62");
		
		JLabel label = new JLabel("\u6B4C\u66F2\u5217\u8868\uFF1A");
		
		bt_select = new JButton("\u9009\u62E9\u6B4C\u66F2\u6587\u4EF6\u5939");
		
		slider = new JSlider();
		slider.setOrientation(1);
		
		JLabel label_1 = new JLabel("\u97F3\u91CF");
		
		label_2 = new JLabel("\u6B4C\u66F2\u6570\u91CF\uFF1A");
		
		lab_number = new JLabel("New label");
		
		JLabel label_3 = new JLabel("\u5F53\u524D\u6B4C\u66F2\uFF1A");
		
		lab_music_name = new JLabel("");
		
		label_4 = new JLabel("\u8FDB\u5EA6\u63A7\u5236\uFF1A");
		
	    slider_progress = new JSlider();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addContainerGap(62, Short.MAX_VALUE)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(label)
											.addGap(36)
											.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lab_number))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 343, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(bt_select))))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(21)
									.addComponent(bt_last)
									.addGap(18)
									.addComponent(bt_next)
									.addGap(26)
									.addComponent(bt_end)
									.addGap(14)
									.addComponent(bt_pause)
									.addGap(18)
									.addComponent(bt_play)
									.addGap(99)))
							.addGap(17))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(label_4)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(slider_progress, GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(label_3)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lab_music_name, GroupLayout.PREFERRED_SIZE, 462, GroupLayout.PREFERRED_SIZE)))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(label_1)
							.addGap(4))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(label_2)
						.addComponent(lab_number)
						.addComponent(label_1))
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(bt_select))
							.addGap(27)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(bt_last)
								.addComponent(bt_end)
								.addComponent(bt_pause)
								.addComponent(bt_play)
								.addComponent(bt_next))
							.addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_3)
								.addComponent(lab_music_name))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(slider_progress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_4)))
						.addComponent(slider, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		//增加监听事件
		bt_end.addActionListener(this);
		bt_last.addActionListener(this);
		bt_next.addActionListener(this);
		bt_pause.addActionListener(this);
		bt_play.addActionListener(this);
		bt_select.addActionListener(this);
		if(player!=null)
		{
		player.addControllerListener(this);
		}
		slider.addChangeListener(this);
		slider_progress.addChangeListener(this);
	}
	@Override
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==bt_play)
		{
			if(player==null)
			{
			
			  this.playMusic();
				
			}else
			{
				if(comboBox.getSelectedIndex()!=musicIndex)
				{
					player.stop();
					player.deallocate();
					player=null;
					this.playMusic();
				}
				if(time!=null)
				{
					System.out.println("歌曲从这个暂停时间开始播放："+time);
					player.setMediaTime(time);
					player.start();
				}
			}
		}else if(e.getSource()==bt_end)//结束当前歌曲
		{
			if(player!=null)
			{
				player.stop();
				player.deallocate();
				player=null;
			}
		}else if(e.getSource()==bt_pause)//暂停歌曲【此功能没有实现】
		{
			if(player!=null)
			{
				time=player.getMediaTime();
				player.stop();
			}
			
		}else if(e.getSource()==bt_next)
		{
			if(player!=null)
			{
				player.stop();
				player.deallocate();
				player=null;
				this.clickNext();
				//重新播放歌曲
				this.playMusic();
			}else
			{
				this.clickNext();
			}
		}else if(e.getSource()==bt_last)
		{
			if(player!=null)
			{
				player.stop();
				player.deallocate();
				player=null;
				this.clickLast();
				//重新播放歌曲
				this.playMusic();
			}else
			{
				this.clickLast();
			}
		}else if(e.getSource()==bt_select)
		{
			String folderUrl="d:/";//选择的文件夹的路径
			JFileChooser jFileChooser=new JFileChooser(folderUrl);
			jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//只能选择文件夹
			jFileChooser.setDialogTitle("请选择放有音乐的文件夹，将为你生成音乐列表");
			jFileChooser.showOpenDialog(this);
			if(jFileChooser.getSelectedFile()!=null)
			{
			musicsUrl=jFileChooser.getSelectedFile().getAbsolutePath();
		    comboBox.removeAllItems();
			this.showMusicList();//重新生成音乐列表
			}
		}
	}
	@Override
	public void controllerUpdate(ControllerEvent arg0) {
		player.getDuration();
		player.start();
	}
	//从一个放有歌曲的文件夹获取音乐列表
	public void showMusicList()
	{
		File file=new File(musicsUrl);
		File[]files=file.listFiles();
		for(File fl:files)
		{
			if(fl.isFile()&&(fl.getName().split("\\.")[1].equals("mp3")||fl.getName().split("\\.")[1].equals("wav")))
			{
				this.comboBox.addItem(fl.getName());
			}
		}
		lab_number.setText(comboBox.getItemCount()+"（首）");
		
	}
	//播放一首歌曲
	public void playMusic()
	{String musicName="";//歌曲名字
		try 
		{	
		    musicIndex=comboBox.getSelectedIndex();
			musicName=comboBox.getSelectedItem().toString();
			File file=new File(musicsUrl+"/"+musicName);
			URL url=file.toURL();
			System.out.println(url);
			player=Manager.createPlayer(url);
			System.out.println("歌曲路径："+url);
			player.start();
			lab_music_name.setText(musicName);
			//当一首歌曲播放的时候设置slider_progress
		} 
		catch (Exception ex) 
		{ 
		    ex.printStackTrace();
		} 
		
	}
	//上一首歌曲
	public void clickLast()
	{
		int index=comboBox.getSelectedIndex();
		if(index>0)
		{
		comboBox.setSelectedIndex(index-1);
		}else
		{
			JOptionPane.showMessageDialog(this,"第一首");
		}
	}
	//下一首歌曲
	public void clickNext()
	{
		int index=comboBox.getSelectedIndex();
		if(index<comboBox.getItemCount()-1)
		{
		comboBox.setSelectedIndex(index+1);
		}else
		{
			JOptionPane.showMessageDialog(this,"最后一首");
		} 
	}
	/*
	//如果当前歌曲结束自动进入下一首歌曲
	public void autoPlayNextMusic()
	{
		comboBox.setSelectedIndex(comboBox.getSelectedIndex()+1);
	}
	*/
	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource()==slider)//音量控制
		{
			slider.setMaximum(10);//设置滑块所支持的最大值
			slider.setMinimum(0);
			try 
			{
				if(player!=null)
				{
					GainControl gainControl=player.getGainControl();
					//gainControl.getControlComponent();
					//gainControl.setMute(true);//静音
					System.out.println(gainControl.getLevel());
					//gainControl.setLevel(0.4f);//音量0.0到1.0之间
//					if(slider.getValue()==0)
//					{
//						gainControl.setLevel(0.0f);						
//					}else if(slider.getValue()==1)
//					{
//						gainControl.setLevel(0.1f);
//					}
					if(slider.getValue()==10)//处理为10时的特殊情况
					{
						gainControl.setLevel(1.0f);
					}else
					{
					    gainControl.setLevel(Float.parseFloat("0."+slider.getValue()+"f"));
					}
				}
				
			} catch (Exception e2) 
			{
				e2.printStackTrace();
			}
			System.out.println("音量："+slider.getValue());
		}else if(e.getSource()==slider_progress)//歌曲进度控制
		{
			if(player!=null)
			{
				GainControl gainControl=player.getGainControl();
				//gainControl.
			}
		}
	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("1111");
	}
	@Override
	public void windowClosing(WindowEvent e) {
		JOptionPane pane = new JOptionPane();
	    int result = pane.showConfirmDialog(frame, "真的要退出吗？", "系统提示", 0);
	    switch (result)
	    {
	    case 0:
	    	if(player!=null)
	    	{
	    		player.stop();
	    		player.deallocate();
	    	}
	    System.exit(0);
	      break;
	    case 1:
	      pane.show(false);
	      break;
	    default:
	      pane.show(false);
	    }
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
	}
	@Override
	public void windowIconified(WindowEvent e) {
	setVisible(false);	
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}

