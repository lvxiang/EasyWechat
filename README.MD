# EasyWechat
EasyWechat是一个微信公众平台二房库，它主要有两个功能：
- 消息创建和解析
- 基于配置文件的会话管理

EasyWechat能够将微信的XML文本信息转换为特定的java对象，也能根据您的需求创建所需要的微信消息，目前能够解析的消息类型包括TEXT和EVENT，能够创建的消息类型包括TEXT和NEWS。

EasyWechat的消息创建API非常简单易用，您可以采用“链式编程”的方式来快速的创建出一条复杂的图文消息。您甚至可以通过类似函数式编程的方式将容器类中的数据展开到图文消息中，下面就是这样的一个实例：

    WechatMsgModel message = WechatModelBuilder
			.createBuilder()
			.createNewsMsg()
			.setFromUserName(model.getToUserName())
			.setToUserName(model.getFromUserName())
			.setCreateTime(String.valueOf(System.currentTimeMillis()))
			.addArticle("TITLE", "", "picUrl", "Url")
			.each(collection, new NewsMsgCallback<YourClass>(){

				@Override
				public void execute(int pos, NewsMsgBuilder builder, YourClass obj) {
				// Do something in the loop	
				}
				
			})
			.build();

这种方式使得您的代码简洁高效而不失可读性。EasyWechat还提供了方便的API将Java对象转换为微信消息，您可以先构造出一个复杂的消息对象，然后按照如下方式进行转换：
    
    String result = Wechat.writeMsg(message, encoding);
您还可以通过快捷方法迅速创建出文本消息：

    Wechat.writeMsg(Wechat.genStdTextModel(event, "计划列表为空"), encoding);

EasyWechat的另一个功能就是会话管理，您可以通过配置XML文件的方式定义出会话内容，如下所示：

    <sessions>
    	<session-chain id="subscribe-chain">
    		<session id="subscribe" class="enter">
    			<type>event</type>
    			<match>subscribe</match>
    			<classname>
    				your.package.your.handler
    			</classname>
    			<conditional>
    				<condition>
    					<value>yes</value>
    					<target>end</target>
    			    	<return><![CDATA[欢迎关注${accountName},您的微信账户已经完成绑定，您可以立即开始使用。输入"注册"或"#1"以重新绑定当前帐号。]]></return>
    				</condition>
    				<condition>
    					<value>no</value>
    					<target>end</target>
    					<return><![CDATA[欢迎关注${accountName},您需要先进行帐号绑定,请输入"注册"或"#1"开始帐号绑定流程。]]></return>
    				</condition>
    			</conditional>
    		</session>
    	</session-chain>
    </sessions>

通过这种方式，您可以通过指定的类来处理指定类型的消息，上述配置中就使用了&lt;your.package.your.handler&gt;类来处理Event时间中EventType为subscribe的事件消息。您所提供的类必须实现&lt;org.dt.easywechat.session.SessionHandler&gt;接口，根据您的返回值为yes/no，会进入不同的condition。

本库目前还远远未到完善地步，欢迎contribute！