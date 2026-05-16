# Đặc tả Kỹ thuật - Hệ Thống Quản Lý Đào Tạo

---

## 1.3. Giao diện người dùng Frontend với Bootstrap 5

Nhằm mang lại trải nghiệm tương tác (UX) trực quan và thân thiện cho người dùng, hệ thống sử dụng Bootstrap 5 làm nền tảng giao diện với thiết kế theo phong cách cổ điển (classic vintage style). Giao diện được xây dựng trên nền Bootstrap 5, cung cấp sẵn các thành phần:

- Bootstrap Icons: Bộ icon phong cách hiện đại cho điều hướng và thao tác.
- Modal: Cửa sổ pop-up cho các thao tác thêm, sửa, xóa dữ liệu.
- Form nhập liệu: Các trường nhập liệu được thiết kế chuẩn responsive, tương thích thiết bị di động.
- DataTables: Tích hợp jQuery DataTables cho bảng dữ liệu có khả năng phân trang, sắp xếp, tìm kiếm.
- Sidebar & Navbar: Thanh điều hướng linh hoạt, dễ dàng tùy biến theo quyền người dùng.
- Phong cách thiết kế: Giao diện theo phong cách cổ điển với tông màu xanh rêu (vintage green) và màu nâu đất, tạo cảm giác chuyên nghiệp, ổn định.

---

## 1.4. Cơ sở dữ liệu với Microsoft SQL Server

Dữ liệu điểm thi đòi hỏi tính ràng buộc cực kỳ chặt chẽ. Microsoft SQL Server được lựa chọn nhờ khả năng xử lý giao dịch (ACID) tuyệt vời, đảm bảo hiệu năng xử lý tốt ngay cả khi hàng loạt giảng viên cùng truy cập nhập điểm cuối kỳ.

---

## 2. Mục tiêu nghiên cứu

### 2.1. Mục tiêu tổng quát

Xây dựng và phát triển hệ thống quản lý đào tạo tự động hóa quy trình quản lý học vụ, quản lý giảng dạy và quản lý kỳ thi, nâng cao hiệu quả công tác quản lý và chất lượng đào tạo tại các trường đại học.

### 2.2. Mục tiêu cụ thể

- Thiết kế và xây dựng cơ sở dữ liệu quản lý đào tạo trên Microsoft SQL Server.
- Phát triển hệ thống Backend sử dụng Spring Boot Web API.
- Xây dựng giao diện người dùng thân thiện, responsive với Bootstrap 5.
- Triển khai module quản lý đào tạo: Quản lý học kỳ, Quản lý môn học, Lớp học phần, Phân công giảng dạy, Quản lý sinh viên, Quản lý giảng viên, Lớp sinh viên, Sinh viên theo lớp, Phân công cố vấn.
- Triển khai module quản lý thi cử: Quản lý loại kỳ thi, Quản lý đề thi, Quản lý lịch thi, Quản lý phòng thi, Quản lý đăng ký thi, Quản lý kết quả thi.
- Triển khai module quản lý danh mục: Niên học, Khoa, Ngành, Chương trình đào tạo.
- Triển khai hệ thống phân quyền người dùng: Quản lý người dùng, Quản lý vai trò, Quản lý quyền, Gán vai trò cho người dùng, Gán quyền cho vai trò.
- Đảm bảo tính bảo mật và toàn vẹn dữ liệu.

---

## 3. Phạm vi và đối tượng

### 3.1. Phạm vi nghiên cứu

- Quản lý học vụ: Quản lý học kỳ, môn học, lớp học phần, phân công giảng dạy.
- Quản lý sinh viên: Thông tin sinh viên, lớp sinh viên, sinh viên theo lớp, phân công cố vấn.
- Quản lý giảng viên: Thông tin giảng viên.
- Quản lý thi cử: Loại kỳ thi, đề thi, lịch thi, phòng thi, đăng ký thi, kết quả thi.
- Quản lý danh mục: Niên học, Khoa, Ngành, Chương trình đào tạo.
- Phân quyền người dùng: Người dùng, vai trò, quyền.

### 3.2. Đối tượng sử dụng

- Quản trị viên (Admin): Quản lý toàn bộ hệ thống, phân quyền người dùng.
- Giảng viên (Teacher): Quản lý điểm danh, điểm thi, quản lý đề thi.
- Sinh viên (Student): Xem lịch thi, đăng ký thi, xem kết quả thi.

---

# CHƯƠNG 3: XÂY DỰNG VÀ TRIỂN KHAI HỆ THỐNG

## 3.1. Các module chức năng chính

Hệ thống được xây dựng với 4 nhóm module chính:

### Module Quản lý đào tạo

