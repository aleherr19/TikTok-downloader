package com.main;

import java.io.IOException;
import java.net.MalformedURLException;

public class Main{

	
	/*
	 * A simple tiktok downloader in java.
	 * Please note: this will download videos WITH a watermark.
	 * 
	 * If you are having problems downloading videos,
	 * use your browser on your computer, visit a tiktok video,
	 * and copy the link in the address bar and use that URL to 
	 * download the tiktok.
	 */
	
	final String TikTokSaveLocation = "C:\\Users\\Alex\\Desktop\\TikTok_" + System.currentTimeMillis() + ".mp4";
	final String TikTokURL = "https://www.tiktok.com/@duncanyounot/video/6752667006530620678";
	final String TikTokURL2 = "https://www.tiktok.com/@jay.gamao/video/6747120899637382402";
	
	// Remove static context
	public static void main(String[] args) { new Main().mainMethod(); }
	
	
	// Main method
	public void mainMethod() {
		
		try {
			
			//Initialize the TikTokDownloaderV2 class
			TikTokDownloaderV2 ttd = new TikTokDownloaderV2(TikTokSaveLocation, TikTokURL);
			
			//Call the download method to download the video
			ttd.download();
				
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
	}
		

}
