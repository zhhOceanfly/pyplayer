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
		//���������ʵ�֣���
		/*String  MessageStr= Messages.getText().toString();
		 MailSenderInfo mailInfo = new MailSenderInfo(); 
		  mailInfo.setMailServerHost("smtp.sina.com"); 
		  mailInfo.setMailServerPort("25"); 
		  mailInfo.setValidate(true); 
		  mailInfo.setUserName("hjjhjj11111@sina.com"); 
		  mailInfo.setPassword("12346789");//������������ 
		  mailInfo.setFromAddress("18004023915@sina.com"); 
		  mailInfo.setToAddress("hjjhjj1111@sina.com"); 
		  mailInfo.setSubject("�����������"); 
		  mailInfo.setContent(MessageStr); 
	        //�������Ҫ�������ʼ�
		  SimpleMailSender sms = new SimpleMailSender();
	        sms.sendTextMail(mailInfo);//���������ʽ 
	       sms.sendHtmlMail(mailInfo);//����html��ʽ
	*/	
		Toast.makeText(SendMessage.this, "�Բ������������������֤ʧ�ܣ��ܾ������ʼ��������Գ���������ʽ��½������1015121748@qq.com!лл������ϣ�", Toast.LENGTH_LONG).show();
		
	}
	
}
