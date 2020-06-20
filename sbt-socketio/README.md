Netty-socketio: https://github.com/mrniko/netty-socketio
Netty-socketio-demo: https://github.com/mrniko/netty-socketio-demo

Netty-socketio Demo
Demo for Netty-socketio project.

Usage example
Build or install Netty-socketio lib to your maven repository. mvn clean install

Switch to /server folder and build server by maven.

Run server by command mvn exec:java

Run client in browser, by opening* file /client/index.html

Note about Chrome and IE browsers
If you want to open index.html in Chrome or IE browser you need to host it somewhere (nginx or apache, for example), or page will not work due to absence of correct "origin" http header.

Demo scenarios
By default you will run a chat which communcate with server via json objects. There are several demo scenarios available:

Class - Web client page

com.corundumstudio.socketio.demo.ChatLauncher - /client/index.html

com.corundumstudio.socketio.demo.EventChatLauncher - /client/event-index.html

com.corundumstudio.socketio.demo.SslChatLauncher - /client/ssl-event-index.html

com.corundumstudio.socketio.demo.NamespaceChatLauncher - /client/namespace-index.html

com.corundumstudio.socketio.demo.AckChatLauncher - /client/ack-index.html

com.corundumstudio.socketio.demo.BinaryEventLauncher - /client/binary-event-index.html

You can select appropriate server launcher by command with main.class parameter:

mvn exec:java -Dmain.class=com.corundumstudio.socketio.demo.SslChatLauncher