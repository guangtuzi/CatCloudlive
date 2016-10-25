#CatCloudlive for Android
##Abstract
当前的直播如火如荼，搭建一个完整的手机直播系统，涉及到很多必须的环节：推流端（采集、信号前处理、编码、推流），服务器端（转码、录制、转发、截图等），客户端播放（拉流、解码、渲染），互动系统（弹幕、聊天室、礼物系统、赞等）。作为个人开发者来说，整套流程全弄完显得有点力不从心，最简单的就是集成现成的sdk，但sdk不开源，你无法了解内部实现细节，不利于初涉此行业的小白们成长进步。为此鄙人开源了此CatCloudlive项目，即方便了需要此方面知识的爱学者，也锻炼了自己组织项目、开发项目、写作的的经验能力。

CatCloudlive项目中包含了android client端和tomcat server端以及一个mysql数据库，有需要的朋友们可以在此基础上修改数据库的表设计，毕竟后期怎么设计礼物系统，因人而异，请大胆设计，当然了本项目并不是闭门造车，一蹴而就，在此感谢作者begeekmyfriend、jjdxmashl贡献了他们的开源项目：[jjdxm_ijkplayer](https://github.com/jjdxmashl/jjdxm_ijkplayer)、[yasea](https://github.com/begeekmyfriend/yasea)。
##Introduction
* `CatCloudliveClient`：android客户端，包含注册、登录、直播、播放功能。支持设备android api 19及以上，[yasea](https://github.com/begeekmyfriend/yasea)只支持armeabi-v7a架构的手机进行推流，本项目重新编译了，生成支持各种架构的.so库（arm64-v8a、armeabi、armeabi-v7a、mips、mips64、x86、x86_64）。主界面支持上拉和下拉刷新操作。

* 'CloudLiveVideoServer`：Tomcat服务器端，负责客户端注册和登录事宜。什么？你还不知道怎么部署服务器？该去好好学习相关知识了。^……^

* 'MySql_database'：此为一个mysql数据库，尽情的修改它吧，当然在代码中要能体现出来^……^

* `RTMP服务器端`：对于拉流过来的信号进行处理和转发，以及其他一些复杂功能，这个可以用nginx来做，但现在有一个很好的开源项目：[SRS](https://github.com/ossrs/srs/tree/2.0release)，SRS定位是运营级的互联网直播服务器集群，能将RTMP流接入SRS，进行各种转换，功能很强大，感兴趣的朋友可以前去学习了解。你可以自己搭建该服务器，当然为了测试没必要个人花钱买一个服务器来部署，本项目中采用SRS的一个公用的在线服务器用来测试：[SRS公用播放器](http://winlinvip.github.io/srs.release/trunk/research/players/srs_player.html?vhost=players)，它的服务器地址前缀是：rtmp://ossrs.net:1935/...，后面加上用户名和id即能转化为一个独立的直播地址（本项目是这么干的）。当然如果你自己部署rtmp服务器，你就填写自己的服务器的ip地址。

##Attention
* 修改CatCloudlive/CatCloudliveClient/app/src/main/java/com/catlivevideo/living/catlivevideo/bean/Url.java，把URL = "localhost:8080/CloudLiveVideoServer/"中的`localhost`改为你自己的服务器的ip，本机测试为localhost。
* 修改CatCloudlive/CloudLiveVideoServer/src/com/catlivevideo/living/config.properties，把其中的userName、password设为你登录数据库的用户名和密码。<br/>

##Screenshots<br/>
<div align = "center">
 <img src="https://github.com/yishuihan008/CatCloudlive/blob/master/screenshots/Screenshot_2016-10-25-09-46-45_com.catlivevideo.l.png" width = "260" height = "450" alt="gologin" align = "left" />
  <img src="https://github.com/yishuihan008/CatCloudlive/blob/master/screenshots/Screenshot_2016-10-25-12-39-45_com.catlivevideo.l.png" width = "260" height = "450" alt="login"  align = "center"/>
   <img src="https://github.com/yishuihan008/CatCloudlive/blob/master/screenshots/Screenshot_2016-10-25-12-39-59_com.catlivevideo.l.png" width = "260" height = "450" alt="quicklogin"  align = "right"/><br/>
    <img src="https://github.com/yishuihan008/CatCloudlive/blob/master/screenshots/Screenshot_2016-10-25-12-40-06_com.catlivevideo.l.png" width = "260" height = "450" alt="register" align = "left" />
     <img src="https://github.com/yishuihan008/CatCloudlive/blob/master/screenshots/Screenshot_2016-10-25-12-40-46_com.catlivevideo.l.png" width = "260" height = "450" alt="living" align = "center" />
      <img src="https://github.com/yishuihan008/CatCloudlive/blob/master/screenshots/Screenshot_2016-10-25-12-40-55_com.catlivevideo.l.png" width = "260" height = "450" alt="usersetting" align = "right" />
</div>
##To be improved
现如今直播中没有美颜，滤镜磨皮等措施，90%的主播们没法看，so在推流前，camera采集到的信号需要进行前处理，可以采用著名的GPUImage，当然如果公司有实力的话也可以通过自己的算法实现，本项目没有此功能啦，确保展现的是真实的自己^-^；另外礼物系统、聊天系统、弹幕系统，依赖IM，这个可以采用环信、极光IM等sdk了。本项目搭建了界面框架，具体功能可以依据各自需要进行修改，希望能对您有所帮助，欢迎拍砖。
