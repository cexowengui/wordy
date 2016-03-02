/**
 * 这是一个利用jetty自定义的简易http 服务器，完成的基本两个功能 通过浏览器的访问站点，可以注册 添加好友 聊天
 * localhost:80/app/index  > 注册和登录页面
 * app/${id} >用户的聊天界面 在这个可以进行很多操作，发起聊天  创建群聊  搜索用户并添加好友 暂时就这三个功能
 * 还有一个，给自己发信息
 * 以上功能通过不同的jetty handler来实现，主要是ServletHandler ResourcesHandler 。
 * 主要编码地方 url请求映射 页面编写（请求jsp?） api规范 
 */
/**
 * @author cexowengui
 * 2016年3月2日
 */
package com.example.http.server;