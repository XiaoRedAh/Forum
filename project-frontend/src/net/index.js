import axios from "axios";
import {ElMessage} from "element-plus";
import router from "@/router";

//失败和异常给个默认处理就行
const defaultError = ()=>ElMessage.error('发生了一些错误，请联系管理员')
const defaultFailure = (message)=>ElMessage.warning(message)

//封装axios的post方法
//data就是接收后端响应过来的数据。后端那边统一了响应类，也就是RestBean
function post(url, data, success, type = 'x-www-form-urlencoded', failure = defaultFailure, error = defaultError){
    axios.post(url, data, {//配置
        //由于都是以表单形式提交，因此默认headers的Content-Type为表单形式（但实际写后端穿的一般都是json）
        headers:{
            'Content-Type': 'application/' + type
        },
        withCredentials: true//发起请求，携带cookie（基于session的校验需要cookie，以后用jwt的时候就不用cookie了）
    }).then(({data})=>{
        if(data.status===401){//用户没登陆，后端会响应401
            //没登录，直接退回去
            router.push('/')
            ElMessage.warning('请重新登录')
        }
        else if(data.success)
            success(data.message, data.status)
        else
            failure(data.message, data.status)
    }).catch(error)
}

//封装axios的get方法（get没有data）
function get(url, success, failure = defaultFailure, error = defaultError){
    axios.get(url,{
        //因为get方法不需要提交东西，所以不用配置headers
        withCredentials: true//发起请求，携带cookie（基于session的校验需要cookie，以后用jwt的时候就不用cookie了）
    }).then(({data})=>{
        if(data.status===401){//用户没登陆，后端会响应401
            //没登录，直接退回去
            router.push('/')
            ElMessage.warning('请重新登录')
        }
        else if(data.success)
            success(data.message, data.status,)
        else
            failure(data.message, data.status)
    }).catch(error)
}
const logout = ()=>{
    //向后端发送对应路径的get请求
    get('/api/auth/logout',(message) =>{//退出登录成功
        //先把用户的登录状态清空，才能成功返回到登录页面（配置了路由守卫，如果不清空，前端还是认为你是登录状态，回不到登录页面）
        ElMessage.success(message)
        store.auth.user = null
        localStorage.removeItem('user')//将localStorage存储的用户信息也删掉
        router.push('/')//跳转回登录页面
    })
}

//封装好后，暴露出去
export {get, post, logout}