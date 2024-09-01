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


MUSCI_PATH = ".\\music";
IMAGE_PATH = ".\\image"
SCREEN_SIZE = (459, 650);
#define of params  
NUM_SAMPLES = 2000  
framerate = 8000  
channels = 1  
sampwidth = 2  
#record time  
TIME = 20  
  
def save_wave_file(filename, data):  
    '''''save the date to the wav file'''  
    wf = wave.open(filename, 'wb')  
    wf.setnchannels(channels)  
    wf.setsampwidth(sampwidth)  
    wf.setframerate(framerate)  
    wf.writeframes("".join(data))  
    wf.close()  
def record_wave(recording):  
    #open the input of wave  
    pa = PyAudio()  
    stream = pa.open(format = paInt16, channels = 1,  
                    rate = framerate, input = True,  
                    frames_per_buffer = NUM_SAMPLES)  
    save_buffer = []  
    count = 0  
    while count < TIME*4:  
        #read NUM_SAMPLES sampling data  
        string_audio_data = stream.read(NUM_SAMPLES)  
        save_buffer.append(string_audio_data) 
         
        count += 1  
        print '.'
        if not recording:
            stream.stop_stream();  
    
    filename = datetime.now().strftime("%Y-%m-%d_%H_%M_%S")+".wav"  
    save_wave_file(filename, save_buffer)
    save_buffer = []  
    print filename, "saved" 


        
    
        
def getMusic(path):
    stack = Stack();
    music_files = [];
    stack.push(path);
    name = [];
    if not os.path.exists(path):
        return [], [];
    while stack.size() >0:
        tmp = stack.pop();
        
        files = os.listdir(tmp);
        for filename in files:
            if os.path.isdir(tmp+ "\\"+filename):
                stack.push(tmp+ "\\"+filename);
            elif os.path.isfile(tmp+ "\\"+filename):
                if filename.lower().endswith(".wav") or filename.lower().endswith(".ogg") or filename.lower().endswith(".mp3"):
                    new = os.path.join(tmp, filename);
                    music_files.append(new);
                    name.append(filename);
    return music_files, name;





def main(path):
    reload(sys);
    sys.setdefaultencoding("GBK");
    if path is None:
        path = MUSCI_PATH;
    pygame.init();  #初始化pygame
    
    
    screen = pygame.display.set_mode(SCREEN_SIZE, DOUBLEBUF,32);
    pygame.display.set_caption("P A P");
    #low = pygame.image.load('low.png');
    high = pygame.image.load('.\\image\\high.png');
    
    pos = pygame.image.load('.\\image\\pos.png');
    #背景图
    bg_image = pygame.image.load(".\\image\\guitar1.jpg").convert_alpha();
    
    
    
    count=8;
    
    
    
    
    #中文显示字体
    try:
        font = pygame.font.Font(".\\font\\yh.ttf", 35);
        vfont = pygame.font.Font(".\\font\\yh.ttf", 20); 
    except:
        font = pygame.font.SysFont("kaiti", 35);
        vfont = pygame.font.SysFont("kaiti", 20); 
    #时钟工具
    clock = pygame.time.Clock();
    #混音器,采样率、位数、声道数、buffer
    pygame.mixer.pre_init(441000, 16, 2, 1024*4);
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
    #buttons["end"] = Button("end",50,565,".//image//end.png");

    music_files,names =  getMusic(path);
    max_track = len(music_files);
    
    music_name = [];
    for name in names:
        #txt = os.path.split(name)[-1];
        try:
            tmp = name[:-4].strip().decode("GBK");
        except :
            tmp = name[:-4].strip();
        
        music_name.append(font.render(tmp, True, (250, 242, 120)));

    playing = False;
    playing_index = 0;
    first_play = True;
    #recording=False;
    volume = 0.8;

    #时间循环
    mainloop = True;
    vol_change = False;
    draw_time = 0;
    suprise = True;
    s_setp = 0;
    
    while mainloop:
        clock.tick(24);     #限制屏幕刷新速率
        pygame.mixer.music.set_volume(volume);
        #画背景
        screen.blit(bg_image,(0,0));
        #screen.blit(vol,(5,5));
        button_pressed = None;
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
                if volume > 1.0:
                    volume = 1.0;
                elif volume < 0.0:
                    volume = 0.0;
                
                 
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
                
                    

        except Exception ,e:
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
        screen.blit(label, ((screen_w - w)/2, 85));
        
        #写时间
        if first_play:
            txt = "0:0" + "  " + str(playing_index+1) +"/" + str(max_track);
        else:
            time = pygame.mixer.music.get_pos() / 1000;
            txt = str(time / 60)+ ":" + str(time % 60)+ "  " + str(playing_index+1)+ "/" + str(max_track);
        
        label = font.render(txt, True, (250, 242, 120))
        w, h = label.get_size();
        screen.blit(label, ((screen_w - w)/2, 150 - h/2));
        #绘制进度条
        times = pygame.mixer.music.get_pos() / 1000;
        tme=times
        if tme>210:
            tme=210      
        for j in range(tme):
            screen.blit(pos,(22+2*j, 584))
            
            
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
            label = vfont.render("↑/↓加减音量".decode("GBK"), True, (22, 242, 22))
            w, h = label.get_size();
            screen.blit(label, ((screen_w - w)/2, 220));        
        
        
        pygame.display.flip(); #双缓冲要用这个更新 



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
