import api from './api/api';
import utils from './utils'

// 广告管理功能
const adManager = {
    // 加载广告列表
    async loadAds() {
        const tableBody = document.getElementById('adsTableBody');
        const loadingMessage = document.getElementById('loadingMessage');
        const messageEl = document.getElementById('message');

        if (tableBody) {
            tableBody.innerHTML = '';
            loadingMessage.style.display = 'block';
            if (messageEl) messageEl.style.display = 'none';
        }

        try {
            const result = await api.get('/api/ads');

            if (tableBody) {
                loadingMessage.style.display = 'none';

                if (result.success && result.data.length > 0) {
                    this.renderAdsTable(result.data);
                } else {
                    tableBody.innerHTML = '<tr><td colspan="9" style="text-align: center;">暂无广告数据</td></tr>';
                }
            }

            return result.data || [];
        } catch (error) {
            console.error('加载广告列表失败:', error);
            if (tableBody) {
                loadingMessage.style.display = 'none';
                tableBody.innerHTML = '<tr><td colspan="9" style="text-align: center; color: red;">加载失败: ' + error.message + '</td></tr>';
            }
            return [];
        }
    },

    // 渲染广告表格
    renderAdsTable(ads) {
        const tableBody = document.getElementById('adsTableBody');
        const statusFilter = document.getElementById('statusFilter');
        const searchInput = document.getElementById('searchInput');

        let filteredAds = ads;

        // 应用状态过滤
        if (statusFilter && statusFilter.value) {
            filteredAds = filteredAds.filter(ad => ad.status == statusFilter.value);
        }

        // 应用搜索过滤
        if (searchInput && searchInput.value) {
            const searchTerm = searchInput.value.toLowerCase();
            filteredAds = filteredAds.filter(ad =>
                ad.title.toLowerCase().includes(searchTerm)
            );
        }

        if (filteredAds.length === 0) {
            tableBody.innerHTML = '<tr><td colspan="9" style="text-align: center;">没有找到匹配的广告</td></tr>';
            return;
        }

        tableBody.innerHTML = filteredAds.map(ad => `
            <tr>
                <td>${ad.id}</td>
                <td>${ad.title}</td>
                <td>${this.getTypeText(ad.type)}</td>
                <td>
                    <span class="status-badge status-${ad.status}">
                        ${utils.getStatusText(ad.status)}
                    </span>
                </td>
                <td>${utils.formatDate(ad.startTime)}</td>
                <td>${utils.formatDate(ad.endTime)}</td>
                <td>${ad.currentImpressions || 0}</td>
                <td>${ad.clickCount || 0}</td>
                <td>
                    <button onclick="editAd(${ad.id})" class="btn btn-secondary" style="padding: 4px 8px; margin-right: 5px;">编辑</button>
                    <button onclick="changeAdStatus(${ad.id}, ${ad.status === 1 ? 2 : 1})" class="btn ${ad.status === 1 ? 'btn-danger' : 'btn-success'}" style="padding: 4px 8px; margin-right: 5px;">
                        ${ad.status === 1 ? '下线' : '上线'}
                    </button>
                    <button onclick="deleteAd(${ad.id})" class="btn btn-danger" style="padding: 4px 8px;">删除</button>
                </td>
            </tr>
        `).join('');
    },

    // 获取类型文本
    getTypeText(type) {
        const typeMap = {
            'IMAGE': '图片',
            'TEXT': '文字',
            'VIDEO': '视频',
            'HTML': 'HTML'
        };
        return typeMap[type] || type;
    },

    // 加载单个广告数据
    async loadAd(id) {
        try {
            const result = await api.get('/api/ads/' + id);
            if (result.success) {
                return result.data;
            }
            throw new Error('加载广告失败');
        } catch (error) {
            console.error('加载广告失败:', error);
            utils.showMessage('加载广告失败: ' + error.message);
            return null;
        }
    },

    // 保存广告（创建或更新）
    async saveAd(adData) {
        try {
            let result;
            if (adData.id) {
                // 更新广告
                result = await api.put('/api/ads/' + adData.id, adData);
            } else {
                // 创建广告
                result = await api.post('/api/ads', adData);
            }

            if (result.success) {
                utils.showMessage(adData.id ? '广告更新成功' : '广告创建成功', 'success');
                setTimeout(() => {
                    window.location.href = 'ad-list.html';
                }, 1500);
            } else {
                throw new Error(result.message);
            }
        } catch (error) {
            console.error('保存广告失败:', error);
            utils.showMessage('保存失败: ' + error.message);
        }
    },

    // 删除广告
    async deleteAd(id) {
        if (!confirm('确定要删除这个广告吗？此操作不可恢复。')) {
            return;
        }

        try {
            const result = await api.delete('/api/ads/' + id);
            if (result.success) {
                utils.showMessage('广告删除成功', 'success');
                // 重新加载列表
                setTimeout(() => {
                    loadAds();
                }, 1000);
            } else {
                throw new Error(result.message);
            }
        } catch (error) {
            console.error('删除广告失败:', error);
            utils.showMessage('删除失败: ' + error.message);
        }
    },

    // 更改广告状态
    async changeAdStatus(id, newStatus) {
        const action = newStatus === 1 ? '上线' : '下线';
        if (!confirm(`确定要${action}这个广告吗？`)) {
            return;
        }

        try {
            const result = await api.put('/api/ads/' + id + '/status?status=' + newStatus);
            if (result.success) {
                utils.showMessage(`广告${action}成功`, 'success');
                // 重新加载列表
                setTimeout(() => {
                    loadAds();
                }, 1000);
            } else {
                throw new Error(result.message);
            }
        } catch (error) {
            console.error('更改广告状态失败:', error);
            utils.showMessage(`${action}失败: ` + error.message);
        }
    }
};

