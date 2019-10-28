# TikTok-downloader

A simple java class that can parse and download a video from TikTok :)

To download a tiktok, initialize the TikTokDownloaderV2 class with the required arguments.
```TikTokDownloaderV2 ttd = new TikTokDownloaderV2(String fileLocation, String videoURL);```

Next, call the download method. It may throw exceptions, so use a try/catch block 
```ttd.download();```
