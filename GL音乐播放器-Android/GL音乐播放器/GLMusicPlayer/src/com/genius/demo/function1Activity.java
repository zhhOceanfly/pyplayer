package com.genius.demo;



import com.genius.demo.MusicPlayActivity;
import com.genius.demo.furui.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
/**
 * @author Google_acmer
 *
 */
public class function1Activity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	
	private ImageButton next;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show1);
      next=(ImageButton)findViewById(R.id.nextbutton);
        next.setOnClickListener(this);
        
    }
   

		public void onClick(View v) {
			// TODO Auto-generated method stub
		    finish();
			Intent intent = new Intent(function1Activity.this,function2Activity.class);
			startActivity(intent);
			
		}
    	
    
}
    