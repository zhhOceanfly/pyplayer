   M S B A M L   `   `   `  ���� E  AKuKuPlayer, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null-clr-namespace:WpfKuGou_BetaWpfKuGou_Beta  a ]PresentationFramework.Aero, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35gJclr-namespace:Microsoft.Windows.Themes;assembly=PresentationFramework.AeroMicrosoft.Windows.Themes     WpfKuGou_Beta.MainWindow   \ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     -localclr-namespace:WpfKuGou_Beta     ]ThemesJclr-namespace:Microsoft.Windows.Themes;assembly=PresentationFramework.Aero     -     � Title$  酷库音乐��$	��711��$
��1000�� � Icon$ 	kukou.ico�� � WindowStartupLocation$ CenterScreen=� � WindowStyle$
 None=� � 
ResizeMode$ NoResize=� � 	Resources  �� 
TargetType �� Property �� Value	 �� 
TargetType
 �� CornerRadius   MenuItemstyle ��Name �� ImageSource �Name m� IsSubmenuOpen � AllowsTransparency K� IsItemsHost �� Triggers P� Property P� Value �� 
TargetName  Storyboard1 g� RepeatBehavior g� AutoReverse �� TargetProperty �� 
TargetName<  6System.Windows.Media.Animation.EasingThicknessKeyFrame p� KeyTime p� Value��%�  'o�       &	  @     &	 B    �� " o�.���  � �� �� "	 o�.R��� $
 0��._��� �� " m�.���  � �� �� "	 m�.R��� $ bd��$	��0,2q�$
 0������ $ skin/menu_normal.png��._�� .+��� $
��0,10q�$��Header��$��1��$
��Left=�� $	 pop��$��10q�#��z  . $��Bottom=�.���� $��5q�$
 4�����	#FFF0F0F0$��1q����	#FF32ECA8._���  .  P�  �@J�IsMouseOver . . ��� $ bd�� � �� �� $ skin/menu_hot.png��P�  �@m�	IsPressed . . ��� $ bd�� � �� �� $ skin/menu_hot.png���� $	 pop�� � �� . �� $ Forever�� .  .��r� $ (FrameworkElement.Margin)�$ 	textBlock��.	�  $ 0��$ 0q�  $ 0:0:2��$ 	0,-20,0,0q�.��� -   $
��Root�� � RowDefinitions �� $	��110 ��� $��* ��� $��30 � � ColumnDefinitions �� $��46 ��� $	��292 ��� $��* ����� $ BackGround\back.png��.+�� $��0��$��0��$��1��$��2��.+��� -    ��Name$ 
textBlock1��$
��Left=�$��16,6,0,0q�$	��Top=�$��65��$��23��$��酷库音乐��$��15(�
�������   WpfKuGou_Beta.ImageButton  $
��Left=�$��	12,68,0,0q�$	��Top=�$��29��$��26��   NorImage$ skin/Lyric_normal.png��   HorImage$ skin/Lyric_hot.png��    	DownImage$  skin/Lyric_down.png��  ImageButtonStyle#���    -   !  Name$! BtnPre��$
��Left=�$��	88,51,0,0q�$	��Top=�$��49��$��49��$ skin/pre_normal.png��$ skin/pre_hot.png��$  skin/pre_down.png��#���    -   $! BtnPlay��   WpfKuGou_Beta.ImageButton"   Click$" 	OnBtnPlay��$
��Left=�$��
149,51,0,0q�$	��Top=�$��49��$��49��$ skin/play_normal.png��$ skin/play_hot.png��$  skin/play_down.png��#���    -   $! BtnPause��$" 
OnBtnPause��$
��Left=�$��
149,51,0,0q�$	��Top=�$��49��$��49��$ skin/pause_normal.png��$ skin/pause_hot.png��$  skin/play_down.png��#���  $y�Hidden=�  -   $! BtnNext��$
��Left=�$��49��$��
210,51,0,0q�$	��Top=�$��49��$ skin/next_normal.png��$ skin/next_hot.png��$  skin/next_down.png��#���  �� -   $��slider_play��$
��Left=�$��14��$��	33,32,0,0q�$	��Top=�$	��286��  SimpleSlider#���  ���	#F0315CB8�� -	   $��	totaltime��$
��Left=�$��
282,43,0,0q�$
��Wrap=�$	��Top=�$��35��
��������� -
   $��Hasplay��$
��Left=�$��	32,43,0,0q�$
��Wrap=�$	��Top=�
�������� $��0��$��2�� �� $
��152* ��� $
��175* � �� $��40 ��� $��* �.+��� # �� Orientation$# 
Horizontal=�$��2��.+��� $��登录��$��18��$��25��
�������$��30,0,0,0q��� $��13��$��1��
�������$��4,0,0,0q��� $��注册��$��18��$��25��
�������$��4,0,0,0q��� $ ��skin\00259[29x29x8BPP].png��$��5,0,0,0q�$��35��$��35���� -   $��SearchContent��$	��310��$��27��$��0,4,35,0q����	#5FCBCBCB  TextBoxStyle#���    -   $! SerchBtn��$" BtnSearchMusic��$��27��$��33��$��	-33,0,0,0q�#���  $ skin/Search_normal.png��$ skin/Search_hot.png��$  skin/Search_normal.png��  -   $! GameBtn��$��18��$��52��$��10,0,0,0q�#���  $ skin/play_game_normal.png��$ skin/play_game_hot.png��$  skin/play_game_down.png��$ � ToolTip$$ 酷狗网络游戏平台��  -   $! MoneyBtn��$��28��$��26��$��5,0,0,0q�#���  $  skin/00567[26x28x8BPP].png��$  skin/00568[26x28x8BPP].png��$   skin/00568[26x28x8BPP].png��$$ 抢福袋，马上有奖��  -   $! ChangeSkinBtn��$" ShowChangeSkinDlg��$��18��$��23��$��5,0,0,0q�#���  $ skin/skin_normal.png��$ skin/skin_hot.png��$  skin/skin_down.png��$$ 更换皮肤��o� $��23��$��23��$��5,0,0,0q�.@�m� $��23��$��23��#��[  .@�m� $��登录��m� $��添加歌曲��m� $��播放模式��m� $��	遥控器��m� $��	均衡器��m� $��音效插件��m� $��定时设置��m� $��下载设置��m� $��在线升级��m� $��退出��  -   $! MinBtn��$" MinBtn_Click��$��19��$��21��$��5,0,0,0q�#���  $ skin/min_normal.bmp��$ skin/min_hot.bmp��$  skin/min_down.bmp��$$ 	最小化��  -   $! MaxBtn��$" 
OnBtnClose��$��23��$��23��$��5,0,0,0q�#���  $ skin/close_normal.png��$ skin/close_hot.png��$  skin/close_down.png��$$ 关闭���� $��1��$��2��$# 
Horizontal=�.+�  $��50��$��34��$��35,0,0,0q�#���  $  skin/00123[34x50x8BPP].png��$  skin/00120[34x50x8BPP].png��$   skin/00122[34x50x8BPP].png��$$ 返回��  $��50��$��34��$��15,0,0,0q�#���  $  skin/00127[34x50x8BPP].png��$  skin/00125[34x50x8BPP].png��$   skin/00126[34x50x8BPP].png��$$ 刷新��*   $WpfKuGou_Beta.ImageRadioButtonButton  -   %  Name$% yueku��*   $WpfKuGou_Beta.ImageRadioButtonButton&   Checked$& web_Checked��$��50��$��80��$��10,0,0,0q�  ImageRadioButtonStyle#���  '   NorImage$ ' skin/00977[80x50x8BPP].png��(   HorImage$ ( skin/00978[80x50x8BPP].png��)   SelectedImage$ ) skin/00979[80x50x8BPP].png��* 	� 	GroupName$* Styles��$$$ 最新最火歌曲尽在其中��+ d� 	IsChecked$
+ TrueY�  -   $% fm��$& web_Checked��$��50��$��80��$��10,0,0,0q�#���  $ ' skin/01081[80x50x8BPP].png��$ ( skin/01082[80x50x8BPP].png��$ ) skin/01083[80x50x8BPP].png��$* Styles��$!$ 享受随便听听的精彩��  -   $% mv��$& web_Checked��$��50��$��80��$��10,0,0,0q�#���  $ ' skin/00711[80x50x8BPP].png��$ ( skin/00712[80x50x8BPP].png��$ ) skin/00713[80x50x8BPP].png��$* Styles��$ $ 随意点播百万高清MV��  -   $
% show��$& web_Checked��$��50��$��80��$��10,0,0,0q�#���  $ ' skin/00386[80x50x8BPP].png��$ ( skin/00387[80x50x8BPP].png��$ ) skin/00388[80x50x8BPP].png��$* Styles��$$ 在线直播��  -   $% jiemu��$& web_Checked��$��50��$��80��$��10,0,0,0q�#���  $ ' skin/01845[80x50x8BPP].png��$ ( skin/01846[80x50x8BPP].png��$ ) skin/01847[80x50x8BPP].png��$* Styles��$$ 精彩节目,听我想听��  -   $	% lrc��$& web_Checked��$��50��$��80��$��10,0,0,0q�#���  $ ' skin/00165[80x50x8BPP].png��$ ( skin/00166[80x50x8BPP].png��$ ) skin/00167[80x50x8BPP].png��$* Styles��$$$ 精准歌词搭配高清写真���� $��1��$��0������ $! skin\01716[48x441x8BPP].png��.+�  $��ImageRadioButtonButton��$��62��$��43��#���  $ ' skin/01226[46x62x8BPP].png��$ ( skin/01227[46x62x8BPP].png��$ ) skin/01228[46x62x8BPP].png��  $��ImageRadioButtonButton��$��62��$��43��#���  $ ' skin/01209[46x62x8BPP].png��$ ( skin/01210[46x62x8BPP].png��$ ) skin/01211[46x62x8BPP].png��  $��ImageRadioButtonButton��$��62��$��43��#���  $ ' skin/01229[46x62x8BPP].png��$ ( skin/01230[46x62x8BPP].png��$ ) skin/01231[46x62x8BPP].png��  $��ImageRadioButtonButton��$��62��$��43��#���  $ ' skin/01215[46x62x8BPP].png��$ ( skin/01216[46x62x8BPP].png��$ ) skin/01217[46x62x8BPP].png��  $��ImageRadioButtonButton��$��62��$��43��#���  $ ' skin/01212[46x62x8BPP].png��$ ( skin/01213[46x62x8BPP].png��$ ) skin/01214[46x62x8BPP].png��  $��ImageRadioButtonButton��$��62��$��43��#���  $ ' skin/01412[46x62x8BPP].png��$ ( skin/01413[46x62x4BPP].png��$ ) skin/01414[46x62x8BPP].png��� $��1��$��1�� �� $	��71* ��� $
��221* � �� $��30 ��� $��* �.+�r� -   $��Sound��, r� LoadedBehavior$, Manual=�]� $��0��$��Stretch=�$��Stretch=����	#50FFFFFF$��2��.+��� $��播放列表��- �� Padding$- 10,0,0,0q�$��Center=�$��20��$
��Bold"����	#FF464646  $" AddSong��$��Right=�$��Center=�$��26��$��22��$��0,0,20,0q�$  skin/01944[26x22x2BPP].png��$  skin/01945[26x22x2BPP].png��$   skin/01946[26x22x2BPP].png��#���  $$ 列表菜单���� -   $��SongList��$��1��$��Stretch=�$��Stretch=�  ListBoxItemMusicStyle#���  ���	#50FFFFFF$��0q�$��2��. J� 	AllowDrop. . � $��1��$��2��.+��� -   / ��Name$/ CanvasLyric��$��	28,0,28,0q�}�. ���	#00DA00FD.+��� -   0 ��Name$0 StackPanelCommonLyric��1 �� Left$1 0��
2 �� Top$	2 300��3 J� OpacityMask
3 �   ����� *ActualWidth+4 �� ElementName$4 CanvasLyric��5 �� Mode$5 OneWay=��� -   $/ CanvasFocusLyric��$��36��$1 0��$	2 253��
3 �   �}�. ���� *ActualWidth+$4 CanvasLyric��$5 OneWay=���Z� .+��� -   $ TBFocusLyricBack��$��22(�6 �� TextAlignment$6 Center=�
�������
3 �   ����� *Height+$4 CanvasFocusLyric������ *ActualWidth+$4 CanvasLyric��$5 OneWay=��� -   $/ CanvasFocusLyricFore��$��30��}�. 
3 �   ����� *Height+$4 CanvasFocusLyric��.+��� -   $ TBFocusLyricFore��
��� ���
3 �   ����� *Text+$4 TBFocusLyricBack������ *Height+$4 CanvasFocusLyric������ *
FontSize+$4 TBFocusLyricBack��6 �� *TextAlignment+$4 TBFocusLyricBack������ *
FontFamily+$4 TBFocusLyricBack������ *ActualWidth+$4 CanvasLyric��$5 OneWay=�y��� *
Visibility+$4 TBFocusLyricBack������ *TextWrapping+$4 TBFocusLyricBack������ *TextTrimming+$4 TBFocusLyricBack��(  "System.Windows.Controls.WebBrowser  -    $	��Web��$y�Visible=��� $��2��$��3��.+��� $��Center=�$��Right=�$��	790,5,0,0q�$	��200��$��20��.+��� -!   $ 
textBlock2��$��佛前的一条小鱼��$��Right=�$��Center=�$��20��$	��200��$6 Right=�$2 0��
��������� -"   $ 
textBlock3��$��QQ: 452578374��$��Right=�$��Center=�$��20��$	��200��$2 20��$6 Right=�
�������