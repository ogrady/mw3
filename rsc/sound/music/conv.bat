for %%a in ("*.mp3") do ffmpeg -i "%%a" -b:a 128k "%%~na.ogg"
pause