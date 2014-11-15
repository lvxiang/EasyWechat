#!/bin/sh

# sendTextMessage()
#{
	
curl -d '<xml><FromUserName>123123</FromUserName><ToUserName>312312</ToUserName><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[$1]]></Content><CreateTime>123456789</CreateTime></xml>' localhost:8080/wxri/wechat/serve?signature=0ec79692d4aa7d5581d484d334994a20c0967945&timestamp=1&nonce=2

#}

curl -d '<xml><FromUserName>123123</FromUserName><ToUserName>312312</ToUserName><MsgType><![CDATA[event]]></MsgType><Event>subscribe</Event><CreateTime>123456789</CreateTime></xml>' localhost:8080/wxri/wechat/serve?signature=0ec79692d4aa7d5581d484d334994a20c0967945&timestamp=1&nonce=2
