# TikTok-downloader
A simple java class that can parse and download a video from TikTok :)

Call the download method to downlaod the video to a specified directory
by doing 'download(String TikTokURL, File fileLocation)' please note this
method throws a basic exception if failed.

You can override the 'messageCallback(String title, String text)' method
for viewing/changing/checking the output log of the class. Mainly
for debugging info, but useful still.
