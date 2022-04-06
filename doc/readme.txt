spring-cloud-base-microserver       -----springCloud的基本组件

spring-cloud-common                 -----服务的实体父类，feign父类，provide父类,程序时间等utils

spring-cloud-quartz                 -----springCloud分布式定时任务第三方组件

spring-cloud-service-common         -----公共服务模块(短信，推送，图片上传接口服务)

spring-cloud-service-finance        -----财务服务模块

spring-cloud-service-information    -----资讯服务模块

spring-cloud-service-market         -----市场服务模块

spring-cloud-service-order          -----订单服务模块

spring-cloud-service-system         -----系统服务模块

spring-cloud-service-user           -----用户服务模块


服务名                 说明                  端口

eureka                  注册中心               8081
gateway                 网关                   8082
common                  公共模块               8083
user                    用户模块               8084
order                   订单模块               8085
system                  系统模块               8086
finance                 财务模块               8087
infor                   资讯模块               8088
market                  市场模块               8089


xxl.job.executor.port   执行器监听端口         9999
xxl-job-admin           定时任务后台管理       8090
xxl-job-executor        定时任务执行器         8091

