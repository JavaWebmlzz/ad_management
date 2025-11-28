// 认证相关功能
const auth = {
    // auth.js - 修改登录方法
    async login(username, password) {
        try {
            const result = await api.post('/api/auth/login', {
                username: username,
                password: password
            });

            if (result.success) {
                // 保存token和用户信息
                localStorage.setItem('admin_token', result.data.token);
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

    // 退出登录
    logout() {
        localStorage.removeItem('admin_token');
        localStorage.removeItem('admin_user');
        utils.redirect('index.html');
    },

    // 检查登录状态，未登录跳转到登录页
    checkLoginStatus() {
        if (!utils.checkLogin() && !window.location.href.includes('index.html')) {
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

// 退出登录函数（全局）
function logout() {
    auth.logout();
}

// 检查登录状态
function checkLoginStatus() {
    auth.checkLoginStatus();
}