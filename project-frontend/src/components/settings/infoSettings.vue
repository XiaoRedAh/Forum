<template>
  <el-form
      ref="form"
      :rules="rules"
      label-position="top"
      label-width="100px"
      :model="infoFrom"
      style="max-width: 460px"
  >
    <el-form-item prop="username" label="用户名">
      <el-input :maxlength="8" v-model="infoFrom.username" />
    </el-form-item>
    <el-form-item label="性别">
      <el-radio-group v-model="infoFrom.sex" class="ml-4">
        <el-radio label="male" size="large">男</el-radio>
        <el-radio label="female" size="large">女</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item prop="phone" label="手机号">
      <el-input :maxlength="11" v-model="infoFrom.phone" />
    </el-form-item>
    <el-form-item label="QQ" prop="qq">
      <el-input :maxlength="11" v-model="infoFrom.qq" />
    </el-form-item>
    <el-form-item label="微信" prop="wx">
      <el-input :maxlength="30" v-model="infoFrom.wx" />
    </el-form-item>
    <el-form-item label="博客" prop="blog">
      <el-input :maxlength="50" v-model="infoFrom.blog" />
    </el-form-item>
    <el-form-item label="个人简介" prop="desc">
      <el-input :maxlength="500" type="textarea" v-model="infoFrom.desc" :rows="6"/>
    </el-form-item>
  </el-form>
  <el-button type="success" :icon="Select" @click="save">保存个人信息设置</el-button>
</template>

<script setup>
import {useStore} from "@/stores";
const store = useStore()//用户信息存储在这个全局变量中
import {Select} from "@element-plus/icons-vue";

import {onMounted, reactive, ref} from "vue";
import {post, get} from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router";

const form = ref()
//校验“用户名输入框”不能有特殊字符
const validateUsername = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入用户名'));
  } else {
    //用正则表达式判断更方便。（以下正则表达式含义：包含中文英文的用户名，不能有特殊字符）
    if (!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)) {
      callback(new Error('用户名不能包含特殊字符，只能是中文/英文'));
    }else{
      callback();
    }
  }
};

const validatePhone = (rule, value, callback) => {
  if(value !== '' && !/^1[3-9]\d{9}$/.test(value)){
    callback(new Error('手机号格式错误'));
  } else {
    callback();
  }
};

const validateBlogURL = (rule, value, callback) => {
  if(value !== '' && !/^https?:\/\/((www.)?[\w-]+.[\w-]+.?[\w-])?\/?([\w-])\/?([\w-]*)\/?$/.test(value)){
    callback(new Error('博客地址格式错误'));
  } else {
    callback();
  }
};
//制定校验规则
const rules ={
  username: [
    { validator: validateUsername, trigger: ['blur','change'] },
    { min: 2, max: 8, message: '用户名长度必须在2~8个字符之间', trigger: ['blur','change'] }
  ],
  phone: [
    { validator: validatePhone, trigger: ['blur','change'] },
    { min: 11, max: 11, message: '手机号只能是11位', trigger: ['blur','change'] }
  ],
  qq: [
    { min: 8, max: 11, message: 'qq号只能是8~11位', trigger: ['blur','change'] }
  ],
  wx: [
    { min: 5, max: 30, message: '微信号只能是5~30位', trigger: ['blur','change'] }
  ],
  blog: [
    { validator: validateBlogURL, trigger: ['blur','change'] },
    { max: 50, message: '博客地址过长', trigger: ['blur','change'] }
  ],
  desc: [
    { max: 500, message: '个人简介不能超过500字', trigger: ['blur','change'] }
  ]
}
/*"个人信息设置"的各个参数*/
const infoFrom =reactive({
  username: null,
  desc: '',
  qq: '',
  phone: '',
  wx: '',
  blog: '',
  sex: 'male'
})

//点击保存按钮，触发这个保存函数
const save = ()=>{
  form.value.validate((isValid)=>{
    if(isValid){
      post('/api/user/save-info',infoFrom,()=>{
        //用户名修改成功后，重新获取用户信息，使得新用户名马上能呈现在页面上
        get('/api/user/me',(message)=>{
          //获取成功，就将用户信息存储在前端，然后才跳转到index
          store.auth.user = message
          localStorage.setItem("user", JSON.stringify(message))//存在localStorage永久存储
        },()=>{
          store.auth.user = null
        })
        ElMessage.success("保存成功！")
      },'json')
    }else{
      ElMessage.warning('表单内容有误，请重新检查表单内容')
    }
  })
}

onMounted(()=>{
  if(infoFrom.username==null){
    get('/api/user/info',(message)=>{
      infoFrom.username=message.username
      infoFrom.desc=message.desc
      infoFrom.qq=message.qq
      infoFrom.wx=message.wx
      infoFrom.sex=message.sex ? message.sex:'male'
      infoFrom.phone=message.phone
    })
  }
})
</script>

<style scoped>

</style>