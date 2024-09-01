package com.genius.demo;




import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.genius.demo.furui.R;

public class WelcomActivity extends Activity {

	SharedPreferences preferences;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	//随机选择欢迎界面
		if(((Math.random()*10))<1)
        {
        	
			setContentView(R.layout.welcome);
        }
        else if(((Math.random()*10))<5&&((Math.random()*10))>=3)
        {
        	setContentView(R.layout.welcome1);
        	
        }
         else if(((Math.random()*10))<7&&((Math.random()*10))>=5)
         {
        	 setContentView(R.layout.welcome2);
         }
         else if(((Math.random()*10))<=9&&((Math.random()*10))>7)
         {
        	 setContentView(R.layout.welcome3);
         }
         else 
         {
        	 setContentView(R.layout.welcome4);
         }
		//利用线程休眠
		  Thread thread = new Thread() {
	        	@Override
	        	public void run() {
	        	try {
	        	sleep(3000);
	        	} catch (InterruptedException e) {
	        	e.printStackTrace();
	        	}
	            finish();
	            preferences = getSharedPreferences("data", 0);
	          //判断是不是首次登录，  
	            if (preferences.getInt("firststart", 0)!=1) {   
	             //将登录标志位设置为false，下次登录时不在显示首次登录界面  
	             preferences.edit().putInt("firststart", 1).commit();  
	            finish();
	             Intent intent = new Intent(WelcomActivity.this,function1Activity.class);
	         	startActivity(intent);
	             }else
	             {
	        	Intent intent = new Intent(WelcomActivity.this,MusicPlayActivity.class);
	        	startActivity(intent);
	            }
	        	}
	        	
	        	};
	        	thread.start();
	        	
	}
	

}
