# TikTok-downloader

<b>Unfortunately due to Tiktok changing the way videos are stored a few months ago,
this doesn't work anymore. But don't worry, I'll eventually fix it later on
when i'm free.</b>

A simple java class that can parse and download a video from TikTok :)

Call the download method to downlaod the video to a specified directory
by doing 'download(String TikTokURL, File fileLocation)' please note this
method throws a basic exception if failed.

You can override the 'messageCallback(String title, String text)' method
for viewing/changing/checking the output log of the class. Mainly
for debugging info, but useful still.
