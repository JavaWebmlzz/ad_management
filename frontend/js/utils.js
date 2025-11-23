// 工具函数
const utils = {
    // 显示消息
    showMessage(message, type = 'error') {
        const messageEl = document.getElementById('message');
        if (messageEl) {
            messageEl.textContent = message;
            messageEl.className = `message ${type}`;
            messageEl.style.display = 'block';

            // 3秒后自动隐藏
            setTimeout(() => {
                messageEl.style.display = 'none';
            }, 3000);
        } else {
            alert(message);
        }
    },

    // 格式化日期
    formatDate(dateString) {
        if (!dateString) return '-';
        const date = new Date(dateString);
        return date.toLocaleString('zh-CN');
    },

    // 获取状态文本
    getStatusText(status) {
        const statusMap = {
            0: '待审核',
            1: '已上线',
            2: '已下线',
            3: '已过期'
        };
        return statusMap[status] || '未知';
    },

    // 检查登录状态
    checkLogin() {
        const token = localStorage.getItem('admin_token');
        const user = localStorage.getItem('admin_user');
        return token && user;
    },

    // 跳转页面
    redirect(url) {
        window.location.href = url;
    }
};