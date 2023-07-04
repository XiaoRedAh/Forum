<template>
  <div>
    <div>
      <h1><el-icon><Message/></el-icon>邮箱设置</h1>
      <el-form
          ref="emailForm"
          :rules="rules"
          label-position="top"
          label-width="100px"
          :model="securityForm"
          style="max-width: 460px"
      >
        <el-form-item label="邮箱地址" prop="email">
          <el-input v-model="securityForm.email" />
        </el-form-item>
      </el-form>
      <el-button type="success" :icon="Select" @click="saveEmail">保存邮件地址</el-button>
    </div>
    <el-divider/>
    <div>
      <h1><el-icon><Lock/></el-icon>密码设置</h1>
      <el-form
          ref="passwordForm"
          :rules="rules"
          label-position="top"
          label-width="100px"
          :model="securityForm"
          style="max-width: 460px"
      >
        <el-form-item label="原密码" prop="password_old">
          <el-input type="password" show-password v-model="securityForm.password_old" />
        </el-form-item>
        <el-form-item label="新密码" prop="password_new">
          <el-input  type="password" show-password v-model="securityForm.password_new" />
        </el-form-item>
        <el-form-item label="重复新密码" prop="password_new_repeat">
          <el-input  type="password" show-password v-model="securityForm.password_new_repeat" />
        </el-form-item>
      </el-form>
      <el-button type="success" :icon="Select" @click="changePassword">保存重置密码</el-button>
    </div>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import {Lock, Message, Select} from "@element-plus/icons-vue";
import {get, post} from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router";
import {useStore} from "@/stores";

const store = useStore()//用户信息存储在这个全局变量中

const securityForm = reactive({
  email: '',
  password_old: '',
  password_new: '',
  password_new_repeat: ''
})

const emailForm = ref()
const saveEmail = ()=>{
  emailForm.value.validate((isValid)=>{
    if(isValid){
      post('/api/user/save-email',{email: securityForm.email},
          ()=>{
            //邮箱修改成功后，重新获取用户信息，使得新邮箱马上能呈现在页面上
            get('/api/user/me',(message)=>{
              //获取成功，就将用户信息存储在前端，然后才跳转到index
              store.auth.user = message
              localStorage.setItem("user", JSON.stringify(message))//存在localStorage永久存储
            },()=>{
              store.auth.user = null
            })
        ElMessage.success("保存成功！")
          })
    }else{
      ElMessage.warning("邮箱格式有误，请正确填写")
    }
  })
}

const passwordForm = ref()
const changePassword = ()=>{
  passwordForm.value.validate((isValid)=>{
    if(isValid){
      post('/api/user/change-password',{
        old: securityForm.password_old,
        new: securityForm.password_new
      },()=>{
        ElMessage.success("密码修改成功，请重新登录")
        //修改成功后，会执行退出登录的操作
        get('/api/auth/logout',(message) =>{//退出登录成功
          // 先把用户的登录状态清空，才能成功返回到登录页面（配置了路由守卫，如果不清空，前端还是认为你是登录状态，回不到登录页面）
          ElMessage.success(message)
          store.auth.user = null
          localStorage.removeItem('user')//将localStorage存储的用户信息也删掉
          router.push('/')//跳转回登录页面
        })
      })
    }else{
      ElMessage.warning("密码校验失败，请检查是否正确填写")
    }
  })
}

const validatePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'));
  } else if (value !== securityForm.password_new) {
    callback(new Error('两次输入密码不一致!'));
  } else {
    callback();
  }
};
//制定校验规则
const rules ={
  password_old: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 16, message: '密码长度必须在6~16个字符之间', trigger: ['blur','change'] }
  ],
  password_new: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 16, message: '密码长度必须在6~16个字符之间', trigger: ['blur','change'] }
  ],
  password_new_repeat: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    { validator: validatePassword, trigger: ['blur','change'] },
  ],
  email:[
    { required: true, message: '请输入邮箱地址', trigger: 'blur' },
    { type: 'email', message: '请输入合法的电子邮箱地址', trigger: ['blur', 'change'] }
  ],
}
</script>

<style scoped>

</style>