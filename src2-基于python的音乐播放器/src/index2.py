# -*- coding:utf-8 -*- 

import pygame,os,sys;
from pygame.locals import *;
from MButton import Button
from MStack import Stack
import wx;
import numpy as np
from pyaudio import PyAudio,paInt16
from datetime import datetime
import wave
import time
import pyaudio


MUSCI_PATH = ".\\music"; #����·��
IMAGE_PATH = ".\\image"  #ͼƬ·��
SCREEN_SIZE = (459, 650);  #���ô��ڴ�С
#define of params  
NUM_SAMPLES = 2000   # pyAudio�ڲ�����Ŀ�Ĵ�С
framerate = 8000     # ȡ��Ƶ��
channels = 1  
sampwidth = 2  
#record time  
TIME = 20    #���ó�ʼ��¼��ʱ��
#��¼��ʱ�����������ݱ���Ϊwav�ļ�  
def save_wave_file(filename, data):    
    '''''save the date to the wav file'''  
    wf = wave.open(filename, 'wb')  
    wf.setnchannels(channels)  #������
    wf.setsampwidth(sampwidth)  #ÿλ���
    wf.setframerate(framerate)  #������
    wf.writeframes("".join(data))  
    wf.close()  
    
def record_wave():  
    #open the input of wave  
    pa = PyAudio()  
    stream = pa.open(format = paInt16, channels = 1,  
                    rate = framerate, input = True,  
                    frames_per_buffer = NUM_SAMPLES)  
    #ȡ��ֵ��������ʽ (paFloat32, paInt32, paInt24, paInt16, paInt8 ...)
    #input - ��������־�����ΪTrue�Ļ�����������
    #output - �������־�����ΪTrue�Ļ����������
    save_buffer = []  
    count = 0 
    #������ȡ���ݵ��������У���ȡʱ��ΪԤ��TIME 
    while count < TIME*4:  
        #read NUM_SAMPLES sampling data  
        string_audio_data = stream.read(NUM_SAMPLES)  
        save_buffer.append(string_audio_data) #����ȡ��������ӵ������� 
        count += 1  
        print 'recording... ...'  
    #����ǰʱ����Ϊ�ļ��� 
    filename = datetime.now().strftime("%Y-%m-%d_%H_%M_%S")+".wav"  
    # ��save_buffer�е�����д��WAV�ļ���WAV�ļ����ļ����Ǳ����ʱ��
    save_wave_file(filename, save_buffer)
    save_buffer = []  
    print filename, "saved" 

    
def getMusic(path):
    stack = Stack();
    music_files = [];
    stack.push(path);  #ʹ��ջ������·��
    name = [];
    if not os.path.exists(path): #���·���Ƿ���ڣ�os.path.exists() ���Ŀ¼�����ڣ��᷵��һ��0ֵ
        return [], [];
    while stack.size() >0:
        tmp = stack.pop();  #��·����ֵ���������������ݸ���tmp
        
        files = os.listdir(tmp);
        for filename in files:
            if os.path.isdir(tmp+ "\\"+filename):
                stack.push(tmp+ "\\"+filename);
            elif os.path.isfile(tmp+ "\\"+filename):
                if filename.lower().endswith(".wav") or filename.lower().endswith(".ogg") or filename.lower().endswith(".mp3"):
                    new = os.path.join(tmp, filename);
                    music_files.append(new);
                    name.append(filename);
    return music_files, name;  #������Ƶ�ļ���

def draw_dbf():
    NUM_SAMPLE = 2000      # pyAudio�ڲ�����Ŀ�Ĵ�С
    SAMPLING_RATE = 8000    # ȡ��Ƶ��

    # ������������
    pa=pyaudio.PyAudio()
       
    stream = pa.open(format=paInt16, channels=1, rate=SAMPLING_RATE, input=True, 
                frames_per_buffer=NUM_SAMPLE) 
        

    # ����NUM_SAMPLES��ȡ��
    string_audio_data = stream.read(NUM_SAMPLE) 
    # �����������ת��Ϊ����
    audios_data = np.fromstring(string_audio_data, dtype=np.short)
    pa.terminate()
    return audios_data



