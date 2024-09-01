package com.genius.demo;

import java.util.ArrayList;
import java.util.List;
import android.view.KeyEvent;
import com.genius.adapter.GridViewAdapter;
import com.genius.adapter.ListViewAdapter;
import com.genius.adapter.MenuAdapter;
import com.genius.interfaces.IOnServiceConnectComplete;
import com.genius.interfaces.IOnSliderHandleViewClickListener;
import com.genius.musicplay.MediaAlbum;
import com.genius.musicplay.MusicData;
import com.genius.musicplay.MusicPlayMode;
import com.genius.musicplay.MusicPlayState;
import com.genius.service.ServiceManager;
import com.genius.widget.GalleryFlow;
import com.genius.widget.ImageAdapter;
import com.genius.widget.ImageUtil;
import com.genius.widget.MySlidingDrawer;
import com.genius.widget.Settings;
import com.genius.demo.Menu;
import com.genius.demo.MusicPlayActivity.UIManager.PopMenuManager;
import com.genius.demo.MusicPlayActivity.UIManager.SliderDrawerManager;













import com.genius.demo.furui.R;
import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager.OnDismissListener;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

/**
 * @author Google_acmer
 *
 */
public  class MusicPlayActivity extends Activity implements IOnServiceConnectComplete, SensorEventListener{
    /** Called when the activity is first created. */
	private final static String TAG = "MusicPlayActivity";
	private SensorManager sensorManager;
	private boolean mRegisteredSensor;
	private final String BROCAST_NAME = "com.genius.musicplay.brocast";
	private  int isplay=0;
   	private final static int REFRESH_PROGRESS_EVENT = 0x100;
   	private final static int ABOUT_DIALOG_ID = 1;
   	private Menu xmenu; // �Զ���˵�
   	private ImageView musicAlbum;	//����ר������
	private ImageView musicAblumReflection;	//��Ӱ����
	Vibrator vibrator;//��������
	private Handler mHandler;
	
	private UIManager mUIManager;
	
	private ServiceManager mServiceManager;
	
	private MusicTimer mMusicTimer;
	
	private MusicPlayStateBrocast mPlayStateBrocast;

	private SDStateBrocast mSDStateBrocast;
	
	private List<MusicData> m_MusicFileList;
	
	private ListViewAdapter mListViewAdapter;
	
	private boolean mIsSdExist = false;
	private boolean mIsHaveData = false;
	
	private int mCurMusicTotalTime = 0;
	
	private int mCurPlayMode = MusicPlayMode.MPM_LIST_LOOP_PLAY;
	private int listPosition=0; // ���Ÿ�����mp3Infos��λ��
	SharedPreferences preferences;
	private String listPosition1;
	private int Shake_pause;
	private int Shake_next;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //����data�ļ���ȡ����
        preferences = getSharedPreferences("data", 0);
        int position=preferences.getInt("background_id", 0);//�ı���Ƥ��ID
        Shake_pause=preferences.getInt("Shake_id", 0);//˦����ͣ
        Shake_next=preferences.getInt("Shake1_id", 0);//˦���и�
  
        Settings mSetting = new Settings(this, true);
		this.getWindow().setBackgroundDrawableResource(Settings.SKIN_RESOURCES[position]);//��ʼ����ֽ
        long time1 = System.currentTimeMillis();
        init();
    	long time2 = System.currentTimeMillis();
    	
