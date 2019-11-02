# Multi-client-whiteboard
This shared whiteboard allows multi users to draw together. The admin user has the right to allow others to join or kick them out.
He/She can also save/save as/open/ the whiteboard. Also, users can chat with a chat window. 

## How to use it
1. git clone the project:

       git clone https://github.com/tianzhipengfei/Multi-client-whiteboard.git && cd Multi-client-whiteboard/
    
2. start server (admin user's) whiteboard: 

       java -jar server.jar [server's ip] [port_number] [admin's username]
       # you can use localhost as server's ip, and then the server's IP address will show in terminal
    
3. start clients whiteboard

       java -jar client.jar [server's ip] [port_number] [client's username]
