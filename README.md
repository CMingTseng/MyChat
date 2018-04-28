
##   集成

1.以导入moudle的方式导入项目uikit

2.在主项目的AndroidManifest.xml中需要修改的地方（也就是本项目的app项目）
```

 <!-- SDK 权限申明, 第三方 APP 接入时，请将 com.dingmouren.mychat 替换为自己的包名和
    下面的 uses-permission 一起加入到你的 AndroidManifest 文件中。 -->
<permission
        android:name="com.dingmouren.mychat.permission.RECEIVE_MSG"
        android:protectionLevel="signature"/>
		
		
<!-- 接收 SDK 消息广播权限， 第三方 APP 接入时，请将 com.dingmouren.mychat 替换为自己的包名 -->
<uses-permission android:name="com.dingmouren.mychat.permission.RECEIVE_MSG"/>
	
	
<!-- android:authorities="{包名}.ipc.provider", 请将com.dingmouren.mychat替换为自己的包名 -->
<provider
            android:name="com.netease.nimlib.ipc.NIMContentProvider"
            android:authorities="com.dingmouren.mychat.ipc.provider"
            android:exported="false"
            android:process=":core" />
			
			
<!-- 填写自己的网易云应用的appkey -->
<meta-data
            android:name="com.netease.nim.appKey"
            android:value="3cfdb047d770108ff173cccf4cd392fc" />
```

**注意：** uikit中的gradle中的构建所需要的版本号要与主项目一致