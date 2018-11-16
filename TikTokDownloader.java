package com.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class TikTokDownloader{	

	//Class-wide variables
	private int oldProg = 0;
	private File fileLocation;

	
	/*
	 * Just call this method and you're good to go :)
	 */
	public void download(String TikTokURL, File fileLocation) throws Exception{
		this.fileLocation = fileLocation;
				
		messageCallback("INIT", "Checking url...");
		
		//If the URL is from the TikTok website.
		if (TikTokURL.contains("tiktok")) {
			messageCallback("INIT", "It's a TikTok!");
			//Find the video URL using this parser.
			ParseTikTok(TikTokURL);
		} else {
			messageCallback("INIT", "It doens't look like this video is a TikTok!");
		}

	}

	
	/*
	 * Can be overridden by some class
	 * so you can have control of the output info
	 */
	public void messageCallback(String title, String text) {
		System.out.println("[" + title + "] " + text);
	}

	

	private void ParseTikTok(String url) throws Exception {

		/*
		 * Only used on android 8.0+ devices.
		 * Android no longer connects to un-secure websites
		 * and although TikTok is a secure website, when
		 * shared, the URL oddly is not in HTTPS form, 
		 * but HTTP. Weird, right?
		 */
		if (url.startsWith("http:")) {
			url = url.replace("http:", "https:");
		}

		messageCallback("HTTPS","Loading url...");

		/*
		 * Create a basic connection to the website.
		 */
		URL link = new URL(url);
		URLConnection urlConnection = link.openConnection();
		urlConnection.connect();

		BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

		/*
		 * In the website, down below in some JSON or Javascript code (cant remember)
		 * there's a little variable in the big-ass array called "play_addr"
		 * which contains the video URL. This simply finds it, and crops
		 * it out from the rest of the useless content after it which we
		 * don't need. The continueSearch boolean is there because there
		 * are multiple "play_addr" variables after the first one. So
		 * once we grab the first one -- which is thankfully the video URL,
		 * we can tell the while loop to stop because if it got to the next
		 * one, it would override the current playHeader String variable
		 * with some other useless thing it found. 
		 */
		String playHeader = "NULL";
		String temp;
		boolean continueSearch = true;
		while ((temp = in.readLine()) != null) {
			if (temp.contains("play_addr") && continueSearch) {
				continueSearch = false;
				messageCallback("HTTPS", "Found Play Header!");
				playHeader = temp.substring(temp.indexOf("play_addr"), temp.indexOf("\",\"\\/\\/"));
			}
		}

		in.close();

		/*
		 * Some more cropping of the String to make it a bare-bone URL
		 */
		playHeader = "https:" + playHeader.substring(25, playHeader.length()).replace("\\/", "/");
		
		messageCallback("HTTPS", "Video URL located at: " + playHeader);
		messageCallback("HTTPS","Starting download...");

		Download(playHeader);
	}

	private void Download(String url) throws Exception {
		File videoFile = new File(fileLocation + ".mp4");
		URL vidUrl = new URL(url);

		/*
		 * Make a basic connection to the video URL
		 */
		URLConnection urlConnection = vidUrl.openConnection();
		urlConnection.connect();
		final int file_size = urlConnection.getContentLength();
		InputStream in = urlConnection.getInputStream();
		FileOutputStream fos = new FileOutputStream(videoFile);

		/*
		 * Once the inputstream and fileoutputstream have been
		 * a simple 8 byte buffer has been made, after that
		 * download it and write it to a file while keeping
		 * track of the amount of bytes downloaded for 
		 * progress results
		 */
		
		byte[] buffer = new byte[8];
		int len;
		long total = 0;
		while ((len = in.read(buffer)) != -1) {
			total += len;

			final int prog = (int) ((total * 100) / file_size);

			if (oldProg != prog) {
				messageCallback("PROGRESS", prog + "%");
			}

			oldProg = prog;

			fos.write(buffer, 0, len);
		}

		fos.close();
		/*
		 * And we're done! :)
		 */
		messageCallback("COMPLETE", "Looks like your video is done downloading!");

	}

}
