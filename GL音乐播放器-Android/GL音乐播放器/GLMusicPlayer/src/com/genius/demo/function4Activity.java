package com.genius.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.genius.demo.MusicPlayActivity;
import com.genius.demo.furui.R;
import com.genius.demo.WelcomActivity;

/**
 * @author Google_acmer
 *
 */
public class function4Activity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	
	private  ImageButton next;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show4);
        next=(ImageButton)findViewById(R.id.nextbutton);
        next.setOnClickListener(this);
        
    }
   

		public void onClick(View v) {
			// TODO Auto-generated method stub
		finish();
			Intent intent = new Intent(function4Activity.this,MusicPlayActivity.class);
			startActivity(intent);
			
		}
}
    