- Quản lý học kỳ, môn học, lớp học phần, phân công giảng dạy.
- Quản lý sinh viên, giảng viên, lớp sinh viên.
- Sinh viên theo lớp, phân công cố vấn.

### Module Quản lý thi cử

- Quản lý loại kỳ thi, đề thi, lịch thi, phòng thi.
- Quản lý đăng ký thi, kết quả thi.

### Module Quản lý danh mục

- Niên học, khoa, ngành, chương trình đào tạo.

### Module Quản lý hệ thống

- Quản l�� người dùng, vai trò, quyền.
- Gán vai trò cho người dùng, gán quyền cho vai trò.

## 3.2. Thiết kế các API RESTful chính

### Authentication API

| Phương thức | Endpoint | Mô tả |
|------------|----------|-------|
| POST | /api/auth/login | Đăng nhập hệ thống |
| POST | /api/auth/logout | Đăng xuất hệ thống |

### User Management API

| Phương thức | Endpoint | Mô tả |
|------------|----------|-------|
| GET | /api/users | Lấy danh sách người dùng |
| GET | /api/users/{id} | Lấy thông tin người dùng theo ID |
| POST | /api/users | Tạo mới người dùng |
| PUT | /api/users/{id} | Cập nhật người dùng |
| DELETE | /api/users/{id} | Xóa người dùng |

### Role Management API

| Phương thức | Endpoint | Mô tả |
|------------|----------|-------|
| GET | /api/roles | Lấy danh sách vai trò |
| GET | /api/roles/{id} | Lấy thông tin vai trò theo ID |
| POST | /api/roles | Tạo mới vai trò |
| PUT | /api/roles/{id} | Cập nhật vai trò |
| DELETE | /api/roles/{id} | Xóa vai trò |

### Permission Management API

| Phương thức | Endpoint | Mô tả |
|------------|----------|-------|
| GET | /api/permissions | Lấy danh sách quyền |
| POST | /api/permissions | Tạo mới quyền |
| DELETE | /api/permissions/{id} | Xóa quyền |

### Student Management API

| Phương thức | Endpoint | Mô tả |
|------------|----------|-------|
| GET | /api/students | Lấy danh sách sinh viên |
| GET | /api/students/{id} | Lấy thông tin sinh viên theo ID |
| POST | /api/students | Tạo mới sinh viên |
| PUT | /api/students/{id} | Cập nhật sinh viên |
| DELETE | /api/students/{id} | Xóa sinh viên |

### Teacher Management API

| Phương thức | Endpoint | Mô tả |
|------------|----------|-------|
| GET | /api/teachers | Lấy danh sách giảng viên |
| GET | /api/teachers/{id} | Lấy thông tin giảng viên theo ID |
| POST | /api/teachers | Tạo mới giảng viên |
| PUT | /api/teachers/{id} | Cập nhật giảng viên |
| DELETE | /api/teachers/{id} | Xóa giảng viên |

### Subject Management API

| Phương thức | Endpoint | Mô tả |
|------------|----------|-------|
| GET | /api/subjects | Lấy danh sách môn học |
| GET | /api/subjects/{id} | Lấy thông tin môn học theo ID |
| POST | /api/subjects | Tạo mới môn học |
| PUT | /api/subjects/{id} | Cập nhật môn học |
| DELETE | /api/subjects/{id} | Xóa môn học |

### Course Class API

| Phương thức | Endpoint | Mô tả |
|------------|----------|-------|
| GET | /api/course-classes | Lấy danh sách lớp học phần |
| GET | /api/course-classes/{id} | Lấy thông tin lớp học phần |
| POST | /api/course-classes | Tạo mới lớp học phần |
| PUT | /api/course-classes/{id} | Cập nhật lớp học phần |
| DELETE | /api/course-classes/{id} | Xóa lớp học phần |

### Exam Management API

| Phương thức | Endpoint | Mô tả |
|------------|----------|-------|
| GET | /api/exams | Lấy danh sách lịch thi |
| GET | /api/exams/{id} | Lấy thông tin lịch thi |
| POST | /api/exams | Tạo mới lịch thi |
| PUT | /api/exams/{id} | Cập nhật lịch thi |
| DELETE | /api/exams/{id} | Xóa lịch thi |

### Exam Result API

| Phương thức | Endpoint | Mô tả |
|------------|----------|-------|
| GET | /api/exam-results | Lấy danh sách kết quả thi |
| GET | /api/exam-results/{id} | Lấy kết quả thi theo ID |
| POST | /api/exam-results | Tạo mới kết quả thi |
| PUT | /api/exam-results/{id} | Cập nhật kết quả thi |
| DELETE | /api/exam-results/{id} | Xóa kết quả thi |