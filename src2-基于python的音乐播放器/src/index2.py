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


MUSCI_PATH = ".\\music"; #音乐路径
IMAGE_PATH = ".\\image"  #图片路径
SCREEN_SIZE = (459, 650);  #设置窗口大小
#define of params  
NUM_SAMPLES = 2000   # pyAudio内部缓存的块的大小
framerate = 8000     # 取样频率
channels = 1  
sampwidth = 2  
#record time  
TIME = 20    #设置初始化录音时间
#将录音时缓冲区的内容保存为wav文件  
def save_wave_file(filename, data):    
    '''''save the date to the wav file'''  
    wf = wave.open(filename, 'wb')  
    wf.setnchannels(channels)  #声道数
    wf.setsampwidth(sampwidth)  #每位宽度
    wf.setframerate(framerate)  #采样率
    wf.writeframes("".join(data))  
    wf.close()  
    
def record_wave():  
    #open the input of wave  
    pa = PyAudio()  
    stream = pa.open(format = paInt16, channels = 1,  
                    rate = framerate, input = True,  
                    frames_per_buffer = NUM_SAMPLES)  
    #取样值的量化格式 (paFloat32, paInt32, paInt24, paInt16, paInt8 ...)
    #input - 输入流标志，如果为True的话则开启输入流
    #output - 输出流标志，如果为True的话则开启输出流
    save_buffer = []  
    count = 0 
    #反复读取数据到缓冲区中，读取时间为预定TIME 
    while count < TIME*4:  
        #read NUM_SAMPLES sampling data  
        string_audio_data = stream.read(NUM_SAMPLES)  
        save_buffer.append(string_audio_data) #将读取的数据添加到缓冲区 
        count += 1  
        print 'recording... ...'  
    #将当前时间作为文件名 
    filename = datetime.now().strftime("%Y-%m-%d_%H_%M_%S")+".wav"  
    # 将save_buffer中的数据写入WAV文件，WAV文件的文件名是保存的时刻
    save_wave_file(filename, save_buffer)
    save_buffer = []  
    print filename, "saved" 

    
def getMusic(path):
    stack = Stack();
    music_files = [];
    stack.push(path);  #使用栈来保存路径
    name = [];
    if not os.path.exists(path): #检查路径是否存在，os.path.exists() 如果目录不存在，会返回一个0值
        return [], [];
    while stack.size() >0:
        tmp = stack.pop();  #将路径的值弹出，并将其内容赋给tmp
        
        files = os.listdir(tmp);
        for filename in files:
            if os.path.isdir(tmp+ "\\"+filename):
                stack.push(tmp+ "\\"+filename);
            elif os.path.isfile(tmp+ "\\"+filename):
                if filename.lower().endswith(".wav") or filename.lower().endswith(".ogg") or filename.lower().endswith(".mp3"):
                    new = os.path.join(tmp, filename);
                    music_files.append(new);
                    name.append(filename);
    return music_files, name;  #查找音频文件名

def draw_dbf():
    NUM_SAMPLE = 2000      # pyAudio内部缓存的块的大小
    SAMPLING_RATE = 8000    # 取样频率

    # 开启声音输入
    pa=pyaudio.PyAudio()
       
    stream = pa.open(format=paInt16, channels=1, rate=SAMPLING_RATE, input=True, 
                frames_per_buffer=NUM_SAMPLE) 
        

    # 读入NUM_SAMPLES个取样
    string_audio_data = stream.read(NUM_SAMPLE) 
    # 将读入的数据转换为数组
    audios_data = np.fromstring(string_audio_data, dtype=np.short)
    pa.terminate()
    return audios_data



