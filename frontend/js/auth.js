import utils from './utils.js';
import api from './api/api.js';

// 认证相关功能
const auth = {
    // 登录
    async login(username, password) {
        try {
            const result = await api.post('/api/auth/login', {
                username: username,
                password: password
            });

            if (result.success) {
                // 保存token和用户信息
                localStorage.setItem('admin_token', result.data?.token);
                localStorage.setItem('admin_user', result.data.user);
                utils.showMessage('登录成功', 'success');

                setTimeout(() => {
                    utils.redirect('dashboard.html');
                }, 1000);
            } else {
                utils.showMessage(result.message);
            }
        } catch (error) {
            utils.showMessage(error.message || '登录失败');
        }
    },

    // 注册
    async register(registerData) {
        try {
            // 验证密码确认
            if (registerData.password !== registerData.confirmPassword) {
                utils.showMessage('两次输入的密码不一致');
                return;
            }

            const result = await api.post('/api/auth/register', {
                username: registerData.username,
                password: registerData.password,
                email: registerData.email,
                realName: registerData.realName
            });

            if (result.success) {
                utils.showMessage('注册成功，请登录', 'success');
                setTimeout(() => {
                    utils.redirect('index.html');
                }, 1500);
            } else {
                utils.showMessage(result.message);
            }
        } catch (error) {
            utils.showMessage(error.message || '注册失败');
        }
    },

    // 退出登录
    logout() {
        localStorage.removeItem('admin_token');
        localStorage.removeItem('admin_user');
        utils.redirect('index.html');
    },

    // 检查登录状态，未登录跳转到登录页
    checkLoginStatus() {
        if (!utils.checkLogin() && !window.location.href.includes('index.html') &&
            !window.location.href.includes('register.html')) {
            utils.redirect('index.html');
        }
    }
};

// 登录表单提交
if (document.getElementById('loginForm')) {
    document.getElementById('loginForm').addEventListener('submit', function(e) {
        e.preventDefault();
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        auth.login(username, password);
    });
}

// 注册表单提交
if (document.getElementById('registerForm')) {
    document.getElementById('registerForm').addEventListener('submit', function(e) {
        e.preventDefault();
        const registerData = {
            username: document.getElementById('username').value,
            realName: document.getElementById('realName').value,
            email: document.getElementById('email').value,
            password: document.getElementById('password').value,
            confirmPassword: document.getElementById('confirmPassword').value
        };
        auth.register(registerData);
    });
}

// 退出登录函数（全局）
function logout() {
    auth.logout();
}

// 检查登录状态
function checkLoginStatus() {
    auth.checkLoginStatus();
}