<template>
  <div style="height: 100vh">
    <el-container style="height: 100%">
      <!--左侧栏。构成：网站名及logo+导航栏-->
      <!--:style动态设置整个左侧栏的宽度，实现折叠功能：hideMenu为true，宽度变为0；为false，宽度就是默认样式中的270px-->
      <el-aside class="content-aside" :style="{width : hideMenu ? '0': '270px'}">
        <!--左侧栏上方：网站名和logo-->
        <!--:style动态设置字体透明度，使得折叠过渡更加自然：hideMenu为true，字体变透明；为false，字体不透明-->
        <div class="content-aside-topShow-logoAndName"
             :style="{opacity : hideMenu ?'0' : '1'}">
          xiaoRed高级论坛
        </div>
        <!--左侧导航栏-->
        <el-menu
            class="content-aside-menu"
            router
        >
          <el-menu-item index="/index">
              <el-icon><Menu /></el-icon>
              <span>帖子列表</span>
          </el-menu-item>
          <el-menu-item index="2">
            <el-icon><Document /></el-icon>
            <span>校园表白墙</span>
          </el-menu-item>
          <el-menu-item index="3">
            <el-icon><Help /></el-icon>
            <span>帖子管理</span>
          </el-menu-item>
          <el-menu-item index="4">
            <el-icon><Compass/></el-icon>
            <span>我的收藏</span>
          </el-menu-item>
          <el-menu-item index="/index/settings">
            <el-icon><Tools /></el-icon>
            <span>个人设置</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <!--顶部栏-->
        <el-header class="content-header">
          <!--控制左侧导航栏展开，隐藏的按钮-->
          <div>
            <!--点击按钮，对hideMenu取反-->
            <!--根据hideMenu的值不同，按钮图标也不同-->
            <el-button style="margin-top: 10px;font-size: 25px;width: 20px"
                       :icon="hideMenu?Expand : Fold" text @click="hideMenu = !hideMenu"/>
          </div>
          <!--添加一个输入框-->
          <div style="flex: 1;margin: 0 150px 0 40px">
            <el-input :prefix-icon="Search" style="margin-top: 15px"></el-input>
          </div>
          <!--展示当前登录的用户名和邮箱-->
          <div class="user-name-email">
            <!--注意：username,email对应的是后端里AccountUser的字段-->
            <div class="user-name">{{store.auth.user.username}}</div>
            <div class="user-email">{{store.auth.user.email}}</div>
          </div>
          <!--引入element-plus的头像和下拉菜单-->
          <div style="margin-top: 10px" class="user-avatar">
            <el-dropdown>
              <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item :icon="User">个人信息</el-dropdown-item>
                  <el-dropdown-item :icon="SwitchButton" divided @click="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>
        <!--主体内容-->
        <el-main style="padding: 0">
          <router-view/>
        </el-main>
      </el-container>
    </el-container>

  </div>

</template>

<script setup>
import {get} from "@/net"
import {ElMessage} from "element-plus";
import router from "@/router";
import {useStore} from "@/stores";
import {User, SwitchButton, Fold, Search,Compass,Menu,Expand,Document,Help,Tools} from "@element-plus/icons-vue";
import {ref} from "vue";

const store = useStore()//用户信息存储在这个全局变量中

const hideMenu = ref(false)//控制左侧栏是否隐藏，默认不隐藏

const logout = ()=>{
  //向后端发送对应路径的get请求
  get('/api/auth/logout',(message) =>{//退出登录成功
    //先把用户的登录状态清空，才能成功返回到登录页面（配置了路由守卫，如果不清空，前端还是认为你是登录状态，回不到登录页面）
    ElMessage.success(message)
    store.auth.user = null
    router.push('/')//跳转回登录页面
  })
}
</script>

<style scoped>
.content-aside{
  border-right: solid 1px #e0e0e0;/*左侧栏右边界添加灰线分割线*/
  width: 270px;/*左侧导航栏默认宽度*/
  transition: 0.3s;/*属性变化（侧边栏折叠）不立即生效，而是有一个0.3s的过渡时间*/
}
.content-aside-topShow-logoAndName{
  /*在左侧导航栏上方展示网站名字和logo*/
  height: 60px;/*盒子高度为60px，与顶部栏的高度一致对齐*/
  line-height: 60px;/*文字行距设置为60px*/
  text-align: center;/*文字居中显示*/
  transition: 0.3s/*属性变化（侧边栏折叠时，字体透明度动态变化）不立即生效，而是有一个0.3s的过渡时间*/
}
.content-aside-menu{
  height: calc(100% - 60px);/*菜单高度根据顶上的网站名动态计算，避免溢出*/
  border: none;/*把element-ul自带的分割线去掉，用我自定义的分割线*/
}
.content-header{
  display: flex;/*整个顶部栏采用流式布局*/
  border-bottom: solid 1px #e0e0e0;/*顶部栏下边界添加灰色分割线*/
  padding: 0 20px;
}
.user-avatar:hover{
  cursor: pointer;/*当鼠标悬浮在头像上时，指针变成小手*/
}
.user-name-email{
  /*展示用户名和密码的统一样式*/
  text-align: right;
  margin: 10px 10px 0 0;
}
.user-name{
  /*用户名展示的个性样式：加粗字体，比邮箱大*/
  font-weight: bold;
  font-size: 18px
}
.user-email{
  /*邮箱展示的个性样式：灰色字体（比用户名浅点），字体调小*/
  color: grey;
  font-size: 14px;
  line-height: 14px
}

</style>