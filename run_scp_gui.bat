@echo off
cd /d %~dp0
CALL "C:\Users\hiroh\miniconda3\Scripts\activate.bat" scp_env
python scp_gui.py