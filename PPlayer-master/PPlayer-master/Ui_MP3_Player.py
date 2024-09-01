﻿# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'MP3_Player.ui'
#
# Created: Thu Jun 25 16:52:45 2015
#	  by: PyQt4 UI code generator 4.9.6
#
# WARNING! All changes made in this file will be lost!

from PyQt4 import QtCore, QtGui
from table_view import *
from volumebutton import *
from mybuttons import *
from progressslider import *
import images

try:
	_fromUtf8 = QtCore.QString.fromUtf8
except AttributeError:
	def _fromUtf8(s):
		return s

try:
	_encoding = QtGui.QApplication.UnicodeUTF8
	def _translate(context, text, disambig):
		return QtGui.QApplication.translate(context, text, disambig, _encoding)
except AttributeError:
	def _translate(context, text, disambig):
		return QtGui.QApplication.translate(context, text, disambig)

class Ui_MainWindow(object):
	def setupUi(self, MainWindow):
		# MainWindow.setObjectName(_fromUtf8("MainWindow"))
		MainWindow.resize(400, 540)
		MainWindow.setMinimumSize(QtCore.QSize(312, 250))
		MainWindow.setMaximumSize(QtCore.QSize(312, 16777215))
		self.centralwidget = QtGui.QWidget(MainWindow)
		self.centralwidget.setObjectName(_fromUtf8("centralwidget"))
		self.verticalLayout = QtGui.QVBoxLayout(self.centralwidget)
		self.verticalLayout.setContentsMargins(0, 0, 0, 0)
		self.verticalLayout.setObjectName(_fromUtf8("verticalLayout"))
		self.title = QtGui.QFrame(self.centralwidget)
		self.title.setFrameShape(QtGui.QFrame.StyledPanel)
		self.title.setFrameShadow(QtGui.QFrame.Raised)
		self.title.setObjectName(_fromUtf8("title"))
		self.horizontalLayout = QtGui.QHBoxLayout(self.title)
		self.horizontalLayout.setSpacing(9)
		self.horizontalLayout.setMargin(0)
		self.horizontalLayout.setContentsMargins(5, 0, 5, 0)
		self.horizontalLayout.setObjectName(_fromUtf8("horizontalLayout"))
		self.appName = QtGui.QLabel(self.title)
		font = QtGui.QFont()
		font.setPointSize(10)
		font.setBold(True)
		font.setWeight(75)
		font.setStrikeOut(False)
		self.appName.setFont(font)
		self.appName.setObjectName(_fromUtf8("appName"))
		self.label_1 = QLabel(self.title)
		self.label_1.setScaledContents(True)
		self.label_1.setObjectName(_fromUtf8("label_1"))	
		self.label_1.setMaximumSize(20, 20)
		self.horizontalLayout.addWidget(self.label_1)
		self.horizontalLayout.addWidget(self.appName)
		spacerItem = QtGui.QSpacerItem(40, 20, QtGui.QSizePolicy.Expanding, QtGui.QSizePolicy.Minimum)
		self.horizontalLayout.addItem(spacerItem)
		self.miniBt = PushButton(self.title)
		self.miniBt.setObjectName(_fromUtf8("miniBt"))
		self.horizontalLayout.addWidget(self.miniBt, 0, QtCore.Qt.AlignTop)
		self.horizontalLayout.addWidget(self.miniBt)
		self.closeBt = PushButton(self.title)
		self.closeBt.setObjectName(_fromUtf8("closeBt"))
		self.horizontalLayout.addWidget(self.closeBt, 0, QtCore.Qt.AlignTop)
		self.verticalLayout.addWidget(self.title)
		self.processFrame = QtGui.QFrame(self.centralwidget)
		self.processFrame.setFrameShape(QtGui.QFrame.StyledPanel)
		self.processFrame.setFrameShadow(QtGui.QFrame.Raised)
		self.processFrame.setObjectName(_fromUtf8("processFrame"))
		self.verticalLayout_2 = QtGui.QVBoxLayout(self.processFrame)
		self.verticalLayout_2.setSpacing(0)
		self.verticalLayout_2.setContentsMargins(5, 9, 5, 0)
		self.verticalLayout_2.setObjectName(_fromUtf8("verticalLayout_2"))
		self.musicName = nameLabel(self.processFrame)
		self.musicName.setObjectName(_fromUtf8("musicname"))
		self.musicName.setContentsMargins(0, 0, 0, 5)
		self.verticalLayout_2.addWidget(self.musicName)
		self.seekSlider = progressSlider(QtCore.Qt.Horizontal, self.processFrame)
		self.seekSlider.setObjectName(_fromUtf8("seekSlider"))
		self.seekSlider.setTracking(True)
		self.seekSlider.setSingleStep(5000)
		self.verticalLayout_2.addWidget(self.seekSlider)
		self.frame = QtGui.QFrame(self.processFrame)
		self.frame.setMinimumSize(QtCore.QSize(0, 0))
		self.frame.setFrameShape(QtGui.QFrame.NoFrame)
		self.frame.setFrameShadow(QtGui.QFrame.Raised)
		self.frame.setLineWidth(0)
		self.frame.setObjectName(_fromUtf8("frame"))
		self.horizontalLayout_4 = QtGui.QHBoxLayout(self.frame)
		self.horizontalLayout_4.setSpacing(0)
		self.horizontalLayout_4.setSizeConstraint(QtGui.QLayout.SetDefaultConstraint)
		self.horizontalLayout_4.setContentsMargins(0, 0, 0, 0)
		self.horizontalLayout_4.setObjectName(_fromUtf8("horizontalLayout_4"))
		self.currentTime = QtGui.QLabel(self.frame)
		self.currentTime.setObjectName(_fromUtf8("currentTime"))
		self.horizontalLayout_4.addWidget(self.currentTime)
		self.playTime = QtGui.QLabel(self.frame)
		font = QtGui.QFont()
		font.setBold(False)
		font.setWeight(50)
		self.playTime.setFont(font)
		self.playTime.setLayoutDirection(QtCore.Qt.LeftToRight)
		self.playTime.setAlignment(QtCore.Qt.AlignRight|QtCore.Qt.AlignTrailing|QtCore.Qt.AlignVCenter)
		self.playTime.setObjectName(_fromUtf8("playTime"))
		self.horizontalLayout_4.addWidget(self.playTime)
		self.verticalLayout_2.addWidget(self.frame)
		self.verticalLayout.addWidget(self.processFrame)
		self.controlFrame = QtGui.QFrame(self.centralwidget)
		self.controlFrame.setFrameShape(QtGui.QFrame.StyledPanel)
		self.controlFrame.setFrameShadow(QtGui.QFrame.Raised)
		self.controlFrame.setObjectName(_fromUtf8("controlFrame"))
		self.horizontalLayout_2 = QtGui.QHBoxLayout(self.controlFrame)
		self.horizontalLayout_2.setSpacing(0)
		self.horizontalLayout_2.setContentsMargins(5, 0, 5, 0)
		self.horizontalLayout_2.setObjectName(_fromUtf8("horizontalLayout_2"))
		spacerItem1 = QtGui.QSpacerItem(40, 20, QtGui.QSizePolicy.Expanding, QtGui.QSizePolicy.Minimum)
		self.horizontalLayout_2.addItem(spacerItem1)			
		self.srrFrame = QtGui.QFrame(self.controlFrame)
		self.srrFrame.setFrameShape(QtGui.QFrame.NoFrame)
		self.srrFrame.setFrameShadow(QtGui.QFrame.Raised)
		self.srrFrame.setLineWidth(0)
		self.srrHorizontalLayout = QtGui.QHBoxLayout(self.srrFrame)
		self.srrHorizontalLayout.setSpacing(0)
		self.srrHorizontalLayout.setMargin(0)
		self.srrHorizontalLayout.setObjectName(_fromUtf8("srrHorizontalLayout"))				
		self.shuffle = SRButton(self.controlFrame)
		self.shuffle.setMaximumSize(QtCore.QSize(30, 16777215))
		self.shuffle.setObjectName(_fromUtf8("shuffle"))
		self.shuffle.setFlat(True)	
		self.repeat1 = SRButton(self.controlFrame)
		self.repeat1.setMaximumSize(QtCore.QSize(30, 16777215))
		self.repeat1.setObjectName(_fromUtf8("repeat1"))
		self.repeat1.setFlat(True)		
		self.repeat = SRButton(self.controlFrame)
		self.repeat.setMaximumSize(QtCore.QSize(30, 16777215))
		self.repeat.setObjectName(_fromUtf8("repeat"))
		self.repeat.setFlat(True)
		self.srrHorizontalLayout.addWidget(self.shuffle, 0, Qt.AlignBottom)
		self.srrHorizontalLayout.addWidget(self.repeat1, 0, Qt.AlignBottom)
		self.srrHorizontalLayout.addWidget(self.repeat, 0, Qt.AlignBottom)
		self.horizontalLayout_2.addWidget(self.srrFrame)
		self.horizontalLayout_2.addItem(spacerItem1)		
		self.playFrame = QtGui.QFrame(self.controlFrame)
		self.playFrame.setFrameShape(QtGui.QFrame.NoFrame)
		self.playFrame.setFrameShadow(QtGui.QFrame.Raised)
		self.playFrame.setLineWidth(0)
		self.playHorizontalLayout = QtGui.QHBoxLayout(self.playFrame)
		self.playHorizontalLayout.setSpacing(0)
		self.playHorizontalLayout.setMargin(0)
		self.playHorizontalLayout.setObjectName(_fromUtf8("playHorizontalLayout"))	
		self.previous = Button(self.controlFrame)
		self.previous.setMaximumSize(QtCore.QSize(30, 16777215))
		self.previous.setObjectName(_fromUtf8("previous"))
		self.play = PlayButton(self.controlFrame)
		self.play.setMaximumSize(QtCore.QSize(30, 16777215))
		self.play.setObjectName(_fromUtf8("play"))
		self.play.setCheckable(True)
		self.play.setFlat(True)
		self.stop = Button(self.controlFrame)
		self.stop.setMaximumSize(QtCore.QSize(30, 16777215))
		self.stop.setObjectName(_fromUtf8("stop"))
		self.next = Button(self.controlFrame)
		self.next.setMaximumSize(QtCore.QSize(30, 16777215))
		self.next.setObjectName(_fromUtf8("next"))
		self.playHorizontalLayout.addWidget(self.previous)
		self.playHorizontalLayout.addWidget(self.play)
		self.playHorizontalLayout.addWidget(self.stop)
		self.playHorizontalLayout.addWidget(self.next)
		self.horizontalLayout_2.addWidget(self.playFrame)
		self.horizontalLayout_2.addItem(spacerItem1)
		self.frame_3 = QtGui.QFrame(self.controlFrame)
		self.frame_3.setFrameShape(QtGui.QFrame.NoFrame)
		self.frame_3.setFrameShadow(QtGui.QFrame.Raised)
		self.frame_3.setObjectName(_fromUtf8("frame_3"))
		self.horizontalLayout_6 = QtGui.QHBoxLayout(self.frame_3)
		self.horizontalLayout_6.setSpacing(0)
		self.horizontalLayout_6.setMargin(0)
		self.horizontalLayout_6.setObjectName(_fromUtf8("horizontalLayout_6"))
		spacerItem2 = QtGui.QSpacerItem(40, 20, QtGui.QSizePolicy.Expanding, QtGui.QSizePolicy.Minimum)
		self.horizontalLayout_6.addItem(spacerItem2)
		self.volume = VolumeButton(self.frame_3)
		self.volume.setCheckable(True)
		self.volume.setFlat(True)
		self.volume.setObjectName(_fromUtf8("volume"))
		self.horizontalLayout_6.addWidget(self.volume)
		self.volumeSlider = phonon.Phonon.VolumeSlider(self.frame_3)
		self.volumeSlider.setMinimumSize(QtCore.QSize(60, 0))
		self.volumeSlider.setMaximumSize(QtCore.QSize(60, 16777215))
		self.volumeSlider.setMuteVisible(False)
		self.volumeSlider.setObjectName(_fromUtf8("volumeSlider"))													   
		self.horizontalLayout_6.addWidget(self.volumeSlider)
		self.horizontalLayout_2.addWidget(self.frame_3)
		self.verticalLayout.addWidget(self.controlFrame)
		self.frame_4 = QtGui.QFrame(self.centralwidget)
		self.frame_4.setFrameShape(QtGui.QFrame.StyledPanel)
		self.frame_4.setFrameShadow(QtGui.QFrame.Raised)
		self.frame_4.setObjectName(_fromUtf8("frame_4"))
		self.verticalLayout_3 = QtGui.QVBoxLayout(self.frame_4)
		self.verticalLayout_3.setSpacing(0)
		self.verticalLayout_3.setContentsMargins(5, 0, 5, 0)
		self.verticalLayout_3.setObjectName(_fromUtf8("verticalLayout_3"))				
		self.tabWidget = QtGui.QTabWidget(self.frame_4)
		font = QtGui.QFont()
		font.setBold(True)
		font.setItalic(False)
		font.setUnderline(False)
		font.setWeight(75)
		font.setStrikeOut(False)
		font.setKerning(True)
		self.tabWidget.setFont(font)		
		self.tabWidget.setTabPosition(QtGui.QTabWidget.North)
		self.tabWidget.setTabShape(QtGui.QTabWidget.Rounded)
		self.tabWidget.setElideMode(QtCore.Qt.ElideLeft)
		self.tabWidget.setUsesScrollButtons(True)
		self.tabWidget.setDocumentMode(False)
		self.tabWidget.setTabsClosable(False)
		self.tabWidget.setMovable(False)
		self.tabWidget.setObjectName(_fromUtf8("tabWidget"))
		self.playListTab = QtGui.QWidget()
		self.playListTab.setObjectName(_fromUtf8("playListTab"))
		self.verticalLayout_4 = QtGui.QVBoxLayout(self.playListTab)
		self.verticalLayout_4.setSpacing(0)
		self.verticalLayout_4.setMargin(0)
		self.verticalLayout_4.setObjectName(_fromUtf8("verticalLayout_4"))
		self.tableView = myTableView(self.playListTab)
		self.tableView.setObjectName(_fromUtf8("musiclist"))
		self.verticalLayout_4.addWidget(self.tableView)
		self.operationFrame = QtGui.QFrame(self.playListTab)
		self.operationFrame.setFrameShape(QtGui.QFrame.StyledPanel)
		self.operationFrame.setFrameShadow(QtGui.QFrame.Raised)
		self.operationFrame.setObjectName(_fromUtf8("operationFrame"))
		self.horizontalLayout_3 = QtGui.QHBoxLayout(self.operationFrame)
		self.horizontalLayout_3.setSpacing(5)
		self.horizontalLayout_3.setContentsMargins(0, 2, 0, 2)
		self.horizontalLayout_3.setObjectName(_fromUtf8("horizontalLayout_3"))
		self.add = PushButton(self.operationFrame)
		self.add.setMaximumSize(QtCore.QSize(25, 25))
		self.add.setObjectName(_fromUtf8("add"))
		self.horizontalLayout_3.addWidget(self.add)
		self.save = PushButton(self.operationFrame)
		self.save.setMaximumSize(QtCore.QSize(20, 20))
		self.save.setObjectName(_fromUtf8("save"))
		self.horizontalLayout_3.addWidget(self.save)
		self.delete = PushButton(self.operationFrame)
		self.delete.setMaximumSize(QtCore.QSize(25, 25))
		self.delete.setObjectName(_fromUtf8("delete"))
		self.horizontalLayout_3.addWidget(self.delete)
		self.search = PushButton(self.operationFrame)
		self.search.setMaximumSize(QtCore.QSize(25, 25))
		self.search.setObjectName(_fromUtf8("search"))
		self.search.setCheckable(True)
		self.horizontalLayout_3.addWidget(self.search)
		spacerItem3 = QtGui.QSpacerItem(40, 20, QtGui.QSizePolicy.Expanding, QtGui.QSizePolicy.Minimum)
		self.horizontalLayout_3.addItem(spacerItem3)
		self.statusLabel = QtGui.QLabel(self.operationFrame)
		self.statusLabel.setObjectName(_fromUtf8("statusLabel"))
		self.statusLabel.setToolTip("Ready")
		self.horizontalLayout_3.addWidget(self.statusLabel, 0, QtCore.Qt.AlignRight)
		self.verticalLayout_4.addWidget(self.operationFrame)
		self.tabWidget.addTab(self.playListTab, _fromUtf8(""))
		self.favoriteTab = QtGui.QWidget()
		self.favoriteTab.setObjectName(_fromUtf8("favoriteTab"))
		self.verticalLayout_5 = QtGui.QVBoxLayout(self.favoriteTab)
		self.verticalLayout_5.setSpacing(0)
		self.verticalLayout_5.setMargin(0)
		self.verticalLayout_5.setObjectName(_fromUtf8("verticalLayout_5"))
		self.tableView_2 = myFavoriteTable(self.favoriteTab)
		self.tableView_2.setObjectName(_fromUtf8("musiclist"))
		self.verticalLayout_5.addWidget(self.tableView_2)
		self.operationFrame_2 = QtGui.QFrame(self.favoriteTab)
		self.operationFrame_2.setFrameShape(QtGui.QFrame.StyledPanel)
		self.operationFrame_2.setFrameShadow(QtGui.QFrame.Raised)
		self.operationFrame_2.setObjectName(_fromUtf8("operationFrame_2"))
		self.horizontalLayout_5 = QtGui.QHBoxLayout(self.operationFrame_2)
		self.horizontalLayout_5.setSpacing(5)
		self.horizontalLayout_5.setContentsMargins(0, 2, 0, 2)
		self.horizontalLayout_5.setObjectName(_fromUtf8("horizontalLayout_5"))
		self.save_2 = PushButton(self.operationFrame_2)
		self.save_2.setMaximumSize(QtCore.QSize(25, 25))
		self.save_2.setObjectName(_fromUtf8("save_2"))
		self.horizontalLayout_5.addWidget(self.save_2)
		self.delete_2 = PushButton(self.operationFrame_2)
		self.delete_2.setMaximumSize(QtCore.QSize(25, 25))
		self.delete_2.setObjectName(_fromUtf8("delete_2"))
		self.horizontalLayout_5.addWidget(self.delete_2)
		spacerItem4 = QtGui.QSpacerItem(40, 20, QtGui.QSizePolicy.Expanding, QtGui.QSizePolicy.Minimum)
		self.horizontalLayout_5.addItem(spacerItem4)
		self.verticalLayout_5.addWidget(self.operationFrame_2)
		self.tabWidget.addTab(self.favoriteTab, _fromUtf8(""))
		self.tab = QtGui.QWidget()
		self.tab.setObjectName(_fromUtf8("tab"))
		self.verticalLayout_6 = QtGui.QVBoxLayout(self.tab)
		self.verticalLayout_6.setSpacing(0)
		self.verticalLayout_6.setMargin(0)
		self.verticalLayout_6.setObjectName(_fromUtf8("verticalLayout_6"))
		self.textEdit = lyricTable(self.tab)
		self.textEdit.setObjectName(_fromUtf8("lyric"))
		# self.textEdit.setVerticalScrollBarPolicy(Qt.ScrollBarAlwaysOff)
		# self.textEdit.setHorizontalScrollBarPolicy(Qt.ScrollBarAlwaysOff)
		self.verticalLayout_6.addWidget(self.textEdit)			
		self.lycframe = QtGui.QFrame(self.playListTab)
		self.lycframe.setFrameShape(QtGui.QFrame.StyledPanel)
		self.lycframe.setFrameShadow(QtGui.QFrame.Raised)
		self.lycframe.setObjectName(_fromUtf8("lycframe"))
		self.lycframe.setMaximumSize(QtCore.QSize(16777215, 26))
		self.horizontalLayout_6 = QtGui.QHBoxLayout(self.lycframe)
		self.horizontalLayout_6.setSpacing(5)
		self.horizontalLayout_6.setContentsMargins(0, 2, 0, 2)
		self.horizontalLayout_6.setObjectName(_fromUtf8("horizontalLayout_6"))
		self.showOnDesk = PushButton(self.lycframe)
		self.showOnDesk.setObjectName(_fromUtf8("showOnDesk"))
		# button menu
		self.lyricMenu = QtGui.QMenu(MainWindow)
		self.lyricMenu.setObjectName(_fromUtf8("lyricmenu"))
		self.showOnDeskMenu = QtGui.QMenu(self.lyricMenu)
		self.showOnDeskMenu.setTitle('Show On Desktop')
		
		self.singleLine = QtGui.QAction("&Single Line", MainWindow)
		self.multipleLines = QtGui.QAction("&Multiple Lines", MainWindow)
		self.singleLine.setCheckable(True)
		self.multipleLines.setCheckable(True)
		
		self.showOnDeskMenu.addAction(self.singleLine)
		self.showOnDeskMenu.addAction(self.multipleLines)
		self.lyricMenu.addAction(self.showOnDeskMenu.menuAction())
		# self.lyricMenu.addAction(self.multipleLines)
		self.showOnDesk.setMenu(self.lyricMenu)
		
		# button end
		self.horizontalLayout_6.addWidget(self.showOnDesk)
		self.horizontalLayout_6.addItem(spacerItem4)
		self.verticalLayout_6.addWidget(self.lycframe)	
		self.tabWidget.addTab(self.tab, _fromUtf8(""))
		self.verticalLayout_3.addWidget(self.tabWidget)
		self.verticalLayout.addWidget(self.frame_4)
		MainWindow.setCentralWidget(self.centralwidget)
		self.retranslateUi(MainWindow)
		self.tabWidget.setCurrentIndex(0)
		QtCore.QMetaObject.connectSlotsByName(MainWindow)
		
	def retranslateUi(self, MainWindow):
		icon = QIcon()
		icon.addPixmap(QPixmap(_fromUtf8(":/icons/music.png")), QIcon.Normal, QIcon.Off)
		MainWindow.setWindowIcon(icon)
		MainWindow.setWindowTitle(_fromUtf8("Pinus Player"))
		self.appName.setText(_translate("MainWindow", "Pinus Player", None))
		self.miniBt.setText(_translate("MainWindow", "-", None))
		self.closeBt.setText(_translate("MainWindow", "X", None))
		self.musicName.setText(_translate("MainWindow", "", None))
		self.currentTime.setFont(QtGui.QFont("verdana", 7))
		self.currentTime.setText(_translate("MainWindow", "00:00", None))
		self.playTime.setText(_translate("MainWindow", "", None))
		self.playTime.setFont(QtGui.QFont("verdana", 7))
		self.repeat.setText(_translate("MainWindow", "", None))
		self.shuffle.setText(_translate("MainWindow", "", None))
		self.repeat1.setText(_translate("MainWindow", "", None))
		self.previous.setText(_translate("MainWindow", "<<", None))
		self.play.setText(_translate("MainWindow", ">", None))
		self.next.setText(_translate("MainWindow", ">>", None))
		self.stop.setText(_translate("MainWindow", "O", None))
		self.volume.setText(_translate("MainWindow", "V", None))
		self.add.setText(_translate("MainWindow", "+", None))
		self.delete.setText(_translate("MainWindow", "-", None))
		self.search.setText(_translate("MainWindow", "", None))
		self.save.setText(_translate("MainWindow", "/", None))
		self.tabWidget.setTabText(self.tabWidget.indexOf(self.playListTab), _translate("MainWindow", "All Music", None))
		self.save_2.setText(_translate("MainWindow", "save", None))
		self.delete_2.setText(_translate("MainWindow", "del", None))
		self.tabWidget.setTabText(self.tabWidget.indexOf(self.favoriteTab), _translate("MainWindow", "My Favorites", None))
		self.tabWidget.setTabText(self.tabWidget.indexOf(self.tab), _translate("MainWindow", "Lyric", None))
		self.showOnDesk.setText(_translate("MainWindow", "Show On Desktop", None))
from PyQt4 import phonon

