// Admin Pagination Utility
const AdminPagination = {
    paginate: function(items, currentPage, pageSize) {
        if (!Array.isArray(items) || items.length === 0) return [];
        const start = (currentPage - 1) * pageSize;
        const end = start + pageSize;
        return items.slice(start, end);
    },
    
    render: function(containerId, totalItems, currentPage, pageSize, onPageChange) {
        const container = document.getElementById(containerId);
        if (!container) {
            console.warn("Container not found:", containerId);
            return;
        }
        
        const totalPages = Math.ceil(totalItems / pageSize);
        if (totalPages <= 1) {
            container.innerHTML = '';
            return;
        }
        
        let html = '<ul class="pagination pagination-sm m-0">';
        for (let i = 1; i <= totalPages; i++) {
            const active = i === currentPage ? 'active' : '';
            html += `<li class="page-item ${active}">
                <button class="page-link" onclick="window.AdminPagination.goToPage(${i}, ${pageSize}, ${totalItems}, '${containerId}', ${onPageChange.toString()})">${i}</button>
            </li>`;
        }
        html += '</ul>';
        container.innerHTML = html;
    },
    
    goToPage: function(page, pageSize, totalItems, containerId, callback) {
        if (callback) {
            callback(page);
        }
    }
};

// Make it globally available
window.AdminPagination = AdminPagination;