def main(path):
    reload(sys);
    sys.setdefaultencoding("GBK"); #ϵͳĬ���ַ�����ΪGBK
    if path is None:
        path = MUSCI_PATH;
    pygame.init();  #��ʼ��pygame
    
    #���ô���/DOUBLEBUF    ����һ����˫���塱���ڣ�������HWSURFACE����OPENGLʱʹ��
    #ʹ��˫���������Ը���
    screen = pygame.display.set_mode(SCREEN_SIZE, DOUBLEBUF,32);
    
    #���ô��ڱ���
    pygame.display.set_caption("P A P");
    #���ػ���������ͼƬ
    high = pygame.image.load('.\\image\\high.png');
    #���ػ��Ʋ��Ž�������ͼƬ
    pos = pygame.image.load('.\\image\\pos.png');
    #���ػ��Ʒֱ�ͼ��ͼƬ
    db_image = pygame.image.load('.\\image\\2.png')
    
    #����ͼ
    #�����
    #mouse_cursor= pygame.image.load('.\\image\\cursor.png').convert_alpha();
    bg_image = pygame.image.load(".\\image\\guitar1.jpg").convert_alpha();
    
    
    
    count=8;
    
    #������ʾ����
    #ʹ�õ�ǰ�����壬����޷�ʵ�֣���ʹ��ϵͳ�Դ��Ŀ���
    try:
        font = pygame.font.Font(".\\font\\yh.ttf", 35);
        vfont = pygame.font.Font(".\\font\\yh.ttf", 20); 
    except:
        font = pygame.font.SysFont("kaiti", 35);
        vfont = pygame.font.SysFont("kaiti", 20); 
    #ʱ�ӹ���
    clock = pygame.time.Clock();
    #������,�����ʡ�λ������������buffer
    pygame.mixer.pre_init(44100, 16, 2, 1024*4);
    #���ֽ���������TRACK_END�¼�
    #USEREVENT������һ���û��¼�
    TRACK_END = USEREVENT + 1   
    pygame.mixer.music.set_endevent(TRACK_END)
    
    #��ť
    buttons = {};
    buttons["pre"] = Button("pre",200,565,".\\image\\pre.png");
    buttons["play"] = Button("play",300,565,".\\image\\play.png");
    buttons["pause"] = Button("pause",300,565,".\\image\\pause.png");
    buttons["next"] = Button("next",400,565,".\\image\\next.png");
    #buttons["down"] = Button("next",520,510,".//image//down.png");
    #buttons["up"] = Button("next",570,510,".//image//up.png");
    buttons["record"] = Button("record",100,565,".//image//record.png");
    #buttons["end"] = Button("end",50,525,".//image//end.png");

    music_files,names =  getMusic(path);
    max_track = len(music_files);#�����ܳ���
    
    music_name = [];
    for name in names:
        #txt = os.path.split(name)[-1];
        try:
            tmp = name[:-4].strip().decode("GBK");
        except :
            tmp = name[:-4].strip();
        
        music_name.append(font.render(tmp, True, (250, 242, 120)));
    #����һЩ��־λ
    playing = False;
    playing_index = 0;
    first_play = True;
    #���ó�ʼ����
    volume = 0.8;

    #ʱ��ѭ��
    mainloop = True;
    vol_change = False;
    draw_time = 0;
    suprise = True;
    s_setp = 0;
    
    x =100  #�����ĳ�ʼx����ֵ
    
    while mainloop:
        clock.tick(30);     #������Ļˢ������
        pygame.mixer.music.set_volume(volume);  #��ȡ����ֵ
        #������
        screen.blit(bg_image,(0,0));
        
        button_pressed = None;
        #��pygame.event.get()��ʱ������л�ȡ�¼�������Ȼ���ж���ʲô�����¼�
        #event.get()����ÿ��ִ�к󷵻�eventlist
        for event in pygame.event.get():
            if event.type == QUIT:  #�˳�����
                mainloop = False;
                pygame.mixer.quit();
                pygame.quit();
                return;
            elif event.type == KEYDOWN:
                if event.key == K_UP:
                    volume += 0.1;
                    vol_change = True;
                    draw_time = 0;
                    
                    count+=1;
                elif event.key == K_DOWN:
                    volume -= 0.1;
                    vol_change = True;
                    draw_time = 0;
                    
                    count-=1;
                if volume > 1.0:  #�趨�����ķ�Χ
                    volume = 1.0;
                elif volume < 0.0:
                    volume = 0.0;
                
                #ģ�������
                elif event.key == pygame.K_q:
                    soundwav=pygame.mixer.Sound("DO.wav")
                    soundwav.play()  
                elif event.key == pygame.K_w:  
                    soundwav=pygame.mixer.Sound("RE.wav")
                    soundwav.play()  
                elif event.key == pygame.K_e:  
                    soundwav=pygame.mixer.Sound("M.wav")
                    soundwav.play()  
                elif event.key == pygame.K_r:  
                    soundwav=pygame.mixer.Sound("FA.wav")
                    soundwav.play()
                elif event.key == pygame.K_t:  
                    soundwav=pygame.mixer.Sound("SO.wav")
                    soundwav.play()  
                elif event.key == pygame.K_y:  
                    soundwav=pygame.mixer.Sound("LA.wav")
                    soundwav.play()
                elif event.key == pygame.K_u:  
                    soundwav=pygame.mixer.Sound("XI.wav")
                    soundwav.play()
            
                elif event.key == pygame.K_1:
                    soundwav=pygame.mixer.Sound("DOL.wav")
                    soundwav.play()
                elif event.key == pygame.K_2:
                    soundwav=pygame.mixer.Sound("REL.wav")
                    soundwav.play()
                elif event.key == pygame.K_3:
                    soundwav=pygame.mixer.Sound("ML.wav")
                    soundwav.play()    
                elif event.key == pygame.K_4:
                    soundwav=pygame.mixer.Sound("FAL.wav")
                    soundwav.play()    
                elif event.key == pygame.K_5:
                    soundwav=pygame.mixer.Sound("SOL.wav")
                    soundwav.play()   
                elif event.key == pygame.K_6:
                    soundwav=pygame.mixer.Sound("LAL.wav")
                    soundwav.play()    
                elif event.key == pygame.K_7:
                    soundwav=pygame.mixer.Sound("XIL.wav")
                    soundwav.play()    
                
                
            elif event.type == MOUSEBUTTONUP:  # �������
                for name,but in buttons.iteritems():
                    if name == "play" and playing:
                        continue;
                    elif name == "pause" and not playing:
                        continue;
                    elif but.isIn(event.pos):
                        button_pressed = name;
                        break;
            
            elif event.type == TRACK_END:
                button_pressed = "next";     #ģ�ⰴ����һ��
                pygame.time.delay(1000);
            
        #�������¼���
        try:
            if button_pressed is not None:
                if button_pressed == "play":
                    if first_play:
                        pygame.mixer.music.load(music_files[playing_index]);
                        first_play = False;
                        pygame.mixer.music.play();
                        playing = True;
                    elif not playing:
                        pygame.mixer.music.unpause();
                        playing = True;
                        
                elif button_pressed == "pause":
                    if first_play:
                        pass;
                    elif playing:
                        pygame.mixer.music.pause();
                        playing = False;
                        
                elif button_pressed == "pre":
                    if playing:
                        if pygame.mixer.music.get_pos() > 3000: #���Ŵ��������˾����¿�ʼ��
                            pygame.mixer.music.stop();
                            pygame.mixer.music.play();
                        else:
                            pygame.mixer.music.stop();
                            playing_index = (playing_index - 1) % max_track;
                            pygame.mixer.music.load(music_files[playing_index]);
                            pygame.mixer.music.play();
                    else:
                        pygame.mixer.music.stop();
                        first_play = True;
                        playing_index = (playing_index - 1) % max_track;
                    
                    
                        
                               
                elif button_pressed == "next":
                    if playing:
                            pygame.mixer.music.stop();
                            playing_index = (playing_index + 1) % max_track;
                            ok = False;
                            pygame.mixer.music.load(music_files[playing_index]);
                            pygame.mixer.music.play();
                            
                            
                    else:
                        pygame.mixer.music.stop();
                        first_play = True;
                        playing_index = (playing_index + 1) % max_track;
                elif button_pressed == "record":
                    record_wave()
                    
                    

        except Exception ,e:  #������ִ��󣬽�����д����־�ļ������˳�����
            file = open("log.txt", "a");
            file.write(str(e));
            file.flush();
            file.close();
            dlg = wx.MessageDialog(None, "��֧�ָ��ļ���", "����", wx.OK|wx.ICON_ERROR);
            dlg.ShowModal();
            dlg.Destroy();
            mainloop = True;
            pygame.quit();
            sys.exit(0);
        #��������
        for i in range(count):
            screen.blit(high,(93+10*i, 33))
        
        
        #����ť
        for button in buttons.values():
            if playing and button.name == "play" :
                continue;
            elif not playing and button.name == "pause":
                continue;
            button.draw(screen);
            
         #д����
        if max_track == 0:
            label = font.render(("�ļ�����û������ ").decode('GBK'), True, (250, 242, 120));
            
        else:
            label = music_name[playing_index];
        w, h = label.get_size();
        screen_w = SCREEN_SIZE[0];
        #��������
        x-=2
        if x < - label.get_width():
            x =459-label.get_width();
        
        screen.blit(label, (x, 85));
        
        #дʱ��
        if first_play:
            txt = "0:0" + "  " + str(playing_index+1) +"/" + str(max_track);
        else:
            time = pygame.mixer.music.get_pos() / 1000;
            txt = str(time / 60)+ ":" + str(time % 60)+ "  " + str(playing_index+1)+ "/" + str(max_track);
        
        label = font.render(txt, True, (250, 242, 120))
        w, h = label.get_size();
        screen.blit(label, ((screen_w - w)/2, 150 - h/2));
        #���Ʋ��Ž���״̬
        tme = pygame.mixer.music.get_pos() / 1000;
        
        if tme>210:
            tme=210      
        for j in range(tme):
            screen.blit(pos,(22+2*j, 584))
        
        #pygame.draw.rect(screen, (0, 255, 0), ((115, 140), (250, 180)), 3)
        audio_data = draw_dbf()
       
        m1=int(abs(audio_data[0])/100)
        if m1>20:
            m1=20
        m2=int(abs(audio_data[20])/100)
        if m2>20:
            m2=20
        m3=int(abs(audio_data[40])/100)
        if m3>20:
            m3=20
        n1=int(abs(audio_data[80])/100)
        if n1>20:
            n1=20
        n2=int(abs(audio_data[100])/100)
        if n2>20:
            n2=20
        n3=int(abs(audio_data[120])/100)
        if n3>20:
            n3=20
        n4=int(abs(audio_data[140])/100)
        if n4>20:
            n4=20
        n5=int(abs(audio_data[160])/100)
        if n5>20:
            n5=20
        n6=int(abs(audio_data[180])/100)
        if n6>20:
            n6=20
        for i in range(m1):
            screen.blit(db_image,(120,300-10*i))
        for i in range(m2):
            screen.blit(db_image,(143,300-10*i))
        for i in range(m3):
            screen.blit(db_image,(166,300-10*i))
        for i in range(n1):
            screen.blit(db_image,(189,300-10*i))
        for i in range(n2):
            screen.blit(db_image,(212,300-10*i))
        for i in range(n3):
            screen.blit(db_image,(235,300-10*i))    
        for i in range(n4):
            screen.blit(db_image,(258,300-10*i))
        for i in range(n5):
            screen.blit(db_image,(281,300-10*i))  
        for i in range(n6):
            screen.blit(db_image,(304,300-10*i))
        audio_data=[]
        
        
           
            
        #������
        draw_time += 1;
        if vol_change:
            if draw_time > 3:
                draw_time = 0;
                vol_change = False;
            now_vol = int( pygame.mixer.music.get_volume() * 10 );
            label = vfont.render(("����: "+str(now_vol)).decode('GBK'), True, (255, 48, 33))
            w, h = label.get_size();
            #screen.blit(label, ((screen_w - w)/2, 180));
        #suprise
        if suprise:
            s_setp += 1;
            if s_setp > 40:
                suprise = False;
            label = vfont.render("��/���Ӽ�����".decode("GBK"), True, (250, 242, 120))
            w, h = label.get_size();
            screen.blit(label, ((screen_w - w)/2, 220));  

        '''a,b=pygame.mouse.get_pos()
        #������λ��
        pygame.mouse.set_visible(False)
        a-= mouse_cursor.get_width()/2
        b-= mouse_cursor.get_height()/2
        #����������Ͻ�λ��
        screen.blit(mouse_cursor, (a, b))
        #�ѹ�껭��ȥ
        '''
      
        
        #��pygame.display.flip()��ˢ����ʾ
        pygame.display.flip(); #˫����Ҫ��������� 
        #pygame.display.update()�ǽ����ݻ���ǰ����ʾ



if __name__ == "__main__":
    path = None;
    app = wx.PySimpleApp()  
    dialog = wx.DirDialog(None, 'ѡ��һ�������ļ���',  defaultPath = "C://",
          style=wx.DD_DEFAULT_STYLE | wx.DD_NEW_DIR_BUTTON)  
    if dialog.ShowModal() == wx.ID_OK:  
        path = dialog.GetPath()   
    dialog.Destroy()  
    #path = MUSCI_PATH;
    
    main(path);
    sys.exit(0);
