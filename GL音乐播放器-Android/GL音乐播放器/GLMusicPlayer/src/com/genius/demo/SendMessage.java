/**
 * 
 */
package com.genius.demo;

import com.genius.demo.furui.R;
import com.util.mail.MailSenderInfo;
import com.util.mail.SimpleMailSender;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * @author google_acmer
 *
 */
public class SendMessage extends Activity implements OnClickListener{
	public ImageButton send;
	private EditText Messages;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        Messages=(EditText)findViewById(R.id.editText2);
        send = (ImageButton) findViewById(R.id.send_message);
        send.setOnClickListener(this);
       
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//技术问题待实现！！
		/*String  MessageStr= Messages.getText().toString();
		 MailSenderInfo mailInfo = new MailSenderInfo(); 
		  mailInfo.setMailServerHost("smtp.sina.com"); 
		  mailInfo.setMailServerPort("25"); 
		  mailInfo.setValidate(true); 
		  mailInfo.setUserName("hjjhjj11111@sina.com"); 
		  mailInfo.setPassword("12346789");//您的邮箱密码 
		  mailInfo.setFromAddress("18004023915@sina.com"); 
		  mailInfo.setToAddress("hjjhjj1111@sina.com"); 
		  mailInfo.setSubject("设置邮箱标题"); 
		  mailInfo.setContent(MessageStr); 
	        //这个类主要来发送邮件
		  SimpleMailSender sms = new SimpleMailSender();
	        sms.sendTextMail(mailInfo);//发送文体格式 
	       sms.sendHtmlMail(mailInfo);//发送html格式
	*/	
		Toast.makeText(SendMessage.this, "对不起，由于邮箱服务器验证失败，拒绝接收邮件，您可以尝试其他方式登陆发送至1015121748@qq.com!谢谢您的配合！", Toast.LENGTH_LONG).show();
		
	}
	
}
