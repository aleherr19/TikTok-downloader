package com.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class TikTokDownloaderV2 {

	//Set up variables
	private File file;
	private URL url;
	
	
	/*
	 * This method sets the variables needed to download a video.
	 * fileLocation is the location where the video is going to be saved
	 * videoURL is the URL of the video to be downloaded
	 */
	public TikTokDownloaderV2(String fileLocation, String videoURL) throws MalformedURLException {
		file = new File(fileLocation);
		url = new URL(videoURL);
		System.out.println("Initialized TikTokDownloader version 2");
	}
	
	
	
	/*
	 * This method connects to the website from the given URL.
	 * It removes useless content and grabs the video URL and thumbnail URL
	 * from the webpage
	 */
	public void download() throws IOException {
		
		//Create a URL connection
		URLConnection conn = url.openConnection();
		
		//Set the user agent so TikTok will think we're a person using a browser instead of a program
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

        //Set up the bufferedReader
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		/*
		 * Read every line until we get
		 * to a string with the text 'videoObject'
		 * which is where misc. information about
		 * the user is stored and where the video
		 * URL is stored too
		 */
		String data;
		while((data = in.readLine()) !=null) {
			if(data.contains("videoObject")) {
				// Read up until we reach a string in the HTML file valled 'videoObject'
				break;
			}
		}
		
		//Close the bufferedReader as we don't need it anymore
		in.close();
		
		/*
		 * Because we are viewing the raw source code from
		 * the website, there's a lot of trash including but not
		 * limited to HTML tags, javascript, random text, and so
		 * on. We don't want that. That's why it will be cropped
		 * out down below
		 */
		
		//Crop out the useless tags and code behind the VideoObject string
		data = data.substring(data.indexOf("VideoObject"), data.length());
		
		
		//Grab the thumb nail URL
		String thumbnailURL = data.substring(data.indexOf("thumbnailUrl") + 16);
		thumbnailURL = thumbnailURL.substring(0, thumbnailURL.indexOf("\""));
		
		// Print out the thumbnail URL
		System.out.println("ThumbnailURL: " + thumbnailURL);
		
		//Grab content URL (video file)
		String contentURL = data.substring(data.indexOf("contentUrl") + 13);
		contentURL = contentURL.substring(0, contentURL.indexOf("?"));
		
		//Print out the video URL 
		System.out.println("ContentURL: " + contentURL);
		
		//Now that we have the Thumbnail and Video URL, we can download them!
		downloadVideoFile(contentURL);
	}
	
	
	/*
	 * This method actually downloads the video
	 */
	private void downloadVideoFile(String url) throws MalformedURLException, IOException {
		
		//Set up the stream and connect to the video URL
		InputStream inputStream = new URL(url).openStream();
		
		//Set up the buffer to store the inputstream bytes into
		//It can be anything, but 512 is an okay buffer size
		byte[] videoBuffer = new byte[512];
		
		//Set up the file output stream
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		
		/*
		 * When the streams ends (which means we downloaded the whole video)
		 * it will return '-1'
		 * While we store the bytes into the videoBuffer from inputStream 
		 * and the stream doesn't equal -1, write each byte from the buffer into the file.
		 * When the stream equals -1, close the file as the video is complete
		 */
		int len;
		while((len = inputStream.read(videoBuffer)) != -1) {
			fileOutputStream.write(videoBuffer, 0, len);
		}
		
		//Close the file stream
		fileOutputStream.close();
		
		//Done message
		System.out.println("Video Downloaded!");
		
		
	
	
	}
	
	
	
	
	
	
	
	
	
}
