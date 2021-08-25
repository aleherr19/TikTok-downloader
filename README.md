# TikTok-downloader (Doesn't work. Looking for a fix. 8/24/2021)


Before you look any further, please note that this code does not work at this moment.

...
A simple java class that can parse and download a video from TikTok :)

To download a tiktok, initialize the TikTokDownloaderV2 class with the required arguments.
```TikTokDownloaderV2 ttd = new TikTokDownloaderV2(String fileLocation, String videoURL);```

Next, call the download method. It may throw exceptions, so use a try/catch block 
```ttd.download();```

You can view an example in the ```Main.class``` file.
