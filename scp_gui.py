# -*- coding: utf-8 -*-
import tkinter as tk
from tkinter import filedialog, messagebox
from scp import SCPClient
import paramiko
import os

class SCPApp:
    def __init__(self, root):
        self.root = root
        self.root.title("SCP 転送ツール")
        self.root.geometry("670x230")

        # 初期値
        self.defaults = {
            "local_path": r"C:/pleiades/2023-12/workspace/todo_application/src/main/resources/prod/mail.properties",
            "username": "hiroya",
            "hostname": "192.168.0.147",
            "remote_path": "/home/hiroya",
            "key_path": r"C:/Users/hiroh/.ssh/ssh_key"
        }

        # ローカルファイル（Entryとして他と揃える）
        self.add_field("ローカルファイル", 0, self.defaults["local_path"])
        tk.Button(root, text="参照", command=self.select_file).grid(row=0, column=2, padx=5)

        # 他の項目
        self.add_field("ユーザー名", 1, self.defaults["username"])
        self.add_field("ホスト名（IP）", 2, self.defaults["hostname"])
        self.add_field("リモートパス", 3, self.defaults["remote_path"])
        self.add_field("秘密鍵パス", 4, self.defaults["key_path"])

        # ボタンフレーム
        btn_frame = tk.Frame(root)
        btn_frame.grid(row=5, column=1, pady=15)

        tk.Button(btn_frame, text="SCPで送信", width=20, command=self.send_file).pack(side="left", padx=10)
        tk.Button(btn_frame, text="閉じる", width=10, command=root.destroy).pack(side="left", padx=10)

    def add_field(self, label, row, default):
        tk.Label(self.root, text=label).grid(row=row, column=0, sticky='e', padx=10, pady=5)
        entry = tk.Entry(self.root, width=80)
        entry.insert(0, default)
        entry.grid(row=row, column=1, padx=5)
        setattr(self, f"entry_{row}", entry)

    def select_file(self):
        file_path = filedialog.askopenfilename()
        if file_path:
            self.entry_0.delete(0, tk.END)
            self.entry_0.insert(0, file_path)

    def send_file(self):
        try:
            local_file = self.entry_0.get()
            username = self.entry_1.get()
            hostname = self.entry_2.get()
            remote_path = self.entry_3.get()
            key_path = self.entry_4.get()

            key = paramiko.RSAKey.from_private_key_file(key_path)
            ssh = paramiko.SSHClient()
            ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
            ssh.connect(hostname, username=username, pkey=key)

            with SCPClient(ssh.get_transport()) as scp:
                scp.put(local_file, remote_path)

            messagebox.showinfo("成功", "ファイル送信完了！")

        except Exception as e:
            messagebox.showerror("エラー", f"送信に失敗しました:\n{e}")

# 実行
if __name__ == "__main__":
    root = tk.Tk()
    app = SCPApp(root)
    root.mainloop()