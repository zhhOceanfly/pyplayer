package com.genius.musicplay;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.genius.demo.furui.R;



import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

public class MediaAlbum {
	//��ȡר�������Uri
		private static final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");
		/**
		 * ��ȡĬ��ר��ͼƬ
		 * @param context
		 * @return
		 */
		public static Bitmap getDefaultArtwork(Context context,boolean small) {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inPreferredConfig = Bitmap.Config.RGB_565;
			if(small){	//����СͼƬ
				return BitmapFactory.decodeStream(context.getResources().openRawResource(R.drawable.ablum_deflaut), null, opts);
			}
			return BitmapFactory.decodeStream(context.getResources().openRawResource(R.drawable.ablum_deflaut), null, opts);
		}
		
		
		/**
		 * ���ļ����л�ȡר������λͼ
		 * @param context
		 * @param songid
		 * @param albumid
		 * @return
		 */
		private static Bitmap getArtworkFromFile(Context context, long songid, long albumid){
			Bitmap bm = null;
			if(albumid < 0 && songid < 0) {
				throw new IllegalArgumentException("Must specify an album or a song id");
			}
			try {
				BitmapFactory.Options options = new BitmapFactory.Options();
				FileDescriptor fd = null;
				if(albumid < 0){
					Uri uri = Uri.parse("content://media/external/audio/media/"
							+ songid + "/albumart");
					ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
					if(pfd != null) {
						fd = pfd.getFileDescriptor();
					}
				} else {
					Uri uri = ContentUris.withAppendedId(albumArtUri, albumid);
					ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
					if(pfd != null) {
						fd = pfd.getFileDescriptor();
					}
				}
				options.inSampleSize = 1;
				// ֻ���д�С�ж�
				options.inJustDecodeBounds = true;
				// ���ô˷����õ�options�õ�ͼƬ��С
				BitmapFactory.decodeFileDescriptor(fd, null, options);
				// ���ǵ�Ŀ������800pixel�Ļ�������ʾ
				// ������Ҫ����computeSampleSize�õ�ͼƬ���ŵı���
				options.inSampleSize = 100;
				// ���ǵõ������ŵı��������ڿ�ʼ��ʽ����Bitmap����
				options.inJustDecodeBounds = false;
				options.inDither = false;
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				
				//����options��������������Ҫ���ڴ�
				bm = BitmapFactory.decodeFileDescriptor(fd, null, options);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return bm;
		}
		
		/**
		 * ��ȡר������λͼ����
		 * @param context
		 * @param song_id
		 * @param album_id
		 * @param allowdefalut
		 * @return
		 */
		public static Bitmap getArtwork(Context context, long song_id, int album_id, boolean allowdefalut, boolean small){
			if(album_id < 0) {
				if(song_id < 0) {
					Bitmap bm = getArtworkFromFile(context, song_id, -1);
					if(bm != null) {
						return bm;
					}
				}
				if(allowdefalut) {
					return getDefaultArtwork(context, small);
				}
				return null;
			}
			ContentResolver res = context.getContentResolver();
			Uri uri = ContentUris.withAppendedId(albumArtUri, album_id);
			if(uri != null) {
				InputStream in = null;
				try {
					in = res.openInputStream(uri);
					BitmapFactory.Options options = new BitmapFactory.Options();
					//���ƶ�ԭʼ��С
					options.inSampleSize = 1;
					//ֻ���д�С�ж�
					options.inJustDecodeBounds = true;
					//���ô˷����õ�options�õ�ͼƬ�Ĵ�С
					BitmapFactory.decodeStream(in, null, options);
					/** ���ǵ�Ŀ��������N pixel�Ļ�������ʾ�� ������Ҫ����computeSampleSize�õ�ͼƬ���ŵı��� **/
					/** �����targetΪ800�Ǹ���Ĭ��ר��ͼƬ��С�����ģ�800ֻ�ǲ������ֵ���������������Ľ�� **/
					if(small){
						options.inSampleSize = computeSampleSize(options, 40);
					} else{
						options.inSampleSize = computeSampleSize(options, 600);
					}
					// ���ǵõ������ű��������ڿ�ʼ��ʽ����Bitmap����
					options.inJustDecodeBounds = false;
					options.inDither = false;
					options.inPreferredConfig = Bitmap.Config.ARGB_8888;
					in = res.openInputStream(uri);
					return BitmapFactory.decodeStream(in, null, options);
				} catch (FileNotFoundException e) {
					Bitmap bm = getArtworkFromFile(context, song_id, album_id);
					if(bm != null) {
						if(bm.getConfig() == null) {
							bm = bm.copy(Bitmap.Config.RGB_565, false);
							if(bm == null && allowdefalut) {
								return getDefaultArtwork(context, small);
							}
						}
					} else if(allowdefalut) {
						bm = getDefaultArtwork(context, small);
					}
					return bm;
				} finally {
					try {
						if(in != null) {
							in.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return null;
		}

		/**
		 * ��ͼƬ���к��ʵ�����
		 * @param options
		 * @param target
		 * @return
		 */
		public static int computeSampleSize(Options options, int target) {
			int w = options.outWidth;
			int h = options.outHeight;
			int candidateW = w / target;
			int candidateH = h / target;
			int candidate = Math.max(candidateW, candidateH);
			if(candidate == 0) {
				return 1;
			}
			if(candidate > 1) {
				if((w > target) && (w / candidate) < target) {
					candidate -= 1;
				}
			}
			if(candidate > 1) {
				if((h > target) && (h / candidate) < target) {
					candidate -= 1;
				}
			}
			return candidate;
		}
}