def main(path):
    reload(sys);
    sys.setdefaultencoding("GBK"); #系统默认字符编码为GBK
    if path is None:
        path = MUSCI_PATH;
    pygame.init();  #初始化pygame
    
    #设置窗口/DOUBLEBUF    创建一个“双缓冲”窗口，建议在HWSURFACE或者OPENGL时使用
    #使用双缓冲区可以更快
    screen = pygame.display.set_mode(SCREEN_SIZE, DOUBLEBUF,32);
    
    #设置窗口标题
    pygame.display.set_caption("P A P");
    #加载绘制音量的图片
    high = pygame.image.load('.\\image\\high.png');
    #加载绘制播放进度条的图片
    pos = pygame.image.load('.\\image\\pos.png');
    #加载绘制分贝图的图片
    db_image = pygame.image.load('.\\image\\2.png')
    
    #背景图
    #鼠标光标
    #mouse_cursor= pygame.image.load('.\\image\\cursor.png').convert_alpha();
    bg_image = pygame.image.load(".\\image\\guitar1.jpg").convert_alpha();
    
    
    
    count=8;
    
    #中文显示字体
    #使用当前的字体，如果无法实现，再使用系统自带的楷体
    try:
        font = pygame.font.Font(".\\font\\yh.ttf", 35);
        vfont = pygame.font.Font(".\\font\\yh.ttf", 20); 
    except:
        font = pygame.font.SysFont("kaiti", 35);
        vfont = pygame.font.SysFont("kaiti", 20); 
    #时钟工具
    clock = pygame.time.Clock();
    #混音器,采样率、位数、声道数、buffer
    pygame.mixer.pre_init(44100, 16, 2, 1024*4);
    #音乐结束则析出TRACK_END事件
    #USEREVENT触发了一个用户事件
    TRACK_END = USEREVENT + 1   
    pygame.mixer.music.set_endevent(TRACK_END)
    
    #按钮
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
    max_track = len(music_files);#音乐总长度
    
    music_name = [];
    for name in names:
        #txt = os.path.split(name)[-1];
        try:
            tmp = name[:-4].strip().decode("GBK");
        except :
            tmp = name[:-4].strip();
        
        music_name.append(font.render(tmp, True, (250, 242, 120)));
    #设置一些标志位
    playing = False;
    playing_index = 0;
    first_play = True;
    #设置初始音量
    volume = 0.8;

    #时间循环
    mainloop = True;
    vol_change = False;
    draw_time = 0;
    suprise = True;
    s_setp = 0;
    
    x =100  #歌名的初始x坐标值
    
    while mainloop:
        clock.tick(30);     #限制屏幕刷新速率
        pygame.mixer.music.set_volume(volume);  #获取音量值
        #画背景
        screen.blit(bg_image,(0,0));
        
        button_pressed = None;
        #在pygame.event.get()从时间队列中获取事件出来，然后判断是什么类型事件
        #event.get()函数每次执行后返回eventlist
        for event in pygame.event.get():
            if event.type == QUIT:  #退出程序
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
                if volume > 1.0:  #设定音量的范围
                    volume = 1.0;
                elif volume < 0.0:
                    volume = 0.0;
                
                #模拟电子琴
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
                
                
            elif event.type == MOUSEBUTTONUP:  # 按下鼠标
                for name,but in buttons.iteritems():
                    if name == "play" and playing:
                        continue;
                    elif name == "pause" and not playing:
                        continue;
                    elif but.isIn(event.pos):
                        button_pressed = name;
                        break;
            
            elif event.type == TRACK_END:
                button_pressed = "next";     #模拟按下下一首
                pygame.time.delay(1000);
            
        #处理按键事件；
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
                        if pygame.mixer.music.get_pos() > 3000: #播放大于三秒了就重新开始
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
                    
                    

        except Exception ,e:  #如果出现错误，将错误写到日志文件，并退出程序
            file = open("log.txt", "a");
            file.write(str(e));
            file.flush();
            file.close();
            dlg = wx.MessageDialog(None, "不支持该文件！", "错误", wx.OK|wx.ICON_ERROR);
            dlg.ShowModal();
            dlg.Destroy();
            mainloop = True;
            pygame.quit();
            sys.exit(0);
        #画音量条
        for i in range(count):
            screen.blit(high,(93+10*i, 33))
        
        
        #画按钮
        for button in buttons.values():
            if playing and button.name == "play" :
                continue;
            elif not playing and button.name == "pause":
                continue;
            button.draw(screen);
            
         #写歌名
        if max_track == 0:
            label = font.render(("文件夹中没有音乐 ").decode('GBK'), True, (250, 242, 120));
            
        else:
            label = music_name[playing_index];
        w, h = label.get_size();
        screen_w = SCREEN_SIZE[0];
        #滚动歌名
        x-=2
        if x < - label.get_width():
            x =459-label.get_width();
        
        screen.blit(label, (x, 85));
        
        #写时间
        if first_play:
            txt = "0:0" + "  " + str(playing_index+1) +"/" + str(max_track);
        else:
            time = pygame.mixer.music.get_pos() / 1000;
            txt = str(time / 60)+ ":" + str(time % 60)+ "  " + str(playing_index+1)+ "/" + str(max_track);
        
        label = font.render(txt, True, (250, 242, 120))
        w, h = label.get_size();
        screen.blit(label, ((screen_w - w)/2, 150 - h/2));
        #绘制播放进度状态
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
        
        
           
            
        #画音量
        draw_time += 1;
        if vol_change:
            if draw_time > 3:
                draw_time = 0;
                vol_change = False;
            now_vol = int( pygame.mixer.music.get_volume() * 10 );
            label = vfont.render(("音量: "+str(now_vol)).decode('GBK'), True, (255, 48, 33))
            w, h = label.get_size();
            #screen.blit(label, ((screen_w - w)/2, 180));
        #suprise
        if suprise:
            s_setp += 1;
            if s_setp > 40:
                suprise = False;
            label = vfont.render("↑/↓加减音量".decode("GBK"), True, (250, 242, 120))
            w, h = label.get_size();
            screen.blit(label, ((screen_w - w)/2, 220));  

        '''a,b=pygame.mouse.get_pos()
        #获得鼠标位置
        pygame.mouse.set_visible(False)
        a-= mouse_cursor.get_width()/2
        b-= mouse_cursor.get_height()/2
        #计算光标的左上角位置
        screen.blit(mouse_cursor, (a, b))
        #把光标画上去
        '''
      
        
        #用pygame.display.flip()来刷新显示
        pygame.display.flip(); #双缓冲要用这个更新 
        #pygame.display.update()是将数据画到前面显示



if __name__ == "__main__":
    path = None;
    app = wx.PySimpleApp()  
    dialog = wx.DirDialog(None, '选择一个音乐文件夹',  defaultPath = "C://",
          style=wx.DD_DEFAULT_STYLE | wx.DD_NEW_DIR_BUTTON)  
    if dialog.ShowModal() == wx.ID_OK:  
        path = dialog.GetPath()   
    dialog.Destroy()  
    #path = MUSCI_PATH;
    
    main(path);
    sys.exit(0);
