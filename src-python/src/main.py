#coding=GBK
""" 
@author ivan
选择一个音乐文件夹
这是一个音乐播放器，音乐文件放到./music中，支持多文件夹。
2013,3,21
"""
import pygame,os,sys;
from pygame.locals import *;
from MButton import Button
from MStack import Stack
import wx;



MUSCI_PATH = ".\\music";
IMAGE_PATH = ".\\image"
SCREEN_SIZE = (600, 533);

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
                if filename.lower().endswith(".wav") \
                or filename.lower().endswith(".ogg") \
                or filename.lower().endswith(".mp3"):
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
    pygame.display.set_caption("For My Dear 小安安");
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
    TRACK_END = USEREVENT + 1
    pygame.mixer.music.set_endevent(TRACK_END)
    #背景图
    bg_image = pygame.image.load(".\\image\\bg.jpg").convert_alpha();
    #按钮
    buttons = {};
    buttons["pre"] = Button("pre",250,525,".\\image\\pre.png");
    buttons["play"] = Button("play",350,525,".\\image\\play.png");
    buttons["pause"] = Button("pause",350,525,".\\image\\pause.png");
    buttons["next"] = Button("next",450,525,".\\image\\next.png");
    #buttons["down"] = Button("next",520,510,".//image//down.png");
    #buttons["up"] = Button("next",570,510,".//image//up.png");

    music_files,names =  getMusic(path);
    max_track = len(music_files);
    
    music_name = [];
    for name in names:
        #txt = os.path.split(name)[-1];
        try:
            tmp = name[:-4].strip().decode("GBK");
        except :
            tmp = name[:-4].strip();
        
        music_name.append(font.render(tmp, True, (255, 255, 255)));

    playing = False;
    playing_index = 0;
    first_play = True;
    volume = 0.8;

    #时间循环
    mainloop = True;
    vol_change = False;
    draw_time = 0;
    suprise = True;
    s_setp = 0;
    #######################################################
    while mainloop:
        clock.tick(10);     #限制屏幕刷新速率
        pygame.mixer.music.set_volume(volume);
        button_pressed = None;
        for event in pygame.event.get():
            if event.type == QUIT:  #退出程序
                mainloop = False;
                pygame.mixer.quit();
                pygame.quit();
                return;
            elif event.type == KEYUP:
                if event.key == K_UP:
                    volume += 0.1;
                    vol_change = True;
                    draw_time = 0;
                elif event.key == K_DOWN:
                    volume -= 0.1;
                    vol_change = True;
                    draw_time = 0;
                if volume > 1.0:
                    volume = 1.0;
                elif volume < 0.0:
                    volume = 0.0;
                
                
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
                button_pressed = "next";    #模拟按下下一首
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
                        if pygame.mixer.music.get_pos() > 3000: #播放大于三秒了就重新开始
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
        except Exception ,e:
            file = open("log.txt", "a");
            file.write(str(e));
            file.flush();
            file.close();
            dlg = wx.MessageDialog(None, "不支持该文件！", "错误", wx.OK|wx.ICON_ERROR);
            dlg.ShowModal();
            dlg.Destroy();
            mainloop = False;
            pygame.quit();
            sys.exit(0);
            
        #画背景
        screen.blit(bg_image,(0,0));
        #画按钮
        for button in buttons.values():
            if playing and button.name == "play" :
                continue;
            elif not playing and button.name == "pause":
                continue;
            button.draw(screen);
            
        #写歌名
        if max_track == 0:
            label = font.render(("文件夹中没有音乐 ").decode('GBK'), True, (255, 255, 255));
            
        else:
            label = music_name[playing_index];
        w, h = label.get_size();
        screen_w = SCREEN_SIZE[0];
        screen.blit(label, ((screen_w - w)/2, 80));
        
        #写时间
        if first_play:
            txt = "0:0" + "  " + str(playing_index+1) +"/" + str(max_track);
        else:
            time = pygame.mixer.music.get_pos() / 1000;
            txt = str(time / 60)+ ":" + str(time % 60)+ "  " + str(playing_index+1)+ "/" + str(max_track);
        label = font.render(txt, True, (255, 255, 255))
        w, h = label.get_size();
        screen.blit(label, ((screen_w - w)/2, 150 - h/2));
        
        #画音量
        draw_time += 1;
        if vol_change:
            if draw_time > 30:
                draw_time = 0;
                vol_change = False;
            now_vol = int( pygame.mixer.music.get_volume() * 10 );
            label = vfont.render(("音量: "+str(now_vol)).decode('GBK'), True, (255, 255, 255))
            w, h = label.get_size();
            screen.blit(label, ((screen_w - w)/2, 180));
        #suprise
        if suprise:
            s_setp += 1;
            if s_setp > 40:
                suprise = False;
            label = vfont.render("按下Up/Down有惊喜噢".decode("GBK"), True, (255, 255, 255))
            w, h = label.get_size();
            screen.blit(label, ((screen_w - w)/2, 250));        
        
        pygame.display.flip(); #双缓冲要用这个更新 




if __name__ == "__main__":
    path = None;
    app = wx.PySimpleApp()  
    dialog = wx.DirDialog(None, u'选择一个音乐文件夹',  defaultPath = "E://",
          style=wx.DD_DEFAULT_STYLE | wx.DD_NEW_DIR_BUTTON)  
    if dialog.ShowModal() == wx.ID_OK:  
        path = dialog.GetPath()   
    dialog.Destroy()  
    #path = MUSCI_PATH;
    main(path);
    sys.exit(0);