// 全局函数，供HTML调用
function loadAds() {
    return adManager.loadAds();
}

function editAd(id) {
    window.location.href = 'ad-form.html?id=' + id;
}

function deleteAd(id) {
    adManager.deleteAd(id);
}

function changeAdStatus(id, newStatus) {
    adManager.changeAdStatus(id, newStatus);
}

// 表单设置
function setupForm() {
    const form = document.getElementById('adForm');
    const urlParams = new URLSearchParams(window.location.search);
    const adId = urlParams.get('id');

    if (adId) {
        // 编辑模式
        document.getElementById('formTitle').textContent = '编辑广告';
        loadAdData(adId);
    }

    if (form) {
        form.addEventListener('submit', function(e) {
            e.preventDefault();
            saveAdForm();
        });
    }
}

// 加载广告数据到表单
async function loadAdData(id) {
    const ad = await adManager.loadAd(id);
    if (ad) {
        document.getElementById('adId').value = ad.id;
        document.getElementById('title').value = ad.title || '';
        document.getElementById('type').value = ad.type || '';
        document.getElementById('adSpaceId').value = ad.adSpaceId || '';
        document.getElementById('priority').value = ad.priority || 1;
        document.getElementById('imageUrl').value = ad.imageUrl || '';
        document.getElementById('linkUrl').value = ad.linkUrl || '';
        document.getElementById('content').value = ad.content || '';
        document.getElementById('maxImpressions').value = ad.maxImpressions || '';

        // 处理时间格式
        if (ad.startTime) {
            const startTime = new Date(ad.startTime);
            document.getElementById('startTime').value = startTime.toISOString().slice(0, 16);
        }
        if (ad.endTime) {
            const endTime = new Date(ad.endTime);
            document.getElementById('endTime').value = endTime.toISOString().slice(0, 16);
        }
    }
}

// 保存表单数据
async function saveAdForm() {
    const formData = {
        title: document.getElementById('title').value,
        type: document.getElementById('type').value,
        adSpaceId: parseInt(document.getElementById('adSpaceId').value),
        priority: parseInt(document.getElementById('priority').value) || 1,
        imageUrl: document.getElementById('imageUrl').value,
        linkUrl: document.getElementById('linkUrl').value,
        content: document.getElementById('content').value,
        maxImpressions: document.getElementById('maxImpressions').value ?
            parseInt(document.getElementById('maxImpressions').value) : null
    };

    const adId = document.getElementById('adId').value;
    if (adId) {
        formData.id = parseInt(adId);
    }

    // 处理时间
    const startTime = document.getElementById('startTime').value;
    const endTime = document.getElementById('endTime').value;
    if (startTime) formData.startTime = startTime + ':00';
    if (endTime) formData.endTime = endTime + ':00';

    await adManager.saveAd(formData);
}