    	sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);//��ȡϵͳ�Ĵ������������
    	TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);//ȡ��TelephonyManager����
    	telManager.listen(new MobliePhoneStateListener(),PhoneStateListener.LISTEN_CALL_STATE);
    	vibrator =(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);//��ȡϵͳ���𶯷���
    	LoadMenu();
    }
  //   ����˵�  
    private void LoadMenu() {
		xmenu = new Menu(this);
		List<int[]> data1 = new ArrayList<int[]>();
		data1.add(new int[] { R.drawable.btn_menu_skin, R.string.skin_settings });
		data1.add(new int[] { R.drawable.btn_menu_exit, R.string.menu_exit_txt });

		xmenu.addItem("����", data1, new MenuAdapter.ItemListener() {

			public void onClickListener(int position, View view) {
				xmenu.cancel();
				if (position == 0) {
					Intent intent = new Intent(MusicPlayActivity.this,SkinSettingActivity.class);
					startActivityForResult(intent, 1);
					//Toast.makeText(MusicPlayActivity.this, "���ڿ�����", Toast.LENGTH_SHORT).show();
				} else if (position == 1) {
					exit();
				}
			}
		});
		List<int[]> data2 = new ArrayList<int[]>();
		data2.add(new int[] { R.drawable.btn_menu_setting,
				R.string.menu_settings });
		data2.add(new int[] { R.drawable.btn_menu_sleep, R.string.menu_time_txt});
		data2.add(new int[] { R.drawable.btn_menu_sleep, R.string.menu_time_txt1});
		
		xmenu.addItem("����", data2, new MenuAdapter.ItemListener() {

			@Override
			public void onClickListener(int position, View view) {
			//	xmenu.cancel();
				if (position == 0) {
					Toast.makeText(MusicPlayActivity.this, "�˰汾���ޣ����¸��汾���ԣ�", Toast.LENGTH_SHORT).show();
				} else if (position == 1) {
					if(Shake_pause!=20)
					{Shake_pause=20;//˦����ͣ����
					preferences.edit().putInt("Shake_id", Shake_pause).commit();
					Toast.makeText(MusicPlayActivity.this, "˦����ͣ��������˦˦����", Toast.LENGTH_SHORT).show();
					}
					else
					{Shake_pause=10;//˦����ͣ��ͣ
					preferences.edit().putInt("Shake_id", Shake_pause).commit();
					Toast.makeText(MusicPlayActivity.this, "˦����ͣ�ѹر�", Toast.LENGTH_SHORT).show();
					}
				} else if (position == 2) {
					if(Shake_next!=20)
					{Shake_next=20;//˦���и�����
					preferences.edit().putInt("Shake1_id", Shake_next).commit();
					Toast.makeText(MusicPlayActivity.this, "˦���и���������˦˦����", Toast.LENGTH_SHORT).show();
					}
					else
					{Shake_next=10;//˦���и���ͣ
					preferences.edit().putInt("Shake1_id", Shake_next).commit();
					Toast.makeText(MusicPlayActivity.this, "˦���и��ѹر�", Toast.LENGTH_SHORT).show();
					}
					
				}
			}

		});
		List<int[]> data3 = new ArrayList<int[]>();
		data3.add(new int[] { R.drawable.ic_menu_about_default, R.string.about_title });
		data3.add(new int[] { R.drawable.ic_menu_back_pressed, R.string.back_message });
		xmenu.addItem("����", data3, new MenuAdapter.ItemListener() {
			@Override
			public void onClickListener(int position, View view) {
				if (position == 0) {
					showDialog(ABOUT_DIALOG_ID);
				} else if (position == 1) {
					Intent intent = new Intent(MusicPlayActivity.this,SendMessage.class);
					startActivity(intent);
				} 
				
			}
		});
		xmenu.create();		//�����˵�
	}
    //�õ��ı��Ƥ��������
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && requestCode == 1) {
			Settings setting = new Settings(this, false);
			this.getWindow().setBackgroundDrawableResource(setting.getCurrentSkinResId());
		}
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onMenuOpened(int featureId, android.view.Menu menu) {
		// �˵���������ʾ������1�Ǹò����ܵ�ID���ڶ���λ�ã��������ĸ���XY����
		xmenu.showAtLocation(findViewById(R.id.mainLayout), Gravity.BOTTOM
				| Gravity.CENTER_HORIZONTAL, 0,0);
		// �������true�Ļ��ͻ���ʾϵͳ�Դ��Ĳ˵�����֮����false�Ļ�������ʾ�Լ�д��
		return false;
	}

    
    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		mMusicTimer.stopTimer();
		unregisterReceiver(mPlayStateBrocast);
		unregisterReceiver(mSDStateBrocast);
		mServiceManager.disconnectService();
		
	
	}

    public void onResume()
    {
    	super.onResume();
    	//Ϊϵͳ������������ע�������
    	List<Sensor> sensors=sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if(sensors.size()>0){
			Sensor sensor=sensors.get(0);
			mRegisteredSensor=sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
		}
    }
    
    public void onStart()
    {
    	super.onStart();
    }
    
   
    

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		
		switch(id)
		{
		case ABOUT_DIALOG_ID:
		{
    		Dialog aboutDialog = new AlertDialog.Builder(MusicPlayActivity.this)
            .setIcon(R.drawable.about_dialog_icon)
            .setTitle(R.string.about_title_name)
            .setMessage(R.string.about_content)
            .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    /* User clicked OK so do some stuff */
                }
            }).create();
    		
    		return aboutDialog;
		}
		default:
			break;
		}
		
		return null;
	}



	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		mUIManager.Back();
	}



	public void init()
    {
		mHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				
				switch(msg.what)
				{
					case REFRESH_PROGRESS_EVENT:
						mUIManager.setPlayInfo(mServiceManager.getCurPosition(), mCurMusicTotalTime, null);
						break;
					default:
						break;
				}		
			}
			
		};
		
		mUIManager = new UIManager();
		
		mServiceManager = new ServiceManager(this);
		mServiceManager.setOnServiceConnectComplete(this);
		mServiceManager.connectService();
	
	
		mMusicTimer = new MusicTimer(mHandler, REFRESH_PROGRESS_EVENT);

		m_MusicFileList = new ArrayList<MusicData>();
		mListViewAdapter = new ListViewAdapter(this, m_MusicFileList);
		mUIManager.mListView.setAdapter(mListViewAdapter);
	
		
		mPlayStateBrocast = new MusicPlayStateBrocast();
		IntentFilter intentFilter1 = new IntentFilter(BROCAST_NAME);
		registerReceiver(mPlayStateBrocast, intentFilter1);
		
		mSDStateBrocast = new SDStateBrocast();
		IntentFilter intentFilter2 = new IntentFilter();
		intentFilter2.addAction(Intent.ACTION_MEDIA_MOUNTED);
        intentFilter2.addAction(Intent.ACTION_MEDIA_UNMOUNTED);    
        intentFilter2.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED); 
        intentFilter2.addAction(Intent.ACTION_MEDIA_EJECT);    
        intentFilter2.addDataScheme("file"); 
        registerReceiver(mSDStateBrocast, intentFilter2);
    }
	//��ȡ�����б�
    private List<MusicData> getMusicFileList()
    {
    	List<MusicData> list = new ArrayList<MusicData>();
    	//��ȡר�������Uri
   
    	String[] projection = new String[]{MediaStore.Audio.Media._ID, 
    									MediaStore.Audio.Media.TITLE, 
    									MediaStore.Audio.Media.DURATION,
    									MediaStore.Audio.Media.DATA,
    									MediaStore.Audio.Media.ARTIST,
    									MediaStore.Audio.Media.ALBUM_ID,
    									};   
    	
    	long time1 = System.currentTimeMillis();
    	Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI , projection, null, null, null);
    	if (cursor != null)
    	{
    		cursor.moveToFirst();
    		int colIdIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
		    int colNameIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int colTimeIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int colPathIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int colArtistIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int albumId = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            int fileNum = cursor.getCount();  
            for(int counter = 0; counter < fileNum; counter++){        
                
                MusicData data = new MusicData();
                data.mMusicName = cursor.getString(colNameIndex);
                data.mMusicTime = cursor.getInt(colTimeIndex);
                data.mMusicPath = cursor.getString(colPathIndex);
                data.mMusicAritst = cursor.getString(colArtistIndex);
                data.MusicAlbumId = cursor.getInt(albumId);
                data.MusicId= cursor.getInt(colIdIndex);
                list.add(data);
                cursor.moveToNext();
            }
            
            cursor.close();
    	}
    	long time2 = System.currentTimeMillis();
    	
    	Log.i(TAG, "seach filelist cost = " + (time2 - time1));
    	return list;
    }

    public void showNoData()
    {
    	//Toast.makeText(this, "No Music Data...", Toast.LENGTH_SHORT).show();
    }
	   
	public void rePlay()
	{
		if (mIsHaveData == false)
		{
			showNoData();
		}else{
			mServiceManager.rePlay();
		}
		
		
	}
	   
	public void play(int position)
	{
		if (mIsHaveData == false)
		{
			showNoData();
		}else{
			mServiceManager.play(position);
		}

	}
	   
	public void pause()
	{
		mServiceManager.pause();
	}
	   
	public void stop()
	{
		mServiceManager.stop();
	}
	   
	   
	public void playPre()
	{
		if (mIsHaveData == false)
		{
			showNoData();
		}else{
			mServiceManager.playPre();
		}
		
	}
	   
	public void playNext()
	{
		if (mIsHaveData == false)
		{
			showNoData();
		}else{
			mServiceManager.playNext();
		}
	
	}

	   
	public void seekTo(int rate)
	{
		mServiceManager.seekTo(rate);
	}
	   
	public void exit()
	{
		mServiceManager.exit();
		finish();
	}
	
	public void modeChange()
	{
		mCurPlayMode++;
		if (mCurPlayMode > MusicPlayMode.MPM_RANDOM_PLAY)
		{
			mCurPlayMode = MusicPlayMode.MPM_SINGLE_LOOP_PLAY;
		}
		
		mServiceManager.setPlayMode(mCurPlayMode);
		mUIManager.setPlayMode(mCurPlayMode, true);
	}
    
	@Override
	public void OnServiceConnectComplete() {
		// TODO Auto-generated method stub
        
		String state = Environment.getExternalStorageState().toString();
		if (state.equals(Environment.MEDIA_MOUNTED))
		{
			mIsSdExist = true;
		}else{
			Toast.makeText(this, "SD��δ��װ�����鰲װSD��", Toast.LENGTH_SHORT).show();
			return ;
		}
	
		int playState = mServiceManager.getPlayState();
		switch(playState)
		{
			case MusicPlayState.MPS_NOFILE:
				m_MusicFileList = getMusicFileList();
			
				mServiceManager.refreshMusicList(m_MusicFileList);
				break;
			case MusicPlayState.MPS_INVALID:
			case MusicPlayState.MPS_PREPARE:
			case MusicPlayState.MPS_PLAYING:
			case MusicPlayState.MPS_PAUSE:
				long time1 = System.currentTimeMillis();
				m_MusicFileList = mServiceManager.getFileList();
				long time2 = System.currentTimeMillis();
				Log.i(TAG, "mServiceManager.getFileList()	cost = " + (time2 - time1));
				mServiceManager.sendPlayStateBrocast();
				break;
			default:
				break;
		}
		
		if (m_MusicFileList.size() > 0)
		{	
			mIsHaveData = true;
		}
		
		mListViewAdapter.refreshAdapter(m_MusicFileList);

		mUIManager.setPlayMode(mServiceManager.getPlayMode(), false);
		
	}

    
	class  SDStateBrocast extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			 String action = intent.getAction(); 
			 
			if (action.equals(Intent.ACTION_MEDIA_MOUNTED))
			  {
			
				  mIsSdExist = true;
			  }else if (action.equals(Intent.ACTION_MEDIA_UNMOUNTED))
			  {
		
				  mIsSdExist = false;
				 
			  }else if (Intent.ACTION_MEDIA_SCANNER_FINISHED.equals(action))
			  {
				
				  if (mIsSdExist)
				  {
					  m_MusicFileList = getMusicFileList();
					  mServiceManager.refreshMusicList(m_MusicFileList);
					  if (m_MusicFileList.size() > 0)
					  {	
						mIsHaveData = true;
					  }
					  mListViewAdapter.refreshAdapter(m_MusicFileList);
				  }
				  
			  }else if (Intent.ACTION_MEDIA_EJECT.equals(action))
			  {
				  m_MusicFileList.clear();
				  mListViewAdapter.refreshAdapter(m_MusicFileList);
				  mIsHaveData = false;
				  mUIManager.emptyPlayInfo();
			  }
			
		}
		
	}
	
	
	class  MusicPlayStateBrocast extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			  String action = intent.getAction(); 
			  if (action.equals(BROCAST_NAME))
			  {
				  TransPlayStateEvent(intent);
			  }
			
		}
		
		
		public void TransPlayStateEvent(Intent intent)
		{
			MusicData data = new MusicData();
			int playState = intent.getIntExtra(MusicPlayState.PLAY_STATE_NAME, -1);
			Bundle bundle = intent.getBundleExtra(MusicData.KEY_MUSIC_DATA);
			if (bundle != null)
			{
				data = bundle.getParcelable(MusicData.KEY_MUSIC_DATA);
			}
			int playIndex = intent.getIntExtra(MusicPlayState.PLAY_MUSIC_INDEX, -1);

			switch (playState) {
			case MusicPlayState.MPS_INVALID:
				mMusicTimer.stopTimer();
				Toast.makeText(MusicPlayActivity.this, "��ǰ�����ļ���Ч", Toast.LENGTH_SHORT).show();
				mUIManager.setPlayInfo(0, data.mMusicTime, data.mMusicName);
				mUIManager.showPlay(true);
				break;
			case MusicPlayState.MPS_PREPARE:
				mMusicTimer.stopTimer();
				mCurMusicTotalTime = data.mMusicTime;
				if (mCurMusicTotalTime == 0)
				{
					mCurMusicTotalTime = mServiceManager.getDuration();
				}
				mUIManager.setPlayInfo(0, data.mMusicTime, data.mMusicName);
				mUIManager.showPlay(true);
				break;
			case MusicPlayState.MPS_PLAYING:
				mMusicTimer.startTimer();
				if (mCurMusicTotalTime == 0)
				{
					mCurMusicTotalTime = mServiceManager.getDuration();
				}
				mUIManager.setPlayInfo(mServiceManager.getCurPosition(), data.mMusicTime, data.mMusicName);
				mUIManager.showPlay(false);
				break;
			case MusicPlayState.MPS_PAUSE:
				mMusicTimer.stopTimer();
				if (mCurMusicTotalTime == 0)
				{
					mCurMusicTotalTime = mServiceManager.getDuration();
				}	
				mUIManager.setPlayInfo(mServiceManager.getCurPosition(), data.mMusicTime, data.mMusicName);
				mUIManager.showPlay(true);
				break;
			default:
				break;
			}
			
			mUIManager.setSongNumInfo(playIndex, m_MusicFileList.size());
			
			mListViewAdapter.setPlayState(playIndex, playState);
		}
		
		
	}
	
	//��ʾר����Ƭ
	private void showArtwork(MusicData mp3Info) {
		Bitmap bm = MediaAlbum.getArtwork(this, mp3Info.MusicId, mp3Info.MusicAlbumId, true, false);
		//�л�����ʱ��ר��ͼƬ����͸��Ч��
				Animation albumanim = AnimationUtils.loadAnimation(MusicPlayActivity.this, R.anim.album_replace);
				//��ʼ���Ŷ���Ч��
				musicAlbum.startAnimation(albumanim);
				if(bm != null) {
					musicAlbum.setImageBitmap(bm);	//��ʾר������ͼƬ
					musicAblumReflection.setImageBitmap(ImageUtil.createReflectionBitmapForSingle(bm));	//��ʾ��Ӱ
				} else {
					bm = MediaAlbum.getDefaultArtwork(this, false);
					musicAlbum.setImageBitmap(bm);	//��ʾר������ͼƬ
					musicAblumReflection.setImageBitmap(ImageUtil.createReflectionBitmapForSingle(bm));	//��ʾ��Ӱ
				}
	}


	
	
    
    class UIManager implements OnItemClickListener{
    	
    	private final static String TAG = "UIManager";
    	
    	private SliderDrawerManager mSliderDrawerManager;
    	
    	private PopMenuManager 		mPopMenuManager;
    	
    	public ListView				mListView;

    	private View        		mMusicListView;
    	  
  	
    	private int mModeDrawableIDS[] = {	R.drawable.mode_single_loop,
				R.drawable.mode_order,
				R.drawable.mode_list_loop,
				R.drawable.mode_random
    	};
    	
    	private String modeToasts[] = {"����ѭ��",
    									"˳�򲥷�",
    									"�б�ѭ��",
    									"�������"
    	};
    	
    	public UIManager()
    	{ 		
    		initView();
    	}
    	
    	private void initView()
    	{
           
        	mListView = (ListView) findViewById(R.id.listView);
        	mListView.setOnItemClickListener(this);
        	mMusicListView = findViewById(R.id.ListLayout);
        	
    		mSliderDrawerManager = new SliderDrawerManager();
    		mPopMenuManager = new PopMenuManager();
    		//Toast.makeText(MusicPlayActivity.this, listPosition, Toast.LENGTH_SHORT).show();
    	
    	}
    	
    	public void setPlayInfo(int curTime, int totalTime, String musicName)
    	{
    		curTime /= 1000;
    		totalTime /= 1000;
    		int curminute = curTime/60;
    		int cursecond = curTime%60;
    		
    		String curTimeString = String.format("%02d:%02d", curminute,cursecond);
    		
    		int totalminute = totalTime/60;
    		int totalsecond = totalTime%60;
    		String totalTimeString = String.format("%02d:%02d", totalminute,totalsecond);
    		
    		int rate = 0;
    		if (totalTime != 0)
    		{
    			rate = (int) ((float)curTime / totalTime * 100);       		       
    		}
    		
    		mSliderDrawerManager.mPlayProgress.setProgress(rate);
    		
    		mSliderDrawerManager.mcurtimeTextView.setText(curTimeString);
    		mSliderDrawerManager.mtotaltimeTextView.setText(totalTimeString);
    		if (musicName != null)
    		{
    			mSliderDrawerManager.mPlaySongTextView.setText(musicName);	
    		}
    	
    	}
    	
    	
    	public void emptyPlayInfo()
    	{
    		mSliderDrawerManager.mPlayProgress.setProgress(0);	
    		mSliderDrawerManager.mcurtimeTextView.setText("00:00");
    		mSliderDrawerManager.mtotaltimeTextView.setText("00:00");
    		mSliderDrawerManager.mPlaySongTextView.setText(R.string.default_title_name);	
    		
    	}
    	
    	public void setSongNumInfo(int curPlayIndex, int totalSongNum)
    	{
    		String str = String.valueOf(curPlayIndex + 1) + "/" + String.valueOf(totalSongNum);
    		listPosition=Integer.parseInt(String.valueOf(curPlayIndex ));
    		m_MusicFileList = getMusicFileList();
    		MusicData data = m_MusicFileList.get(listPosition);
			showArtwork(data);
    		//Toast.makeText(MusicPlayActivity.this, a, Toast.LENGTH_SHORT).show();
    		mSliderDrawerManager.mSongNumTextView.setText(str);
    	}
    	
    	public void showPlay(boolean flag)
    	{
    		if (flag)
    		{
    			mSliderDrawerManager.mBtnPlay.setVisibility(View.VISIBLE);
    			mSliderDrawerManager.mBtnPause.setVisibility(View.GONE);
    			mSliderDrawerManager.mBtnHandlePlay.setVisibility(View.VISIBLE);
    			mSliderDrawerManager.mBtnHandlePause.setVisibility(View.INVISIBLE);
        		
    		}else{
    			mSliderDrawerManager.mBtnPlay.setVisibility(View.GONE);
    			mSliderDrawerManager.mBtnPause.setVisibility(View.VISIBLE);
    			mSliderDrawerManager.mBtnHandlePlay.setVisibility(View.INVISIBLE);
    			mSliderDrawerManager.mBtnHandlePause.setVisibility(View.VISIBLE);
    		}
    		
    	}
    	
    	public void ShowHandlePanel(boolean flag)
    	{
    		if (flag)
    		{
    			mSliderDrawerManager.mHandlePane.setVisibility(View.VISIBLE);
    		}else{
    			mSliderDrawerManager.mHandlePane.setVisibility(View.INVISIBLE);
    		}
    		
    	}
    	
    	public void setPlayMode(int mode, Boolean bShowToast)
    	{
    		if (mode >= MusicPlayMode.MPM_SINGLE_LOOP_PLAY && mode <= MusicPlayMode.MPM_RANDOM_PLAY)
    		{
    			mSliderDrawerManager.mBtnModeSet.setImageResource(mModeDrawableIDS[mode]);
        		
        		if (bShowToast)
        		{
        			Toast.makeText(MusicPlayActivity.this, modeToasts[mode], Toast.LENGTH_SHORT).show();	
        		}
        		
    		}

    	}

    	public void Back()
    	{
    		if (mSliderDrawerManager.mSlidingDrawer.isOpened())
    		{
    			mSliderDrawerManager.mSlidingDrawer.close();
    		}else{
    			finish();
    		}
    	}
    	
    	

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
				long arg3) {
			// TODO Auto-generated method stub
			
			play(pos);		
			
		}
		
		

		
		
		
		class SliderDrawerManager  implements OnClickListener, OnSeekBarChangeListener, 
		OnDrawerOpenListener, OnDrawerCloseListener, IOnSliderHandleViewClickListener
		{
			public MySlidingDrawer mSlidingDrawer;
			
			
			public ImageButton   mBtnHandle;
			public ImageButton   mBtnHandlePlay;
			public ImageButton   mBtnHandlePause;
			public TextView 	  mSongNumTextView;
			public View 		  mHandlePane;
			public TextView mPlaySongTextView;
		
	    	
			
			public ImageButton mBtnModeSet;
			public ImageButton mBtnVolumnSet;
			public SeekBar mPlayProgress;
			public TextView mcurtimeTextView;
			public TextView mtotaltimeTextView;
	    	
	    	
			public ImageButton mBtnPlay;
			public ImageButton mBtnPause;
			public ImageButton mBtnStop;	    		    	
			public ImageButton mBtnPlayNext;
			public ImageButton mBtnPlayPre;
			    	  
	    	
	    	private boolean mPlayAuto = true;
	    	
	    	
	    	private GalleryFlow mGalleryFlow1;
	    	private GalleryFlow mGalleryFlow2;
	    	private AudioManager am;		//��Ƶ�������ã��ṩ����Ƶ�Ŀ���
	    	RelativeLayout ll_player_voice;	//����������岼��
	    	int currentVolume;				//��ǰ����
	    	int maxVolume;					//�������
	    	public ImageButton mBtnMenu;	//��ʾ�����������İ�ť
	    	SeekBar GL_player_voice;		//����������С
	    	// ���������ʾ�����ض���
	    	private Animation showVoicePanelAnimation;
	    	private Animation hiddenVoicePanelAnimation;
	    	
			public SliderDrawerManager()
			{
				initView();
			}
			
			private void initView()
			{
				GL_player_voice = (SeekBar) findViewById(R.id.GL_player_voice);
				mBtnPlay = (ImageButton) findViewById(R.id.buttonPlay);
	        	mBtnPause = (ImageButton) findViewById(R.id.buttonPause);
	        	mBtnStop = (ImageButton) findViewById(R.id.buttonStop);
	        	mBtnPlayPre = (ImageButton) findViewById(R.id.buttonPlayPre);      	
	        	mBtnPlayNext = (ImageButton) findViewById(R.id.buttonPlayNext);      	
	            mBtnMenu = (ImageButton) findViewById(R.id.buttonMenu);
	            mBtnModeSet = (ImageButton) findViewById(R.id.buttonMode);
	            mBtnVolumnSet = (ImageButton) findViewById(R.id.buttonVolumn);
	        	mBtnHandle = (ImageButton) findViewById(R.id.handler_icon);
	        	mBtnHandlePlay= (ImageButton) findViewById(R.id.handler_play);
	        	mBtnHandlePause = (ImageButton) findViewById(R.id.handler_pause);
	        	musicAlbum = (ImageView) findViewById(R.id.iv_music_ablum);
	    		musicAblumReflection = (ImageView) findViewById(R.id.iv_music_ablum_reflection);
	            mBtnPlay.setOnClickListener(this);
	            mBtnPause.setOnClickListener(this);
	            mBtnStop.setOnClickListener(this);
	            mBtnPlayPre.setOnClickListener(this);
	            mBtnPlayNext.setOnClickListener(this);
	            mBtnStop.setOnClickListener(this);
	            mBtnMenu.setOnClickListener(this);	        	
	        	mBtnModeSet.setOnClickListener(this);
	        	mBtnVolumnSet.setOnClickListener(this);
		        GL_player_voice.setOnSeekBarChangeListener(new SeekBarChangeListener());
		        ll_player_voice = (RelativeLayout) findViewById(R.id.ll_player_voice);
	            mPlaySongTextView = (TextView) findViewById(R.id.textPlaySong);        	
	        	mcurtimeTextView = (TextView) findViewById(R.id.textViewCurTime);
	        	mtotaltimeTextView = (TextView) findViewById(R.id.textViewTotalTime);
	        	mSongNumTextView = (TextView) findViewById(R.id.textSongNum);
	        	
	        		    
	        	
	        	mPlayProgress = (SeekBar) findViewById(R.id.seekBar);
	        	mPlayProgress.setOnSeekBarChangeListener(new SeekBarChangeListener());
	        	
	       
	        	mSlidingDrawer = (MySlidingDrawer) findViewById(R.id.slidingDrawer);
	        	mSlidingDrawer.setOnDrawerOpenListener(this);
	        	mSlidingDrawer.setOnDrawerCloseListener(this);
	        	mSlidingDrawer.setHandleId(R.id.handler_icon);
	        	mSlidingDrawer.setTouchableIds(new int[]{R.id.handler_play, R.id.handler_pause});
	        	mSlidingDrawer.setOnSliderHandleViewClickListener(this);
	        	
     	
	        
	        	mHandlePane = findViewById(R.id.handle_panel);
	        	//�������������ʾ�����صĶ���
	    		showVoicePanelAnimation = AnimationUtils.loadAnimation(MusicPlayActivity.this, R.anim.push_up_in);
	    		hiddenVoicePanelAnimation = AnimationUtils.loadAnimation(MusicPlayActivity.this, R.anim.push_up_out);
	    		
	    		//���ϵͳ��Ƶ����������
	    		am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
	    		currentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
	    		maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	    		GL_player_voice.setProgress(currentVolume);
	    		
	    		m_MusicFileList = getMusicFileList();
	    		MusicData data = m_MusicFileList.get(listPosition);
				showArtwork(data);
	    		am.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
			}
	    	
			/**
			 * ��ʾר������
			 */
			

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(v.getId())
				{
				case R.id.buttonPlay:
					rePlay();isplay=1;
					break;
				case R.id.buttonPause:
					pause();isplay=0;
					break;
				case R.id.buttonStop:
					stop();isplay=0;
					break;
				case R.id.buttonPlayPre:
					playPre();isplay=1;
					break;
				case R.id.buttonPlayNext:
					playNext();isplay=1;
					break;
				case R.id.buttonMenu:
					voicePanelAnimation();
					break;
				case R.id.buttonMode:
					modeChange();
					break;
				case R.id.buttonVolumn:
					Toast.makeText(MusicPlayActivity.this, "������,�����ť�����õ�", Toast.LENGTH_SHORT).show();					;
					break;
				default:
					break;
				}
			}
			//������ʾ�����������Ķ���
			public void voicePanelAnimation() {
				if(ll_player_voice.getVisibility() == View.GONE) {
					ll_player_voice.startAnimation(showVoicePanelAnimation);
					ll_player_voice.setVisibility(View.VISIBLE);
				}
				else{
					ll_player_voice.startAnimation(hiddenVoicePanelAnimation);
					ll_player_voice.setVisibility(View.GONE);
				}
			}
			private class SeekBarChangeListener implements OnSeekBarChangeListener {

				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					switch(seekBar.getId()) {
					case R.id.seekBar:
						if (fromUser) {
							mServiceManager.seekTo(progress); // �û����ƽ��ȵĸı�
						}
						break;
					case R.id.GL_player_voice:
						// ��������
						am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
						System.out.println("am--->" + progress);
						break;
					}
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {

				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {

				}

			}

		
			@Override
			public void onDrawerOpened() {
				// TODO Auto-generated method stub
				mMusicListView.setVisibility(View.INVISIBLE);
				mBtnHandle.setImageResource(R.drawable.handle_down);
				ShowHandlePanel(false);
			}

			@Override
			public void onDrawerClosed() {
				// TODO Auto-generated method stub
				mMusicListView.setVisibility(View.VISIBLE);
				mBtnHandle.setImageResource(R.drawable.handle_up);
				ShowHandlePanel(true);
			}

			@Override
			public void onViewClick(View view) {
				// TODO Auto-generated method stub
				switch(view.getId())
				{
				case R.id.handler_play:
					rePlay();isplay=1;
					break;
				case R.id.handler_pause:
					pause();isplay=0;
					break;
				default:
					break;
				}
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
		
			
			 
			
		}
		
		
		
	
		
		
		
		
		class PopMenuManager implements android.widget.PopupWindow.OnDismissListener{
			
			MenuItemData    		mMenuItemData;
			private GridView 		mMenuGrid;			// �����˵�GRIDVIEW
			private View 			mMenuView;			// �����˵���ͼ
			private GridViewAdapter	mGridViewAdapter;	// �����˵�������
			private PopupWindow		mPopupWindow;		// �����˵�WINDOW
			
			private View 			mPopBackgroundView;
			
			public PopMenuManager()
	    	{ 		
	    		initView();
	    	}
	    	
	    	private void initView()
	    	{
	    		mPopBackgroundView = findViewById(R.id.VirtualLayout);
	    		
	        	String []menu_name_array1 = getResources().getStringArray(R.array.menu_item_name_array);	
	        	LevelListDrawable levelListDrawable1 = (LevelListDrawable) getResources().getDrawable(R.drawable.menu_image_list);
	        	mMenuItemData = new MenuItemData(levelListDrawable1, menu_name_array1, menu_name_array1.length);
	        	
	        	LayoutInflater inflater = getLayoutInflater();
	        	mMenuView = inflater.inflate(R.layout.menu, null);
	    		mMenuGrid = (GridView)mMenuView.findViewById(R.id.menuGridView);
	    		

	    		mGridViewAdapter = new GridViewAdapter(MusicPlayActivity.this, mMenuItemData);
	    		mMenuGrid.setAdapter(mGridViewAdapter);

	    		
	        	mMenuGrid.setOnItemClickListener(new OnItemClickListener() {

	    			@Override
	    			public void onItemClick(AdapterView<?> parent, View view,
	    					int position, long id) {
	    				// TODO Auto-generated method stub		
	    			//	onMenuItemClick(position);
	    			}

	        		
	    		});        		
	       		
	       		
	       		
	    		mPopupWindow = new PopupWindow(mMenuView, LayoutParams.FILL_PARENT,  LayoutParams.WRAP_CONTENT);
	    		mPopupWindow.setFocusable(true);		// ���û�л�ý���menu�˵��еĿؼ��¼��޷���Ӧ
	    		
	    		//	�������м���ȥ��Ϳ���ʹ��BACK���ر�POPWINDOW
	    		ColorDrawable dw = new ColorDrawable(0x00);
	    		mPopupWindow.setBackgroundDrawable(dw);
	    		
	    	    mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);  
	    	    mPopupWindow.setOnDismissListener(this);
	    	}
	    	
	    	  
	       
	        
	        @Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				mPopBackgroundView.setVisibility(View.INVISIBLE);
			}
		}
    }
    //������Ӧ����
    private static final int SHAKE_THRESHOLD = 4000;//������ƾ��ȣ�ԽС��ʾ��ӦԽ����
	private long lastUpdate=0;
	private double last_x=0;
	private double last_y= 4.50;
	private double last_z=9.50;
	//������ƾ��ȣ�ԽС��ʾ��ӦԽ����
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		//����׼�ȸı�
	}


	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
			long curTime = System.currentTimeMillis();
			
			// ÿ100������һ��   
			if ((curTime - lastUpdate) > 100) { 
			long diffTime = (curTime - lastUpdate);  
			lastUpdate = curTime;   
			double x=event.values[SensorManager.DATA_X];
			double y=event.values[SensorManager.DATA_Y];
			double z=event.values[SensorManager.DATA_Z];
			float speed = (float) (Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000);   			  
			if (speed > SHAKE_THRESHOLD) {   
				
                        //��⵽ҡ�κ�ִ�еĴ���
				 if(isplay==1){
					 if(Shake_pause==20&&Shake_next!=20)
					 {
						 vibrator.vibrate(1000);//�ֻ���һ��
					
					  pause();
					 }
					 if(Shake_next==20&&Shake_pause!=20)
					  playNext();
					  isplay=0;
					
				  }else {
					  if(Shake_pause==20)
					  rePlay();
					  isplay=1;	
				  }
				 if(Shake_pause==20&&Shake_next==20)
				 {
					 Toast.makeText(MusicPlayActivity.this, "������˼��˦���и��˦����ֻͣ�ܿ���һ����", Toast.LENGTH_SHORT).show();
				 }
			}  
			last_x = x;   
			last_y = y;   
			last_z = z;   
			}
		}
	}
	//�绰����
	private class MobliePhoneStateListener extends PhoneStateListener {

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE: /* ���κ�״̬ʱ */
				rePlay();
				
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK: /* ����绰ʱ */
				
			case TelephonyManager.CALL_STATE_RINGING: /* �绰����ʱ */
				  pause();
				break;
			default:
				break;

			}

		}

	}

	/**
	 * �����ؼ������Ի���ȷ���˳�
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			new com.genius.widget.CustomDialog.Builder(MusicPlayActivity.this)
					.setTitle(R.string.info)
					.setMessage(R.string.dialog_messenge)
					.setPositiveButton(R.string.confrim,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									exit();

								}
							}).setNeutralButton(R.string.cancel, null).show();
			return false;
		}
		return false;
	}

	
}