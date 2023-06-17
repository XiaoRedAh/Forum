<script setup>
import {get} from "@/net";
import {ElMessage} from "element-plus";
import {useStore} from "@/stores";
import router from "@/router";
//存储用户信息的全局变量
const store = useStore()
//把存在localStorage中的user取出来
const user = localStorage.getItem('user')

if(user != null){//如果存储的用户信息不为空，就将用户信息恢复回store里
  store.auth.user=JSON.parse(user)//localStorage中的是json格式，因此还要解析
}

</script>

<template>
 <router-view></router-view>
</template>

<style scoped>

</style>
